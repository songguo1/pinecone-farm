<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="无人机型号" prop="droneModel">
        <el-input
          v-model="queryParams.droneModel"
          placeholder="请输入无人机型号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="无人机序列号" prop="serialNumber">
        <el-input
          v-model="queryParams.serialNumber"
          placeholder="请输入无人机序列号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="无人机当前状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择无人机当前状态"
          clearable
        >
          <el-option
            v-for="dict in status"
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
        <el-button
          :icon="Refresh"
          icon="el-icon-refresh"
          size="small"
          @click="resetQuery"
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
          v-hasPermi="['drones:drones:add']"
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
          v-hasPermi="['drones:drones:edit']"
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
          v-hasPermi="['drones:drones:remove']"
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
          v-hasPermi="['drones:drones:export']"
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
      :data="dronesList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="无人机型号" align="center" prop="droneModel" />
      <el-table-column
        label="无人机序列号"
        align="center"
        prop="serialNumber"
      />
      <el-table-column
        label="无人机制造商"
        align="center"
        prop="manufacturer"
      />
      <el-table-column
        label="无人机购买日期"
        align="center"
        prop="purchaseDate"
        width="180"
      >
        <template #default="{ row }">
          <span>{{ parseTime(row.purchaseDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="无人机当前状态" align="center" prop="status">
        <template #default="{ row }">
          <dict-tag :options="status" :value="row.status" />
        </template>
      </el-table-column>
      <el-table-column
        label="无人机电池容量"
        align="center"
        prop="batteryCapacity"
      />
      <el-table-column
        label="无人机最大飞行时间"
        align="center"
        prop="maxFlightTime"
      />
      <el-table-column
        label="无人机最大载荷"
        align="center"
        prop="maxPayload"
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template #default="{ row }">
          <el-button
            size="small"
            type="primary"
            key="primary"
            link
            :icon="Edit"
            @click="handleUpdate(row)"
            v-hasPermi="['drones:drones:edit']"
            >修改</el-button
          >
          <el-button
            size="small"
            type="primary"
            key="primary"
            link
            :icon="Delete"
            @click="handleDelete(row)"
            v-hasPermi="['drones:drones:remove']"
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

    <!-- 添加或修改无人机信息，记录所有无人机的详细信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="无人机型号" prop="droneModel">
          <el-input v-model="form.droneModel" placeholder="请输入无人机型号" />
        </el-form-item>
        <el-form-item label="无人机序列号" prop="serialNumber">
          <el-input
            v-model="form.serialNumber"
            placeholder="请输入无人机序列号"
          />
        </el-form-item>
        <el-form-item label="无人机制造商" prop="manufacturer">
          <el-input
            v-model="form.manufacturer"
            placeholder="请输入无人机制造商"
          />
        </el-form-item>
        <el-form-item label="无人机购买日期" prop="purchaseDate">
          <el-date-picker
            clearable
            v-model="form.purchaseDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择无人机购买日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="无人机当前状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择无人机当前状态">
            <el-option
              v-for="dict in status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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
  listDrones,
  getDrones,
  delDrones,
  addDrones,
  updateDrones,
} from "@/api/drones/drones";
import { download } from "@/utils/request";
import {
  Search,
  Refresh,
  Plus,
  Edit,
  Delete,
  Download,
} from "@element-plus/icons-vue";

const { proxy } = getCurrentInstance();
// 字典数据
const { status } = proxy.useDict("status");

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
const dronesList = ref([]);

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  droneModel: null,
  serialNumber: null,
  status: null,
});

// 表单参数
const form = reactive({});

// 表单校验规则
const rules = reactive({
  droneModel: [
    { required: true, message: "无人机型号不能为空", trigger: "blur" },
  ],
  serialNumber: [
    { required: true, message: "无人机序列号不能为空", trigger: "blur" },
  ],
});

// 查询列表
const getList = async () => {
  loading.value = true;
  try {
    const response = await listDrones(queryParams);
    dronesList.value = response.rows;
    total.value = response.total;
  } finally {
    loading.value = false;
  }
};

// 表单重置
const reset = () => {
  form.id = null;
  form.droneModel = null;
  form.serialNumber = null;
  form.manufacturer = null;
  form.purchaseDate = null;
  form.status = null;
  form.batteryCapacity = null;
  form.maxFlightTime = null;
  form.maxPayload = null;
  form.createdTime = null;
  form.updatedTime = null;
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
  ids.value = selection.map((item) => item.id);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
};

// 新增按钮操作
const handleAdd = () => {
  reset();
  open.value = true;
  title.value = "添加无人机信息";
};

// 修改按钮操作
const handleUpdate = async (row) => {
  reset();
  const id = row.id || ids.value;
  try {
    const response = await getDrones(id);
    Object.assign(form, response.data);
    open.value = true;
    title.value = "修改无人机信息";
  } catch (error) {
    console.error("获取无人机信息失败", error);
  }
};

// 提交按钮
const submitForm = () => {
  formRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        if (form.id != null) {
          await updateDrones(form);
          ElMessage.success("修改成功");
        } else {
          await addDrones(form);
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
  const deleteIds = row.id || ids.value;
  try {
    await ElMessageBox.confirm(
      `是否确认删除无人机信息编号为"${deleteIds}"的数据项？`
    );
    await delDrones(deleteIds);
    getList();
    ElMessage.success("删除成功");
  } catch (error) {
    console.error("删除失败", error);
  }
};

// 导出按钮操作
const handleExport = () => {
  download(
    "drones/drones/export",
    {
      ...queryParams,
    },
    `drones_${new Date().getTime()}.xlsx`
  );
};

onMounted(() => {
  getList();
});
</script>
