<template>
  <div class="add-weather-container">
    <el-card class="form-card">
      <template #header>
        <div class="card-header">
          <span>修改气象站</span>
        </div>
      </template>
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        size="default"
        label-width="120px"
      >
        <el-form-item label="气象站名称" prop="stationName">
          <el-input
            v-model="form.stationName"
            type="text"
            placeholder="请输入气象站名称"
            clearable
            :style="{ width: '100%' }"
          ></el-input>
        </el-form-item>

        <el-form-item label="气象站状态" prop="status">
          <el-select
            v-model="form.status"
            placeholder="请选择气象站状态"
            clearable
            :style="{ width: '100%' }"
          >
            <el-option
              v-for="dict in device_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="图标" prop="icon">
          <image-upload v-model="form.icon" />
        </el-form-item>

        <el-form-item label="设备图片" prop="deviceImage">
          <image-upload v-model="form.deviceImage" />
        </el-form-item>

        <el-form-item label="备注信息" prop="remarks">
          <el-input
            v-model="form.remarks"
            type="textarea"
            placeholder="请输入备注信息"
            :autosize="{ minRows: 4, maxRows: 4 }"
            :style="{ width: '100%' }"
          ></el-input>
        </el-form-item>

        <el-form-item label="最后更新时间" prop="lastUpdate">
          <el-date-picker
            v-model="form.lastUpdate"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            :style="{ width: '100%' }"
            placeholder="请选择最后一次数据更新时间"
            clearable
          ></el-date-picker>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="map-card">
      <template #header>
        <div class="card-header">
          <span>设备位置</span>
        </div>
      </template>
      <div class="map-container">
        <Map @get-wkt="getWkt" type="Point"></Map>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ElMessage, ElMessageBox } from "element-plus";
import Map from "@/components/map/index.vue";
import {
  listWeather,
  getWeather,
  delWeather,
  addWeather,
  updateWeather,
} from "@/api/devices/weather";

import { ref, reactive, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();

const formRef = ref();
const data = reactive({
  form: {
    stationName: undefined,
    status: undefined,
    icon: null,
    deviceImage: null,
    remarks: undefined,
    lastUpdate: null,
  },
  rules: {
    stationName: [
      { required: true, message: "请输入气象站名称", trigger: "blur" },
      { min: 2, max: 50, message: "长度在 2 到 50 个字符", trigger: "blur" },
    ],
    status: [
      { required: true, message: "请选择气象站状态", trigger: "change" },
    ],
    icon: [{ required: true, message: "请上传设备图标", trigger: "change" }],
    deviceImage: [
      { required: true, message: "请上传设备图片", trigger: "change" },
    ],
    remarks: [
      { required: true, message: "请输入备注信息", trigger: "blur" },
      { min:0, max: 200, message: "长度超过 200 个字符", trigger: "blur" },
    ],
    lastUpdate: [
      {
        required: true,
        message: "请选择最后一次数据更新时间",
        trigger: "change",
      },
    ],
    location: [
      {
        required: true,
        message: "请在地图上选择气象站位置",
        trigger: "change",
      },
    ],
  },
});
const { proxy } = getCurrentInstance();

// 字典数据
const { device_status, weather_station_type } = proxy.useDict(
  "device_status",
  "weather_station_type"
);
const { form, rules } = toRefs(data);

// 获取设备信息
const getDeviceInfo = async () => {
  try {
    const response = await getWeather(route.params.id);
    Object.assign(form.value, response.data);
  } catch (error) {
    console.error("获取设备信息失败", error);
    ElMessage.error("获取设备信息失败");
  }
};

/**
 * @name: 获取WKT
 * @description: 获取WKT
 * @return {*}
 */
const getWkt = (wkt) => {
  form.value.location = wkt;
};

// 表单重置
const reset = () => {
  form.value.stationId = null;
  form.value.stationName = null;
  form.value.status = null;
  form.value.icon = null;
  form.value.deviceImage = null;
  form.value.remarks = null;
  form.value.lastUpdate = null;
  formRef.value?.resetFields();
};

const resetForm = () => {
  reset();
};

const submitForm = () => {
  formRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        await updateWeather(form.value);
        ElMessage.success("修改成功");
        router.push({ name: "Weather" });
      } catch (error) {
        console.error("提交失败", error);
      }
    }
  });
};

onMounted(() => {
  getDeviceInfo();
});
</script>

<style lang="scss" scoped>
.add-weather-container {
  display: flex;
  gap: 20px;
  padding: 20px;
  height: calc(100vh - 100px);
  background-color: #f5f7fa;

  .form-card,
  .map-card {
    flex: 1;
    height: 100%;
    display: flex;
    flex-direction: column;

    :deep(.el-card__body) {
      flex: 1;
      overflow: auto;
    }
  }

  .card-header {
    font-size: 16px;
    font-weight: bold;
  }

  .map-container {
    height: 100%;
    min-height: 400px;
    border-radius: 4px;
    overflow: hidden;
  }

  :deep(.el-form-item) {
    margin-bottom: 22px;

    .el-form-item__label {
      font-weight: 500;
    }
  }

  :deep(.el-input),
  :deep(.el-select) {
    .el-input__wrapper {
      box-shadow: 0 0 0 1px #dcdfe6 inset;

      &:hover {
        box-shadow: 0 0 0 1px #c0c4cc inset;
      }

      &.is-focus {
        box-shadow: 0 0 0 1px #409eff inset;
      }
    }
  }

  :deep(.el-textarea__inner) {
    box-shadow: 0 0 0 1px #dcdfe6 inset;

    &:hover {
      box-shadow: 0 0 0 1px #c0c4cc inset;
    }

    &:focus {
      box-shadow: 0 0 0 1px #409eff inset;
    }
  }

  .el-button {
    padding: 12px 20px;
    min-width: 100px;
  }
}
</style>
