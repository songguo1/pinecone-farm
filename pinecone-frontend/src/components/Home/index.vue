<template>
  <div
    class="map-container"
    style="position: relative; width: 100%; height: 100%"
  >
    <div id="map" style="width: 100%; height: 100%"></div>

    <div class="map-tools">
      <el-button-group>
        <el-button type="primary" @click="handleZoomIn">
          <el-icon><ZoomIn /></el-icon>
        </el-button>
        <el-button type="primary" @click="handleZoomOut">
          <el-icon><ZoomOut /></el-icon>
        </el-button>
        <el-button type="primary" @click="handleReset">
          <el-icon><Refresh /></el-icon>
        </el-button>
      </el-button-group>
    </div>

    <div class="legend-panel">
      <el-card header="图例">
        <div class="legend-item">
          <span class="legend-color" style="background: #67c23a"></span>
          <span>农田</span>
        </div>
        <div class="legend-item">
          <span class="legend-color" style="background: #409eff"></span>
          <span>水源</span>
        </div>
        <div class="legend-item">
          <span class="legend-color" style="background: #f56c6c"></span>
          <span>大棚</span>
        </div>
      </el-card>
    </div>
  </div>
</template>
<script setup>
import { onMounted, ref } from "vue";
import { useMapInitialization } from "@/hooks/map/useMapInitialization";
import { useDrawing } from "@/hooks/map/useDrawing";
import { useLayer } from "@/hooks/map/useLayer";
import { ElButton, ElButtonGroup, ElCard, ElIcon } from "element-plus";
import { ZoomIn, ZoomOut, Refresh } from "@element-plus/icons-vue";

const token = "219d900abe817a196c0af21d6cec754b";
const { map, initMap } = useMapInitialization();
const { addBusinessLayers } = useLayer(map);

const handleZoomIn = () => {
  map.value?.zoomIn();
};

const handleZoomOut = () => {
  map.value?.zoomOut();
};

const handleReset = () => {
  map.value?.setZoom(12);
  map.value?.setCenter([116.397428, 39.90923]);
};

onMounted(async () => {
  await initMap(token);
  await addBusinessLayers(map);
});
</script>

<style scoped>
.map-tools {
  position: absolute;
  top: 20px;
  right: 20px;
  z-index: 1000;
}

.legend-panel {
  position: absolute;
  bottom: 20px;
  right: 20px;
  z-index: 1000;
}

.legend-item {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
}

.legend-color {
  display: inline-block;
  width: 20px;
  height: 20px;
  margin-right: 8px;
  border-radius: 4px;
}
</style>
