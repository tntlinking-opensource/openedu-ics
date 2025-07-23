<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学年" prop="year">
        <el-input
          v-model="queryParams.year"
          placeholder="请输入学年"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学期" prop="term">
        <el-input
          v-model="queryParams.term"
          placeholder="请输入学期"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="当前学期" prop="current">
        <el-select v-model="queryParams.current" placeholder="请选择是否当前学期" clearable size="small">
          <el-option
            v-for="dict in currentOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini"
                   @click="handleAdd" v-hasPermi="['school:teachCalendar']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
                   @click="handleUpdate" v-hasPermi="['school:teachCalendar']">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                   @click="handleDelete" v-hasPermi="['school:teachCalendar']">删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="teachCalendarList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="学年" align="center" prop="year"/>
      <el-table-column label="学期" align="center" prop="term"/>
      <el-table-column label="开始日期" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束日期" align="center" prop="endDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!--      <el-table-column label="是否当前学期" align="center" prop="current" :formatter="currentFormat"/>-->
      <el-table-column label="是否当前学期" align="center" prop="current">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.current"/>
        </template>
      </el-table-column>

      <el-table-column label="周数" align="center" prop="weekNum"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit"
                     @click="handleUpdate(scope.row)" v-hasPermi="['school:teachCalendar']">修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete"
                     @click="handleDelete(scope.row)" v-hasPermi="['school:teachCalendar']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize" @pagination="getList"/>

    <!-- 添加或修改学年学期对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" :close-on-click-modal="false" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <template v-if="!form.id">
          <el-form-item label="学年" prop="year">
            <el-input v-model="form.year" placeholder="请输入学年"/>
          </el-form-item>
          <el-form-item label="学期" prop="term">
            <el-input v-model="form.term" placeholder="请输入学期"/>
          </el-form-item>
        </template>
        <template v-else>
          <el-form-item label="学年" prop="year">{{ form.year }}</el-form-item>
          <el-form-item label="学期" prop="term">{{ form.term }}</el-form-item>
        </template>
        <el-form-item label="开始日期" prop="startDate">
          <el-date-picker clearable size="small"
                          v-model="form.startDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择开始日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束日期" prop="endDate">
          <el-date-picker clearable size="small"
                          v-model="form.endDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择结束日期">
          </el-date-picker>
        </el-form-item>
        <!--        <el-form-item label="周数" prop="weekNum">-->
        <!--          <el-input v-model="form.weekNum" placeholder="请输入周数"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="是否当前学期">
          <el-radio-group v-model="form.current">
            <el-radio v-for="dict in currentOptions" :key="dict.dictValue"
                      :label="dict.dictValue">{{ dict.dictLabel }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addTeachCalendar,
  delTeachCalendar,
  exportTeachCalendar,
  getTeachCalendar,
  listTeachCalendar,
  updateTeachCalendar
} from "@/api/teach/school/teachCalendar";

export default {
  name: "TeachCalendar",
  components: {},
  dicts: ['sys_yes_no'],
  data() {
    return {
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
      // 学年学期表格数据
      teachCalendarList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否当前学期字典
      currentOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        schoolId: null,
        year: null,
        term: null,
        startDate: null,
        endDate: null,
        current: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        schoolId: [
          {required: true, message: "学校ID不能为空", trigger: "blur"}
        ],
        year: [
          {required: true, message: "学年不能为空", trigger: "blur"}
        ],
        term: [
          {required: true, message: "学期不能为空", trigger: "blur"}
        ],
        startDate: [
          {required: true, message: "开始日期不能为空", trigger: "blur"}
        ],
        endDate: [
          {required: true, message: "开始日期不能为空", trigger: "blur"}
        ],
        current: [
          {required: true, message: "是否当前学期不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_yes_no").then(response => {
      this.currentOptions = response.data;
    });
  },
  methods: {
    /** 查询学年学期列表 */
    getList() {
      this.loading = true;
      listTeachCalendar(this.queryParams).then(response => {
        this.teachCalendarList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 是否当前学期字典翻译
    currentFormat(row, column) {
      return this.selectDictLabel(this.currentOptions, row.current);
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
        year: null,
        term: null,
        startDate: null,
        endDate: null,
        current: "Y"
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
      this.title = "添加学年学期";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id ? [row.id] : this.ids
      getTeachCalendar(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改学年学期";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTeachCalendar(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTeachCalendar(this.form).then(response => {
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
      this.$confirm('是否确认删除学年学期?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delTeachCalendar(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有学年学期数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.exportLoading = true;
        return exportTeachCalendar(queryParams);
      }).then(response => {
        this.download(response.msg);
        this.exportLoading = false;
      }).catch(() => {
      });
    }
  }
};
</script>
