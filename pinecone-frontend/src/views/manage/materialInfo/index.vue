<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryRef"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="农资编码" prop="materialCode">
        <el-input
          v-model="queryParams.materialCode"
          placeholder="请输入农资编码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="农资名称" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入农资名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="农资类别" prop="materialTypeId">
        <el-select
          v-model="queryParams.materialTypeId"
          placeholder="请选择农资类别"
          clearable
        >
          <el-option
            v-for="item in materialTypeOptions"
            :key="item.materialTypeId"
            :label="item.materialTypeName"
            :value="item.materialTypeId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="计量单位" prop="measureUnit">
        <el-input
          v-model="queryParams.measureUnit"
          placeholder="请输入计量单位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['manage:materialInfo:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:materialInfo:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:materialInfo:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['manage:materialInfo:export']"
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
      :data="materialInfoList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="农资编码" align="center" prop="materialCode" />
      <el-table-column label="农资名称" align="center" prop="materialName" />
      <el-table-column label="农资类别" align="center" prop="materialTypeId">
        <template #default="scope">
          <span>{{ getMaterialTypeName(scope.row.materialTypeId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="计量单位" align="center" prop="measureUnit" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template #default="scope">
          <el-button
            link
            type="primary"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:materialInfo:edit']"
            >修改</el-button
          >
          <el-button
            link
            type="primary"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:materialInfo:remove']"
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

    <!-- 添加或修改农资信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form
        ref="materialInfoRef"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="农资编码" prop="materialCode">
          <el-input v-model="form.materialCode" placeholder="请输入农资编码" />
        </el-form-item>
        <el-form-item label="农资名称" prop="materialName">
          <el-input v-model="form.materialName" placeholder="请输入农资名称" />
        </el-form-item>
        <el-form-item label="农资类别" prop="materialTypeId">
          <el-select v-model="form.materialTypeId" placeholder="请选择农资类别">
            <el-option
              v-for="item in materialTypeOptions"
              :key="item.materialTypeId"
              :label="item.materialTypeName"
              :value="item.materialTypeId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="计量单位" prop="measureUnit">
          <el-input v-model="form.measureUnit" placeholder="请输入计量单位" />
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

<script setup name="MaterialInfo">
import {
  listMaterialInfo,
  getMaterialInfo,
  delMaterialInfo,
  addMaterialInfo,
  updateMaterialInfo,
} from "@/api/manage/materialInfo";
import { listMaterialType } from "@/api/manage/materialType";
import { onMounted } from "vue";

const { proxy } = getCurrentInstance();

const materialInfoList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    materialCode: null,
    materialName: null,
    materialTypeId: null,
    measureUnit: null,
  },
  rules: {
    materialCode: [
      { required: true, message: "农资编码不能为空", trigger: "blur" },
    ],
    materialName: [
      { required: true, message: "农资名称不能为空", trigger: "blur" },
    ],
    materialTypeId: [
      { required: true, message: "农资类别不能为空", trigger: "blur" },
    ],
    measureUnit: [
      { required: true, message: "计量单位不能为空", trigger: "blur" },
    ],
  },
});

const { queryParams, form, rules } = toRefs(data);

// 农资类别选项列表
const materialTypeOptions = ref([]);

/** 获取农资类别下拉框数据 */
function getMaterialTypeOptions() {
  listMaterialType().then((response) => {
    materialTypeOptions.value = response.rows;
  });
}

/** 根据ID获取农资类别名称 */
function getMaterialTypeName(materialTypeId) {
  const type = materialTypeOptions.value.find(
    (item) => item.materialTypeId === materialTypeId
  );
  return type ? type.materialTypeName : "";
}

/** 查询农资信息列表 */
function getList() {
  loading.value = true;
  listMaterialInfo(queryParams.value).then((response) => {
    materialInfoList.value = response.rows;
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
    materialId: null,
    materialCode: null,
    materialName: null,
    materialTypeId: null,
    measureUnit: null,
    remark: null,
    status: null,
    orderNum: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    delFlag: null,
  };
  proxy.resetForm("materialInfoRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map((item) => item.materialId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  getMaterialTypeOptions();
  open.value = true;
  title.value = "添加农资信息";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  getMaterialTypeOptions();
  const _materialId = row.materialId || ids.value;
  getMaterialInfo(_materialId).then((response) => {
    form.value = response.data;
    open.value = true;
    title.value = "修改农资信息";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["materialInfoRef"].validate((valid) => {
    if (valid) {
      if (form.value.materialId != null) {
        updateMaterialInfo(form.value).then((response) => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addMaterialInfo(form.value).then((response) => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _materialIds = row.materialId || ids.value;
  proxy.$modal
    .confirm('是否确认删除农资信息编号为"' + _materialIds + '"的数据项？')
    .then(function () {
      return delMaterialInfo(_materialIds);
    })
    .then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    })
    .catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download(
    "manage/materialInfo/export",
    {
      ...queryParams.value,
    },
    `materialInfo_${new Date().getTime()}.xlsx`
  );
}

// 在页面加载时获取农资类别数据
onMounted(() => {
  getList();
  getMaterialTypeOptions();
});
</script>
