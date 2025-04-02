import * as Cesium from "cesium";

export const loadModel = async (viewer: Cesium.Viewer) => {
  const position = Cesium.Cartesian3.fromDegrees(	113.53161008245179,22.73858108723544, 0);
  const modelMatrix = Cesium.Transforms.eastNorthUpToFixedFrame(position);
  
  // 创建旋转矩阵
  const rotation = Cesium.Matrix3.fromRotationZ(Cesium.Math.toRadians(-91));
  const rotationMatrix = Cesium.Matrix4.fromRotationTranslation(rotation);
  
  // 将旋转矩阵应用到模型矩阵
  const finalMatrix = Cesium.Matrix4.multiply(modelMatrix, rotationMatrix, new Cesium.Matrix4());

  try {
    const model = await Cesium.Model.fromGltfAsync({
      url: "/static/models/scene.glb",
      modelMatrix: finalMatrix,  // 使用最终的变换矩阵
      scale: 1,
      minimumPixelSize: 128,
      maximumScale: 20000,
    });

    viewer.scene.primitives.add(model);
    console.log("模型加载成功");
    
    flyToModel(viewer);
  } catch (error) {
    console.error("模型加载出错:", error);
  }
};

export const flyToModel = (viewer: Cesium.Viewer) => {
  viewer.camera.setView({
    destination: Cesium.Cartesian3.fromDegrees(		113.53251378179486,22.70837907250421, 3500),
    // destination: Cesium.Cartesian3.fromDegrees(-122.567, 37.843, 1000),
    orientation: {
      heading: Cesium.Math.toRadians(0),
      pitch: Cesium.Math.toRadians(-45),
      roll: 0.0,
    },
  });
}; 