<template>
  <div class="map-view">
    <div id="map" style="width: 100%; height: 100%"></div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useMapInitialization } from "@/hooks/map/useMapInitialization";
import useLoadGeom from "@/hooks/map/useLoadGeom";

const props = defineProps({
  Id: {
    type: Number,
    required: true,
  },
  type: {
    type: String,
    default: "farmland",
    validator: (value) => ["farmland", "greenhouse", "pond"].includes(value),
  },
});

const token = "219d900abe817a196c0af21d6cec754b";
const { map, initMap } = useMapInitialization();
const { loadGeometrybyId } = useLoadGeom();

onMounted(async () => {
  await initMap(token);
  await loadGeometrybyId(props.Id, map, props.type);
});
</script>

<style scoped>
.map-view {
  width: 100%;
  height: 100%;
}
</style>
