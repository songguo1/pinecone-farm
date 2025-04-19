<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="设备名称" prop="deviceName">
        <el-input
          v-model="queryParams.deviceName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择设备状态"
          clearable
        >
          <el-option
            v-for="dict in device_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          :icon="Search"
          size="small"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button :icon="Refresh" size="small" @click="resetQuery"
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
          size="small"
          @click="handleAdd"
          v-hasPermi="['devices:monitoring:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          :icon="Download"
          size="small"
          @click="handleExport"
          v-hasPermi="['devices:monitoring:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <!-- 卡片列表 -->
    <el-row v-loading="loading" :gutter="20">
      <el-col
        :xs="24"
        :sm="12"
        :md="8"
        :lg="6"
        v-for="item in monitoringList"
        :key="item.monitoringDeviceId"
        class="mb20"
      >
        <el-card
          :body-style="{ padding: '0px' }"
          shadow="hover"
          class="device-card"
        >
          <div class="device-header-area">
            <div class="device-icon">
              <el-image :src="item.icon" fit="contain" class="icon-image">
                <template #error>
                  <el-icon class="icon-placeholder"><Monitor /></el-icon>
                </template>
              </el-image>
            </div>
            <div class="device-status">
              <dict-tag :options="device_status" :value="item.status" />
            </div>
          </div>
          <div class="device-image">
            <el-image :src="item.deviceImage" fit="cover">
              <template #error>
                <div class="image-slot">
                  <el-icon><Picture /></el-icon>
                </div>
              </template>
            </el-image>
          </div>
          <div class="device-content">
            <div class="device-title">
              <span class="device-name">{{ item.deviceName }}</span>
            </div>
            <div class="device-info">
              <div class="info-grid">
                <div class="info-item">
                  <div class="info-label">
                    <el-icon><DataLine /></el-icon>
                    <span>存储容量</span>
                  </div>
                  <div class="info-value">{{ item.storageCapacity }}</div>
                </div>
                <div class="info-item">
                  <div class="info-label">
                    <el-icon><Timer /></el-icon>
                    <span>最后记录</span>
                  </div>
                  <div class="info-value">
                    {{ parseTime(item.lastRecord, "{y}-{m}-{d}") }}
                  </div>
                </div>
              </div>
              <div class="info-item full-width" v-if="item.remarks">
                <div class="info-label">
                  <el-icon><InfoFilled /></el-icon>
                  <span>备注</span>
                </div>
                <div class="info-value">
                  <el-tooltip
                    :content="item.remarks"
                    placement="top"
                    :hide-after="0"
                  >
                    <span class="remarks">{{ item.remarks }}</span>
                  </el-tooltip>
                </div>
              </div>
            </div>
            <div class="device-actions">
              <el-button
                type="primary"
                link
                :icon="InfoFilled"
                @click="handleView(item)"
                >查看</el-button
              >
              <el-button
                type="primary"
                link
                :icon="Edit"
                @click="handleUpdate(item)"
                v-hasPermi="['devices:monitoring:edit']"
                >修改</el-button
              >
              <el-button
                type="danger"
                link
                :icon="Delete"
                @click="handleDelete(item)"
                v-hasPermi="['devices:monitoring:remove']"
                >删除</el-button
              >
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <pagination
      v-show="total > 0"
      v-model:total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改监控设备对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备名称" prop="deviceName">
          <el-input v-model="form.deviceName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="存储容量" prop="storageCapacity">
          <el-input
            v-model="form.storageCapacity"
            placeholder="请输入存储容量"
          />
        </el-form-item>
        <el-form-item label="设备状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择设备状态">
            <el-option
              v-for="dict in device_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <image-upload v-model="form.icon" />
        </el-form-item>
        <el-form-item label="设备图片" prop="deviceImage">
          <image-upload v-model="form.deviceImage" />
        </el-form-item>
        <el-form-item label="经度" prop="longitude">
          <el-input-number
            v-model="form.longitude"
            :precision="6"
            :step="0.000001"
            :min="-180"
            :max="180"
            placeholder="请输入经度"
          />
        </el-form-item>
        <el-form-item label="纬度" prop="latitude">
          <el-input-number
            v-model="form.latitude"
            :precision="6"
            :step="0.000001"
            :min="-90"
            :max="90"
            placeholder="请输入纬度"
          />
        </el-form-item>
        <el-form-item label="备注信息" prop="remarks">
          <el-input
            v-model="form.remarks"
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
      title="查看设备位置"
      width="500px"
      append-to-body
    >
      <div class="view-dialog-content">
        <Map-view
          v-if="viewDialogVisible"
          :Id="selectedDeviceId"
          type="monitoring"
        ></Map-view>
      </div>
    </el-dialog>
  </div>
</template>

<style scoped>
.mb20 {
  margin-bottom: 15px; /* 减少底部间距 */
}

.device-card {
  transition: all 0.3s;
  border-radius: 8px;
  overflow: hidden;
  height: 100%; /* 确保所有卡片高度一致 */
  display: flex;
  flex-direction: column;
}

.device-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
}

.device-header-area {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 12px; /* 减少上下内边距 */
  background: #f8f9fa;
}

.device-icon {
  width: 32px; /* 减小图标尺寸 */
  height: 32px;
  border-radius: 6px;
  overflow: hidden;
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.icon-image {
  width: 24px; /* 减小图标尺寸 */
  height: 24px;
}

.icon-placeholder {
  font-size: 20px; /* 减小占位图标尺寸 */
  color: #909399;
}

.device-image {
  height: 140px; /* 减小图片高度 */
  overflow: hidden;
  position: relative;
}

.device-image .el-image {
  width: 100%;
  height: 100%;
  transition: transform 0.3s;
}

.device-card:hover .device-image .el-image {
  transform: scale(1.05);
}

.image-slot {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: #f5f7fa;
  color: #909399;
  font-size: 24px;
}

.device-content {
  padding: 12px; /* 减少内边距 */
  flex: 1; /* 让内容区域填充剩余空间 */
  display: flex;
  flex-direction: column;
}

.device-title {
  margin-bottom: 10px; /* 减少底部间距 */
  padding-bottom: 8px; /* 减少底部内边距 */
  border-bottom: 1px solid #ebeef5;
}

.device-name {
  font-size: 16px; /* 减小字体大小 */
  font-weight: 600;
  color: #303133;
}

.device-info {
  margin: 8px 0; /* 减少上下间距 */
  flex: 1; /* 让信息区域填充剩余空间 */
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px; /* 减少网格间距 */
  margin-bottom: 8px; /* 减少底部间距 */
}

.info-item {
  display: flex;
  flex-direction: column;
  gap: 2px; /* 减少内部元素间距 */
}

.info-item.full-width {
  grid-column: 1 / -1;
  border-top: 1px solid #ebeef5;
  padding-top: 8px; /* 减少上内边距 */
  margin-top: 4px;
}

.info-label {
  display: flex;
  align-items: center;
  gap: 4px;
  color: #909399;
  font-size: 12px; /* 减小字体大小 */
}

.info-label .el-icon {
  font-size: 14px; /* 减小图标大小 */
  color: #409eff;
}

.info-value {
  color: #303133;
  font-size: 13px; /* 减小字体大小 */
  font-weight: 500;
}

.remarks {
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 100%;
  color: #606266;
}

.device-actions {
  display: flex;
  justify-content: flex-end;
  gap: 8px; /* 减少按钮间距 */
  margin-top: 10px; /* 减少顶部间距 */
  padding-top: 10px; /* 减少顶部内边距 */
  border-top: 1px solid #ebeef5;
}

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

<script setup>
import { ref, reactive, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  listMonitoring,
  getMonitoring,
  delMonitoring,
  addMonitoring,
  updateMonitoring,
} from "@/api/devices/monitoring";
import { download } from "@/utils/request";
import {
  Search,
  Refresh,
  Plus,
  Edit,
  Delete,
  Download,
  Picture,
  Monitor,
  DataLine,
  Timer,
  InfoFilled,
} from "@element-plus/icons-vue";
import MapView from "@/components/View/index.vue";
import { useRouter } from "vue-router";

const router = useRouter();

const { proxy } = getCurrentInstance();

// 字典数据
const { device_status } = proxy.useDict("device_status");

// refs
const queryFormRef = ref(null);
const formRef = ref(null);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const open = ref(false);
const title = ref("");
const monitoringList = ref([]);

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  deviceName: null,
  status: null,
});

// 表单参数
const form = reactive({});

// 表单校验规则
const rules = reactive({
  deviceName: [
    { required: true, message: "设备名称不能为空", trigger: "blur" },
  ],
  status: [{ required: true, message: "设备状态不能为空", trigger: "change" }],
  longitude: [{ required: true, message: "经度不能为空", trigger: "blur" }],
  latitude: [{ required: true, message: "纬度不能为空", trigger: "blur" }],
});

// 添加新的响应式变量
const viewDialogVisible = ref(false);
const selectedDeviceId = ref(null);

// 查询列表
const getList = async () => {
  loading.value = true;
  try {
    const response = await listMonitoring(queryParams);
    monitoringList.value = response.rows;
    total.value = response.total;
  } finally {
    loading.value = false;
  }
};

// 表单重置
const reset = () => {
  form.monitoringDeviceId = null;
  form.deviceName = null;
  form.storageCapacity = null;
  form.status = null;
  form.icon = null;
  form.deviceImage = null;
  form.remarks = null;
  form.lastRecord = null;
  form.longitude = null;
  form.latitude = null;
  formRef.value?.resetFields();
};

// 取消按钮
const cancel = () => {
  open.value = false;
  reset();
};

// 搜索按钮操作
const handleQuery = () => {
  queryParams.pageNum = 1;
  getList();
};

// 重置按钮操作
const resetQuery = () => {
  queryFormRef.value?.resetFields();
  handleQuery();
};

// 多选框选中数据
const handleSelectionChange = (selection) => {
  ids.value = selection.map((item) => item.monitoringDeviceId);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
};

// 新增按钮操作
const handleAdd = () => {
  // reset();
  // open.value = true;
  // title.value = "添加监控设备";
  router.push("/devices/monitoring/add");
};

// 修改按钮操作
const handleUpdate = async (row) => {
  const monitoringDeviceId = row.monitoringDeviceId || ids.value;
  router.push(`/devices/monitoring-edit/index/${monitoringDeviceId}`);
};

// 提交按钮
const submitForm = () => {
  formRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        if (form.monitoringDeviceId != null) {
          await updateMonitoring(form);
          ElMessage.success("修改成功");
        } else {
          await addMonitoring(form);
          ElMessage.success("新增成功");
        }
        open.value = false;
        getList();
      } catch (error) {
        console.error("提交失败", error);
      }
    }
  });
};

// 删除按钮操作
const handleDelete = async (row) => {
  const monitoringDeviceIds = row.monitoringDeviceId || ids.value;
  try {
    await ElMessageBox.confirm(
      `是否确认删除监控设备编号为"${monitoringDeviceIds}"的数据项？`
    );
    await delMonitoring(monitoringDeviceIds);
    getList();
    ElMessage.success("删除成功");
  } catch (error) {
    console.error("删除失败", error);
  }
};

// 导出按钮操作
const handleExport = () => {
  download(
    "devices/monitoring/export",
    {
      ...queryParams,
    },
    `monitoring_${new Date().getTime()}.xlsx`
  );
};

// 查看按钮操作
const handleView = (row) => {
  selectedDeviceId.value = row.monitoringDeviceId;
  viewDialogVisible.value = true;
};

onMounted(() => {
  getList();
});
</script>
