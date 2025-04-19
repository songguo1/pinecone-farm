<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryRef"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="病虫害识别" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择病虫害识别"
          clearable
        >
          <el-option
            v-for="dict in agri_pest_photos_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['data:pest:add']"
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
          v-hasPermi="['data:pest:edit']"
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
          v-hasPermi="['data:pest:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['data:pest:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        v-model:showSearch="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <!-- 瀑布流布局 -->
    <div v-loading="loading" class="waterfall-container">
      <div v-for="item in pestList" :key="item.photoId" class="waterfall-item">
        <div class="image-card" :class="getStatusClass(item.status)">
          <div class="image-wrapper">
            <div class="preview-wrapper" @click="handlePreview(item.photoPath)">
              <el-image
                :src="item.photoPath"
                fit="cover"
                loading="lazy"
                class="hover-image"
              >
                <template #placeholder>
                  <div class="image-placeholder">
                    <el-icon><Picture /></el-icon>
                  </div>
                </template>
              </el-image>
            </div>
            <div class="image-overlay">
              <div class="image-info">
                <div class="status-tag">
                  <dict-tag
                    :options="agri_pest_photos_status"
                    :value="item.status"
                  />
                </div>
                <span class="capture-time">{{
                  parseTime(item.captureTime, "{y}-{m}-{d}")
                }}</span>
              </div>
              <div class="image-actions">
                <el-button
                  circle
                  type="primary"
                  size="small"
                  @click="handleUpdate(item)"
                  v-hasPermi="['data:pest:edit']"
                >
                  <el-icon><Edit /></el-icon>
                </el-button>
                <el-button
                  circle
                  type="danger"
                  size="small"
                  @click="handleDelete(item)"
                  v-hasPermi="['data:pest:remove']"
                >
                  <el-icon><Delete /></el-icon>
                </el-button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改病虫害照片对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="pestRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="病虫害照片" prop="photoPath">
          <image-upload v-model="form.photoPath" />
        </el-form-item>
        <el-form-item label="照片拍摄时间" prop="captureTime">
          <el-date-picker
            clearable
            v-model="form.captureTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择照片拍摄时间"
          >
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

    <!-- 图片预览对话框 -->
    <el-dialog
      v-model="previewVisible"
      title="图片预览"
      width="800px"
      append-to-body
    >
      <el-image
        :src="previewUrl"
        fit="contain"
        style="width: 100%; height: 500px"
      >
        <template #placeholder>
          <div class="image-placeholder">
            <el-icon><Picture /></el-icon>
          </div>
        </template>
      </el-image>
    </el-dialog>
  </div>
</template>

<script setup name="Pest">
import {
  listPest,
  getPest,
  delPest,
  addPest,
  updatePest,
} from "@/api/data/pest";
import { Picture, Edit, Delete } from "@element-plus/icons-vue";

const { proxy } = getCurrentInstance();
const { agri_pest_photos_status } = proxy.useDict("agri_pest_photos_status");

const pestList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const previewVisible = ref(false);
const previewUrl = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    status: null,
  },
  rules: {
    photoPath: [
      { required: true, message: "病虫害照片不能为空", trigger: "blur" },
    ],
    captureTime: [
      { required: true, message: "照片拍摄时间不能为空", trigger: "blur" },
    ],
  },
});

const { queryParams, form, rules } = toRefs(data);

/** 查询病虫害照片列表 */
function getList() {
  loading.value = true;
  listPest(queryParams.value).then((response) => {
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
    updateBy: null,
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
  ids.value = selection.map((item) => item.photoId);
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
  const _photoId = row.photoId || ids.value;
  getPest(_photoId).then((response) => {
    form.value = response.data;
    open.value = true;
    title.value = "修改病虫害照片";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["pestRef"].validate((valid) => {
    if (valid) {
      if (form.value.photoId != null) {
        updatePest(form.value).then((response) => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addPest(form.value).then((response) => {
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
  proxy.$modal
    .confirm('是否确认删除病虫害照片编号为"' + _photoIds + '"的数据项？')
    .then(function () {
      return delPest(_photoIds);
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
    "data/pest/export",
    {
      ...queryParams.value,
    },
    `pest_${new Date().getTime()}.xlsx`
  );
}

// 根据状态获取卡片样式类
function getStatusClass(status) {
  const statusMap = {
    0: "status-normal",
    1: "status-warning",
    2: "status-danger",
  };
  return statusMap[status] || "";
}

// 处理图片预览
function handlePreview(url) {
  previewUrl.value = url;
  previewVisible.value = true;
}

getList();
</script>

<style scoped>
.waterfall-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
  padding: 20px;
}

.waterfall-item {
  break-inside: avoid;
  margin-bottom: 20px;
}

.image-card {
  position: relative;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  border: 2px solid transparent;
}

.image-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 16px 0 rgba(0, 0, 0, 0.2);
}

/* 状态样式 */
.status-normal {
  border-color: #67c23a;
}

.status-warning {
  border-color: #e6a23c;
}

.status-danger {
  border-color: #f56c6c;
}

.image-wrapper {
  position: relative;
  padding-top: 75%; /* 4:3 比例 */
}

.image-wrapper .el-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.preview-wrapper {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  cursor: pointer;
}

.image-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(to top, rgba(0, 0, 0, 0.8), transparent);
  padding: 15px;
  opacity: 0;
  transition: opacity 0.3s ease;
  z-index: 1;
}

.image-card:hover .image-overlay {
  opacity: 1;
}

.image-info {
  color: white;
  margin-bottom: 10px;
}

.status-tag {
  margin-bottom: 8px;
}

.capture-time {
  display: block;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.8);
}

.image-actions {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  pointer-events: auto;
}

.image-placeholder {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-color: #f5f7fa;
  color: #909399;
}

.hover-image {
  transition: transform 0.3s ease;
}

.hover-image:hover {
  transform: scale(1.05);
}

/* 状态标签样式覆盖 */
:deep(.el-tag) {
  border: none;
  font-weight: bold;
  cursor: default;
  pointer-events: none;
}

:deep(.el-tag--success) {
  background-color: rgba(103, 194, 58, 0.1);
  color: #67c23a;
}

:deep(.el-tag--warning) {
  background-color: rgba(230, 162, 60, 0.1);
  color: #e6a23c;
}

:deep(.el-tag--danger) {
  background-color: rgba(245, 108, 108, 0.1);
  color: #f56c6c;
}
</style>
