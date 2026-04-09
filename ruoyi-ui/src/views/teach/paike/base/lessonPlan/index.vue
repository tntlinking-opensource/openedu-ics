<template>
  <div>
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <!--      <el-form-item label="年级" prop="grade">
              <el-input v-model="queryParams.grade" placeholder="请输入年级" clearable
                        size="small"
                        @keyup.enter.native="handleQuery"/>
            </el-form-item>-->
      <el-form-item label="年级">
        <el-select v-model="queryParams.grade" placeholder="请选择年级" size="small" @change="handleQuery">
          <el-option v-for="v in grades" :key="v" :label="v" :value="v"/>
        </el-select>
      </el-form-item>
      <!--      <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>-->
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini"
                   @click="handleAdd" v-hasPermi="['teach.paike:lessonPlan']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-edit" size="mini"
                   @click="handleList" v-hasPermi="['teach.paike:lessonPlan']">编辑任课信息
        </el-button>
      </el-col>
      <!--      <el-col :span="1.5">
              <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                         @click="handleDelete" v-hasPermi="['teach.paike:lessonPlan']">删除
              </el-button>
            </el-col>-->
      <el-col :span="1.5">
        <el-button type="info" plain icon="el-icon-upload2" size="mini"
                   @click="handleImport" v-hasPermi="['teach.paike:lessonPlan']">导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" :loading="exportLoading"
                   @click="handleExport" v-hasPermi="['teach.paike:lessonPlan']">导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="adminclasses" v-if="showTable">
      <!--      <el-table-column type="selection" width="55" align="center"/>-->
      <!--      <el-table-column label="年级" prop="grade" align="center"/>-->
      <!--      <el-table-column label="班级" prop="adminclass" align="center"/>-->
      <el-table-column label="班级" prop="adminclass" align="center"/>
      <template v-for="course in courses">
        <el-table-column :label="course" align="center">
          <template slot-scope="scope">
            <div v-for="v in getCourse(scope.row.adminclass, course)">
              <div v-if="v.teacher">{{ v.teacher }}({{ v.hours }})</div>
              <div v-else>{{ v.hours }}</div>
            </div>
          </template>
        </el-table-column>
      </template>
      <el-table-column label="操作" prop="操作" align="center" width="120">
        <template slot-scope="scope">
          <div>
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <!--    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum"
                    :limit.sync="queryParams.pageSize" @pagination="getList"/>-->

    <LessonPlanEdit v-if="editOpen" :open.sync="editOpen" :entity="entity" @submit="getList"></LessonPlanEdit>
    <LessonPlanUpload v-if="uploadOpen" :open.sync="uploadOpen" @submit="getList"></LessonPlanUpload>
    <LessonPlanList v-if="listOpen" :open.sync="listOpen" @submit="getList"></LessonPlanList>
  </div>
</template>

<script>
import service, {
  deleteByAdminclass,
  exportLessonPlan,
  getIndexDatas,
  listLessonPlan
} from "@/api/teach/paike/base/lessonPlan"
import indexMixin from "@/utils/views/indexMixin"
import LessonPlanEdit from "./edit"
import LessonPlanUpload from "./upload"
import {getByKey} from "../../../../../utils/ruoyi";
import LessonPlanList from "./list";

export default {
  name: "LessonPlan",
  mixins: [indexMixin],
  components: {LessonPlanList, LessonPlanEdit, LessonPlanUpload},
  data() {
    return {
      showTable: true,
      listOpen: false,
      // 任课信息表格数据
      lessonPlanList: [],
      // 查询参数
      queryParams: {
        schoolId: null,
        lessonScheduleId: null,
        grade: null,
        adminclass: null,
        course: null,
        hours: null,
        teacher: null
      },
      grades: [],
      entity: {},
    }
  },
  computed: {
    adminclasses: function () {
      return getByKey(this.lessonPlanList, 'adminclass').map(adminclass => {
        return {adminclass}
      })
    },
    courses: function () {
      let courses = getByKey(this.lessonPlanList, 'course')
      courses.sort(function (item1, item2) {
        return item1.localeCompare(item2, 'zh-CN');
      })
      return courses
    }
  },
  created() {
    getIndexDatas().then(res => {
      // Object.assign(this, res)
      this.grades = res.grades
      if (this.grades.length > 0) {
        this.queryParams.grade = this.grades[0]
      }
      this.getList()
    })
  },
  methods: {
    getService() {
      return service
    },
    getCourse(adminclass, course) {
      let plans = this.lessonPlanList.filter(p => p.adminclass == adminclass && p.course == course)
      // if(course == '社团'){
      //   console.log(adminclass, course, plans)
      // }
      return plans
    },
    /** 查询任课信息列表 */
    getList() {
      this.loading = true
      this.showTable = false
      listLessonPlan(this.queryParams).then(response => {
        this.lessonPlanList = response.rows
        this.total = response.total
        this.loading = false
        this.showTable = true
      })
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.entity = {}
      this.editOpen = true
    },
    handleList() {
      this.listOpen = true
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      let entity = {grade: this.queryParams.grade}
      entity.adminclass = row.adminclass
      this.entity = entity
      this.editOpen = true
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      // const ids = row.id || this.ids
      // this.$confirm('是否确认删除任课信息?', "警告", {
      //   confirmButtonText: "确定",
      //   cancelButtonText: "取消",
      //   type: "warning"
      // }).then(function () {
      //   return delLessonPlan(ids)
      // }).then(() => {
      //   this.getList()
      //   this.msgSuccess("删除成功")
      // }).catch(() => {
      // })
      let data = {adminclass: row.adminclass}
      console.log('handleDelete', row, data)
      this.$confirm('是否确认删除任课信息?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        return deleteByAdminclass(data)
      }).then(() => {
        this.getList()
        this.msgSuccess("删除成功")
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      this.$confirm('是否确认导出所有任课信息数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.exportLoading = true
        return exportLessonPlan(queryParams)
      }).then(response => {
        this.downloadFile(response.msg)
        this.exportLoading = false
      }).catch(() => {
      })
    }
  }
}
</script>
