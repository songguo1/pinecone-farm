import * as Cesium from "cesium";
let canvas, context;
/**
 * @author: linmaoxin
 * @date: 2024/12/14
 * @description 渲染billboard,可选择 带背景的文字+图标icon、仅背景的文字 两种类型。1、返回Promise对象，异步获取billboard；2、也可以使用回调分别获取image和pixelOffset。
 * @param {*} options
 * @callback function
 * @returns Promise<object>
 * @example
 * let options = {
        type: 'text-icon',  // 默认 'text-icon'类型(图标+文字)、 'text' 类型(仅文字)
        paddingTo: 20, // 图标距离文字背景
        textPadding: 20, // 文字左右间距
        textBcgWidth: 200, // 文字背景宽度
        textBcgHeight: 50, // 文字背景高度
        iconWidth: 60, // 图标宽度
        iconHeight: 70, // 图标高度
        textBcgUrl: '../assets/label-bcg.png', // 文字背景图片地址 
        iconUrl: '../assets/positionIcon.png', // 图标地址
        textUrl:'', // 字体地址
        text: '测试文字测试文字', // 文字内容
        textColor: '#DCD085', // 字体颜色
        textFontSize: 16, // 字体大小
        textFontWeight: 800, // 字体粗细
    } 
 */
export function renderBillboard(options, callback) {
  let settings = {
    type: options.type || "text-icon",
    paddingTo: options.paddingTo || 5,
    textPadding: options.textPadding || 20,
    textBcgWidth: options.textBcgWidth || 150,
    textBcgHeight: options.textBcgHeight || 40,
    iconWidth: options.iconWidth || 40,
    iconHeight: options.iconHeight || 40,
    lineWidth: options.lineWidth || 20,
    lineHeight: options.lineHeight || 200,
    textBcgUrl:
      options.textBcgUrl || console.error("请传入文字背景图片地址 textBcgUrl"),
    iconUrl: options.iconUrl || console.error("请传入图标图片地址 iconUrl"),
    // verticalLineUrl: options.verticalLineUrl || "../assets/vertical-line.png",
    textUrl: options.textUrl,
    text: options.text || "",
    textColor: options.textColor || "#ffffff",
    textFontSize: options.textFontSize || 16,
    textFontWeight: options.textFontWeight || 500,
    position: options.position || null,
    viewer: options.viewer || null,
  };

  if (!canvas) {
    canvas = document.createElement("canvas");
  }
  if (!context) {
    context = canvas.getContext("2d", { willReadFrequently: true });
  }
  // 字体初始化
  context.font = `${Number(settings.textFontWeight)} ${Number(
    settings.textFontSize
  )}px Microsoft Yahei`;
  context.fillStyle = settings.textColor;
  context.textAlign = "center";
  context.textBaseline = "middle";

  context.canvas.willReadFrequently = true;
  // 根据字数计算宽度 并加上左右间距
  const textMetrics = context.measureText(settings.text);
  settings.textBcgWidth = textMetrics.width + settings.textPadding;
  // 画布大小
  if (settings.type === "text-icon") {
    settings.textBcgWidth =
      settings.textBcgWidth > settings.iconWidth
        ? settings.textBcgWidth
        : settings.iconWidth;
    canvas.width = settings.textBcgWidth;
    canvas.height =
      settings.textBcgHeight + // 文字背景高度
      settings.paddingTo + // 间距
      settings.iconHeight + // 图标高度
      settings.paddingTo + // 再加一个间距
      settings.lineHeight; // 垂直线高度
  } else if (settings.type === "text") {
    canvas.width = settings.textBcgWidth;
    canvas.height = settings.textBcgHeight;
  }
  return new Promise((result) => {
    // 加载图片资源
    if (settings.type === "text-icon") {
      // 字体二次设置
      context.font = `${Number(settings.textFontWeight)} ${Number(
        settings.textFontSize
      )}px Microsoft Yahei`;
      context.fillStyle = settings.textColor;
      context.textAlign = "center";
      context.textBaseline = "middle";
      const image1 = new Image();
      const image2 = new Image();
      const image3 = new Image();

      // 添加 crossOrigin 属性
      image1.crossOrigin = "anonymous";
      image2.crossOrigin = "anonymous";
      image3.crossOrigin = "anonymous";

      image1.src = new URL(settings.iconUrl, import.meta.url).href+ `?timestamp= ${Date.now()}`;
      image2.src = new URL(settings.textBcgUrl, import.meta.url).href+ `?timestamp= ${Date.now()}`;

      image1.onload = () => {
        context.drawImage(image1, 0, 0, canvas.width, canvas.height);
      };
      image2.onload = () => {
        context.drawImage(image2, 0, 0, canvas.width, canvas.height);
      };
      // image3.src = new URL(settings.verticalLineUrl, import.meta.url).href; // vite

      Promise.all([
        new Promise((resolve1) => {
          image1.onload = resolve1;
        }),
        new Promise((resolve2) => {
          image2.onload = resolve2;
        }),
        // new Promise((resolve3) => {
        //   image3.onload = resolve3;
        // }),
      ]).then(() => {
        // 清除画布
        context.clearRect(0, 0, canvas.width, canvas.height);
        context.beginPath();

        // 渲染文字背景
        context.drawImage(
          image2,
          0,
          0,
          settings.textBcgWidth,
          settings.textBcgHeight
        );

        // 渲染图标
        context.drawImage(
          image1,
          canvas.width / 2 - settings.iconWidth / 2,
          settings.textBcgHeight + settings.paddingTo,
          settings.iconWidth,
          settings.iconHeight
        );

        // // 渲染垂直线
        // context.drawImage(
        //   image3,
        //   canvas.width / 2 - settings.lineWidth / 2,
        //   settings.textBcgHeight +
        //     settings.paddingTo +
        //     settings.iconHeight +
        //     settings.paddingTo,
        //   settings.lineWidth,
        //   settings.lineHeight
        // );

        // 渲染文字
        context.fillText(
          settings.text,
          settings.textBcgWidth / 2,
          settings.textBcgHeight / 2
        );

        //  判断callback是不是函数
        if (typeof callback === "function") {
          callback({
            image: canvas.toDataURL(),
            pixelOffsetY: Math.round(-canvas.height / 2),
          });
        }
        const billboardResult = {
          image: canvas.toDataURL(),
          pixelOffset: new Cesium.Cartesian2(0, Math.round(-canvas.height / 2)),
        };
        if (settings.position && settings.viewer) {
          const { lon, lat, height } = settings.position;
          const longitude = Number(lon);
          const latitude = Number(lat);
          const altitude = Number(height);
          console.log(altitude);

          // 添加标签 billboard
          settings.viewer.entities.add({
            position: Cesium.Cartesian3.fromDegrees(
              longitude,
              latitude,
              altitude
            ),
            billboard: {
              image: billboardResult.image,
              horizontalOrigin: Cesium.HorizontalOrigin.CENTER,
              disableDepthTestDistance: Number.POSITIVE_INFINITY,
              scaleByDistance: new Cesium.NearFarScalar(1.5e2, 1.0, 8.0e6, 0.0),
              scale: 1.0,
              heightReference: Cesium.HeightReference.RELATIVE_TO_GROUND,
            },
          });
        }
        result(billboardResult);
      });
    } else if (settings.type === "text") {
      const image2 = new Image();
      // 添加 crossOrigin 属性
      image2.crossOrigin = "anonymous";
      image2.src = new URL(settings.textBcgUrl, import.meta.url).href;
      image2.onload = () => {
        // 清除画布
        context.clearRect(0, 0, canvas.width, canvas.height);
        context.beginPath();
        // 渲染
        context.drawImage(image2, 0, 0, canvas.width, canvas.height);
        context.fillText(
          settings.text,
          settings.textBcgWidth / 2,
          settings.textBcgHeight / 2
        );

        //  判断callback是不是函数
        if (typeof callback === "function") {
          callback({
            image: canvas.toDataURL(),
            pixelOffsetY: Math.round(-canvas.height / 2),
          });
        }
        const billboardResult = {
          image: canvas.toDataURL(),
          pixelOffset: new Cesium.Cartesian2(0, Math.round(-canvas.height / 2)),
        };
        if (settings.position && settings.viewer) {
          const { lon, lat, height } = settings.position;
          const longitude = Number(lon);
          const latitude = Number(lat);
          const altitude = Number(height);

          // 添加标签 billboard
          settings.viewer.entities.add({
            position: Cesium.Cartesian3.fromDegrees(
              longitude,
              latitude,
              altitude
            ),
            billboard: {
              image: billboardResult.image,
              horizontalOrigin: Cesium.HorizontalOrigin.CENTER,
              disableDepthTestDistance: Number.POSITIVE_INFINITY,
              scaleByDistance: new Cesium.NearFarScalar(1.5e2, 1.0, 8.0e6, 0.0),
              scale: 1.0,
              heightReference: Cesium.HeightReference.RELATIVE_TO_GROUND,
            },
          });
        }
        result(billboardResult);
      };
    }
  });
}
