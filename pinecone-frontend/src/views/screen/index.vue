<template>
  <div class="screen-container">
    <div class="header-bg"></div>
    <div class="header-area">
      <!-- 左侧导航 -->
      <div class="nav-item" @click="handleNavClick('admin')">
        <DvBorderBox8>
          <div class="nav-content">
            <i class="el-icon-setting"></i>
            <span>后台管理</span>
          </div>
        </DvBorderBox8>
      </div>

      <!-- 标题部分 -->
      <div class="screen-title">
        <DvDecoration5
          style="
            width: 800px;
            height: 50px;
            position: absolute;
            top: -25px;
            left: 50%;
            transform: translateX(-50%);
          "
        />
        <div class="title-wrapper">
          <DvDecoration8 :dur="3" style="width: 150px" />
          <div class="title-content">
            松果智慧农田系统
            <div class="title-decoration">
              <DvDecoration6 style="width: 400px; height: 8px" />
            </div>
          </div>
          <DvDecoration8 :dur="3" :reverse="true" style="width: 150px" />
        </div>
        <DvDecoration7
          style="
            width: 500px;
            height: 30px;
            position: absolute;
            bottom: -20px;
            left: 50%;
            transform: translateX(-50%);
          "
        />
      </div>

      <!-- 右侧导航 -->
      <div class="nav-item" @click="handleNavClick('ai')">
        <DvBorderBox8>
          <div class="nav-content">
            <i class="el-icon-chat-dot-round"></i>
            <span>AI助手</span>
          </div>
        </DvBorderBox8>
      </div>
    </div>

    <!-- 现有的 Cesium 容器 -->
    <div id="cesiumContainer"></div>

    <!-- 添加数据可视化面板 -->
    <div class="data-panels">
      <!-- 左侧面板 -->
      <div class="left-panel">
        <DvBorderBox1>
          <div class="chart-item">
            <div class="chart-title">设备状态统计</div>
            <div ref="pieChart" style="height: 200px"></div>
          </div>
        </DvBorderBox1>

        <DvBorderBox1>
          <div class="chart-item">
            <div class="chart-title">实时监测数据</div>
            <div ref="lineChart" style="height: 200px"></div>
          </div>
        </DvBorderBox1>
      </div>

      <!-- 右侧面板 -->
      <div class="right-panel">
        <DvBorderBox1>
          <div class="chart-item">
            <div class="chart-title">农作物分布</div>
            <div ref="barChart" style="height: 180px"></div>
          </div>
        </DvBorderBox1>

        <DDecoration3 style="width: 100%; height: 5px" />

        <DvBorderBox1>
          <div class="chart-item">
            <DvScrollBoard :config="tableConfig" />
          </div>
        </DvBorderBox1>
      </div>

      <!-- 修改图层管理框 -->
      <div class="layer-panel">
        <DvBorderBox2 :dur="3" style="width: 400px; height: 100%">
          <div class="layer-content">
            <div class="layer-title">图层管理</div>
            <div class="layer-list">
              <div class="layer-item">
                <el-checkbox v-model="layers.farmland">农田地块</el-checkbox>
              </div>
              <div class="layer-item">
                <el-checkbox v-model="layers.greenhouse">智能大棚</el-checkbox>
              </div>
              <div class="layer-item">
                <el-checkbox v-model="layers.pond">水产养殖</el-checkbox>
              </div>
              <div class="layer-item">
                <el-checkbox v-model="layers.devices">监测设备</el-checkbox>
              </div>
              <div class="layer-item">
                <el-checkbox v-model="layers.drones">无人机</el-checkbox>
              </div>
            </div>
          </div>
        </DvBorderBox2>
      </div>
    </div>

    <!-- 使用全新的方式显示AI助手 -->
    <AiAssistant v-model="showAiDialog" />
  </div>
</template>

<script setup>
import { onMounted, ref, watch, nextTick } from "vue";
import { initViewer } from "@/utils/screen/viewer";
import { configureTianDiTuLayers } from "@/utils/screen/layers";
import { addWaterEffect } from "@/utils/screen/water";
import { loadModel } from "@/utils/screen/model";
import { initBorderLayer } from "@/utils/screen/border";
// import { renderBillboard } from "@/utils/screen/billboard.js";
import { loadDrones } from "@/utils/screen/drones.js";
import { loadDevices } from "@/utils/screen/devices.js";
import * as Cesium from "cesium";
import {
  loadFarmland,
  loadGreenhouse,
  loadPond,
} from "@/utils/screen/block.js";
import { poiIconLabelAdd } from "@/utils/screen/icon.js";
import * as echarts from "echarts";
import {
  BorderBox1 as DvBorderBox1,
  Decoration3 as DvDecoration3,
  Decoration5 as DvDecoration5,
  Decoration6 as DvDecoration6,
  Decoration7 as DvDecoration7,
  Decoration8 as DvDecoration8,
  ScrollBoard as DvScrollBoard,
  BorderBox2 as DvBorderBox2,
  BorderBox8 as DvBorderBox8,
} from "@kjgl77/datav-vue3";
import headerBg from "@/assets/screen/header.png";
import AiAssistant from "@/components/AiAssistant/index.vue";

let options = {
  type: "text-icon", // 默认 'text-icon'类型(图标+文字)、 'text' 类型(仅文字)
  paddingTo: 1, // 图标距离文字背景
  textPadding: 20, // 文字左右间距
  textBcgWidth: 200, // 文字背景宽度
  textBcgHeight: 50, // 文字背景高度
  iconWidth: 60, // 图标宽度
  iconHeight: 70, // 图标高度
  textBcgUrl: "../../assets/screen/label-bcg.png", // 文字背景图片地址
  // verticalLineUrl: "../../assets/screen/vertical-line.png", // 添加这行
  textUrl: "", // 字体地址
  text: "测试文字测试文字", // 文字内容
  textColor: "#DCD085", // 字体颜色
  textFontSize: 16, // 字体大小
  textFontWeight: 800, // 字体粗细
};

// 图表实例引用
const pieChart = ref(null);
const lineChart = ref(null);
const barChart = ref(null);

// 表格配置
const tableConfig = {
  header: ["设备名称", "状态", "最后更新时间"],
  data: [
    ["温度传感器01", "正常", "2024-03-21 10:00:00"],
    ["湿度传感器02", "离线", "2024-03-21 09:30:00"],
    ["水质监测仪03", "告警", "2024-03-21 10:15:00"],
  ],
  index: true,
  columnWidth: [150, 100, 200],
  align: ["center"],
};

// 注册组件
const components = {
  DvBorderBox1,
  DvDecoration3,
  DvDecoration5,
  DvDecoration6,
  DvDecoration7,
  DvDecoration8,
  DvScrollBoard,
  DvBorderBox2,
  DvBorderBox8,
};

// 图层控制状态
const layers = ref({
  farmland: true,
  greenhouse: true,
  pond: true,
  devices: true,
  drones: true,
});

// 导航项配置
const navItems = ref([
  { name: "总览", icon: "el-icon-monitor" },
  { name: "农田管理", icon: "el-icon-crop" },
  { name: "设备监控", icon: "el-icon-cpu" },
  { name: "数据分析", icon: "el-icon-data-analysis" },
  { name: "告警中心", icon: "el-icon-warning" },
]);

// 当前选中的导航项
const currentNav = ref(0);

// 添加 AI 对话框状态
const showAiDialog = ref(false);

// AI对话框相关
const aiAssistantRef = ref(null);

// 修改导航点击处理函数
const handleNavClick = (type) => {
  if (type === "admin") {
    window.location.href = "/index";
  } else if (type === "ai") {
    showAiDialog.value = true;
  }
};

// 监听图层变化
watch(
  layers,
  (newVal) => {
    // 农田地块
    if (newVal.farmland) {
      loadFarmland(viewer);
    } else {
      // 隐藏农田地块的逻辑
    }

    // 智能大棚
    if (newVal.greenhouse) {
      loadGreenhouse(viewer);
    } else {
      // 隐藏大棚的逻辑
    }

    // 水产养殖
    if (newVal.pond) {
      loadPond(viewer);
    } else {
      // 隐藏水产养殖的逻辑
    }

    // 监测设备
    if (newVal.devices) {
      loadDevices(viewer);
    } else {
      // 隐藏监测设备的逻辑
    }

    // 无人机
    if (newVal.drones) {
      loadDrones(viewer);
    } else {
      // 隐藏无人机的逻辑
    }
  },
  { deep: true }
);

// 初始化图表
const initCharts = () => {
  // 饼图
  const pie = echarts.init(pieChart.value);
  pie.setOption({
    series: [
      {
        type: "pie",
        radius: ["40%", "70%"],
        data: [
          { value: 35, name: "在线" },
          { value: 10, name: "离线" },
          { value: 5, name: "告警" },
        ],
        label: {
          color: "#fff",
        },
      },
    ],
    backgroundColor: "transparent",
  });

  // 折线图
  const line = echarts.init(lineChart.value);
  line.setOption({
    xAxis: {
      type: "category",
      data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
      axisLabel: { color: "#fff" },
    },
    yAxis: {
      type: "value",
      axisLabel: { color: "#fff" },
    },
    series: [
      {
        data: [820, 932, 901, 934, 1290, 1330, 1320],
        type: "line",
        smooth: true,
      },
    ],
    backgroundColor: "transparent",
  });

  // 柱状图
  const bar = echarts.init(barChart.value);
  bar.setOption({
    xAxis: {
      type: "category",
      data: ["水稻", "小麦", "玉米", "蔬菜"],
      axisLabel: { color: "#fff" },
    },
    yAxis: {
      type: "value",
      axisLabel: { color: "#fff" },
    },
    series: [
      {
        data: [120, 200, 150, 80],
        type: "bar",
      },
    ],
    backgroundColor: "transparent",
  });
};

// 主初始化函数
const initialize = async () => {
  const viewer = await initViewer();
  configureTianDiTuLayers(viewer);
  await addWaterEffect(viewer);
  await loadModel(viewer);
  await initBorderLayer(viewer);
  await loadFarmland(viewer);
  await loadGreenhouse(viewer);
  await loadPond(viewer);
  await loadDrones(viewer);
  const positions = await loadDevices(viewer);

  for (const pos of positions.value) {
    console.log(pos);
    // await renderBillboard({
    //   ...options,
    //   text: pos.text,
    //   showVerticalLine: true,
    //   position: pos,
    //   iconUrl:pos.iconUrl,
    //   viewer: viewer,
    // });
    poiIconLabelAdd(
      pos.lon,
      pos.lat,
      pos.text,
      Cesium.Color.fromCssColorString("##F0F0F0"),
      pos.iconUrl,
      viewer
    );
  }

  // 初始化图表
  initCharts();
};

onMounted(() => {
  initialize();
});
</script>

<style lang="scss" scoped>
.screen-container {
  width: 100%;
  height: 100vh;
  position: relative;
}
.header-bg {
  background-image: url("../../assets/screen/header.png");
  position: absolute;
  top: -20px;
  left: 0;
  width: 100%;
  height: 180px;
  background-repeat: no-repeat;
  background-size: 100% 100%;
  background-position: center top;
  z-index: 1;
}

/* 确保其他元素在背景之上 */
.header-area {
  position: absolute;
  top: 0px;
  left: 0;
  right: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 40px;
  z-index: 2;
}

#cesiumContainer {
  width: 100%;
  height: 100%;
}

.data-panels {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  pointer-events: none;
}

.left-panel,
.right-panel {
  position: absolute;
  top: 100px;
  width: 400px;
  pointer-events: auto;
}

.left-panel {
  left: 20px;
}

.right-panel {
  position: absolute;
  top: 100px;
  right: 20px;
  width: 400px;

  pointer-events: auto;
}

.chart-item {
  padding: 15px;
  margin-bottom: 20px;
  background: linear-gradient(
    135deg,
    rgba(6, 50, 102, 0.4),
    rgba(0, 38, 89, 0.4)
  );
}

.chart-title {
  color: #fff;
  font-size: 16px;
  margin-bottom: 10px;
  text-align: center;
}

:deep(.dv-border-box-1) {
  margin-bottom: 20px;
  background: linear-gradient(
    to right,
    rgba(0, 24, 75, 0.8),
    rgba(0, 38, 89, 0.8),
    rgba(0, 24, 75, 0.8)
  );
}

/* 修改标题样式 */
.screen-title {
  position: relative;
  width: 800px;
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.title-wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 20px 0;
  width: 100%;
}

.title-content {
  font-size: 36px;
  font-weight: bold;
  color: #7cfffd;
  margin: 0 40px;
  letter-spacing: 8px;
  text-shadow: 0 0 15px rgba(124, 255, 253, 0.8),
    0 0 25px rgba(124, 255, 253, 0.4);
  position: relative;
  padding: 0 40px;
  white-space: nowrap;
}

.title-decoration {
  position: absolute;
  bottom: -15px;
  left: 50%;
  transform: translateX(-50%);
}

@keyframes breathe {
  0% {
    text-shadow: 0 0 15px rgba(124, 255, 253, 0.8),
      0 0 25px rgba(124, 255, 253, 0.4);
  }
  50% {
    text-shadow: 0 0 25px rgba(124, 255, 253, 1),
      0 0 35px rgba(124, 255, 253, 0.6);
  }
  100% {
    text-shadow: 0 0 15px rgba(124, 255, 253, 0.8),
      0 0 25px rgba(124, 255, 253, 0.4);
  }
}

/* 修改图层管理框样式，使其宽度与右侧面板一致 */
.layer-panel {
  position: fixed;
  bottom: 20px;
  right: 20px;
  width: 400px;
  pointer-events: auto;
  z-index: 1000;
}

.layer-content {
  padding: 15px 20px;
  width: 100%;
  height: 100%;
}

.layer-list {
  display: grid;
  grid-template-columns: repeat(2, 1fr); /* 改为两列布局 */
  gap: 15px;
  padding: 5px 0;
}

.layer-title {
  color: #7cfffd;
  font-size: 16px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 15px;
  letter-spacing: 2px;
}

.layer-item {
  color: #fff;
}

/* 自定义 checkbox 样式 */
:deep(.el-checkbox) {
  --el-checkbox-checked-bg-color: #7cfffd;
  --el-checkbox-checked-text-color: #fff;
  --el-checkbox-text-color: #fff;
  --el-checkbox-input-border-color: #7cfffd;
}

:deep(.el-checkbox__label) {
  color: #fff;
}

/* 修改导航栏样式 */
.nav-item {
  width: 150px;
  height: 45px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

/* 添加渐变背景和悬停效果 */
.nav-content {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  height: 100%;
  color: #7cfffd;
  font-size: 16px;
  background: linear-gradient(
    90deg,
    rgba(124, 255, 253, 0.1) 0%,
    rgba(124, 255, 253, 0.2) 50%,
    rgba(124, 255, 253, 0.1) 100%
  );
  transition: all 0.3s ease;
}

.nav-content:hover {
  background: linear-gradient(
    90deg,
    rgba(124, 255, 253, 0.2) 0%,
    rgba(124, 255, 253, 0.3) 50%,
    rgba(124, 255, 253, 0.2) 100%
  );
  box-shadow: 0 0 15px rgba(124, 255, 253, 0.3);
}

/* 添加激活状态样式 */
.nav-item.active .nav-content {
  background: linear-gradient(
    90deg,
    rgba(124, 255, 253, 0.3) 0%,
    rgba(124, 255, 253, 0.4) 50%,
    rgba(124, 255, 253, 0.3) 100%
  );
  box-shadow: 0 0 20px rgba(124, 255, 253, 0.4);
}

/* 添加图标发光效果 */
.nav-content i {
  font-size: 18px;
  text-shadow: 0 0 10px rgba(124, 255, 253, 0.8);
}

/* 修改图层管理面板的渐变背景 */
:deep(.dv-border-box-2) {
  background: linear-gradient(
    to bottom,
    rgba(0, 24, 75, 0.8),
    rgba(0, 38, 89, 0.8)
  );
}

/* 移除重复的 AI 助手样式 */
.ai-assistant-overlay,
.ai-assistant-wrapper,
.ai-assistant-container,
.ai-assistant-header,
.ai-assistant-title,
.ai-assistant-content {
  display: none;
}
</style>
