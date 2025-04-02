import * as Cesium from "cesium";

export function getGeometry(dis, fov, aspect, basePosition) {
  if (!basePosition) {
    throw new Error("basePosition is required");
  }

  let positions = new Float64Array(5 * 3);
  fov = Cesium.Math.toRadians(fov / 2);
  const tanfov = Math.tan(fov);
  const halfw = tanfov * dis;
  const halfh = halfw / aspect;

  // 点0 坐标 (基准点)
  positions[0] = basePosition.x;
  positions[1] = basePosition.y;
  positions[2] = basePosition.z;

  // 点1 坐标
  positions[3] = basePosition.x + 1.0 * halfw;
  positions[4] = basePosition.y + 1.0 * dis;
  positions[5] = basePosition.z + 1.0 * halfh;

  // 点2 坐标
  positions[6] = basePosition.x - 1.0 * halfw;
  positions[7] = basePosition.y + 1.0 * dis;
  positions[8] = basePosition.z + 1.0 * halfh;

  // 点3 坐标
  positions[9] = basePosition.x - 1.0 * halfw;
  positions[10] = basePosition.y + 1.0 * dis;
  positions[11] = basePosition.z - 1.0 * halfh;

  // 点4 坐标
  positions[12] = basePosition.x + 1.0 * halfw;
  positions[13] = basePosition.y + 1.0 * dis;
  positions[14] = basePosition.z - 1.0 * halfh;

  // 创建顶点属性中的坐标
  const attributes = new Cesium.GeometryAttributes({
    position: new Cesium.GeometryAttribute({
      componentDatatype: Cesium.ComponentDatatype.DOUBLE,
      componentsPerAttribute: 3,
      values: positions,
    }),
  });

  // 创建线框几何体
  const lineIndices = new Uint16Array(18);
  lineIndices[0] = 0;
  lineIndices[1] = 4;
  lineIndices[2] = 0;
  lineIndices[3] = 1;
  lineIndices[4] = 0;
  lineIndices[5] = 2;
  lineIndices[6] = 0;
  lineIndices[7] = 3;
  lineIndices[8] = 1;
  lineIndices[9] = 4;
  lineIndices[10] = 4;
  lineIndices[11] = 1;
  lineIndices[12] = 1;
  lineIndices[13] = 2;
  lineIndices[14] = 2;
  lineIndices[15] = 3;
  lineIndices[16] = 3;
  lineIndices[17] = 4;

  const lineGeometry = new Cesium.Geometry({
    attributes: attributes,
    indices: lineIndices,
    primitiveType: Cesium.PrimitiveType.LINES,
    boundingSphere: Cesium.BoundingSphere.fromVertices(positions),
  });

  // 创建底面几何体
  const bottomIndices = new Uint16Array(6);
  bottomIndices[0] = 1;
  bottomIndices[1] = 2;
  bottomIndices[2] = 3;
  bottomIndices[3] = 1;
  bottomIndices[4] = 3;
  bottomIndices[5] = 4;

  const bottomGeometry = new Cesium.Geometry({
    attributes: attributes,
    indices: bottomIndices,
    primitiveType: Cesium.PrimitiveType.TRIANGLES,
    boundingSphere: Cesium.BoundingSphere.fromVertices(positions),
  });

  return {
    lineGeometry,
    bottomGeometry,
  };
}

export function createProperty(positions, times, viewer) {
  times = times || 120;
  let distance = 0;
  let speed;

  for (let i = 0; i < positions.length - 1; i++) {
    const oneP = positions[i];
    const secP = positions[i + 1];
    const dis = Cesium.Cartesian3.distance(oneP, secP);
    distance += dis;
  }

  speed = distance / times;
  const startTime = viewer.clock.currentTime;
  const endTime = Cesium.JulianDate.addSeconds(
    startTime,
    times,
    new Cesium.JulianDate()
  );

  const property = new Cesium.SampledPositionProperty();
  const hprProperty = new Cesium.SampledProperty(Cesium.Quaternion);

  let oldTimes = 0;
  for (let i = 1; i < positions.length; i++) {
    const nowP = positions[i];
    const lastP = positions[i - 1];
    const headingPitchRoll = new Cesium.HeadingPitchRoll(0, 0, 0); // 假设姿态为默认值

    if (i === 1) {
      property.addSample(startTime, positions[0]);
      const firstQua = Cesium.Transforms.headingPitchRollQuaternion(
        positions[0],
        headingPitchRoll
      );
      hprProperty.addSample(startTime, firstQua);
    }

    oldTimes += Cesium.Cartesian3.distance(nowP, lastP) / speed;
    const nowTime = Cesium.JulianDate.addSeconds(
      startTime,
      oldTimes,
      new Cesium.JulianDate()
    );
    property.addSample(nowTime, nowP);
    const qua = Cesium.Transforms.headingPitchRollQuaternion(
      nowP,
      headingPitchRoll
    );
    hprProperty.addSample(nowTime, qua);
  }

  return { property, hprProperty };
}
