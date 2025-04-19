<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="病虫害识别" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择病虫害识别" clearable>
          <el-option
            v-for="dict in agri_pest_photos_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
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
          v-hasPermi="['data:pest:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['data:pest:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['data:pest:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['data:pest:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="pestList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="photoId" />
      <el-table-column label="病虫害照片" align="center" prop="photoPath" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.photoPath" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="照片拍摄时间" align="center" prop="captureTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.captureTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="病虫害识别" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="agri_pest_photos_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['data:pest:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['data:pest:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改病虫害照片对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="pestRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="病虫害照片" prop="photoPath">
          <image-upload v-model="form.photoPath"/>
        </el-form-item>
        <el-form-item label="照片拍摄时间" prop="captureTime">
          <el-date-picker clearable
            v-model="form.captureTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择照片拍摄时间">
          </el-date-picker>
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

<script setup name="Pest">
import { listPest, getPest, delPest, addPest, updatePest } from "@/api/data/pest";

const { proxy } = getCurrentInstance();
const { agri_pest_photos_status } = proxy.useDict('agri_pest_photos_status');

const pestList = ref([]);
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
    status: null,
  },
  rules: {
    photoPath: [
      { required: true, message: "病虫害照片不能为空", trigger: "blur" }
    ],
    captureTime: [
      { required: true, message: "照片拍摄时间不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询病虫害照片列表 */
function getList() {
  loading.value = true;
  listPest(queryParams.value).then(response => {
    pestList.value = response.rows;
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
    photoId: null,
    photoPath: null,
    captureTime: null,
    status: null,
    createTime: null,
    updateTime: null,
    createBy: null,
    updateBy: null
  };
  proxy.resetForm("pestRef");
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
  ids.value = selection.map(item => item.photoId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加病虫害照片";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _photoId = row.photoId || ids.value
  getPest(_photoId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改病虫害照片";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["pestRef"].validate(valid => {
    if (valid) {
      if (form.value.photoId != null) {
        updatePest(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addPest(form.value).then(response => {
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
  const _photoIds = row.photoId || ids.value;
  proxy.$modal.confirm('是否确认删除病虫害照片编号为"' + _photoIds + '"的数据项？').then(function() {
    return delPest(_photoIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('data/pest/export', {
    ...queryParams.value
  }, `pest_${new Date().getTime()}.xlsx`)
}

getList();
</script>
