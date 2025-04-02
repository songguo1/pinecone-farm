import * as Cesium from "cesium";

export const initViewer = async () => {
  const viewer = new Cesium.Viewer("cesiumContainer", {
    geocoder: false,
    homeButton: false,
    sceneModePicker: false,
    baseLayerPicker: false,
    navigationHelpButton: false,
    animation: false,
    timeline: true,
    fullscreenButton: false,
    vrButton: false,
    infoBox: false,
    //如果不设置添加的模型实体动画不显示
    shouldAnimate: true,//控制视图是否在动画中，用于启用或禁用视图动画
    // terrainProvider: await Cesium.createWorldTerrainAsync({
    //   requestWaterMask: true,
    // }),
  });
  (viewer.cesiumWidget.creditContainer as HTMLElement).style.display = "none";
  // 是否支持图像渲染像素化处理


  viewer.resolutionScale = window.devicePixelRatio;

  // 开启抗锯齿
  (viewer.scene as any).fxaa = true;
  viewer.scene.postProcessStages.fxaa.enabled = true;
  return viewer;
};
