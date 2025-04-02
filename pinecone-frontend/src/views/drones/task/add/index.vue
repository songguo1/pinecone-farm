<template>
  <div class="add-task-container">
    <el-card class="form-card">
      <template #header>
        <div class="card-header">
          <span>新增任务</span>
        </div>
      </template>
        <el-form
          ref="formRef"
          :model="form"
          :rules="rules"
          size="default"
          label-width="100px"
          v-loading="loading"
          class="form-container"
        >
            <el-form-item
              label="任务名称"
              prop="taskName"
              :style="{ width: '100%' }"
            >
              <el-input
                v-model="form.taskName"
                type="text"
                placeholder="请输入任务名称"
                clearable
                :style="{ width: '100%' }"
              ></el-input>
            </el-form-item>
            <el-form-item label="任务类型" prop="taskType">
              <el-select
                v-model="form.taskType"
                placeholder="请选择任务类型"
                clearable
                :style="{ width: '100%' }"
              >
                <el-option
                  v-for="dict in task_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="任务日期" prop="taskDate">
              <el-date-picker
                clearable
                v-model="form.taskDate"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择任务日期"
              ></el-date-picker>
            </el-form-item>
            <el-form-item label="无人机飞手" prop="droneId">
              <el-select
                v-model="form.droneId"
                placeholder="请选择无人机飞手"
                clearable
                :style="{ width: '100%' }"
              >
                <el-option
                  v-for="(item, index) in pilotsList"
                  :key="index"
                  :label="item.name"
                  :value="item.id"
                  :disabled="item.disabled"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="无人机型号" prop="field108">
              <el-select
                v-model="form.field108"
                placeholder="请选择无人机型号"
                clearable
                :style="{ width: '100%' }"
              >
                <el-option
                  v-for="(item, index) in dronesList"
                  :key="index"
                  :label="item.droneModel"
                  :value="item.id"
                  :disabled="item.disabled"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="飞行高度" prop="dronesHeight">
              <el-input
                v-model="form.dronesHeight"
                type="text"
                placeholder="请输入飞行高度"
                clearable
                :style="{ width: '100%' }"
              ></el-input>
            </el-form-item>
            <el-form-item class="form-button">
              <el-button type="primary" @click="submitForm">提交</el-button>
              <el-button @click="resetForm">重置</el-button>
            </el-form-item>

        </el-form>

    </el-card>

    <el-card class="map-card">
      <template #header>
        <div class="card-header">
          <span>航线规划</span>
        </div>
      </template>
      <div class="map-container">
        <Map @get-wkt="getWkt" type="LineString"></Map>
      </div>
    </el-card>
  </div>
</template>
<script setup>
import { ElMessage } from "element-plus";
import Map from "@/components/map/index.vue";
import {
  getAllPilots,
  listPilots,
  getPilots,
  delPilots,
  addPilots,
  updatePilots,
} from "@/api/drones/pilots";

import {
  getAllDrones,
  listDrones,
  getDrones,
  delDrones,
  addDrones,
  updateDrones,
} from "@/api/drones/drones";
import { addTask, updateTask } from "@/api/drones/task";
const formRef = ref();
const loading = ref(true);
const form = ref({});
const { proxy } = getCurrentInstance();
const dronesList = ref([]);
const pilotsList = ref([]);

// 字典数据
const { task_type } = proxy.useDict("task_type");
const rules = reactive({
  taskName: [
    { required: true, message: "请输入任务名称", trigger: "blur" },
    { min: 2, max: 50, message: "长度在 2 到 50 个字符", trigger: "blur" },
  ],
  taskType: [{ required: true, message: "请选择任务类型", trigger: "change" }],
  taskDate: [{ required: true, message: "请选择任务日期", trigger: "change" }],
  droneId: [{ required: true, message: "请选择无人机飞手", trigger: "change" }],
  field108: [
    { required: true, message: "请选择无人机型号", trigger: "change" },
  ],
  dronesHeight: [
    { required: true, message: "请输入飞行高度", trigger: "blur" },
    { pattern: /^\d+$/, message: "飞行高度必须为数字", trigger: "blur" },
  ],
  dronesPath: [
    { required: true, message: "请在地图上规划航线", trigger: "change" },
  ],
});

// refs
const queryFormRef = ref(null);

const dronesPath = ref("");

/**
 * @name: 获取WKT
 * @description: 获取WKT
 * @return {*}
 */
const getWkt = (wkt) => {
  dronesPath.value = wkt;
  console.log(dronesPath.value);
  form.value.dronesPath = dronesPath.value;
  console.log(form.value);
};
/**
 * @name: 表单提交
 * @description: 表单提交方法
 * @return {*}
 */
const submitForm = async () => {
  try {
    await formRef.value.validate();
    if (form.value.id != null) {
      await updateTask(form.value);
      proxy.$modal.msgSuccess("修改成功");
    } else {
      await addTask(form.value);
      proxy.$modal.msgSuccess("新增成功");
    }
    open.value = false;
    getList();
  } catch (error) {
    console.error(error);
  }
};
/**
 * @name: 表单重置
 * @description: 表单重置方法
 * @return {*}
 */
function resetForm() {
  formRef.value.resetFields();
}

const getList = async () => {
  loading.value = true;
  try {
    const response = await getAllDrones();
    console.log(response);
    dronesList.value = response.rows;
    const response2 = await getAllPilots();
    pilotsList.value = response2.rows;
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  getList();
});
</script>
<style lang="scss" scoped>
.add-task-container {
  display: flex;
  gap: 20px;
  padding: 20px;
  height: calc(100vh - 100px);
  background-color: #f5f7fa;
  .form-container {
    padding: 20px;
  }

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
  }

  :deep(.el-form-item) {
    margin-bottom: 32px;

    .el-form-item__label {
      font-weight: 500;
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

  .form-button {
    display: flex;
    justify-content: flex-end;
    margin-left:20
    
  }

  .el-button {
    padding: 12px 20px;
    min-width: 100px;
  }
}
</style>
