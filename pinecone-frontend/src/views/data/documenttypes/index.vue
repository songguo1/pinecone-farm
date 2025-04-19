<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['data:documenttypes:add']"
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
          v-hasPermi="['data:documenttypes:edit']"
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
          v-hasPermi="['data:documenttypes:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['data:documenttypes:export']"
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
      :data="documenttypesList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="文档类型名称"
        align="center"
        prop="documentTypeName"
      />
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
            v-hasPermi="['data:documenttypes:edit']"
            >修改</el-button
          >
          <el-button
            link
            type="primary"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['data:documenttypes:remove']"
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

    <!-- 添加或修改文档类型对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form
        ref="documenttypesRef"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="类型名称" prop="documentTypeName">
          <el-input
            v-model="form.documentTypeName"
            placeholder="请输入文档类型名称"
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

<script setup name="Documenttypes">
import {
  listDocumenttypes,
  getDocumenttypes,
  delDocumenttypes,
  addDocumenttypes,
  updateDocumenttypes,
} from "@/api/data/documenttypes";

const { proxy } = getCurrentInstance();

const documenttypesList = ref([]);
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
    documentTypeId: null,
    documentTypeName: null,
  },
  rules: {
    documentTypeName: [
      { required: true, message: "文档类型名称不能为空", trigger: "change" },
    ],
  },
});

const { queryParams, form, rules } = toRefs(data);

/** 查询文档类型列表 */
function getList() {
  loading.value = true;
  listDocumenttypes(queryParams.value).then((response) => {
    documenttypesList.value = response.rows;
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
    documentTypeId: null,
    documentTypeName: null,
    createTime: null,
    updateTime: null,
    createBy: null,
    updateBy: null,
  };
  proxy.resetForm("documenttypesRef");
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
  ids.value = selection.map((item) => item.documentTypeId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加文档类型";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _documentTypeId = row.documentTypeId || ids.value;
  getDocumenttypes(_documentTypeId).then((response) => {
    form.value = response.data;
    open.value = true;
    title.value = "修改文档类型";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["documenttypesRef"].validate((valid) => {
    if (valid) {
      if (form.value.documentTypeId != null) {
        updateDocumenttypes(form.value).then((response) => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addDocumenttypes(form.value).then((response) => {
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
  const _documentTypeIds = row.documentTypeId || ids.value;
  proxy.$modal
    .confirm('是否确认删除文档类型编号为"' + _documentTypeIds + '"的数据项？')
    .then(function () {
      return delDocumenttypes(_documentTypeIds);
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
    "data/documenttypes/export",
    {
      ...queryParams.value,
    },
    `documenttypes_${new Date().getTime()}.xlsx`
  );
}

getList();
</script>
