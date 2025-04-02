import { ref } from "vue";
import VectorLayer from "ol/layer/Vector";
import VectorSource from "ol/source/Vector";
import { Style, Stroke, Fill } from "ol/style";
import GeoJSON from "ol/format/GeoJSON";
import WKT from "ol/format/WKT";
import { getAllFarmland } from "@/api/manage/farmland";
import { getAllGreenhouse } from "@/api/manage/greenhouse";
import { getAllPond } from "@/api/manage/pond";

export function useLayer() {
  const farmlandList = ref([]);
  const greenhouseList = ref([]);
  const pondList = ref([]);
  const wktFormat = new WKT();

  // 获取所有图层数据
  const fetchLayerData = async () => {
    try {
      const [farmlandRes, greenhouseRes, pondRes] = await Promise.all([
        getAllFarmland(),
        getAllGreenhouse(),
        getAllPond(),
      ]);

      farmlandList.value = farmlandRes.data || [];
      greenhouseList.value = greenhouseRes.data || [];
      pondList.value = pondRes.data || [];

      return {
        farmlandWkt: farmlandList.value
          .map((item) => item.geom)
          .filter(
            (geom) => geom && typeof geom === "string" && geom.trim() !== ""
          ),
        greenhouseWkt: greenhouseList.value
          .map((item) => item.geom)
          .filter(
            (geom) => geom && typeof geom === "string" && geom.trim() !== ""
          ),
        pondWkt: pondList.value
          .map((item) => item.geom)
          .filter(
            (geom) => geom && typeof geom === "string" && geom.trim() !== ""
          ),
      };
    } catch (error) {
      console.error("获取图层数据失败:", error);
      return { farmlandWkt: [], greenhouseWkt: [], pondWkt: [] };
    }
  };

  // 创建矢量图层样式
  const createLayerStyle = (type) => {
    const styles = {
      farmland: {
        stroke: "#2ecc71",
        fill: "rgba(46, 204, 113, 0.3)",
      },
      greenhouse: {
        stroke: "#e74c3c",
        fill: "rgba(231, 76, 60, 0.3)",
      },
      pond: {
        stroke: "#3498db",
        fill: "rgba(52, 152, 219, 0.3)",
      },
    };

    const style = styles[type];
    return new Style({
      stroke: new Stroke({
        color: style.stroke,
        width: 2,
      }),
      fill: new Fill({
        color: style.fill,
      }),
    });
  };

  // WKT转矢量图层
  const wktToVectorLayer = (wktList, type) => {
    if (!wktList?.length) return null;

    try {
      const features = wktList
        .map((wkt) => {
          try {
            return wktFormat.readFeature(wkt, {
              dataProjection: "EPSG:4326",
              featureProjection: "EPSG:3857",
            });
          } catch (err) {
            console.warn("WKT解析错误:", err);
            return null;
          }
        })
        .filter(Boolean);

      if (!features.length) return null;

      return new VectorLayer({
        source: new VectorSource({ features }),
        style: createLayerStyle(type),
      });
    } catch (error) {
      console.error("创建矢量图层失败:", error);
      return null;
    }
  };
  const addBusinessLayers = async (map) => {

  // 加载所有图层数据
  const { farmlandWkt, greenhouseWkt, pondWkt } = await fetchLayerData();

  // 添加各类图层
  const layers = [
    { wkt: farmlandWkt, type: "farmland" },
    { wkt: greenhouseWkt, type: "greenhouse" },
    { wkt: pondWkt, type: "pond" },
  ]
    .map(({ wkt, type }) => wktToVectorLayer(wkt, type))
    .filter(Boolean);

  layers.forEach((layer) => map.value.addLayer(layer));
  }

  // 边界图层相关
  const addBorderLayer = async (map) => {
    try {
      const response = await fetch("/static/geojson/border.geojson");
      const geojsonData = await response.json();

      const borderSource = new VectorSource({
        features: new GeoJSON().readFeatures(geojsonData, {
          dataProjection: "EPSG:4326",
          featureProjection: "EPSG:3857",
        }),
      });

      const borderLayer = new VectorLayer({
        source: borderSource,
        style: (feature) => [
          new Style({
            stroke: new Stroke({
              color: "rgba(0, 0, 0, 0.3)",
              width: 5,
              lineDash: [10, 10],
              lineDashOffset: feature.get("lineDashOffset") || 0,
            }),
          }),
          new Style({
            stroke: new Stroke({
              color: "#f1c40f",
              width: 2,
              lineDash: [10, 10],
              lineDashOffset: feature.get("lineDashOffset") || 0,
            }),
          }),
        ],
      });

      map.value.addLayer(borderLayer);
      animateBorder(borderSource, borderLayer);
    } catch (error) {
      console.error("加载边界图层失败:", error);
    }
  };

  // 边界动画
  const animateBorder = (source, layer) => {
    let offset = 0;
    const animate = () => {
      offset = (offset + 0.5) % 20;
      source.getFeatures().forEach((feature) => {
        feature.set("lineDashOffset", offset);
      });
      layer.changed();
      requestAnimationFrame(animate);
    };
    animate();
  };

  return {
    fetchLayerData,
    addBorderLayer,
    addBusinessLayers,
  };
}
