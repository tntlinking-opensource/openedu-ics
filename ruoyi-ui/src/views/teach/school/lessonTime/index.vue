<template>
  <div class="app-container">
<!--    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch"
             label-width="80px" size="mini">
      <el-form-item label="名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入名称" clearable size="small"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="上午/下午" prop="category">
        <el-select v-model="queryParams.category" placeholder="请选择上午/下午" clearable size="small">
          <el-option v-for="v in categoryOptions" :key="v.dictValue"
                     :label="v.dictLabel" :value="v.dictValue"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>-->

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini"
                   @click="handleAdd" v-hasPermi="['teach:lessonTime']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
                   @click="handleUpdate" v-hasPermi="['teach:lessonTime']">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                   @click="handleDelete" v-hasPermi="['teach:lessonTime']">删除
        </el-button>
      </el-col>
<!--      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini"
                   :loading="exportLoading" @click="handleExport" v-hasPermi="['teach:lessonTime']">导出
        </el-button>
      </el-col>-->
<!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    </el-row>

    <el-table v-loading="loading" :data="lessonTimeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="排序" align="center" prop="sort"/>
      <el-table-column label="名称" align="center" prop="name"/>
      <el-table-column label="节次" align="center" prop="timeIndex"/>
      <el-table-column label="开始时间" align="center" prop="startTime"/>
      <el-table-column label="结束时间" align="center" prop="endTime"/>
      <el-table-column label="上午/下午" align="center" prop="category" :formatter="categoryFormat"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit"
                     @click="handleUpdate(scope.row)" v-hasPermi="['teach:lessonTime']">修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete"
                     @click="handleDelete(scope.row)" v-hasPermi="['teach:lessonTime']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--    <pagination-->
    <!--      v-show="total>0"-->
    <!--      :total="total"-->
    <!--      :page.sync="queryParams.pageNum"-->
    <!--      :limit.sync="queryParams.pageSize"-->
    <!--      @pagination="getList"-->
    <!--    />-->

    <!-- 添加或修改上课节次对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" :close-on-click-modal="false" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序"/>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称"/>
        </el-form-item>
        <el-form-item label="节次类型" prop="lessonType">
          <el-radio-group v-model="form.lessonType" @change="toLessonTypeValue">
            <el-radio
              v-for="dict in lessonTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{ dict.dictLabel }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <!--        <el-form-item label="节次" prop="timeIndex" v-show="lessonTypeValue" >-->
        <!--          <el-input v-model="form.timeIndex" placeholder="请输入节次"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="开始时间" prop="startTime">
          <el-input v-model="form.startTime" placeholder="请输入开始时间"/>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-input v-model="form.endTime" placeholder="请输入结束时间"/>
        </el-form-item>
        <el-form-item label="上午/下午">
          <el-radio-group v-model="form.category">
            <el-radio
              v-for="dict in categoryOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{ dict.dictLabel }}
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
import {addLessonTime, delLessonTime, exportLessonTime, getIndexDatas, getLessonTime, listLessonTime, updateLessonTime} from "@/api/teach/school/lessonTime";

export default {
  name: "LessonTime",
  components: {},
  data() {
    return {
      //节次显示
      lessonTypeValue: true,
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
      // 上课节次表格数据
      lessonTimeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 上午/下午字典
      categoryOptions: [],
      // 节次类型字典
      lessonTypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        category: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        sort: [{required: true, message: "排序不能为空", trigger: "blur"}],
        lessonType: [{required: true, message: "节次类型不能为空", trigger: "blur"}],
        name: [{required: true, message: "名称不能为空", trigger: "blur"}],
        category: [{required: true, message: "上午/下午不能为空", trigger: "blur"}]
      }
    };
  },
  created() {
    getIndexDatas().then(() => {
      this.getList()
    })
    this.getDicts("lesson_category").then(response => {
      this.categoryOptions = response.data;
    });
    this.getDicts("lesson_type").then(response => {
      this.lessonTypeOptions = response.data;
    });
  },
  methods: {
    /** 查询上课节次列表 */
    getList() {
      this.loading = true;
      listLessonTime(this.queryParams).then(response => {
        this.lessonTimeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    toLessonTypeValue(item) {
      if (item != '课程') {
        this.lessonTypeValue = false
      } else {
        this.lessonTypeValue = true
      }
    },
    // 上午/下午字典翻译
    categoryFormat(row, column) {
      return this.selectDictLabel(this.categoryOptions, row.category);
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
        name: null,
        category: "0"
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
      this.title = "添加上课节次";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id ? [row.id] : this.ids
      getLessonTime(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改上课节次";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateLessonTime(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLessonTime(this.form).then(response => {
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
      this.$confirm('是否确认删除上课节次?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delLessonTime(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有上课节次数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.exportLoading = true;
        return exportLessonTime(queryParams);
      }).then(response => {
        this.download(response.msg);
        this.exportLoading = false;
      }).catch(() => {
      });
    }
  }
};
</script>
