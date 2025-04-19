<template>
  <div class="add-sensors-container">
    <el-card class="form-card">
      <template #header>
        <div class="card-header">
          <span>修改传感器</span>
        </div>
      </template>
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        size="default"
        label-width="120px"
      >
        <el-form-item label="传感器名称" prop="sensorName">
          <el-input
            v-model="form.sensorName"
            type="text"
            placeholder="请输入传感器名称"
            clearable
            :style="{ width: '100%' }"
          ></el-input>
        </el-form-item>

        <el-form-item label="传感器类型" prop="sensorType">
          <el-select
            v-model="form.sensorType"
            placeholder="请选择传感器类型"
            clearable
            :style="{ width: '100%' }"
          >
            <el-option
              v-for="dict in sensor_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="测量单位" prop="measurementUnit">
          <el-select
            v-model="form.measurementUnit"
            placeholder="请选择测量单位"
            clearable
            :style="{ width: '100%' }"
          >
            <el-option
              v-for="dict in measurement_unit"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="传感器状态" prop="status">
          <el-select
            v-model="form.status"
            placeholder="请选择传感器状态"
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

        <el-form-item label="最后读取时间" prop="lastReading">
          <el-date-picker
            v-model="form.lastReading"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            :style="{ width: '100%' }"
            placeholder="请选择最后一次读取时间"
            clearable
          ></el-date-picker>
        </el-form-item>

        <el-form-item label="最后测量值" prop="lastValue">
          <el-input
            v-model="form.lastValue"
            type="text"
            placeholder="请输入最后一次测量值"
            clearable
            :style="{ width: '100%' }"
          ></el-input>
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
  listSensors,
  getSensors,
  delSensors,
  addSensors,
  updateSensors,
} from "@/api/devices/sensors";

import { ref, reactive, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();

const formRef = ref();
const data = reactive({
  form: {
    sensorName: undefined,
    sensorType: undefined,
    measurementUnit: undefined,
    status: undefined,
    icon: null,
    deviceImage: null,
    remarks: undefined,
    lastReading: null,
    lastValue: undefined,
  },
  rules: {
    sensorName: [
      {
        required: true,
        message: "请输入传感器名称",
        trigger: "blur",
      },
    ],
    sensorType: [
      {
        required: true,
        message: "请选择传感器类型",
        trigger: "change",
      },
    ],
    measurementUnit: [
      {
        required: true,
        message: "请选择测量单位",
        trigger: "change",
      },
    ],
    status: [
      {
        required: true,
        message: "请选择传感器状态",
        trigger: "change",
      },
    ],
    remarks: [
      {
        required: true,
        message: "请输入备注信息",
        trigger: "blur",
      },
    ],
    lastReading: [
      {
        required: true,
        message: "请选择最后一次读取时间",
        trigger: "change",
      },
    ],
    lastValue: [
      {
        required: true,
        message: "请输入最后一次测量值",
        trigger: "blur",
      },
    ],
  },
});
const { proxy } = getCurrentInstance();

// 字典数据
const { device_status, sensor_type, measurement_unit } = proxy.useDict(
  "device_status",
  "sensor_type",
  "measurement_unit"
);
const { form, rules } = toRefs(data);

// 获取设备信息
const getDeviceInfo = async () => {
  try {
    const response = await getSensors(route.params.id);
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
  form.value.sensorId = null;
  form.value.sensorName = null;
  form.value.sensorType = null;
  form.value.measurementUnit = null;
  form.value.status = null;
  form.value.icon = null;
  form.value.deviceImage = null;
  form.value.remarks = null;
  form.value.lastReading = null;
  form.value.lastValue = null;
  formRef.value?.resetFields();
};

const resetForm = () => {
  reset();
};

const submitForm = () => {
  formRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        await updateSensors(form.value);
        ElMessage.success("修改成功");
        router.push({ name: "Sensors" });
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
.add-sensors-container {
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
      padding: 20px;
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

    &.is-required .el-form-item__label:before {
      color: #ff4949;
    }
  }

  :deep(.el-input),
  :deep(.el-select),
  :deep(.el-date-picker) {
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

  :deep(.image-upload-container) {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    padding: 10px;

    &:hover {
      border-color: #409eff;
    }
  }

  .el-button {
    padding: 12px 20px;
    min-width: 100px;
    margin-right: 12px;

    &:last-child {
      margin-right: 0;
    }
  }
}
</style>
