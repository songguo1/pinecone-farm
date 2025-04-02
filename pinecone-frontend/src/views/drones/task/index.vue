<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryFormRef"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="任务名称" prop="taskName">
        <el-input
          v-model="queryParams.taskName"
          placeholder="请输入任务名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务类型" prop="taskType">
        <el-select
          v-model="queryParams.taskType"
          placeholder="请选择任务类型"
          clearable
        >
          <el-option
            v-for="dict in task_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="任务日期" prop="taskDate">
        <el-date-picker
          clearable
          v-model="queryParams.taskDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择任务日期"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="关联无人机表的ID" prop="droneId">
        <el-input
          v-model="queryParams.droneId"
          placeholder="请输入关联无人机表的ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关联飞手表的ID" prop="pilotId">
        <el-input
          v-model="queryParams.pilotId"
          placeholder="请输入关联飞手表的ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="飞行高度" prop="dronesHeight">
        <el-input
          v-model="queryParams.dronesHeight"
          placeholder="请输入飞行高度"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="Search"
          size="small"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="Refresh" size="small" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          size="small"
          @click="handleAdd"
          v-hasPermi="['drones:task:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          size="small"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['drones:task:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          size="small"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['drones:task:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          size="small"
          @click="handleExport"
          v-hasPermi="['drones:task:export']"
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
      :data="taskList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="任务名称" align="center" prop="taskName" />
      <el-table-column label="任务类型" align="center" prop="taskType">
        <template #default="{ row }">
          <dict-tag :options="task_type" :value="row.taskType" />
        </template>
      </el-table-column>
      <el-table-column
        label="任务日期"
        align="center"
        prop="taskDate"
        width="180"
      >
        <template #default="{ row }">
          <span>{{ parseTime(row.taskDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="关联无人机表的ID" align="center" prop="droneId" />
      <el-table-column label="关联飞手表的ID" align="center" prop="pilotId" />
      <el-table-column label="飞行高度" align="center" prop="dronesHeight" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template #default="{ row }">
          <el-button
            size="small"
            type="primary"
            link
            icon="Edit"
            @click="handleUpdate(row)"
            v-hasPermi="['drones:task:edit']"
            >修改</el-button
          >
          <el-button
            size="small"
            type="primary"
            link
            icon="Delete"
            @click="handleDelete(row)"
            v-hasPermi="['drones:task:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改无人机任务对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="任务名称" prop="taskName">
          <el-input v-model="form.taskName" placeholder="请输入任务名称" />
        </el-form-item>
        <el-form-item label="任务类型" prop="taskType">
          <el-select v-model="form.taskType" placeholder="请选择任务类型">
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
            value-format="yyyy-MM-dd"
            placeholder="请选择任务日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="关联无人机表的ID" prop="droneId">
          <el-input
            v-model="form.droneId"
            placeholder="请输入关联无人机表的ID"
          />
        </el-form-item>
        <el-form-item label="关联飞手表的ID" prop="pilotId">
          <el-input v-model="form.pilotId" placeholder="请输入关联飞手表的ID" />
        </el-form-item>
        <el-form-item label="飞行高度" prop="dronesHeight">
          <el-input v-model="form.dronesHeight" placeholder="请输入飞行高度" />
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

<script setup name="Task">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  listTask,
  getTask,
  delTask,
  addTask,
  updateTask,
} from "@/api/drones/task";
import { getCurrentInstance } from "vue";

const { proxy } = getCurrentInstance();
// 字典数据
const { task_type } = proxy.useDict("task_type");

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
// 无人机任务表格数据
const taskList = ref([]);
// 弹出层标题
const title = ref("");
// 是否显示弹出层
const open = ref(false);
// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  taskName: null,
  taskType: null,
  taskDate: null,
  droneId: null,
  pilotId: null,
  dronesHeight: null,
});

// 表单参数
const form = ref({});
// 表单校验
const rules = reactive({});

// refs
const queryFormRef = ref(null);
const formRef = ref(null);
const router = useRouter();

/** 查询无人机任务列表 */
const getList = async () => {
  loading.value = true;
  try {
    const response = await listTask(queryParams);
    taskList.value = response.rows;
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
  form.value = {
    id: null,
    taskName: null,
    taskType: null,
    taskDate: null,
    droneId: null,
    pilotId: null,
    dronesHeight: null,
    dronesPath: null,
    createdTime: null,
    updatedTime: null,
  };
  proxy.resetForm("formRef");
};

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNum = 1;
  getList();
};

/** 重置按钮操作 */
const resetQuery = () => {
  proxy.resetForm("queryFormRef");
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
  // reset();
  // open.value = true;
  // title.value = "添加无人机任务";
  router.push({ path: "/drones/task/add" });
};

/** 修改按钮操作 */
const handleUpdate = async (row) => {
  reset();
  const id = row.id || ids.value;
  try {
    const response = await getTask(id);
    form.value = response.data;
    open.value = true;
    title.value = "修改无人机任务";
  } catch (error) {
    console.error(error);
  }
};

/** 提交按钮 */
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
    router.push({ path: "/drones/task" });
  } catch (error) {
    console.error(error);
  }
};

/** 删除按钮操作 */
const handleDelete = async (row) => {
  const deleteIds = row.id || ids.value;
  try {
    await proxy.$modal.confirm(
      '是否确认删除无人机任务编号为"' + deleteIds + '"的数据项？'
    );
    await delTask(deleteIds);
    await getList();
    proxy.$modal.msgSuccess("删除成功");
  } catch (error) {
    console.error(error);
  }
};

/** 导出按钮操作 */
const handleExport = () => {
  proxy.download(
    "drones/task/export",
    {
      ...queryParams,
    },
    `task_${new Date().getTime()}.xlsx`
  );
};

onMounted(() => {
  getList();
});
</script>
