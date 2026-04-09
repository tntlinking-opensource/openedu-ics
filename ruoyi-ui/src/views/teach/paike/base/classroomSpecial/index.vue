<template>
  <div>
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="课程" prop="course">
        <el-input v-model="queryParams.course" placeholder="请输入课程" clearable
                  size="small"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="教学楼" prop="building">
        <el-input v-model="queryParams.building" placeholder="请输入教学楼" clearable
                  size="small"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="教室号" prop="room">
        <el-input v-model="queryParams.room" placeholder="请输入教室号" clearable
                  size="small"
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
                   @click="handleAdd" v-hasPermi="['teach.paike:classroomSpecial']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                   @click="handleDelete" v-hasPermi="['teach.paike:classroomSpecial']">删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="el-icon-upload2" size="mini"
                   @click="handleImport" v-hasPermi="['teach.paike:classroomSpecial']">导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" :loading="exportLoading"
                   @click="handleExport" v-hasPermi="['teach.paike:classroomSpecial']">导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="classroomSpecialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="课程" prop="course" align="center"/>
      <el-table-column label="教学楼" prop="building" align="center"/>
      <el-table-column label="教室号" prop="room" align="center"/>
      <el-table-column label="最大开班数" prop="lessonLimit" align="center"/>
      <el-table-column label="年级" prop="grade" align="center"/>
      <el-table-column label="班级" prop="adminclass" align="center"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit"
                     @click="handleUpdate(scope.row)" v-hasPermi="['teach.paike:classroomSpecial']">修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete"
                     @click="handleDelete(scope.row)" v-hasPermi="['teach.paike:classroomSpecial']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize" @pagination="getList"/>

    <ClassroomSpecialEdit v-if="editOpen" :open.sync="editOpen" :id="editId" @submit="getList"></ClassroomSpecialEdit>
    <ClassroomSpecialUpload v-if="uploadOpen" :open.sync="uploadOpen" @submit="getList"></ClassroomSpecialUpload>
  </div>
</template>

<script>
import service, {
  delClassroomSpecial,
  exportClassroomSpecial,
  listClassroomSpecial
} from "@/api/teach/paike/base/classroomSpecial"
import indexMixin from "@/utils/views/indexMixin"
import ClassroomSpecialEdit from "./edit"
import ClassroomSpecialUpload from "./upload"

export default {
  name: "ClassroomSpecial",
  mixins: [indexMixin],
  components: {ClassroomSpecialEdit, ClassroomSpecialUpload},
  data() {
    return {
      // 特殊教室表格数据
      classroomSpecialList: [],
      // 年级字典
      gradeOptions: [],
      // 班级字典
      adminclassOptions: [],
      // 查询参数
      queryParams: {
        schoolId: null,
        lessonScheduleId: null,
        course: null,
        building: null,
        room: null,
        lessonLimit: null,
        grade: null,
        adminclass: null
      },
    }
  },
  created() {
    this.getList()
    this.getDicts("sys_yes_no").then(response => {
      this.gradeOptions = response.data
    })
    this.getDicts("sys_yes_no").then(response => {
      this.adminclassOptions = response.data
    })
    // getIndexDatas().then(res => {
    //   Object.assign(this, res)
    // })
  },
  methods: {
    getService() {
      return service
    },
    /** 查询特殊教室列表 */
    getList() {
      this.loading = true
      listClassroomSpecial(this.queryParams).then(response => {
        this.classroomSpecialList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 年级字典翻译
    gradeFormat(row, column) {
      return this.selectDictLabels(this.gradeOptions, row.grade)
    },
    // 班级字典翻译
    adminclassFormat(row, column) {
      return this.selectDictLabels(this.adminclassOptions, row.adminclass)
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id ? [row.id] : this.ids
      this.$confirm('是否确认删除特殊教室?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delClassroomSpecial(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess("删除成功")
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      this.$confirm('是否确认导出所有特殊教室数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.exportLoading = true
        return exportClassroomSpecial(queryParams)
      }).then(response => {
        this.downloadFile(response.msg)
        this.exportLoading = false
      }).catch(() => {
      })
    }
  }
}
</script>
