<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryRef"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
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
          v-hasPermi="['data:droneography:add']"
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
          v-hasPermi="['data:droneography:edit']"
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
          v-hasPermi="['data:droneography:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['data:droneography:export']"
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
      <div
        v-for="item in droneographyList"
        :key="item.photoId"
        class="waterfall-item"
      >
        <div class="image-card">
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
                <span class="task-id">任务ID: {{ item.taskId }}</span>
                <span class="create-time">{{ item.createTime }}</span>
              </div>
              <div class="image-actions">
                <el-button
                  circle
                  type="primary"
                  size="small"
                  @click="handleUpdate(item)"
                  v-hasPermi="['data:droneography:edit']"
                >
                  <el-icon><Edit /></el-icon>
                </el-button>
                <el-button
                  circle
                  type="danger"
                  size="small"
                  @click="handleDelete(item)"
                  v-hasPermi="['data:droneography:remove']"
                >
                  <el-icon><Delete /></el-icon>
                </el-button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

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

    <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改航拍图片对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form
        ref="droneographyRef"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="航拍图片" prop="photoPath">
          <image-upload v-model="form.photoPath" />
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

<script setup name="Droneography">
import {
  listDroneography,
  getDroneography,
  delDroneography,
  addDroneography,
  updateDroneography,
} from "@/api/data/droneography";
import { Picture, Edit, Delete } from "@element-plus/icons-vue";

const { proxy } = getCurrentInstance();

const droneographyList = ref([]);
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
    taskId: null,
  },
  rules: {
    photoPath: [
      { required: true, message: "航拍图片不能为空", trigger: "blur" },
    ],
  },
});

const { queryParams, form, rules } = toRefs(data);

/** 查询航拍图片列表 */
function getList() {
  loading.value = true;
  listDroneography(queryParams.value).then((response) => {
    droneographyList.value = response.rows;
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
    taskId: null,
    createTime: null,
    updateTime: null,
    createBy: null,
    updateBy: null,
  };
  proxy.resetForm("droneographyRef");
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
  title.value = "添加航拍图片";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _photoId = row.photoId || ids.value;
  getDroneography(_photoId).then((response) => {
    form.value = response.data;
    open.value = true;
    title.value = "修改航拍图片";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["droneographyRef"].validate((valid) => {
    if (valid) {
      if (form.value.photoId != null) {
        updateDroneography(form.value).then((response) => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addDroneography(form.value).then((response) => {
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
    .confirm('是否确认删除航拍图片编号为"' + _photoIds + '"的数据项？')
    .then(function () {
      return delDroneography(_photoIds);
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
    "data/droneography/export",
    {
      ...queryParams.value,
    },
    `droneography_${new Date().getTime()}.xlsx`
  );
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
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
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
}

.image-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 16px 0 rgba(0, 0, 0, 0.2);
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

.image-info span {
  display: block;
  font-size: 12px;
  margin-bottom: 5px;
}

.image-actions {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
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

.preview-wrapper {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  cursor: pointer;
}
</style>
