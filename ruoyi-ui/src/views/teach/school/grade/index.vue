<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="编号" prop="code">
        <el-input v-model="queryParams.code" placeholder="请输入编号" clearable
                  size="small" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入名称" clearable
                  size="small" @keyup.enter.native="handleQuery"/>
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
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="gradeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" prop="code" align="center"/>
      <el-table-column label="名称" prop="name" align="center"/>
      <el-table-column label="年级组长" prop="teacher.name" align="center"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize" @pagination="getList"/>
    <GradeEdit v-if="editOpen" :open.sync="editOpen" :id="editId" @submit="getList"></GradeEdit>
  </div>
</template>
<script>
import {delGrade, exportGrade, getIndexDatas, listGrade} from "@/api/teach/school/grade"
import indexMixin from "@/utils/views/indexMixin"
import GradeEdit from "./edit"

export default {
  name: "Grade",
  mixins: [indexMixin],
  components: {GradeEdit},
  data() {
    return {
      // 年级表格数据
      gradeList: [],
      // 查询参数
      queryParams: {
        schoolId: null,
        code: null,
        name: null,
        teacherId: null,
        deleted: 0,
      },
    }
  },
  created() {
    this.getList()
    getIndexDatas().then(res => {
      Object.assign(this, res)
    })
  },
  methods: {
    /** 查询年级列表 */
    getList() {
      this.loading = true
      listGrade(this.queryParams).then(response => {
        this.gradeList = response.rows
        this.total = response.total
      }).finally(() => {
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
      this.$confirm('是否确认删除年级?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delGrade(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess("删除成功")
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      this.$confirm('是否确认导出所有年级数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.exportLoading = true
        return exportGrade(queryParams)
      }).then(response => {
        this.downloadFile(response.msg)
        this.exportLoading = false
      }).catch(() => {
      })
    }
  }
}
</script>
