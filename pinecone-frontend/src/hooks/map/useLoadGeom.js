import { ref } from "vue";
import { getFarmland } from "@/api/manage/farmland";
import { getGreenhouse } from "@/api/manage/greenhouse";
import { getPond } from "@/api/manage/pond";
import { getMonitoring } from "@/api/devices/monitoring";
import { getSensors } from "@/api/devices/sensors";
import { getWeather } from "@/api/devices/weather";
import WKT from "ol/format/WKT";
import VectorLayer from "ol/layer/Vector";
import VectorSource from "ol/source/Vector";
import { buffer } from "ol/extent";
import { Style, Fill, Stroke, Circle } from "ol/style";
import Point from "ol/geom/Point";
import { fromLonLat } from "ol/proj";

const geom = ref(null);
export default function useLoadGeom() {
  let flashInterval = null;

  const createBlinkingEffect = (feature) => {
    let visible = true;

    // 检查几何类型
    const geomType = feature.getGeometry().getType();
    let visibleStyle, invisibleStyle;

    if (geomType === "Point") {
      // 点状要素样式
      visibleStyle = new Style({
        image: new Circle({
          radius: 5,
          fill: new Fill({
            color: "rgba(255, 0, 0, 0.3)",
          }),
          stroke: new Stroke({
            color: "#ff0000",
            width: 1,
          }),
        }),
      });

      invisibleStyle = new Style({
        image: new Circle({
          radius: 5,
          fill: new Fill({
            color: "rgba(255, 0, 0, 0)",
          }),
          stroke: new Stroke({
            color: "rgba(255, 0, 0, 0)",
            width: 1,
          }),
        }),
      });
    } else {
      // 面状要素样式
      visibleStyle = new Style({
        fill: new Fill({
          color: "rgba(0, 153, 255, 0.3)",
        }),
        stroke: new Stroke({
          color: "#0099ff",
          width: 2,
        }),
      });

      invisibleStyle = new Style({
        fill: new Fill({
          color: "rgba(0, 153, 255, 0)",
        }),
        stroke: new Stroke({
          color: "rgba(0, 153, 255, 0)",
          width: 2,
        }),
      });
    }

    // 清除之前的定时器
    if (flashInterval) {
      clearInterval(flashInterval);
    }

    flashInterval = setInterval(() => {
      feature.setStyle(visible ? invisibleStyle : visibleStyle);
      visible = !visible;
    }, 500);
  };

  const loadGeometrybyId = async (id, map, type = "farmland") => {
    console.log("loadGeometrybyId", id, map, type);
    if (flashInterval) {
      clearInterval(flashInterval);
    }

    let res;
    switch (type) {
      case "farmland":
        res = await getFarmland(id);
        break;
      case "greenhouse":
        res = await getGreenhouse(id);
        break;
      case "pond":
        res = await getPond(id);
        break;
      case "monitoring":
        res = await getMonitoring(id);
        break;
      case "sensor":
        res = await getSensors(id);
        break;
      case "weather":
        res = await getWeather(id);
        break;
      default:
        throw new Error("Unsupported geometry type");
    }
    const wktFormat = new WKT();

    if (["monitoring", "sensor", "weather"].includes(type)) {
      geom.value = res.data.location;
      const feature = wktFormat.readFeature(res.data.location, {
        dataProjection: "EPSG:4326", // WGS84 坐标系
        featureProjection: "EPSG:3857", // Web Mercator 坐标系
      });

      // 根据设备类型设置不同的样式
      let pointColor;
      switch (type) {
        case "monitoring":
          pointColor = "rgba(255, 0, 0, 0.3)"; // 红色
          break;
        case "sensor":
          pointColor = "rgba(0, 128, 0, 0.3)"; // 绿色
          break;
        case "weather":
          pointColor = "rgba(0, 0, 255, 0.3)"; // 蓝色
          break;
      }

      const pointStyle = new Style({
        image: new Circle({
          radius: 5,
          fill: new Fill({
            color: pointColor,
          }),
          stroke: new Stroke({
            color: pointColor.replace("0.3", "1"),
            width: 1,
          }),
        }),
      });

      feature.setStyle(pointStyle);

      const vectorLayer = new VectorLayer({
        source: new VectorSource({
          features: [feature],
        }),
        zIndex: 100, // 确保图层在最上层
      });

      map.value.addLayer(vectorLayer);

      // 创建闪烁效果
      createBlinkingEffect(feature);

      // 聚焦到点位置，增加缓冲距离
      const extent = vectorLayer.getSource().getExtent();
      map.value.getView().fit(buffer(extent, 300));
    } else {
      geom.value = res.data.geom;

      const feature = wktFormat.readFeature(geom.value, {
        dataProjection: "EPSG:4326",
        featureProjection: "EPSG:3857",
      });

      const vectorLayer = new VectorLayer({
        source: new VectorSource({
          features: [feature],
        }),
      });

      map.value.addLayer(vectorLayer);
      createBlinkingEffect(feature);
      //聚焦
      const extent = vectorLayer.getSource().getExtent();
      map.value.getView().fit(buffer(extent, 100));
    }
  };

  const clearFlashingEffect = () => {
    if (flashInterval) {
      clearInterval(flashInterval);
      flashInterval = null;
    }
  };

  return {
    loadGeometrybyId,
    clearFlashingEffect,
  };
}
