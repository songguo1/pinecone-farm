<template>
  <div class="add-monitoring-container">
    <el-card class="form-card">
      <template #header>
        <div class="card-header">
          <span>修改监控设备</span>
        </div>
      </template>
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        size="default"
        label-width="100px"
      >
        <el-form-item label="设备名称" prop="deviceName">
          <el-input
            v-model="form.deviceName"
            type="text"
            placeholder="请输入设备名称"
            clearable
            :style="{ width: '100%' }"
          ></el-input>
        </el-form-item>

        <el-form-item label="存储容量" prop="storageCapacity">
          <el-input
            v-model="form.storageCapacity"
            type="text"
            placeholder="请输入存储容量（单位：GB）"
            clearable
            :style="{ width: '100%' }"
          >
            <template #append>GB</template>
          </el-input>
        </el-form-item>

        <el-form-item label="设备状态" prop="status">
          <el-select
            v-model="form.status"
            placeholder="请选择设备状态"
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
  listMonitoring,
  getMonitoring,
  delMonitoring,
  addMonitoring,
  updateMonitoring,
} from "@/api/devices/monitoring";

import { ref, reactive, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();

const formRef = ref();
const data = reactive({
  form: {
    deviceName: undefined,
    storageCapacity: undefined,
    status: undefined,
    remarks: undefined,
  },
  rules: {
    deviceName: [
      {
        required: true,
        message: "请输入设备名称",
        trigger: "blur",
      },
    ],
    storageCapacity: [
      {
        required: true,
        message: "请输入存储容量（单位：GB）",
        trigger: "blur",
      },
    ],
    status: [
      {
        required: true,
        message: "请选择设备状态",
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
  },
});
const { proxy } = getCurrentInstance();

// 字典数据
const { device_status } = proxy.useDict("device_status");
const { form, rules } = toRefs(data);

// 获取设备信息
const getDeviceInfo = async () => {
  try {
    const response = await getMonitoring(route.params.id);
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
  form.value.monitoringDeviceId = null;
  form.value.deviceName = null;
  form.value.storageCapacity = null;
  form.value.status = null;
  form.value.icon = null;
  form.value.deviceImage = null;
  form.value.remarks = null;
  form.value.lastRecord = null;
  formRef.value?.resetFields();
};

const resetForm = () => {
  reset();
};

const submitForm = () => {
  formRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        await updateMonitoring(form.value);
        ElMessage.success("修改成功");
        router.push({ name: "Monitoring" });
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
.add-monitoring-container {
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
