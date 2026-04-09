<template>
  <div class="">
    <div style="display: flex;">
      <div style="width: 260px;">
        <el-form :inline="true" @submit.native.prevent>
          <el-form-item>
            <el-input v-model="teacher" placeholder="请输入关键字" clearable size="small" @change="searchTeacher"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini">搜索</el-button>
          </el-form-item>
        </el-form>
        <el-table ref="teacherTable" :data="teacherList" height="500" @selection-change="onTeacherChange" @row-click="onTeacherClick">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="教师" prop="teacher" align="center"/>
          <el-table-column label="课程" prop="course" align="center"/>
        </el-table>
      </div>
      <div style="flex-grow: 1; padding-left: 10px;">
        <div style="margin-bottom: 5px;">
          <el-button type="primary" plain @click="selectAll">全不排</el-button>
          <el-button type="primary" plain @click="clean">清空</el-button>
        </div>
        <el-table v-if="showTable" :data="tableList" border>
          <el-table-column label="节次" prop="name" align="center"/>
          <template v-for="(w, wIndex) in weekdays">
            <el-table-column :label="w" align="center">
              <template slot="header" slot-scope="scope">
                <div class="time_th">
                  <div>{{ w }}</div>
                  <div>
                    <div>
                      <label><input type="checkbox" :checked="isMorningSelect(wIndex)"
                                    @change="onMorningChange(wIndex)"/>上午不排</label>
                    </div>
                    <div>
                      <label><input type="checkbox" :checked="isAfternoonSelect(wIndex)"
                                    @change="onAfternoonChange(wIndex)"/>下午不排</label>
                    </div>
                  </div>
                </div>
              </template>
              <template slot-scope="scope">
                <div class="unit" @click="onUnitClick(scope.row, wIndex)">
                  {{ getTimeByRowAndWeekday(scope.row, wIndex) }}
                </div>
              </template>
            </el-table-column>
          </template>
        </el-table>
        <div style="text-align: center; margin: 20px;">
          <el-button type="primary" @click="submitForm" :loading="submiting">保存</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {getIndexDatas, listTeacherTimes, saveOrUpdate} from "@/api/teach/paike/intellect/teacherTimes"
import indexMixin from "@/utils/views/indexMixin"
import TimesMixin from "../../TimesMixin";

export default {
  name: "TeacherTimes",
  mixins: [indexMixin, TimesMixin],
  data() {
    return {
      // 课程排课时间表格数据
      teacher: '',
      teachers: [],
      teacherList: [],
      showTable: true,
    }
  },
  created() {
    this.getList()
    getIndexDatas().then(res => {
      res.teacherList = [].concat(res.teachers)
      Object.assign(this, res)
      this.initList()
    })
  },
  methods: {
    /** 查询课程排课时间列表 */
    getList() {
      this.loading = true
      listTeacherTimes(this.queryParams).then(response => {
        response.rows.forEach(r => Object.assign(r, JSON.parse(r.json)))
        this.ruleList = response.rows
      }).finally(() => {
        this.loading = false
      })
    },
    searchTeacher() {
      if (this.teacher) {
        this.teacherList = this.teachers.filter(t => t.teacher.indexOf(this.teacher) >= 0 || t.course.indexOf(this.teacher) >= 0)
      } else {
        this.teacherList = [].concat(this.teachers)
      }
    },
    //点击行触发，选中或不选中复选框
    onTeacherClick(row, column, event) {
      this.$refs.teacherTable.toggleRowSelection(row);
    },
    //点击行触发，选中或不选中复选框
    onTeacherChange() {
      let selection = this.$refs.teacherTable.store.states.selection
      this.selectRules = selection.map(row => this.getRule(row))
      this.showTable = false
      this.$nextTick(() => this.showTable = true)
    },
    getRule(row) {
      let code = row.teacher
      let rule = this.getRuleByCode(code)
      if (rule == null) {
        let {course} = row
        let times = this.getTimes()
        rule = {
          code, course, times
        }
        this.ruleList.push(rule)
      }
      return rule
    },
    submitForm() {
      this.submiting = true
      this.ruleList.forEach(t => {
        let json = (({teacher, course, times}) => ({teacher, course, times}))(t)
        t.content = this.getContent(t.times)
        t.json = JSON.stringify(json)
      })
      this.ruleList = this.ruleList.filter(t => t.content != '')
      this.ruleList.forEach(t => {
        t.content = `${t.code}${t.course}：${t.content}`
      })
      saveOrUpdate(this.ruleList).then(() => {
        this.msgSuccess("保存成功")
      }).finally(() => {
        this.submiting = false
      })
    }
  }
}
</script>
<style scoped lang="scss">
.unit {
  cursor: pointer;
  height: 40px;
  line-height: 40px;
  &:hover {
    background-color: #eee;
  }
}
</style>
