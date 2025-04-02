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
  viewer.entities.add({
    name: name,
    position: Cesium.Cartesian3.fromDegrees(lon, lat, 300),
    // 图标
    billboard: {
      image: url,
      width: 50,
      height: 50,
    },
    label: {
      //文字标签
      text: name,
      font: "20px sans-serif",
      style: Cesium.LabelStyle.FILL,
      // 对齐方式(水平和竖直)
      horizontalOrigin: Cesium.HorizontalOrigin.LEFT,
      verticalOrigin: Cesium.VerticalOrigin.CENTER,
      pixelOffset: new Cesium.Cartesian2(20, -2),
      showBackground: true,
      backgroundColor: new Cesium.Color.fromBytes(0, 70, 24),
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
