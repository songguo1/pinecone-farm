import { ref } from "vue";
import VectorLayer from "ol/layer/Vector";
import VectorSource from "ol/source/Vector";
import { Style, Stroke, Fill, Circle } from "ol/style";
import Draw from "ol/interaction/Draw";
import WKT from "ol/format/WKT";

export function useDrawing(map) {
  const draw = ref(null);
  const drawSource = ref(null);
  const drawLayer = ref(null);
  const isDrawing = ref(false);
  const wktFormat = new WKT();

  const addPointInteraction = (emit) => {
    console.log("开始点位绘制");
    if (isDrawing.value) {
      console.log("已经在绘制中，忽略请求");
      return;
    }

    try {
      drawSource.value = new VectorSource();
      drawLayer.value = new VectorLayer({
        source: drawSource.value,
        style: new Style({
          image: new Circle({
            radius: 7,
            fill: new Fill({
              color: "rgba(255, 0, 0, 0.8)",
            }),
            stroke: new Stroke({
              color: "#ffffff",
              width: 2,
            }),
          }),
        }),
        zIndex: 999, // 确保在其他图层上方
      });

      if (!map.value) {
        console.error("地图实例不存在，无法添加绘制功能");
        return;
      }

      console.log("添加绘制图层到地图");
      map.value.addLayer(drawLayer.value);

      draw.value = new Draw({
        source: drawSource.value,
        type: "Point",
      });

      console.log("添加绘制交互到地图");
      map.value.addInteraction(draw.value);

      draw.value.on("drawstart", () => {
        console.log("开始绘制点位");
      });

      draw.value.on("drawend", (event) => {
        try {
          const geometry = event.feature.getGeometry();
          console.log("绘制完成，坐标:", geometry.getCoordinates());

          // 转换为WKT格式
          const transformedGeom = geometry
            .clone()
            .transform("EPSG:3857", "EPSG:4326");
          const wkt = wktFormat.writeGeometry(transformedGeom);

          console.log("生成的WKT:", wkt);
          emit("getWkt", wkt);
        } catch (error) {
          console.error("处理绘制结果时出错:", error);
        }
      });

      isDrawing.value = true;
      console.log("点位绘制功能启用成功");
    } catch (error) {
      console.error("启用点位绘制功能时出错:", error);
    }
  };

  // 添加线绘制功能
  const addLineInteraction = (emit) => {
    console.log("添加线绘制功能");
    if (isDrawing.value) return;

    // 创建绘制图层
    drawSource.value = new VectorSource();
    drawLayer.value = new VectorLayer({
      source: drawSource.value,
      style: new Style({
        stroke: new Stroke({
          color: "#0000ff",
          width: 2,
        }),
      }),
    });

    // 添加绘制图层到地图
    if (map.value) {
      map.value.addLayer(drawLayer.value);

      // 创建绘制交互
      draw.value = new Draw({
        source: drawSource.value,
        type: "LineString",
      });

      // 添加绘制交互到地图
      map.value.addInteraction(draw.value);

      // 监听绘制完成事件
      draw.value.on("drawend", (event) => {
        const feature = event.feature;
        const geometry = feature.getGeometry();
        console.log("绘制完成:", geometry);

        // 如果需要获取WKT格式的数据
        const wkt = wktFormat.writeGeometry(
          geometry.clone().transform("EPSG:3857", "EPSG:4326")
        );

        emit("getWkt", wkt);
      });

      isDrawing.value = true;
    }
  };

  const stopDrawing = () => {
    if (map.value && draw.value) {
      map.value.removeInteraction(draw.value);
      draw.value = null;
      isDrawing.value = false;
    }
  };

  const clearDrawing = () => {
    if (drawSource.value) {
      drawSource.value.clear();
    }
  };

  return {
    isDrawing,
    drawSource,
    addPointInteraction,
    addLineInteraction,
    stopDrawing,
    clearDrawing,
  };
}
