<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="传感器名称" prop="sensorName">
        <el-input
          v-model="queryParams.sensorName"
          placeholder="请输入传感器名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="传感器类型" prop="sensorType">
        <el-select
          v-model="queryParams.sensorType"
          placeholder="请选择传感器类型"
          clearable
        >
          <el-option
            v-for="dict in sensor_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="传感器状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择传感器状态"
          clearable
        >
          <el-option
            v-for="dict in device_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          :icon="Search"
          size="small"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button :icon="Refresh" size="small" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          :icon="Plus"
          size="small"
          @click="handleAdd"
          v-hasPermi="['devices:sensors:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          :icon="Edit"
          size="small"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['devices:sensors:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          :icon="Delete"
          size="small"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['devices:sensors:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          :icon="Download"
          size="small"
          @click="handleExport"
          v-hasPermi="['devices:sensors:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="sensorsList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="sensorId" />
      <el-table-column label="传感器名称" align="center" prop="sensorName" />
      <el-table-column label="传感器类型" align="center" prop="sensorType">
        <template #default="scope">
          <dict-tag :options="sensor_type" :value="scope.row.sensorType" />
        </template>
      </el-table-column>
      <el-table-column label="测量单位" align="center" prop="measurementUnit">
        <template #default="scope">
          <dict-tag
            :options="measurement_unit"
            :value="scope.row.measurementUnit"
          />
        </template>
      </el-table-column>
      <el-table-column label="传感器状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="device_status" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="图标" align="center" prop="icon" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.icon" :width="50" :height="50" />
        </template>
      </el-table-column>
      <el-table-column
        label="设备图片"
        align="center"
        prop="deviceImage"
        width="100"
      >
        <template #default="scope">
          <image-preview
            :src="scope.row.deviceImage"
            :width="50"
            :height="50"
          />
        </template>
      </el-table-column>
      <el-table-column label="备注信息" align="center" prop="remarks" />
      <el-table-column
        label="最后一次读取时间"
        align="center"
        prop="lastReading"
        width="180"
      >
        <template #default="scope">
          <span>{{ parseTime(scope.row.lastReading, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后一次测量值" align="center" prop="lastValue" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template #default="scope">
          <el-button
            size="small"
            type="primary"
            link
            :icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['devices:sensors:edit']"
            >修改</el-button
          >
          <el-button
            size="small"
            type="primary"
            link
            :icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['devices:sensors:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      v-model:total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改传感器设备对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="传感器名称" prop="sensorName">
          <el-input v-model="form.sensorName" placeholder="请输入传感器名称" />
        </el-form-item>
        <el-form-item label="传感器类型" prop="sensorType">
          <el-select v-model="form.sensorType" placeholder="请选择传感器类型">
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
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in device_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
              >{{ dict.label }}</el-radio
            >
          </el-radio-group>
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
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="最后一次读取时间" prop="lastReading">
          <el-date-picker
            clearable
            v-model="form.lastReading"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择最后一次读取时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="最后一次测量值" prop="lastValue">
          <el-input
            v-model="form.lastValue"
            placeholder="请输入最后一次测量值"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  listSensors,
  getSensors,
  delSensors,
  addSensors,
  updateSensors,
} from "@/api/devices/sensors";
import { download } from "@/utils/request";
import {
  Search,
  Refresh,
  Plus,
  Edit,
  Delete,
  Download,
} from "@element-plus/icons-vue";
import { useRouter } from "vue-router";

const router = useRouter();

const { proxy } = getCurrentInstance();

// 字典数据
const { device_status, sensor_type, measurement_unit } = proxy.useDict(
  "device_status",
  "sensor_type",
  "measurement_unit"
);

// refs
const queryFormRef = ref(null);
const formRef = ref(null);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const open = ref(false);
const title = ref("");
const sensorsList = ref([]);

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  sensorName: null,
  sensorType: null,
  status: null,
});

// 表单参数
const form = reactive({});

// 表单校验规则
const rules = reactive({
  sensorName: [
    { required: true, message: "传感器名称不能为空", trigger: "blur" },
  ],
  sensorType: [
    { required: true, message: "传感器类型不能为空", trigger: "change" },
  ],
  measurementUnit: [
    { required: true, message: "测量单位不能为空", trigger: "change" },
  ],
  status: [
    { required: true, message: "传感器状态不能为空", trigger: "change" },
  ],
});

// 查询列表
const getList = async () => {
  loading.value = true;
  try {
    const response = await listSensors(queryParams);
    sensorsList.value = response.rows;
    total.value = response.total;
  } finally {
    loading.value = false;
  }
};

// 表单重置
const reset = () => {
  form.sensorId = null;
  form.sensorName = null;
  form.sensorType = null;
  form.measurementUnit = null;
  form.status = null;
  form.icon = null;
  form.deviceImage = null;
  form.remarks = null;
  form.lastReading = null;
  form.lastValue = null;
  formRef.value?.resetFields();
};

// 取消按钮
const cancel = () => {
  open.value = false;
  reset();
};

// 搜索按钮操作
const handleQuery = () => {
  queryParams.pageNum = 1;
  getList();
};

// 重置按钮操作
const resetQuery = () => {
  queryFormRef.value?.resetFields();
  handleQuery();
};

// 多选框选中数据
const handleSelectionChange = (selection) => {
  ids.value = selection.map((item) => item.sensorId);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
};

// 新增按钮操作
const handleAdd = () => {
  // reset();
  // open.value = true;
  // title.value = "添加传感器设备";
  router.push("/devices/sensors/add");
};

// 修改按钮操作
const handleUpdate = async (row) => {
  reset();
  const sensorId = row.sensorId || ids.value;
  try {
    const response = await getSensors(sensorId);
    Object.assign(form, response.data);
    open.value = true;
    title.value = "修改传感器设备";
  } catch (error) {
    console.error("获取传感器设备信息失败", error);
  }
};

// 提交按钮
const submitForm = () => {
  formRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        if (form.sensorId != null) {
          await updateSensors(form);
          ElMessage.success("修改成功");
        } else {
          await addSensors(form);
          ElMessage.success("新增成功");
        }
        open.value = false;
        getList();
      } catch (error) {
        console.error("提交失败", error);
      }
    }
  });
};

// 删除按钮操作
const handleDelete = async (row) => {
  const sensorIds = row.sensorId || ids.value;
  try {
    await ElMessageBox.confirm(
      `是否确认删除传感器设备编号为"${sensorIds}"的数据项？`
    );
    await delSensors(sensorIds);
    getList();
    ElMessage.success("删除成功");
  } catch (error) {
    console.error("删除失败", error);
  }
};

// 导出按钮操作
const handleExport = () => {
  download(
    "devices/sensors/export",
    {
      ...queryParams,
    },
    `sensors_${new Date().getTime()}.xlsx`
  );
};

onMounted(() => {
  getList();
});
</script>
