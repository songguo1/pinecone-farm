import * as Cesium from "cesium";

/**
 * 用于添加poi的icon和label的函数
 * @param {*} lon ：经度
 * @param {*} lat ：纬度
 * @param {*} name ：标签内容
 * @param {*} color ：底部圆和横线的颜色
 * @param {*} url ：icon地址
 * @param {*} viewer ：Cesium viewer实例
 */
function poiIconLabelAdd(lon, lat, name, color, url, viewer) {
  // 添加图标
  viewer.entities.add({
    name: name,
    position: Cesium.Cartesian3.fromDegrees(lon, lat, 300),
    // 图标
    billboard: {
      image: url,
      width: 40,
      height: 40,
      verticalOrigin: Cesium.VerticalOrigin.BOTTOM,
    },
    // 标签放在图标下方
    label: {
      //文字标签
      text: name,
      font: "bold 12px Microsoft YaHei", // 微软雅黑字体，加粗
      style: Cesium.LabelStyle.FILL_AND_OUTLINE, // 填充和轮廓样式
      fillColor: Cesium.Color.fromCssColorString("#FFFFFF"), // 白色文字
      outlineColor: Cesium.Color.fromCssColorString("#000000"), // 黑色轮廓
      outlineWidth: 2, // 轮廓宽度
      // 对齐方式(水平和竖直)
      horizontalOrigin: Cesium.HorizontalOrigin.CENTER, // 水平居中
      verticalOrigin: Cesium.VerticalOrigin.TOP, // 放在图标下方
      pixelOffset: new Cesium.Cartesian2(0, 5), // 设置偏移量，让文字位于图标下方
      showBackground: true,
      backgroundColor: Cesium.Color.fromCssColorString("rgba(0, 82, 121, 0.8)"), // 半透明蓝色背景
      backgroundPadding: new Cesium.Cartesian2(7, 5), // 背景内边距
      disableDepthTestDistance: Number.POSITIVE_INFINITY, // 确保标签始终可见
    },
  });

  // 先画线后画点，防止线压盖点
  let linePositions = [];
  linePositions.push(new Cesium.Cartesian3.fromDegrees(lon, lat, 5));
  linePositions.push(new Cesium.Cartesian3.fromDegrees(lon, lat, 300));
  viewer.entities.add({
    polyline: {
      positions: linePositions,
      width: 1.5,
      material: color,
    },
  });

  // 画点
  viewer.entities.add({
    // 给初始点位设置一定的离地高度，否者会被压盖
    position: Cesium.Cartesian3.fromDegrees(lon, lat, 5),
    point: {
      color: color,
      pixelSize: 15,
    },
  });
}

export { poiIconLabelAdd };
