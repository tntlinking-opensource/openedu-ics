<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入名称" clearable
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
                   @click="handleAdd" v-hasPermi="['teach.paike:lessonSchedule']">新增
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="lessonScheduleList" @selection-change="handleSelectionChange">
      <el-table-column label="学年" prop="teachCalender.year" align="center"/>
      <el-table-column label="学期" prop="teachCalender.term" align="center"/>
      <el-table-column label="名称" prop="name" align="center"/>
      <!--      <el-table-column label="状态" prop="isConflict" align="center">
              <template slot-scope="scope">
                <dict-tag :options="isConflictOptions" :value="scope.row.isConflict"/>
              </template>
            </el-table-column>-->
      <el-table-column label="是否当前排课" prop="isCurrent" align="center">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isCurrent"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-copy-document" @click="handleCopy(scope.row)">复制</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize" @pagination="getList"/>

    <LessonScheduleEdit v-if="editOpen" :open.sync="editOpen" :id="editId" :copyMode="copyMode"
                        @submit="getList"></LessonScheduleEdit>
  </div>
</template>

<script>
import service, {
  delLessonSchedule,
  exportLessonSchedule,
  getIndexDatas,
  listLessonSchedule
} from "../../../../api/teach/paike/lessonSchedule";
import indexMixin from "@/utils/views/indexMixin";
import importData from "@/utils/views/importData";
import LessonScheduleEdit from "./edit";

export default {
  name: "LessonSchedule",
  mixins: [indexMixin, importData],
  components: {LessonScheduleEdit},
  dicts: ['sys_yes_no'],
  data() {
    return {
      // 排课任务表格数据
      lessonScheduleList: [],
      // 学年学期字典
      teachCalenderIdOptions: [],
      // 状态字典
      isConflictOptions: [],
      // 是否当前排课字典
      isCurrentOptions: [],
      // 查询参数
      queryParams: {
        schoolId: null,
        teachCalenderId: null,
        name: null,
        isConflict: null,
        isCurrent: null,
      },
      copyMode: false,
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_yes_no").then(response => {
      this.teachCalenderIdOptions = response.data;
      this.isConflictOptions = response.data;
      this.isCurrentOptions = response.data;
      console.log(this.isCurrentOptions)
    });
    getIndexDatas().then(res => {
      Object.assign(this, res)
    })
  },
  methods: {
    getService() {
      return service
    },
    /** 查询排课任务列表 */
    getList() {
      this.loading = true;
      listLessonSchedule(this.queryParams).then(response => {
        this.lessonScheduleList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 学年学期字典翻译
    teachCalenderIdFormat(row, column) {
      return this.selectDictLabel(this.teachCalenderIdOptions, row.teachCalenderId);
    },
    // 状态字典翻译
    isConflictFormat(row, column) {
      return this.selectDictLabel(this.isConflictOptions, row.isConflict);
    },
    // 是否当前排课字典翻译
    isCurrentFormat(row, column) {
      return this.selectDictLabel(this.isCurrentOptions, row.isCurrent);
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleUpdate(row) {
      const id = row.id || this.ids[0]
      this.editId = id
      this.editOpen = true
      this.copyMode = false
    },
    handleCopy(row) {
      this.handleUpdate(row)
      this.copyMode = true
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id ? [row.id] : this.ids;
      this.$confirm('是否确认删除排课任务?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delLessonSchedule(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有排课任务数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.exportLoading = true;
        return exportLessonSchedule(queryParams);
      }).then(response => {
        this.download(response.msg);
        this.exportLoading = false;
      }).catch(() => {
      });
    }
  }
};
</script>
