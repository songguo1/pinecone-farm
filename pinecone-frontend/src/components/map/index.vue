<template>
  <div
    class="map-container"
    style="position: relative; width: 100%; height: 100%"
  >
    <div id="map" style="width: 100%; height: 100%"></div>
    <div
      class="map-controls"
      style="position: absolute; top: 10px; right: 10px; z-index: 1000"
    >
      <el-button-group>
        <el-button type="primary" @click="startDrawing" :disabled="isDrawing">
          开始绘制
        </el-button>
        <el-button type="warning" @click="stopDrawing" :disabled="!isDrawing">
          停止绘制
        </el-button>
        <el-button
          type="danger"
          @click="clearDrawing"
          :disabled="!drawSource?.value"
        >
          清除
        </el-button>
      </el-button-group>
    </div>
  </div>
</template>
<script setup>
import { onMounted } from "vue";
import { useMapInitialization } from "@/hooks/map/useMapInitialization";
import { useDrawing } from "@/hooks/map/useDrawing";
import {useLayer} from "@/hooks/map/useLayer";

const token = "219d900abe817a196c0af21d6cec754b";
const { map, initMap } = useMapInitialization();
const { addBusinessLayers } = useLayer(map);
const {
  isDrawing,
  drawSource,
  addPointInteraction,
  addLineInteraction,
  stopDrawing,
  clearDrawing,
} = useDrawing(map);

const props = defineProps({
  type: {
    type: String,
    default: "Point",
    validator: (value) => ["Point", "LineString"].includes(value),
  },
});

const emit = defineEmits(["getWkt"]);

function startDrawing() {
  if (props.type === "Point") {
    addPointInteraction(emit);
  } else {
    addLineInteraction(emit);
  }
}

onMounted(async () => {
  await initMap(token);
  await addBusinessLayers(map);
});
</script>
