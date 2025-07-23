<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <div class="title">{{ grade }}--课程课时分布表(星期)</div>
    </div>
    <div style="margin: -16px;">
      <el-table ref="courseTable" :data="courses" class="time_table" border>
        <el-table-column label="课程" align="center" width="150">
          <template slot-scope="scope">
            <div>{{ scope.row }}</div>
          </template>
        </el-table-column>
        <template v-for="(w, wIndex) in weekdays">
          <el-table-column :label="w" align="center">
            <template slot-scope="scope">
              <div>{{ getNum(scope.row, wIndex) }}</div>
            </template>
          </el-table-column>
        </template>
        <el-table-column label="总课时数" align="center" width="150">
          <template slot-scope="scope">
            <div>{{ getSum(scope.row) }}</div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </el-card>
</template>

<script>
import TimesMixin from "../../TimesMixin";

export default {
  name: "courseDay",
  mixins: [TimesMixin],
  props: ['grade', 'data'],
  computed: {
    courses() {
      let list = []
      this.data.plans.forEach(p => {
        if (p.grade == this.grade) {
          if (list.indexOf(p.courseName) < 0) {
            list.push(p.courseName)
          }
        }
      })
      return list
    }
  },
  mounted() {
    this.timeConfig = this.data.timeConfig
    this.initList()
  },
  methods: {
    getNum(courseName, weekday) {
      let key = `${weekday + 1}-`
      let num = 0
      this.data.plans.forEach(p => {
        if (p.courseName == courseName && p.grade == this.grade) {
          num += p.planTimes.split(key).length - 1
        }
      })
      return num
    },
    getSum(courseName) {
      let num = 0
      this.data.plans.forEach(p => {
        if (p.courseName == courseName && p.grade == this.grade) {
          num += p.hour
        }
      })
      return num
    },
  }
}
</script>

<style scoped>
.title { text-align: center; font-weight: bold;}
</style>
