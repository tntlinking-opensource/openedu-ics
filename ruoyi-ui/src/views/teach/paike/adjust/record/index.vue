<template>
  <div class="">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
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
        <el-button type="primary" plain icon="el-icon-plus" size="mini"
                   @click="handleAdd" v-hasPermi="['teach.paike:record']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                   @click="handleDelete" v-hasPermi="['teach.paike:record']">删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="名称" prop="name" align="center"/>
      <el-table-column label="创建时间" prop="createTime" align="center"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit"
                     @click="handleReload(scope.row)" v-hasPermi="['teach.paike:record']">载入
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete"
                     @click="handleDelete(scope.row)" v-hasPermi="['teach.paike:record']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize" @pagination="getList"/>

    <RecordEdit v-if="editOpen" :open.sync="editOpen" :id="editId" @submit="getList"></RecordEdit>
    <RecordUpload v-if="uploadOpen" :open.sync="uploadOpen" @submit="getList"></RecordUpload>
  </div>
</template>

<script>
import service, {delRecord, reloadRecord, listRecord} from "@/api/paike/record"
import indexMixin from "@/utils/views/indexMixin"
import RecordEdit from "./edit"
import RecordUpload from "./upload"

export default {
  name: "adjustRecord",
  mixins: [indexMixin],
  components: {RecordEdit, RecordUpload},
  data() {
    return {
      // 排课存档表格数据
      recordList: [],
      // 查询参数
      queryParams: {
        lessonScheduleId: null,
        name: null,
        json: null,
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
    /** 查询排课存档列表 */
    getList() {
      this.loading = true
      listRecord(this.queryParams).then(response => {
        this.recordList = response.rows
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
      this.$confirm('是否确认删除排课存档?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delRecord(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess("删除成功")
      }).catch(() => {
      })
    },
    /** 删除按钮操作 */
    handleReload(row) {
      const id = row.id
      this.$confirm('是否确认载入排课存档?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return reloadRecord(id)
      }).then(() => {
        this.msgSuccess("载入成功")
      }).catch(() => {
      })
    },
  }
}
</script>
