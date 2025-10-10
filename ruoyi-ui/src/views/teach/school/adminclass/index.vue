<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学年" prop="year">
        <el-select v-model="queryParams.year" placeholder="学年" @change="handleQuery">
          <el-option v-for="v in years" :label="v" :value="v" :key="v"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年级" prop="grade">
        <el-select v-model="queryParams.grade" placeholder="请选择年级" clearable
                   size="small" @change="handleQuery">
          <el-option v-for="v in grades" :key="v.name" :label="v.name" :value="v.name"/>
        </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入名称" clearable size="small"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="班主任" prop="teacherName">
        <el-input v-model="queryParams.teacherName" placeholder="请输入班主任" clearable size="small"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete">
          删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="el-icon-upload2" size="mini" @click="handleImport">导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" :loading="exportLoading"
                   @click="handleExport">导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="adminclassList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="学年" align="center" prop="year"/>
      <el-table-column label="编号" align="center" prop="code"/>
      <el-table-column label="年级" align="center" prop="grade"/>
      <el-table-column label="名称" align="center" prop="name"/>
      <el-table-column label="学生人数" align="center" prop="studentNum"/>
      <el-table-column label="班主任" align="center" prop="teacherName"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize" @pagination="getList"/>
    <!-- 添加或修改班级对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px"
               :close-on-click-modal="false" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学年" prop="year">
          <!--          <el-input v-model="form.year" placeholder="请输入学年"/>-->
          <el-select v-model="form.year" placeholder="请选择学年">
            <el-option v-for="v in years" :key="v" :label="v" :value="v"/>
          </el-select>
        </el-form-item>
        <el-form-item label="年级" prop="grade">
          <el-input v-model="form.grade" placeholder="请输入年级"/>
        </el-form-item>
        <el-form-item label="编号" prop="code">
          <el-input v-model="form.code" placeholder="请输入编号" maxlengh="8"/>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称"/>
        </el-form-item>
        <el-form-item label="班主任" prop="teacherId">
          <el-select v-model="form.teacherId" placeholder="请选择班主任"
                     clearable filterable style="width: 100%;">
            <el-option v-for="v in teachers" :value="v.id" :key="v.id" :label="v.name">
              <span style="float: left">{{ v.name }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ v.code }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学院" prop="collegeId" v-hasRole="['manager']">
          <treeselect v-model="form.collegeId" :options="teachDepartmentOptions"
                      :normalizer="normalizer" placeholder="选择学院"/>
        </el-form-item>
        <el-form-item label="系部" prop="facultyId" v-hasRole="['manager']">
          <treeselect v-model="form.facultyId" :options="teachDepartmentOptions"
                      :normalizer="normalizer" placeholder="选择系部"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 班级导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px"
               :close-on-click-modal="false" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls"
                 :headers="upload.headers" :action="upload.url + '?updateSupport=' + upload.updateSupport"
                 :disabled="upload.isUploading" :on-progress="handleFileUploadProgress"
                 :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-link type="info" style="font-size:12px; color: blue;" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import service, {
  addAdminclass,
  delAdminclass,
  exportAdminclass,
  getAdminclass,
  listAdminclass,
  updateAdminclass
} from "@/api/teach/school/adminclass";
import importData from "@/utils/views/importData";
import {getEditDatas} from "../../../../api/teach/school/adminclass";
import {getCascaderOptions} from "@/api/teach/school/department";
import Treeselect from "@riophae/vue-treeselect";

export default {
  name: "Adminclass",
  mixins: [importData],
  components: {Treeselect},
  data() {
    return {
      // 弹出层标题
      title: "班级",
      url: process.env.VUE_APP_BASE_API + "/teach/school/adminclass",
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 班级表格数据
      adminclassList: [],
      teachers: [],
      // 是否显示弹出层
      open: false,
      // 状态字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        schoolId: null,
        year: null,
        grade: null,
        name: null,
        teacherName: null,
        status: null,
      },
      // 表单参数
      form: {
        collegeId: null,
        facultyId: null,
      },
      // 表单校验
      rules: {
        year: [{required: true, message: "学年不能为空", trigger: "blur"}],
        grade: [{required: true, message: "年级不能为空", trigger: "blur"}],
        code: [{required: true, message: "编号不能为空", trigger: "blur"}],
        name: [{required: true, message: "名称不能为空", trigger: "blur"}],
      },
      years: [],
      grades: [],
      teachDepartmentOptions: [],
    };
  },
  created() {
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
    getEditDatas().then(res => {
      this.years = res.years
      this.teachers = res.teachers
      this.grades = res.grades
      if (!this.queryParams.year) {
        this.queryParams.year = res.year
      }
      // let teachDepartmentOptions = [];
      // teachDepartmentOptions.push(this.handleTree(res.teachDepartments)[0]);
      // this.teachDepartmentOptions = teachDepartmentOptions
      if(res.teachDepartments.length){
        this.teachDepartmentOptions = this.handleTree(res.teachDepartments)[0].children
      }
      this.getList();
    })
  },
  methods: {
    getService() {
      return service
    },
    /** 查询班级列表 */
    getList() {
      this.loading = true;
      listAdminclass(this.queryParams).then(response => {
        this.adminclassList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        schoolId: null,
        year: this.queryParams.year,
        term: null,
        name: null,
        status: '0',
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加班级";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id ? [row.id] : this.ids
      getAdminclass(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改班级";
      });
    },
    /** 转换菜单数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.name,
        children: node.children
      };
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAdminclass(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAdminclass(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id ? [row.id] : this.ids;
      this.$confirm('是否确认删除班级?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delAdminclass(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有班级数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.exportLoading = true;
        return exportAdminclass(queryParams);
      }).then(response => {
        this.downloadFile(response.msg);
        this.exportLoading = false;
      }).catch(() => {
      });
    }
  }
};
</script>
