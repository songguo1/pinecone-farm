import { getAllTasks } from "@/api/drones/task";
import { wktToGeoJSON } from "@/utils/geoFormat";
import { createProperty, getGeometry } from "./property";
import * as Cesium from "cesium";

const dronesPathList = ref([]);

// 计算两点之间的距离（单位：米）
const calculateDistance = (point1, point2) => {
  const cartesian1 = Cesium.Cartesian3.fromDegrees(point1[0], point1[1], 100);
  const cartesian2 = Cesium.Cartesian3.fromDegrees(point2[0], point2[1], 100);
  return Cesium.Cartesian3.distance(cartesian1, cartesian2);
};

export const loadDrones = async (viewer) => {
  const dronesList = await getAllTasks();
  dronesPathList.value = dronesList.rows.map((item) => {
    return wktToGeoJSON(item.dronesPath);
  });
  // console.log(dronesPathList.value[0].coordinates);

  // 创建视锥体几何体
  const fov = 50; // 视场角
  const aspect = 16 / 9; // 宽高比
  const dis = 500; // 视锥体深度

  // 创建视锥体的材质
  const frustumAppearance = new Cesium.PerInstanceColorAppearance({
    flat: true,
    translucent: true,
  });

  const startTime = Cesium.JulianDate.fromDate(new Date(2024, 5, 20, 17));

  // 创建动态路径属性
  // const { property, hprProperty } = createProperty(positions, 120,viewer);

  // 设定飞行速度（米/秒）
  const SPEED = 20;

  // 计算每条路径的总时间
  const pathTimes = dronesPathList.value.map((path) => {
    let totalDistance = 0;
    const coordinates = path.coordinates;
    for (let i = 0; i < coordinates.length - 1; i++) {
      totalDistance += calculateDistance(coordinates[i], coordinates[i + 1]);
    }
    return totalDistance / SPEED; // 总时间 = 总距离/速度
  });

  // 获取最长的飞行时间
  const maxTime = Math.max(...pathTimes);
  const stopTime = Cesium.JulianDate.addSeconds(
    startTime,
    maxTime,
    new Cesium.JulianDate()
  );
  viewer.clock.startTime = startTime.clone();
  viewer.clock.stopTime = stopTime.clone();
  viewer.clock.multiplier = 5;
  viewer.clock.clockRange = Cesium.ClockRange.LOOP_STOP;
  //将视图设置为提供的时间
  viewer.timeline.zoomTo(startTime, stopTime);
  //点位数组
  for (let i = 0; i < dronesPathList.value.length; i++) {
    const dataPoint = dronesPathList.value[i].coordinates;
    // console.log(dataPoint);
    const altitude = dronesPathList.value[i].altitude;

    let positionProperty = new Cesium.SampledPositionProperty();
    for (let j = 0; j < dataPoint.length; j++) {
      // 计算从起点到当前点的累计距离
      let accumulatedDistance = 0;
      for (let k = 0; k < j; k++) {
        accumulatedDistance += calculateDistance(
          dataPoint[k],
          dataPoint[k + 1]
        );
      }
      // 根据距离和速度计算时间
      const timeInSeconds = accumulatedDistance / SPEED;
      const time = Cesium.JulianDate.addSeconds(
        startTime,
        timeInSeconds,
        new Cesium.JulianDate()
      );
      const position = Cesium.Cartesian3.fromDegrees(
        dataPoint[j][0],
        dataPoint[j][1],
        100
      );
      positionProperty.addSample(time, position);
      viewer.entities.add({
        position: position,
        point: {
          color: Cesium.Color.TRANSPARENT,
          pixelSize: 8,
          outlineColor: Cesium.Color.ORANGE,
          outlineWidth: 3,
        },
      });
    }
    let entity = ref(null);
    entity.value = viewer.entities.add({
      id: i,
      name: "王牌战斗机",
      availability: new Cesium.TimeIntervalCollection([
        new Cesium.TimeInterval({
          start: startTime,
          stop: stopTime,
        }),
      ]),
      orientation: new Cesium.VelocityOrientationProperty(positionProperty),
      position: positionProperty,
      model: {
        scale: 1,
        uri: "/static/models/drone1.glb",
        minimumPixelSize: 50,
      },
      path: {
        resolution: 1,
        width: 15,
        material: new Cesium.PolylineGlowMaterialProperty({
          glowPower: 0.1,
          color: Cesium.Color.ORANGE,
        }),
      },
    });

    // 使用路径的第一个点作为初始位置
    const initialPosition = Cesium.Cartesian3.fromDegrees(
      dataPoint[0][0],
      dataPoint[0][1],
      100
    );

    // 创建视锥体实体
    const geometries = getGeometry(
      dis,
      fov,
      aspect,
      new Cesium.Cartesian3(0, 0, 0)
    );

    // 创建线框部分
    let linePrimitive = viewer.scene.primitives.add(
      new Cesium.Primitive({
        geometryInstances: new Cesium.GeometryInstance({
          geometry: geometries.lineGeometry,
          attributes: {
            color: Cesium.ColorGeometryInstanceAttribute.fromColor(
              new Cesium.Color(1.0, 0.0, 0.0, 0.7)
            ),
          },
        }),
        appearance: new Cesium.PerInstanceColorAppearance({
          flat: true,
          translucent: true,
          renderState: {
            depthTest: { enabled: true },
            depthMask: false,
            blending: Cesium.BlendingState.ALPHA_BLEND,
          },
        }),
        asynchronous: false,
      })
    );

    // 创建底面部分
    let bottomPrimitive = viewer.scene.primitives.add(
      new Cesium.Primitive({
        geometryInstances: new Cesium.GeometryInstance({
          geometry: geometries.bottomGeometry,
          attributes: {
            color: Cesium.ColorGeometryInstanceAttribute.fromColor(
              new Cesium.Color(1,1, 1, 0.3)
            ),
          },
        }),
        appearance: new Cesium.PerInstanceColorAppearance({
          flat: true,
          translucent: true,
          renderState: {
            depthTest: { enabled: true },
            depthMask: false,
            blending: Cesium.BlendingState.ALPHA_BLEND,
          },
        }),
        asynchronous: false,
      })
    );

    // 设置初始位置
    const initialModelMatrix = Cesium.Matrix4.fromTranslation(initialPosition);
    linePrimitive.modelMatrix = initialModelMatrix;
    bottomPrimitive.modelMatrix = initialModelMatrix;

    // 更新视锥体位置和方向
    viewer.scene.preUpdate.addEventListener(() => {
      const position = entity.value.position.getValue(viewer.clock.currentTime);
      const orientation = entity.value.orientation.getValue(
        viewer.clock.currentTime
      );

      if (position && orientation) {
        const modelMatrix = Cesium.Matrix4.fromRotationTranslation(
          Cesium.Matrix3.fromQuaternion(orientation),
          position
        );
        linePrimitive.modelMatrix = modelMatrix;
        bottomPrimitive.modelMatrix = modelMatrix;
      }
    });
  }
};
