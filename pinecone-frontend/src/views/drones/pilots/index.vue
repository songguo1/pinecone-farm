<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="飞手姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入飞手姓名"
          clearable
          @keyup.enter="handleQuery"
        />
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
          v-hasPermi="['drones:pilots:add']"
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
          v-hasPermi="['drones:pilots:edit']"
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
          v-hasPermi="['drones:pilots:remove']"
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
          v-hasPermi="['drones:pilots:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        v-model:showSearch="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="pilotsList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="飞手姓名" align="center" prop="name" />
      <el-table-column
        label="飞手执照编号"
        align="center"
        prop="licenseNumber"
      />
      <el-table-column label="飞手联系电话" align="center" prop="phone" />
      <el-table-column label="飞手电子邮箱" align="center" prop="email" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template #default="scope">
          <el-button
            size="small"
            type="primary"
            key="primary"
            link
            :icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['drones:pilots:edit']"
            >修改</el-button
          >
          <el-button
            size="small"
            type="primary"
            key="primary"
            link
            :icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['drones:pilots:remove']"
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

    <!-- 添加或修改飞手信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="飞手姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入飞手姓名" />
        </el-form-item>
        <el-form-item label="飞手执照编号" prop="licenseNumber">
          <el-input
            v-model="form.licenseNumber"
            placeholder="请输入飞手执照编号"
          />
        </el-form-item>
        <el-form-item label="飞手联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入飞手联系电话" />
        </el-form-item>
        <el-form-item label="飞手电子邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入飞手电子邮箱" />
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
  Search,
  Refresh,
  Plus,
  Edit,
  Delete,
  Download,
} from "@element-plus/icons-vue";
import {
  listPilots,
  getPilots,
  delPilots,
  addPilots,
  updatePilots,
} from "@/api/drones/pilots";

// 遮罩层
const loading = ref(true);
// 选中数组
const ids = ref([]);
// 非单个禁用
const single = ref(true);
// 非多个禁用
const multiple = ref(true);
// 显示搜索条件
const showSearch = ref(true);
// 总条数
const total = ref(0);
// 飞手信息表格数据
const pilotsList = ref([]);
// 弹出层标题
const title = ref("");
// 是否显示弹出层
const open = ref(false);
// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  name: null,
});

// 表单参数
const form = reactive({});
// 表单校验
const rules = reactive({});

const queryForm = ref();
const formRef = ref();

/** 查询飞手信息列表 */
const getList = async () => {
  loading.value = true;
  try {
    const response = await listPilots(queryParams);
    pilotsList.value = response.rows;
    total.value = response.total;
  } finally {
    loading.value = false;
  }
};

// 取消按钮
const cancel = () => {
  open.value = false;
  reset();
};

// 表单重置
const reset = () => {
  form.id = null;
  form.name = null;
  form.licenseNumber = null;
  form.phone = null;
  form.email = null;
  form.createdTime = null;
  form.updatedTime = null;
  formRef.value?.resetFields();
};

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNum = 1;
  getList();
};

/** 重置按钮操作 */
const resetQuery = () => {
  queryForm.value?.resetFields();
  handleQuery();
};

// 多选框选中数据
const handleSelectionChange = (selection) => {
  ids.value = selection.map((item) => item.id);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
};

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  open.value = true;
  title.value = "添加飞手信息";
};

/** 修改按钮操作 */
const handleUpdate = async (row) => {
  reset();
  const id = row.id || ids.value;
  try {
    const response = await getPilots(id);
    Object.assign(form, response.data);
    open.value = true;
    title.value = "修改飞手信息";
  } catch (error) {
    console.error("获取飞手信息失败", error);
  }
};

/** 提交按钮 */
const submitForm = () => {
  formRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        if (form.id != null) {
          await updatePilots(form);
          ElMessage.success("修改成功");
        } else {
          await addPilots(form);
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

/** 删除按钮操作 */
const handleDelete = async (row) => {
  const deleteIds = row.id || ids.value;
  try {
    await ElMessageBox.confirm(
      `是否确认删除飞手信息编号为"${deleteIds}"的数据项？`
    );
    await delPilots(deleteIds);
    getList();
    ElMessage.success("删除成功");
  } catch (error) {
    console.error("删除失败", error);
  }
};

/** 导出按钮操作 */
const handleExport = () => {
  download(
    "drones/pilots/export",
    {
      ...queryParams,
    },
    `pilots_${new Date().getTime()}.xlsx`
  );
};

onMounted(() => {
  getList();
});
</script>
