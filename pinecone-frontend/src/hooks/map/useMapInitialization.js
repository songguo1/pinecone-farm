import { ref } from "vue";
import { Map, View } from "ol";
import { Tile } from "ol/layer";
import XYZ from "ol/source/XYZ";
import { transform } from "ol/proj";
import { useLayer } from "./useLayer";

export function useMapInitialization() {
  const map = ref(null);
  const { fetchLayerData, wktToVectorLayer, addBorderLayer } = useLayer();

  // 根据图层类型生成相应的URL
  const getLayerUrlByData = (type, wkid, token) => {
    let url = "http://t{1-7}.tianditu.com/DataServer?";
    let layerId;

    switch (type) {
      case "image":
        layerId = "img_";
        break;
      case "label":
        layerId = "cia_";
        break;
      case "street":
        layerId = "vec_";
        break;
      case "street_label":
        layerId = "cva_";
        break;
      default:
        throw new Error("Unsupported layer type");
    }

    const tileMatrixSetId = wkid === 4326 ? "c" : "w";
    return `${url}T=${layerId}${tileMatrixSetId}&x={x}&y={y}&l={z}&tk=${token}`;
  };

  const initMap = async (token) => {
    try {
      console.log("初始化地图")
      // 初始化地图
      map.value = await createBaseMap(token);

      

      // 添加边界图层
      await addBorderLayer(map);

      return map.value;
    } catch (error) {
      console.error("初始化地图失败:", error);
    }
  };

  const createBaseMap = (token) => {
    const view = new View({
      center: transform(
        [113.53152631868291, 22.73862490706342],
        "EPSG:4326",
        "EPSG:3857"
      ),
      zoom: 14,
    });

    const baseLayers = [
      createTianDiLayer("image", token),
      createTianDiLayer("label", token),
    ];

    return new Map({
      target: "map",
      layers: baseLayers,
      view,
    });
  };

  const createTianDiLayer = (type, token) => {
    const url = getLayerUrlByData(type, 4326, token);
    return new Tile({
      source: new XYZ({
        url,
        projection: "EPSG:4326",
        wrapX: true,
        crossOrigin: "anonymous",
      }),
    });
  };

  return {
    map,
    initMap,
  };
}
