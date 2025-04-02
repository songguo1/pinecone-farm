<template>
  <div class="dashboard-container">
    <!-- 顶部数据统计卡片 -->
    <el-row :gutter="20" class="panel-group">
      <el-col :span="6">
        <el-card>
          <div class="card-panel">
            <el-icon class="card-panel-icon"><Monitor /></el-icon>
            <div class="card-panel-description">
              <div class="card-panel-text">设备总数</div>
              <div class="card-panel-num">128台</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="card-panel">
            <el-icon class="card-panel-icon"><Platform /></el-icon>
            <div class="card-panel-description">
              <div class="card-panel-text">农田面积</div>
              <div class="card-panel-num">2680亩</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="card-panel">
            <el-icon class="card-panel-icon"><Odometer /></el-icon>
            <div class="card-panel-description">
              <div class="card-panel-text">灌溉设备</div>
              <div class="card-panel-num">56台</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="card-panel">
            <el-icon class="card-panel-icon"><Warning /></el-icon>
            <div class="card-panel-description">
              <div class="card-panel-text">告警数量</div>
              <div class="card-panel-num">3条</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 地图和数据面板区域 -->
    <el-row :gutter="20" style="height: calc(100vh - 300px)">
      <el-col :span="18" style="height: 100%">
        <el-card class="map-card">
          <HomeMap />
        </el-card>
      </el-col>
      <el-col :span="6" style="height: 100%">
        <el-card class="info-card">
          <template #header>
            <div class="card-header">
              <span>实时监测</span>
            </div>
          </template>
          <div class="monitor-list">
            <div
              v-for="(item, index) in monitorData"
              :key="index"
              class="monitor-item"
            >
              <div class="item-title">{{ item.name }}</div>
              <div class="item-value">{{ item.value }}{{ item.unit }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup name="Index">
import { ref } from "vue";
import HomeMap from "@/components/Home/index.vue";
import { Monitor, Platform, Odometer, Warning } from "@element-plus/icons-vue";

const monitorData = ref([
  { name: "空气温度", value: "26.5", unit: "°C" },
  { name: "空气湿度", value: "65", unit: "%" },
  { name: "土壤温度", value: "22.3", unit: "°C" },
  { name: "土壤湿度", value: "58", unit: "%" },
  { name: "光照强度", value: "35000", unit: "Lux" },
]);
</script>

<style scoped lang="scss">
.dashboard-container {
  padding: 20px;
  background-color: #f0f2f5;
  height: 100%;

  .panel-group {
    margin-bottom: 20px;
  }

  .card-panel {
    display: flex;
    align-items: center;
    height: 108px;

    .card-panel-icon {
      font-size: 48px;

      color: #409eff;
    }

    .card-panel-description {
      .card-panel-text {
        font-size: 16px;
        color: #666;
        margin-bottom: 12px;
      }
      .card-panel-num {
        font-size: 24px;
        font-weight: bold;
      }
    }
  }

  .map-card,
  .info-card {
    height: 100%;

    :deep(.el-card__body) {
      height: calc(100% - 40px);
    }
  }

  .monitor-list {
    .monitor-item {
      display: flex;
      justify-content: space-between;
      padding: 15px 0;
      border-bottom: 1px solid #eee;

      .item-title {
        color: #666;
      }

      .item-value {
        font-weight: bold;
        color: #409eff;
      }
    }
  }
}
</style>
