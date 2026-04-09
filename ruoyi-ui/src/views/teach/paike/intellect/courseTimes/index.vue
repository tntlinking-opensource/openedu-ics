<template>
  <div class="">
    <div style="display: flex;">
      <div style="width: 180px;">
        <div>课程</div>
        <el-tree ref="gradeTree" :data="courses" node-key="id" :props="coursesProps"
                 show-checkbox check-on-click-node accordion :expand-on-click-node="false"
                 @check="onCourseChange"/>
      </div>
      <div style="flex-grow: 1; padding-left: 10px;">
        <div style="margin-bottom: 5px;">
          <el-button type="primary" plain @click="selectAll">全不排</el-button>
          <el-button type="primary" plain @click="clean">清空</el-button>
        </div>
        <el-table v-loading="loading" :data="tableList" border>
          <el-table-column label="节次" prop="name" align="center"/>
          <template v-for="(w, wIndex) in weekdays">
            <el-table-column align="center">
              <template slot="header" slot-scope="scope">
                <div class="time_th">
                  <div>{{ w }}</div>
                  <div>
                    <div>
                      <label><input type="checkbox" :checked="isMorningSelect(wIndex)" @change="onMorningChange(wIndex)"/>上午不排</label>
                    </div>
                    <div>
                      <label><input type="checkbox" :checked="isAfternoonSelect(wIndex)" @change="onAfternoonChange(wIndex)"/>下午不排</label>
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
import {getIndexDatas, listCourseTimes, saveOrUpdate} from "@/api/teach/paike/intellect/courseTimes"
import indexMixin from "@/utils/views/indexMixin"
import TimesMixin from "../../TimesMixin";

export default {
  name: "CourseTimes",
  mixins: [indexMixin, TimesMixin],
  data() {
    return {
      // 课程排课时间表格数据
      courses: [],
      coursesProps: {
        children: "courses",
        label: "name"
      },
    }
  },
  created() {
    this.getList()
    getIndexDatas().then(res => {
      res.courses.forEach(g => g.courses.forEach(c => c.id = c.grade + c.name))
      Object.assign(this, res)
      this.initList()
    })
  },
  methods: {
    /** 查询课程排课时间列表 */
    getList() {
      this.loading = true
      listCourseTimes(this.queryParams).then(response => {
        response.rows.forEach(r => Object.assign(r, JSON.parse(r.json)))
        this.ruleList = response.rows
        this.loading = false
      })
    },
    onCourseChange() {
      let codes = this.$refs.gradeTree.getCheckedKeys(true)
      this.selectRules = codes.map(code => this.getCourseTimes(code))
    },
    getCourseTimes(code) {
      let rule = this.getRuleByCode(code)
      if (rule == null) {
        let times = this.getTimes()
        rule = {
          code, times
        }
        this.ruleList.push(rule)
      }
      return rule
    },
    submitForm() {
      this.submiting = true
      this.ruleList.forEach(t => {
        let json = (({grade, course, times}) => ({grade, course, times}))(t)
        t.content = this.getContent(t.times)
        t.json = JSON.stringify(json)
      })
      this.ruleList = this.ruleList.filter(t => t.content != '')
      this.ruleList.forEach(t => {
        t.content = `${t.code}：${t.content}`
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
.time_th {
  display: flex;
  div:first-child {flex-grow: 1; display: flex; align-items: center; justify-content: center;}
}
.unit {
  cursor: pointer;
  height: 40px;
  line-height: 40px;
  &:hover {
    background-color: #eee;
  }
}
</style>
