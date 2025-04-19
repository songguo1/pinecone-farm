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
      <el-form-item label="农田名称" prop="landName">
        <el-input
          v-model="queryParams.landName"
          placeholder="请输入农田名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="土壤类型" prop="soilType">
        <el-select
          v-model="queryParams.soilType"
          placeholder="请选择土壤类型"
          clearable
        >
          <el-option
            v-for="dict in soil_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="种植作物种类" prop="cropsType">
        <el-select
          v-model="queryParams.cropsType"
          placeholder="请选择种植作物种类"
          clearable
        >
          <el-option
            v-for="dict in crops_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="灌溉方式" prop="irrigationType">
        <el-select
          v-model="queryParams.irrigationType"
          placeholder="请选择灌溉方式"
          clearable
        >
          <el-option
            v-for="dict in irrigation_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :icon="Search" @click="handleQuery"
          >搜索</el-button
        >
        <el-button :icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          :icon="Plus"
          @click="handleAdd"
          v-hasPermi="['manage:farmland:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          :icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:farmland:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          :icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:farmland:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          :icon="Download"
          @click="handleExport"
          v-hasPermi="['manage:farmland:export']"
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
      :data="farmlandList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="农田名称" align="center" prop="landName" />
      <el-table-column label="土壤类型" align="center" prop="soilType">
        <template #default="{ row }">
          <dict-tag :options="soil_type" :value="row.soilType" />
        </template>
      </el-table-column>
      <el-table-column label="种植作物种类" align="center" prop="cropsType">
      </el-table-column>
      <el-table-column label="灌溉方式" align="center" prop="irrigationType">
        <template #default="{ row }">
          <dict-tag :options="irrigation_type" :value="row.irrigationType" />
        </template>
      </el-table-column>
      <el-table-column label="备注信息" align="center" prop="remark" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="250"
      >
        <template #default="{ row }">
          <el-button
            link
            type="primary"
            :icon="InfoFilled"
            @click="handleView(row)"
            >查看</el-button
          >
          <el-button
            link
            type="primary"
            :icon="Edit"
            @click="handleUpdate(row)"
            v-hasPermi="['manage:farmland:edit']"
            >修改</el-button
          >
          <el-button
            link
            type="primary"
            :icon="Delete"
            @click="handleDelete(row)"
            v-hasPermi="['manage:farmland:remove']"
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

    <!-- 添加或修改农田信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="农田名称" prop="landName">
          <el-input v-model="form.landName" placeholder="请输入农田名称" />
        </el-form-item>
        <el-form-item label="土壤类型" prop="soilType">
          <el-select v-model="form.soilType" placeholder="请选择土壤类型">
            <el-option
              v-for="dict in soil_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="种植作物种类" prop="cropsType">
          <el-select v-model="form.cropsType" placeholder="请选择种植作物种类">
            <el-option
              v-for="dict in crops_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="灌溉方式" prop="irrigationType">
          <el-select v-model="form.irrigationType" placeholder="请选择灌溉方式">
            <el-option
              v-for="dict in irrigation_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注信息" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
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

    <!-- 添加新的查看详情弹窗 -->
    <el-dialog
      v-model="viewDialogVisible"
      title="查看农田详情"
      width="500px"
      append-to-body
    >
      <div class="view-dialog-content">
        <Map-view
          v-if="viewDialogVisible"
          :Id="selectedFarmlandId"
          type="farmland"
        ></Map-view>
      </div>
    </el-dialog>
  </div>
</template>

<script setup name="Farmland">
import { ref, reactive, getCurrentInstance, onMounted } from "vue";
import {
  listFarmland,
  getFarmland,
  delFarmland,
  addFarmland,
  updateFarmland,
} from "@/api/manage/farmland";

import {
  Search,
  Refresh,
  Plus,
  Edit,
  Delete,
  Download,
  InfoFilled,
} from "@element-plus/icons-vue";
import MapView from "@/components/View/index.vue";

const { proxy } = getCurrentInstance();

// 字典数据
const { soil_type, crops_type, irrigation_type } = proxy.useDict(
  "soil_type",
  "crops_type",
  "irrigation_type"
);

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
// 农田信息表格数据
const farmlandList = ref([]);
// 弹出层标题
const title = ref("");
// 是否显示弹出层
const open = ref(false);

// 查询参数
const queryParams = ref({
  pageNum: 1,
  pageSize: 10,
  landName: null,
  soilType: null,
  cropsType: null,
  irrigationType: null,
});

// 表单参数
const form = ref({});
const formRef = ref();
const queryFormRef = ref();

// 表单校验
const rules = {
  landName: [{ required: true, message: "农田名称不能为空", trigger: "blur" }],
  remark: [{ required: true, message: "备注信息不能为空", trigger: "blur" }],
};

// 添加新的响应式变量
const viewDialogVisible = ref(false);
const selectedFarmlandId = ref(null);

/** 查询农田信息列表 */
function getList() {
  loading.value = true;
  listFarmland(queryParams.value).then((response) => {
    farmlandList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    landId: null,
    landName: null,
    soilType: null,
    cropsType: null,
    irrigationType: null,
    status: null,
    geom: null,
    createTime: null,
    updateTime: null,
    createBy: null,
    updateBy: null,
    remark: null,
  };
  proxy.resetForm("formRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryFormRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map((item) => item.landId);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加农田信息";
}

/** 查看按钮操作 */
async function handleView(row) {
  selectedFarmlandId.value = row.landId;
  viewDialogVisible.value = true;
}

/** 修改按钮操作 */
async function handleUpdate(row) {
  reset();
  const landId = row.landId || ids.value;
  const response = await getFarmland(landId);
  form.value = response.data;
  open.value = true;
  title.value = "修改农田信息";
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["formRef"].validate(async (valid) => {
    if (valid) {
      if (form.value.landId != null) {
        await updateFarmland(form.value);
        proxy.$modal.msgSuccess("修改成功");
        console.log(form.value);
      } else {
        await addFarmland(form.value);
        proxy.$modal.msgSuccess("新增成功");
        console.log(form.value);
      }
      open.value = false;
      getList();
    }
  });
}

/** 删除按钮操作 */
async function handleDelete(row) {
  const landIds = row.landId || ids.value;
  await proxy.$modal.confirm(
    '是否确认删除农田信息编号为"' + landIds + '"的数据项？'
  );
  await delFarmland(landIds);
  getList();
  proxy.$modal.msgSuccess("删除成功");
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download(
    "manage/farmland/export",
    {
      ...queryParams.value,
    },
    `farmland_${new Date().getTime()}.xlsx`
  );
}

onMounted(() => {
  getList();
});
</script>

<style scoped>
.view-dialog-content {
  width: 100%;
  height: 600px;
  position: relative;
}

/* 确保弹窗内容区域也具有足够的高度 */
:deep(.el-dialog__body) {
  height: 600px;
  padding: 20px;
  overflow: hidden;
}

:deep(.el-dialog) {
  margin-top: 5vh !important;
}
</style>
