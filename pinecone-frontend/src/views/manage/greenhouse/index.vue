<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="大棚名称" prop="greenhouseName">
        <el-input
          v-model="queryParams.greenhouseName"
          placeholder="请输入大棚名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="大棚结构类型" prop="structureType">
        <el-select
          v-model="queryParams.structureType"
          placeholder="请选择大棚结构类型"
          clearable
        >
          <el-option
            v-for="dict in structure_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否配备加热系统" prop="heatingSystem">
        <el-select
          v-model="queryParams.heatingSystem"
          placeholder="请选择是否配备加热系统"
          clearable
        >
          <el-option
            v-for="dict in heating_system"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="覆盖材料" prop="coverMaterial">
        <el-input
          v-model="queryParams.coverMaterial"
          placeholder="请输入覆盖材料"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="small"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="small" @click="resetQuery"
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
          @click="handleAdd"
          v-hasPermi="['manage:greenhouse:add']"
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
          v-hasPermi="['manage:greenhouse:edit']"
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
          v-hasPermi="['manage:greenhouse:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          :icon="Download"
          @click="handleExport"
          v-hasPermi="['manage:greenhouse:export']"
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
      :data="greenhouseList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="greenhouseId" />
      <el-table-column label="大棚名称" align="center" prop="greenhouseName" />
      <el-table-column label="大棚结构类型" align="center" prop="structureType">
        <template #default="{ row }">
          <dict-tag :options="structure_type" :value="row.structureType" />
        </template>
      </el-table-column>
      <el-table-column
        label="是否配备加热系统"
        align="center"
        prop="heatingSystem"
      >
        <template #default="{ row }">
          <dict-tag :options="heating_system" :value="row.heatingSystem" />
        </template>
      </el-table-column>
      <el-table-column label="覆盖材料" align="center" prop="coverMaterial" />
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
            v-hasPermi="['manage:greenhouse:edit']"
            >修改</el-button
          >
          <el-button
            link
            type="primary"
            :icon="Delete"
            @click="handleDelete(row)"
            v-hasPermi="['manage:greenhouse:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      :total="total"
      @pagination="getList"
    />

    <!-- 添加或修改大棚信息对话框 -->
    <el-dialog v-model="open" :title="title" width="500px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="大棚名称" prop="greenhouseName">
          <el-input
            v-model="form.greenhouseName"
            placeholder="请输入大棚名称"
          />
        </el-form-item>
        <el-form-item label="大棚结构类型" prop="structureType">
          <el-select
            v-model="form.structureType"
            placeholder="请选择大棚结构类型"
          >
            <el-option
              v-for="dict in structure_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否配备加热系统" prop="heatingSystem">
          <el-select
            v-model="form.heatingSystem"
            placeholder="请选择是否配备加热系统"
          >
            <el-option
              v-for="dict in heating_system"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="覆盖材料" prop="coverMaterial">
          <el-input v-model="form.coverMaterial" placeholder="请输入覆盖材料" />
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

    <!-- 查看详情弹窗 -->
    <el-dialog
      v-model="viewDialogVisible"
      title="查看大棚详情"
      width="500px"
      append-to-body
    >
      <div class="view-dialog-content">
        <Map-view :Id="selectedGreenhouseId" type="greenhouse"></Map-view>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, getCurrentInstance } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  Plus,
  Edit,
  Delete,
  Download,
  InfoFilled,
} from "@element-plus/icons-vue";
import MapView from "@/components/View/index.vue";
import {
  listGreenhouse,
  getGreenhouse,
  delGreenhouse,
  addGreenhouse,
  updateGreenhouse,
} from "@/api/manage/greenhouse";
const { proxy } = getCurrentInstance();

const { heating_system, structure_type } = proxy.useDict(
  "heating_system",
  "structure_type"
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
// 大棚信息表格数据
const greenhouseList = ref([]);
// 弹出层标题
const title = ref("");
// 是否显示弹出层
const open = ref(false);
// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  greenhouseName: null,
  structureType: null,
  heatingSystem: null,
  coverMaterial: null,
});

// 表单参数
const form = ref({});
// 表单校验
const rules = reactive({});

const queryFormRef = ref();
const formRef = ref();

// 添加新的响应式变量
const viewDialogVisible = ref(false);
const selectedGreenhouseId = ref(null);

/** 查询大棚信息列表 */
const getList = () => {
  loading.value = true;
  listGreenhouse(queryParams).then((response) => {
    greenhouseList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
};

// 取消按钮
const cancel = () => {
  open.value = false;
  reset();
};

// 表单重置
const reset = () => {
  form.value = {
    greenhouseId: null,
    greenhouseName: null,
    structureType: null,
    heatingSystem: null,
    coverMaterial: null,
    geom: null,
    createTime: null,
    updateTime: null,
    createBy: null,
    updateBy: null,
    remark: null,
  };
  formRef.value?.resetFields();
};

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNum = 1;
  getList();
};

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value?.resetFields();
  handleQuery();
};

// 多选框选中数据
const handleSelectionChange = (selection) => {
  ids.value = selection.map((item) => item.greenhouseId);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
};

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  open.value = true;
  title.value = "添加大棚信息";
};

/** 修改按钮操作 */
const handleUpdate = (row) => {
  reset();
  const greenhouseId = row.greenhouseId || ids.value[0];
  getGreenhouse(greenhouseId).then((response) => {
    form.value = response.data;
    open.value = true;
    title.value = "修改大棚信息";
  });
};

/** 提交按钮 */
const submitForm = () => {
  console.log(form.value);
  formRef.value?.validate((valid) => {
    if (valid) {
      if (form.value.greenhouseId != null) {
        updateGreenhouse(form.value).then(() => {
          ElMessage.success("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addGreenhouse(form.value).then(() => {
          ElMessage.success("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
};

/** 删除按钮操作 */
const handleDelete = (row) => {
  const greenhouseIds = row.greenhouseId || ids.value;
  ElMessageBox.confirm(
    '是否确认删除大棚信息编号为"' + greenhouseIds + '"的数据项？',
    "警告",
    {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }
  ).then(() => {
    delGreenhouse(greenhouseIds).then(() => {
      getList();
      ElMessage.success("删除成功");
    });
  });
};

/** 导出按钮操作 */
const handleExport = () => {
  download(
    "manage/greenhouse/export",
    {
      ...queryParams,
    },
    "greenhouse_" + new Date().getTime() + ".xlsx"
  );
};

// 添加查看按钮操作函数
const handleView = (row) => {
  selectedGreenhouseId.value = row.greenhouseId;
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
