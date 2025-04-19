<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['manage:germplasm:add']"
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
          v-hasPermi="['manage:germplasm:edit']"
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
          v-hasPermi="['manage:germplasm:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['manage:germplasm:export']"
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
      :data="germplasmList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="作物名称" align="center" prop="cropName" />
      <el-table-column label="作物英文名称" align="center" prop="cropEnName" />
      <el-table-column label="种质名称" align="center" prop="germplasmName" />
      <el-table-column
        label="种质英文名称"
        align="center"
        prop="germplasmEnName"
      />
      <el-table-column label="种质图片" align="center" prop="germplasmImg">
        <template #default="scope">
          <el-image
            v-if="scope.row.germplasmImg"
            style="width: 50px; height: 50px"
            :src="scope.row.germplasmImg"
            :preview-src-list="[scope.row.germplasmImg]"
            fit="cover"
          />
          <span v-else>暂无图片</span>
        </template>
      </el-table-column>
      <el-table-column label="宣传语" align="center" prop="germplasmDes" />
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
            v-hasPermi="['manage:germplasm:edit']"
            >修改</el-button
          >
          <el-button
            link
            type="primary"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:germplasm:remove']"
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

    <!-- 添加或修改种质系统对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form
        ref="germplasmRef"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="作物名称" prop="cropName">
          <el-input v-model="form.cropName" placeholder="请输入作物名称" />
        </el-form-item>
        <el-form-item label="英文名称" prop="cropEnName">
          <el-input
            v-model="form.cropEnName"
            placeholder="请输入作物英文名称"
          />
        </el-form-item>
        <el-form-item label="种质名称" prop="germplasmName">
          <el-input v-model="form.germplasmName" placeholder="请输入种质名称" />
        </el-form-item>
        <el-form-item label="英文名称" prop="germplasmEnName">
          <el-input
            v-model="form.germplasmEnName"
            placeholder="请输入种质英文名称"
          />
        </el-form-item>
        <el-form-item label="种质图片" prop="germplasmImg">
          <image-upload v-model="form.germplasmImg" />
        </el-form-item>
        <el-form-item label="宣传语" prop="germplasmDes">
          <el-input
            v-model="form.germplasmDes"
            type="textarea"
            placeholder="请输入宣传语"
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

<script setup name="Germplasm">
import {
  listGermplasm,
  getGermplasm,
  delGermplasm,
  addGermplasm,
  updateGermplasm,
} from "@/api/manage/germplasm";

const { proxy } = getCurrentInstance();

const germplasmList = ref([]);
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
    cropName: null,
    germplasmName: null,
  },
  rules: {
    cropName: [
      { required: true, message: "作物名称不能为空", trigger: "blur" },
    ],
    germplasmName: [
      { required: true, message: "种质名称不能为空", trigger: "blur" },
    ],
  },
});

const { queryParams, form, rules } = toRefs(data);

/** 查询种质系统列表 */
function getList() {
  loading.value = true;
  listGermplasm(queryParams.value).then((response) => {
    germplasmList.value = response.rows;
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
    germplasmId: null,
    cropName: null,
    cropEnName: null,
    germplasmName: null,
    germplasmEnName: null,
    germplasmImg: null,
    germplasmDes: null,
    remark: null,
    status: null,
    orderNum: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    delFlag: null,
  };
  proxy.resetForm("germplasmRef");
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
  ids.value = selection.map((item) => item.germplasmId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加种质系统";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _germplasmId = row.germplasmId || ids.value;
  getGermplasm(_germplasmId).then((response) => {
    form.value = response.data;
    open.value = true;
    title.value = "修改种质系统";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["germplasmRef"].validate((valid) => {
    if (valid) {
      if (form.value.germplasmId != null) {
        updateGermplasm(form.value).then((response) => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addGermplasm(form.value).then((response) => {
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
  const _germplasmIds = row.germplasmId || ids.value;
  proxy.$modal
    .confirm('是否确认删除种质系统编号为"' + _germplasmIds + '"的数据项？')
    .then(function () {
      return delGermplasm(_germplasmIds);
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
    "manage/germplasm/export",
    {
      ...queryParams.value,
    },
    `germplasm_${new Date().getTime()}.xlsx`
  );
}

getList();
</script>
