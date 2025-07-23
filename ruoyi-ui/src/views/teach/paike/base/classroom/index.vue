<template>
  <div>
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="年级" prop="grade">
        <el-input v-model="queryParams.grade" placeholder="请输入年级" clearable
                  size="small"
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="班级" prop="adminclass">
        <el-input v-model="queryParams.adminclass" placeholder="请输入班级" clearable
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
                   @click="handleAdd" v-hasPermi="['teach.paike:classroom']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
                   @click="handleUpdate" v-hasPermi="['teach.paike:classroom']">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                   @click="handleDelete" v-hasPermi="['teach.paike:classroom']">删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="el-icon-upload2" size="mini"
                   @click="handleImport" v-hasPermi="['teach.paike:classroom']">导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" :loading="exportLoading"
                   @click="handleExport" v-hasPermi="['teach.paike:classroom']">导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="classroomList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="年级" prop="grade" align="center"/>
      <el-table-column label="班级" prop="adminclass" align="center"/>
      <el-table-column label="教学楼" prop="building" align="center"/>
      <el-table-column label="教室号" prop="room" align="center"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit"
                     @click="handleUpdate(scope.row)" v-hasPermi="['teach.paike:classroom']">修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete"
                     @click="handleDelete(scope.row)" v-hasPermi="['teach.paike:classroom']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize" @pagination="getList"/>

    <ClassroomEdit v-if="editOpen" :open.sync="editOpen" :id="editId" @submit="getList"></ClassroomEdit>
    <ClassroomUpload v-if="uploadOpen" :open.sync="uploadOpen" @submit="getList"></ClassroomUpload>
  </div>
</template>

<script>
import service, {delClassroom, exportClassroom, listClassroom} from "@/api/teach/paike/base/classroom"
import indexMixin from "@/utils/views/indexMixin"
import ClassroomEdit from "./edit"
import ClassroomUpload from "./upload"

export default {
  name: "Classroom",
  mixins: [indexMixin],
  components: {ClassroomEdit, ClassroomUpload},
  data() {
    return {
      // 班级教室表格数据
      classroomList: [],
      // 查询参数
      queryParams: {
        schoolId: null,
        lessonScheduleId: null,
        grade: null,
        adminclass: null,
        building: null,
        room: null
      },
    }
  },
  created() {
    this.getList()
    // getIndexDatas().then(res => {
    //   Object.assign(this, res)
    // })
  },
  methods: {
    getService() {
      return service
    },
    /** 查询班级教室列表 */
    getList() {
      this.loading = true
      listClassroom(this.queryParams).then(response => {
        this.classroomList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id ? [row.id] : this.ids
      this.$confirm('是否确认删除班级教室?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delClassroom(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess("删除成功")
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      this.$confirm('是否确认导出所有班级教室数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.exportLoading = true
        return exportClassroom(queryParams)
      }).then(response => {
        this.download(response.msg)
        this.exportLoading = false
      }).catch(() => {
      })
    }
  }
}
</script>
