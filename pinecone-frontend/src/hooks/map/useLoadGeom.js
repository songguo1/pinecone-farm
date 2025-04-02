import { ref } from "vue";
import { getFarmland } from "@/api/manage/farmland";
import { getGreenhouse } from "@/api/manage/greenhouse";
import { getPond } from "@/api/manage/pond";
import WKT from "ol/format/WKT";
import VectorLayer from "ol/layer/Vector";
import VectorSource from "ol/source/Vector";
import { buffer } from "ol/extent";
import { Style, Fill, Stroke } from "ol/style";

const geom = ref(null);
export default function useLoadGeom() {
  let flashInterval = null;

  const createBlinkingEffect = (feature) => {
    let visible = true;

    const visibleStyle = new Style({
      fill: new Fill({
        color: "rgba(255, 0, 0, 0.3)",
      }),
      stroke: new Stroke({
        color: "#ff0000",
        width: 2,
      }),
    });

    const invisibleStyle = new Style({
      fill: new Fill({
        color: "rgba(255, 0, 0, 0)",
      }),
      stroke: new Stroke({
        color: "rgba(255, 0, 0, 0)",
        width: 2,
      }),
    });

    flashInterval = setInterval(() => {
      feature.setStyle(visible ? invisibleStyle : visibleStyle);
      visible = !visible;
    }, 500);
  };

  const loadGeometrybyId = async (id, map, type = "farmland") => {
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
      default:
        throw new Error("Unsupported geometry type");
    }

    geom.value = res.data.geom;

    const wktFormat = new WKT();
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
