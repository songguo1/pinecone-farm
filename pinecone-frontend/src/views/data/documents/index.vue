<template>
  <div class="app-container">
    <!-- 搜索区域 - 使用卡片包装并简化UI -->
    <el-card class="search-card">
      <el-form
        :model="queryParams"
        ref="queryRef"
        :inline="true"
        v-show="showSearch"
      >
        <el-form-item label="文档类型" prop="documentTypeId">
          <el-select
            v-model="queryParams.documentTypeId"
            placeholder="请选择文档类型"
            clearable
          >
            <el-option
              v-for="item in documentTypeOptions"
              :key="item.documentTypeId"
              :label="item.documentTypeName"
              :value="item.documentTypeId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="文档标题" prop="title">
          <el-input
            v-model="queryParams.title"
            placeholder="请输入文档标题"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :icon="Search" @click="handleQuery"
            >搜索</el-button
          >
          <el-button :icon="Refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 操作按钮区域 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          :icon="Plus"
          @click="handleAdd"
          v-hasPermi="['data:documents:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          :icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['data:documents:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          :icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['data:documents:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          :icon="Download"
          @click="handleExport"
          v-hasPermi="['data:documents:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        v-model:showSearch="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <!-- 现代化卡片列表视图 -->
    <el-row :gutter="16" v-loading="loading">
      <el-col
        :xs="24"
        :sm="12"
        :md="8"
        :lg="6"
        v-for="(item, index) in documentsList"
        :key="item.documentId"
        class="mb20"
      >
        <el-card shadow="hover" class="document-card">
          <!-- 文档类型和操作区 -->
          <div class="document-header">
            <el-tag class="document-type">{{
              getDocumentTypeName(item.documentTypeId)
            }}</el-tag>
            <div class="document-actions">
              <el-dropdown trigger="click">
                <el-button type="primary" size="small" circle>
                  <el-icon><More /></el-icon>
                </el-button>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item
                      @click="handleUpdate(item)"
                      v-hasPermi="['data:documents:edit']"
                    >
                      <el-icon><Edit /></el-icon> 修改
                    </el-dropdown-item>
                    <el-dropdown-item
                      @click="handleDelete(item)"
                      v-hasPermi="['data:documents:remove']"
                    >
                      <el-icon><Delete /></el-icon> 删除
                    </el-dropdown-item>
                    <el-dropdown-item @click="downloadDocument(item)">
                      <el-icon><Download /></el-icon> 下载
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>
          </div>

          <!-- 文档内容展示区 -->
          <div class="document-content" @click="downloadDocument(item)">
            <!-- 文档类型图标 -->
            <div class="document-icon">
              <img :src="getDocumentIcon(item.filePath)" class="doc-image" />
            </div>
            <!-- 文档信息 -->
            <div class="document-info">
              <h3 class="document-title">{{ item.title }}</h3>
              <p class="document-path">{{ getFileName(item.filePath) }}</p>
              <p class="document-remark" v-if="item.remark">
                {{ item.remark }}
              </p>
            </div>
          </div>

          <!-- 底部信息栏 -->
          <div class="document-footer">
            <el-tooltip
              effect="dark"
              :content="item.createTime"
              placement="top"
            >
              <span class="footer-time"
                ><el-icon><Calendar /></el-icon>
                {{ formatTime(item.createTime) }}</span
              >
            </el-tooltip>
            <el-button
              size="small"
              type="primary"
              text
              @click="downloadDocument(item)"
            >
              <el-icon><Download /></el-icon> 下载
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 空状态展示 -->
    <el-empty
      v-if="documentsList.length === 0 && !loading"
      description="暂无文档数据"
    />

    <!-- 分页区域 -->
    <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改系统文档对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form
        ref="documentsRef"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="文档类型" prop="documentTypeId">
          <el-select v-model="form.documentTypeId" placeholder="请选择文档类型">
            <el-option
              v-for="item in documentTypeOptions"
              :key="item.documentTypeId"
              :label="item.documentTypeName"
              :value="item.documentTypeId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="文档标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入文档标题" />
        </el-form-item>
        <el-form-item label="文档文件" prop="filePath">
          <file-upload v-model="form.filePath" />
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
  </div>
</template>

<script setup name="Documents">
import {
  listDocuments,
  getDocuments,
  delDocuments,
  addDocuments,
  updateDocuments,
} from "@/api/data/documents";
import { listDocumenttypes } from "@/api/data/documenttypes";
import { onMounted, ref, reactive, toRefs } from "vue";
import {
  Search,
  Refresh,
  Plus,
  Edit,
  Delete,
  Download,
  More,
  Calendar,
} from "@element-plus/icons-vue";

// 导入自定义图标
import pdfIcon from "@/assets/icons/doc/pdf.jpg";
import wordIcon from "@/assets/icons/doc/word.jpg";

const { proxy } = getCurrentInstance();

const documentsList = ref([]);
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
    title: null,
  },
  rules: {
    title: [{ required: true, message: "文档标题不能为空", trigger: "blur" }],
    filePath: [
      { required: true, message: "文档文件不能为空", trigger: "blur" },
    ],
  },
});

const { queryParams, form, rules } = toRefs(data);

// 文档类型选项列表
const documentTypeOptions = ref([]);

/** 获取文档类型下拉框数据 */
function getDocumentTypeOptions() {
  listDocumenttypes().then((response) => {
    documentTypeOptions.value = response.rows;
  });
}

/** 根据ID获取文档类型名称 */
function getDocumentTypeName(documentTypeId) {
  const type = documentTypeOptions.value.find(
    (item) => item.documentTypeId === documentTypeId
  );
  return type ? type.documentTypeName : "";
}

/** 查询系统文档列表 */
function getList() {
  loading.value = true;
  listDocuments(queryParams.value).then((response) => {
    documentsList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 获取文件名从路径 */
function getFileName(filePath) {
  if (!filePath) return "";
  return filePath.split("/").pop() || filePath;
}

/** 根据文件类型获取对应图标 */
function getDocumentIcon(filePath) {
  if (!filePath) return wordIcon;

  const ext = filePath.split(".").pop()?.toLowerCase();

  if (ext === "pdf") {
    return pdfIcon;
  } else {
    // 所有其他格式都显示为word图标
    return wordIcon;
  }
}

/** 格式化时间为友好格式 */
function formatTime(timeStr) {
  if (!timeStr) return "";

  const date = new Date(timeStr);
  const now = new Date();

  // 今天内显示时分
  if (date.toDateString() === now.toDateString()) {
    return `今天 ${date.getHours().toString().padStart(2, "0")}:${date
      .getMinutes()
      .toString()
      .padStart(2, "0")}`;
  }

  // 今年内显示月日
  if (date.getFullYear() === now.getFullYear()) {
    return `${date.getMonth() + 1}月${date.getDate()}日`;
  }

  // 其他显示年月日
  return `${date.getFullYear()}-${(date.getMonth() + 1)
    .toString()
    .padStart(2, "0")}-${date.getDate().toString().padStart(2, "0")}`;
}

/** 下载文档 */
function downloadDocument(item) {
  // 实现文件下载逻辑
  window.open(
    `/api/download?filePath=${encodeURIComponent(
      item.filePath
    )}&fileName=${encodeURIComponent(item.title)}`
  );
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    documentId: null,
    documentTypeId: null,
    title: null,
    filePath: null,
    createTime: null,
    updateTime: null,
    createBy: null,
    updateBy: null,
    remark: null,
  };
  proxy.resetForm("documentsRef");
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
  ids.value = selection.map((item) => item.documentId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  getDocumentTypeOptions();
  open.value = true;
  title.value = "添加系统文档";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  getDocumentTypeOptions();
  const _documentId = row.documentId || ids.value;
  getDocuments(_documentId).then((response) => {
    form.value = response.data;
    open.value = true;
    title.value = "修改系统文档";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["documentsRef"].validate((valid) => {
    if (valid) {
      if (form.value.documentId != null) {
        updateDocuments(form.value).then((response) => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addDocuments(form.value).then((response) => {
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
  const _documentIds = row.documentId || ids.value;
  proxy.$modal
    .confirm('是否确认删除系统文档编号为"' + _documentIds + '"的数据项？')
    .then(function () {
      return delDocuments(_documentIds);
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
    "data/documents/export",
    {
      ...queryParams.value,
    },
    `documents_${new Date().getTime()}.xlsx`
  );
}

// 在页面加载时获取文档类型数据
onMounted(() => {
  getList();
  getDocumentTypeOptions();
});
</script>

<style lang="scss" scoped>
.app-container {
  padding: 20px;
  background-color: #f5f7fa;
}

.search-card {
  margin-bottom: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);

  :deep(.el-card__body) {
    padding: 15px 20px;
  }
}

.document-card {
  height: 100%;
  border-radius: 8px;
  transition: all 0.3s;
  overflow: hidden;

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
  }

  .document-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;

    .document-type {
      font-size: 12px;
    }
  }

  .document-content {
    display: flex;
    padding: 15px 0;
    cursor: pointer;

    .document-icon {
      display: flex;
      justify-content: center;
      align-items: center;
      width: 60px;
      height: 60px;
      margin-right: 15px;

      .doc-image {
        width: 48px;
        height: 48px;
        object-fit: contain;
      }
    }

    .document-info {
      flex: 1;
      overflow: hidden;

      .document-title {
        margin: 0 0 8px;
        font-size: 16px;
        font-weight: 500;
        color: #303133;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }

      .document-path {
        margin: 0 0 5px;
        font-size: 13px;
        color: #909399;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }

      .document-remark {
        margin: 0;
        font-size: 12px;
        color: #c0c4cc;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
  }

  .document-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-top: 10px;
    border-top: 1px solid #f0f0f0;

    .footer-time {
      display: flex;
      align-items: center;
      font-size: 12px;
      color: #909399;

      .el-icon {
        margin-right: 5px;
      }
    }
  }
}

.mb20 {
  margin-bottom: 20px;
}
</style>
