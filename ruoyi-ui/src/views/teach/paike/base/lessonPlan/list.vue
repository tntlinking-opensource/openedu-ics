<template>
  <el-dialog title="编辑任课信息" :visible="open" width="1200px" :close-on-click-modal="false"
             append-to-body @close="close">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="年级">
        <el-select v-model="queryParams.grade" placeholder="请选择年级" size="small" clearable @change="handleQuery">
          <el-option v-for="v in grades" :key="v" :label="v" :value="v"/>
        </el-select>
      </el-form-item>
      <el-form-item label="班级" prop="name">
        <el-input v-model="queryParams.adminclass" placeholder="请输入班级" clearable size="small"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="课程">
        <el-select v-model="queryParams.course" placeholder="请选择课程" clearable size="small" @change="handleQuery">
          <el-option v-for="v in courses" :key="v" :label="v" :value="v"/>
        </el-select>
      </el-form-item>
      <el-form-item label="任课教师" prop="name">
        <el-input v-model="queryParams.teacher" placeholder="请输入任课教师" clearable size="small"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-edit" size="mini" :disabled="multiple"
                   @click="handleUpdateTeacher">修改任课教师
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                   @click="handleDelete">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="lessonPlanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="年级" prop="grade" align="center"/>
      <el-table-column label="班级" prop="adminclass" align="center"/>
      <el-table-column label="课程" prop="course" align="center"/>
      <el-table-column label="课时" prop="hours" align="center"/>
      <el-table-column label="任课教师" prop="teacher" align="center"/>
      <el-table-column label="操作" prop="操作" align="center" width="120">
        <template slot-scope="scope">
          <div>
            <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize" @pagination="getList"/>

    <lesson-plan-list-edit v-if="editOpen" :open.sync="editOpen" :id="editId" @submit="getList"></lesson-plan-list-edit>
    <lesson-plan-list-teacher-edit v-if="editTeacherOpen" :open.sync="editTeacherOpen"
                                   :ids="ids" @submit="getList"></lesson-plan-list-teacher-edit>
  </el-dialog>
</template>

<script>
import {getIndexDatas, listLessonPlanPage} from "@/api/teach/paike/base/lessonPlan"
import indexMixin from "@/utils/views/indexMixin"
import LessonPlanListTeacherEdit from "./listTeacherEdit";
import {delLessonPlan} from "../../../../../api/teach/paike/base/lessonPlan";
import LessonPlanListEdit from "./listEdit";

export default {
  name: "LessonPlanList",
  components: {LessonPlanListEdit, LessonPlanListTeacherEdit},
  mixins: [indexMixin],
  props: ['open'],
  data() {
    return {
      // 任课信息表格数据
      lessonPlanList: [],
      // 查询参数
      queryParams: {
        grade: null,
        adminclass: null,
        course: null,
        teacher: null
      },
      grades: [],
      courses: [],
      editTeacherOpen: false,
      entity: {},
    }
  },
  computed: {},
  created() {
    getIndexDatas().then(res => {
      Object.assign(this, res)
      this.getList()
    })
  },
  methods: {
    /** 查询任课信息列表 */
    getList() {
      this.loading = true
      listLessonPlanPage(this.queryParams).then(response => {
        this.lessonPlanList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleUpdateTeacher() {
      this.editTeacherOpen = true
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id ? [row.id] : this.ids;
      this.$confirm('是否确认删除任课信息?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delLessonPlan(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess("删除成功")
      }).catch(() => {
      })
    },
    // 取消按钮
    close() {
      this.$emit('update:open', false)
    },
  }
}
</script>
