<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学年学期" prop="teachCalendarId">
        <el-select v-model="queryParams.teachCalendarId" placeholder="请选择学年学期" size="small"
                   @change="handleQuery">
          <el-option v-for="v in teachCalendars" :key="v.id" :label="v.name" :value="v.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="年级" prop="grade">
        <el-select v-model="queryParams.grade" placeholder="请选择班级" clearable size="small"
                   @change="handleQuery">
          <el-option v-for="v in grades" :key="v.name" :label="v.name" :value="v.name"/>
        </el-select>
      </el-form-item>
      <el-form-item label="班级" prop="adminclassId">
        <el-select v-model="queryParams.adminclassId" placeholder="请选择班级" clearable size="small"
                   @change="handleQuery">
          <el-option v-for="v in adminclasses" :key="v.id" :label="v.name" :value="v.id"/>
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="星期" prop="weekday">
              <el-select v-model="queryParams.weekday" placeholder="请选择星期" clearable filterable size="small"
                         @change="handleQuery">
                <el-option v-for="v in weekdayOptions" :key="v.dictValue"
                           :label="v.dictLabel" :value="v.dictLabel"/>
              </el-select>
            </el-form-item>-->
      <el-form-item label="课程" prop="courseId">
        <el-select v-model="queryParams.courseId" placeholder="请选择课程" clearable size="small"
                   @change="handleQuery">
          <el-option v-for="v in courses" :key="v.id" :label="v.name" :value="v.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="教师" prop="teacher">
        <el-input v-model="queryParams.teacher" placeholder="请输入教师" clearable size="small"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini"
                   @click="handleAdd" v-hasPermi="['teach.school:lesson']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                   @click="handleDelete" v-hasPermi="['teach.school:lesson']">删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="el-icon-upload2" size="mini"
                   @click="handleImport" v-hasPermi="['teach.school:lesson']">导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" :loading="exportLoading"
                   @click="handleExport" v-hasPermi="['teach.school:lesson']">导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="lessonList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="学年" align="center" prop="year"/>
      <el-table-column label="学期" align="center" prop="term"/>
      <el-table-column label="年级" align="center" prop="grade"/>
      <el-table-column label="班级" align="center" prop="adminclass"/>
      <!--      <el-table-column label="星期" align="center" prop="weekday"/>-->
      <!--      <el-table-column label="开始小节" align="center" prop="timeStart"/>-->
      <!--      <el-table-column label="结束小节" align="center" prop="timeEnd"/>-->
      <el-table-column label="课程" align="center" prop="course"/>
      <el-table-column label="课时" align="center" prop="hour"/>
      <el-table-column label="教师" align="center" prop="teachers"/>
      <el-table-column label="周类型" prop="weekType"/>
      <el-table-column label="课程安排" prop="times"/>
      <el-table-column label="教室" align="center" prop="classroom"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit"
                     @click="handleUpdate(scope.row)" v-hasPermi="['teach.school:lesson']">修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete"
                     @click="handleDelete(scope.row)" v-hasPermi="['teach.school:lesson']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList"/>

    <!-- 添加或修改课表管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" :close-on-click-modal="false" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="学年学期" prop="teachCalendarId">
          <el-select v-model="form.teachCalendarId" placeholder="请选择学年学期">
            <el-option v-for="v in teachCalendars" :key="v.id" :label="v.name" :value="v.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="班级" prop="adminclassId">
          <el-select v-model="form.adminclassId" placeholder="请选择班级">
            <el-option v-for="v in adminclasses" :key="v.id" :label="v.name" :value="v.id"/>
          </el-select>
        </el-form-item>
        <!--        <el-form-item label="星期">
                  <el-radio-group v-model="form.weekday">
                    <el-radio v-for="v in weekdayOptions" :key="v.dictValue"
                              :label="v.dictLabel">{{ v.dictLabel }}
                    </el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="开始小节" prop="timeStart">
                  <el-select v-model="form.timeStart" placeholder="请选择开始小节">
                    <el-option v-for="v in times" :key="v.id" :label="v.name" :value="v.name"/>
                  </el-select>
                </el-form-item>
                <el-form-item label="结束小节" prop="timeEnd">
                  <el-select v-model="form.timeEnd" placeholder="请选择结束小节">
                    <el-option v-for="v in times" :key="v.id" :label="v.name" :value="v.name"/>
                  </el-select>
                </el-form-item>-->
        <el-form-item label="课程" prop="courseId">
          <el-select v-model="form.courseId" placeholder="请选择课程">
            <el-option v-for="v in courses" :key="v.id" :label="v.name" :value="v.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="课时" prop="hour">
          <el-input v-model="form.hour" placeholder="请输入课时"/>
        </el-form-item>
        <el-form-item label="主讲教师" prop="teacherId">
          <el-select v-model="form.teacherId" placeholder="请选择教师" filterable>
            <el-option v-for="v in teachers" :key="v.id" :label="v.label" :value="v.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="辅讲教师" prop="teacherId">
          <el-select v-model="form.teacher2Id" placeholder="请选择教师" filterable clearable>
            <el-option v-for="v in teachers" :key="v.id" :label="v.label" :value="v.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="周类型" prop="weekType">
          <el-radio-group v-model="form.weekType">
            <el-radio v-for="v in weekTypes" :key="v.dictValue" :label="v.dictLabel">{{ v.dictLabel }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="周次" v-if="form.weekType == '自定义'">
          <el-row>
            <el-checkbox-group v-model="form.weeks">
              <el-col v-for="v in weeks" :key="v" :span="3">
                <el-checkbox :key="v" :label="v"> {{ v }}</el-checkbox>
              </el-col>
            </el-checkbox-group>
          </el-row>
        </el-form-item>
        <el-form-item label="课程安排" prop="times">
          <el-input v-model="form.times" placeholder="请输入课程安排"/>
          <div style="font-size: 12px;">如：1-1、1-2、2-3、2-4</div>
        </el-form-item>
        <el-form-item label="新增课时安排" prop="times">
          <el-input v-model="form.timesInclude" placeholder="请输入新增课时安排"/>
          <div style="font-size: 12px;">如第18周星期一第三节：18-1-3</div>
        </el-form-item>
        <el-form-item label="排除课时安排" prop="times">
          <el-input v-model="form.timesExclude" placeholder="请输入排除课时安排"/>
        </el-form-item>
        <el-form-item label="教室" prop="classroom">
          <el-input v-model="form.classroom" placeholder="请输入教室"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 课表导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px"
               :close-on-click-modal="false" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls"
                 :headers="upload.headers" :action="upload.url"
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
        <!--        <div slot="tip" style="margin: 15px 0;">
                  <label>导入格式：</label>
                  <el-radio-group v-model="excelFormat">
                    <el-radio :label="'system'">系统默认</el-radio>
                    <el-radio :label="'yunxiao'">云校导出格式</el-radio>
                  </el-radio-group>
                </div>-->
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="excelFormat == 'system'" type="primary" @click="submitFileForm">确 定</el-button>
        <el-button v-if="excelFormat == 'yunxiao'" type="primary" @click="submitFileFormYunXiao" :loading="upload.isUploading">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import service, {addLesson, delLesson, exportLesson, getDatas, getLesson, listLesson, updateLesson} from "@/api/teach/school/lesson";
import importData from "@/utils/views/importData";

export default {
  name: "Lesson",
  mixins: [importData],
  data() {
    return {
      // 弹出层标题
      title: "课表",
      url: process.env.VUE_APP_BASE_API + "/teach/school/lesson",
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
      // 课表管理表格数据
      lessonList: [],
      // 是否显示弹出层
      open: false,
      weekdayOptions: [],
      weekTypes: [],
      teachCalendars: [],
      grades: [],
      adminclasses: [],
      courses: [],
      teachers: [],
      times: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        teachCalendarId: null,
        adminclass: null,
        weekday: null,
        timeStart: null,
        timeEnd: null,
        course: null,
        teacher: null,
        classroom: null
      },
      // 表单参数
      form: {
        teachCalendarId: null,
        weekType: null,
        weeks: [],
      },
      // 表单校验
      rules: {
        teachCalendarId: [{required: true, message: "学年学期不能为空", trigger: "change"}],
        weekday: [{required: true, message: "星期不能为空", trigger: "blur"}],
        adminclassId: [{required: true, message: "班级不能为空", trigger: "change"}],
        timeStart: [{required: true, message: "开始小节不能为空", trigger: "change"}],
        timeEnd: [{required: true, message: "结束小节不能为空", trigger: "change"}],
        courseId: [{required: true, message: "课程不能为空", trigger: "change"}],
        weekType: [{required: true, message: "周类型不能为空", trigger: "change"}],
        // teacherId: [{required: true, message: "教师不能为空", trigger: "change"}],
      },
      excelFormat: 'yunxiao',
      weeks: [],
    };
  },
  created() {
    for (let i = 0; i < 20; i++) {
      this.weeks.push(String(i + 1));
    }
    this.getDatas();
    //this.getList();
    this.getDicts("weekday").then(response => {
      this.weekdayOptions = response.data;
    });
    this.getDicts("lesson_week_type").then(response => {
      this.weekTypes = response.data;
    });
  },
  methods: {
    getService() {
      return service
    },
    getDatas() {
      getDatas().then(res => {
        Object.assign(this, res)
        this.queryParams.teachCalendarId = res.teachCalendarId
        this.getList();
      });
    },
    /** 查询课表管理列表 */
    getList() {
      this.loading = true;
      listLesson(this.queryParams).then(response => {
        this.lessonList = response.rows;
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
      //this.resetForm("form");
      this.form = {
        id: null,
        schoolId: null,
        year: null,
        term: null,
        teachCalendarId: this.queryParams.teachCalendarId,
        adminclass: null,
        weekday: "0",
        adminclassId: null,
        timeStart: null,
        timeEnd: null,
        course: null,
        courseId: null,
        teacher: null,
        teacherId: null,
        classroom: null
      };
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
      getLesson().then(res => {
        this.open = true
        this.title = "添加课表"
        this.form = res.data
        this.form.teachCalendarId = this.queryParams.teachCalendarId
        this.$nextTick(() => {
          this.$refs["form"].clearValidate()
        })
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const id = row.id ? [row.id] : this.ids
      getLesson(id).then(res => {
        let form = res.data;
        form.weeks = form.weeks ? form.weeks.split(',') : []
        console.log('form', form)
        this.form = form;
        this.open = true;
        this.title = "修改课表";
        this.$nextTick(() => {
          this.$refs["form"].clearValidate()
        })
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let form = JSON.parse(JSON.stringify(this.form))
          // form.weeks = form.weeks.join(',')
          form.weeks = form.weeks ? form.weeks.join(',') : ''
          if (this.form.id != null) {
            updateLesson(form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLesson(form).then(response => {
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
      console.log('ids', ids, row)
      this.$confirm('是否确认删除课表管理?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delLesson(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有课表管理数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.exportLoading = true;
        return exportLesson(queryParams);
      }).then(response => {
        this.download(response.msg);
        this.exportLoading = false;
      }).catch(() => {
      });
    },
    importTemplate() {
      this.download('/data/teach/school/lesson.xlsx', '课表信息导入模板.xlsx');
    },
    submitFileFormYunXiao() {
      this.upload.url = this.url + '/importDataYunXiao';
      // console.log('this.upload.url', this.upload.url)
      this.$nextTick(() => {
        this.submitFileForm()
      })
    }
  }
};
</script>
