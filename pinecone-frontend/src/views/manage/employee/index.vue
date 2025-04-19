<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryRef"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="员工类型" prop="employeeType">
        <el-select
          v-model="queryParams.employeeType"
          placeholder="请选择员工类型"
          clearable
        >
          <el-option
            v-for="dict in agri_employee_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="性别" prop="employeeSex">
        <el-select
          v-model="queryParams.employeeSex"
          placeholder="请选择性别"
          clearable
        >
          <el-option
            v-for="dict in sys_user_sex"
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
          v-hasPermi="['manage:employee:add']"
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
          v-hasPermi="['manage:employee:edit']"
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
          v-hasPermi="['manage:employee:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['manage:employee:export']"
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
      :data="employeeList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编码" align="center" prop="employeeCode" />
      <el-table-column label="姓名" align="center" prop="employeeName" />
      <el-table-column label="员工类型" align="center" prop="employeeType">
        <template #default="scope">
          <dict-tag
            :options="agri_employee_type"
            :value="scope.row.employeeType"
          />
        </template>
      </el-table-column>
      <el-table-column label="手机号码" align="center" prop="employeeTel" />
      <el-table-column label="性别" align="center" prop="employeeSex">
        <template #default="scope">
          <dict-tag :options="sys_user_sex" :value="scope.row.employeeSex" />
        </template>
      </el-table-column>
      <el-table-column label="地址" align="center" prop="employeeAddress" />
      <el-table-column label="备注" align="center" prop="remark" />
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
            v-hasPermi="['manage:employee:edit']"
            >修改</el-button
          >
          <el-button
            link
            type="primary"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:employee:remove']"
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

    <!-- 添加或修改员工管理对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form
        ref="employeeRef"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="编码" prop="employeeCode">
          <el-input v-model="form.employeeCode" placeholder="请输入员工编码" />
        </el-form-item>
        <el-form-item label="姓名" prop="employeeName">
          <el-input v-model="form.employeeName" placeholder="请输入员工姓名" />
        </el-form-item>
        <el-form-item label="员工类型" prop="employeeType">
          <el-select v-model="form.employeeType" placeholder="请选择员工类型">
            <el-option
              v-for="dict in agri_employee_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="手机号码" prop="employeeTel">
          <el-input v-model="form.employeeTel" placeholder="请输入手机号码" />
        </el-form-item>
        <el-form-item label="性别" prop="employeeSex">
          <el-select v-model="form.employeeSex" placeholder="请选择性别">
            <el-option
              v-for="dict in sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="地址" prop="employeeAddress">
          <el-input
            v-model="form.employeeAddress"
            type="textarea"
            placeholder="请输入地址"
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

<script setup name="Employee">
import {
  listEmployee,
  getEmployee,
  delEmployee,
  addEmployee,
  updateEmployee,
} from "@/api/manage/employee";

const { proxy } = getCurrentInstance();
const { agri_employee_type, sys_user_sex } = proxy.useDict(
  "agri_employee_type",
  "sys_user_sex"
);

const employeeList = ref([]);
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
    employeeCode: null,
    employeeName: null,
    employeeType: null,
    employeeTel: null,
    employeeSex: null,
  },
  rules: {
    employeeCode: [
      { required: true, message: "编码不能为空", trigger: "blur" },
    ],
    employeeName: [
      { required: true, message: "姓名不能为空", trigger: "blur" },
    ],
    employeeType: [
      { required: true, message: "员工类型不能为空", trigger: "change" },
    ],
    employeeSex: [
      { required: true, message: "性别不能为空", trigger: "change" },
    ],
  },
});

const { queryParams, form, rules } = toRefs(data);

/** 查询员工管理列表 */
function getList() {
  loading.value = true;
  listEmployee(queryParams.value).then((response) => {
    employeeList.value = response.rows;
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
    employeeId: null,
    employeeCode: null,
    employeeName: null,
    employeeType: null,
    employeeTel: null,
    employeeSex: null,
    employeeAddress: null,
    remark: null,
    status: null,
    orderNum: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    delFlag: null,
  };
  proxy.resetForm("employeeRef");
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
  ids.value = selection.map((item) => item.employeeId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加员工管理";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _employeeId = row.employeeId || ids.value;
  getEmployee(_employeeId).then((response) => {
    form.value = response.data;
    open.value = true;
    title.value = "修改员工管理";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["employeeRef"].validate((valid) => {
    if (valid) {
      if (form.value.employeeId != null) {
        updateEmployee(form.value).then((response) => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addEmployee(form.value).then((response) => {
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
  const _employeeIds = row.employeeId || ids.value;
  proxy.$modal
    .confirm('是否确认删除员工管理编号为"' + _employeeIds + '"的数据项？')
    .then(function () {
      return delEmployee(_employeeIds);
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
    "manage/employee/export",
    {
      ...queryParams.value,
    },
    `employee_${new Date().getTime()}.xlsx`
  );
}

getList();
</script>
