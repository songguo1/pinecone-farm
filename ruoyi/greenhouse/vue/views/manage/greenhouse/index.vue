<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryRef"
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
        <el-select
          v-model="queryParams.coverMaterial"
          placeholder="请选择覆盖材料"
          clearable
        >
          <el-option
            v-for="dict in agri_cover_material"
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
          v-hasPermi="['manage:greenhouse:add']"
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
          v-hasPermi="['manage:greenhouse:edit']"
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
          v-hasPermi="['manage:greenhouse:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['manage:greenhouse:export']"
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
      :data="greenhouseList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="大棚唯一标识"
        align="center"
        prop="greenhouseId"
      />
      <el-table-column label="大棚名称" align="center" prop="greenhouseName" />
      <el-table-column label="大棚结构类型" align="center" prop="structureType">
        <template #default="scope">
          <dict-tag
            :options="structure_type"
            :value="scope.row.structureType"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="是否配备加热系统"
        align="center"
        prop="heatingSystem"
      >
        <template #default="scope">
          <dict-tag
            :options="heating_system"
            :value="scope.row.heatingSystem"
          />
        </template>
      </el-table-column>
      <el-table-column label="覆盖材料" align="center" prop="coverMaterial">
        <template #default="scope">
          <dict-tag
            :options="agri_cover_material"
            :value="scope.row.coverMaterial"
          />
        </template>
      </el-table-column>
      <el-table-column label="备注信息" align="center" prop="remark" />
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
            v-hasPermi="['manage:greenhouse:edit']"
            >修改</el-button
          >
          <el-button
            link
            type="primary"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:greenhouse:remove']"
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

    <!-- 添加或修改大棚信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form
        ref="greenhouseRef"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
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
          <el-select v-model="form.coverMaterial" placeholder="请选择覆盖材料">
            <el-option
              v-for="dict in agri_cover_material"
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
  </div>
</template>

<script setup name="Greenhouse">
import {
  listGreenhouse,
  getGreenhouse,
  delGreenhouse,
  addGreenhouse,
  updateGreenhouse,
} from "@/api/manage/greenhouse";

const { proxy } = getCurrentInstance();
const { heating_system, structure_type, agri_cover_material } = proxy.useDict(
  "heating_system",
  "structure_type",
  "agri_cover_material"
);

const greenhouseList = ref([]);
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
    greenhouseName: null,
    structureType: null,
    heatingSystem: null,
    coverMaterial: null,
  },
  rules: {},
});

const { queryParams, form, rules } = toRefs(data);

/** 查询大棚信息列表 */
function getList() {
  loading.value = true;
  listGreenhouse(queryParams.value).then((response) => {
    greenhouseList.value = response.rows;
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
  proxy.resetForm("greenhouseRef");
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
  ids.value = selection.map((item) => item.greenhouseId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加大棚信息";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _greenhouseId = row.greenhouseId || ids.value;
  getGreenhouse(_greenhouseId).then((response) => {
    form.value = response.data;
    open.value = true;
    title.value = "修改大棚信息";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["greenhouseRef"].validate((valid) => {
    if (valid) {
      if (form.value.greenhouseId != null) {
        updateGreenhouse(form.value).then((response) => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addGreenhouse(form.value).then((response) => {
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
  const _greenhouseIds = row.greenhouseId || ids.value;
  proxy.$modal
    .confirm('是否确认删除大棚信息编号为"' + _greenhouseIds + '"的数据项？')
    .then(function () {
      return delGreenhouse(_greenhouseIds);
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
    "manage/greenhouse/export",
    {
      ...queryParams.value,
    },
    `greenhouse_${new Date().getTime()}.xlsx`
  );
}

getList();
</script>
