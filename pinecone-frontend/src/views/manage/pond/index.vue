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
      <el-form-item label="鱼塘名称" prop="pondName">
        <el-input
          v-model="queryParams.pondName"
          placeholder="请输入鱼塘名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="水质等级" prop="waterQuality">
        <el-select
          v-model="queryParams.waterQuality"
          placeholder="请选择水质等级"
          clearable
        >
          <el-option
            v-for="dict in water_depth"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="饲料类型" prop="feedType">
        <el-select
          v-model="queryParams.feedType"
          placeholder="请选择饲料类型"
          clearable
        >
          <el-option
            v-for="dict in feed_type"
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
          v-hasPermi="['manage:pond:add']"
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
          v-hasPermi="['manage:pond:edit']"
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
          v-hasPermi="['manage:pond:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          :icon="Download"
          @click="handleExport"
          v-hasPermi="['manage:pond:export']"
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
      :data="pondList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="pondId" />
      <el-table-column label="鱼塘名称" align="center" prop="pondName" />
      <el-table-column label="平均水深" align="center" prop="waterDepth" />
      <el-table-column label="水质等级" align="center" prop="waterQuality">
        <template #default="{ row }">
          <dict-tag :options="water_depth" :value="row.waterQuality" />
        </template>
      </el-table-column>
      <el-table-column label="水体溶氧量" align="center" prop="oxygenLevel" />
      <el-table-column label="饲料类型" align="center" prop="feedType">
        <template #default="{ row }">
          <dict-tag :options="feed_type" :value="row.feedType" />
        </template>
      </el-table-column>
      <el-table-column label="备注信息" align="center" prop="remark" />
      <el-table-column
        label="操作"
        align="center"
        width="250"
        class-name="small-padding fixed-width"
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
            v-hasPermi="['manage:pond:edit']"
            >修改</el-button
          >
          <el-button
            link
            type="primary"
            :icon="Delete"
            @click="handleDelete(row)"
            v-hasPermi="['manage:pond:remove']"
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

    <!-- 添加或修改鱼塘信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="鱼塘名称" prop="pondName">
          <el-input v-model="form.pondName" placeholder="请输入鱼塘名称" />
        </el-form-item>
        <el-form-item label="平均水深" prop="waterDepth">
          <el-input v-model="form.waterDepth" placeholder="请输入平均水深" />
        </el-form-item>
        <el-form-item label="水质等级" prop="waterQuality">
          <el-select v-model="form.waterQuality" placeholder="请选择水质等级">
            <el-option
              v-for="dict in water_depth"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="水体溶氧量" prop="oxygenLevel">
          <el-input v-model="form.oxygenLevel" placeholder="请输入水体溶氧量" />
        </el-form-item>
        <el-form-item label="饲料类型" prop="feedType">
          <el-select v-model="form.feedType" placeholder="请选择饲料类型">
            <el-option
              v-for="dict in feed_type"
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

    <!-- 查看鱼塘详情对话框 -->
    <el-dialog
      v-model="viewDialogVisible"
      title="查看鱼塘详情"
      width="500px"
      append-to-body
    >
      <div class="view-dialog-content">
        <Map-view :Id="selectedPondId" type="pond"></Map-view>
      </div>
    </el-dialog>
  </div>
</template>

<script setup name="Pond">
import { ref, reactive, getCurrentInstance, onMounted } from "vue";
import {
  listPond,
  getPond,
  delPond,
  addPond,
  updatePond,
} from "@/api/manage/pond";
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
const { water_depth, feed_type } = proxy.useDict("water_depth", "feed_type");

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
// 鱼塘信息表格数据
const pondList = ref([]);
// 弹出层标题
const title = ref("");
// 是否显示弹出层
const open = ref(false);

// 查询参数
const queryParams = ref({
  pageNum: 1,
  pageSize: 10,
  pondName: null,
  waterQuality: null,
  feedType: null,
});

// 表单参数
const form = ref({});
const formRef = ref();
const queryFormRef = ref();

// 表单校验
const rules = {
  pondName: [{ required: true, message: "鱼塘名称不能为空", trigger: "blur" }],
  remark: [{ required: true, message: "备注信息不能为空", trigger: "blur" }],
};

// 添加新的响应式变量
const viewDialogVisible = ref(false);
const selectedPondId = ref(null);

/** 查询鱼塘信息列表 */
function getList() {
  loading.value = true;
  listPond(queryParams.value).then((response) => {
    pondList.value = response.rows;
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
    pondId: null,
    pondName: null,
    waterDepth: null,
    waterQuality: null,
    oxygenLevel: null,
    feedType: null,
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
  ids.value = selection.map((item) => item.pondId);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加鱼塘信息";
}

/** 修改按钮操作 */
async function handleUpdate(row) {
  reset();
  const pondId = row.pondId || ids.value;
  const response = await getPond(pondId);
  form.value = response.data;
  open.value = true;
  title.value = "修改鱼塘信息";
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["formRef"].validate(async (valid) => {
    if (valid) {
      if (form.value.pondId != null) {
        await updatePond(form.value);
        proxy.$modal.msgSuccess("修改成功");
      } else {
        await addPond(form.value);
        proxy.$modal.msgSuccess("新增成功");
      }
      open.value = false;
      getList();
    }
  });
}

/** 删除按钮操作 */
async function handleDelete(row) {
  const pondIds = row.pondId || ids.value;
  await proxy.$modal.confirm(
    '是否确认删除鱼塘信息编号为"' + pondIds + '"的数据项？'
  );
  await delPond(pondIds);
  getList();
  proxy.$modal.msgSuccess("删除成功");
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download(
    "manage/pond/export",
    {
      ...queryParams.value,
    },
    `pond_${new Date().getTime()}.xlsx`
  );
}

// 添加查看按钮操作函数
const handleView = (row) => {
  selectedPondId.value = row.pondId;
  viewDialogVisible.value = true;
};

onMounted(() => {
  getList();
});
</script>

<style scoped>
.view-dialog-content {
  width: 100%;
  height: 500px;
}

:deep(.el-dialog__body) {
  height: 600px;
  padding: 20px;
}
</style>
