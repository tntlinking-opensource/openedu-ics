<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <div class="title">{{ grade }}--教师课时分布表(节次)</div>
    </div>
    <div style="margin: -16px;">
      <el-table ref="courseTable" :data="teachers" class="time_table" border>
        <el-table-column label="教师" align="center" width="150">
          <template slot-scope="scope">
            <div>{{ scope.row }}</div>
          </template>
        </el-table-column>
        <template v-for="(time) in timeConfig.count">
          <el-table-column :label="`第${time}节`" align="center">
            <template slot-scope="scope">
              <div>{{ getNum(scope.row, time) }}</div>
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
  name: "teacherTime",
  mixins: [TimesMixin],
  props: ['grade', 'data'],
  computed: {
    teachers() {
      let list = []
      this.data.plans.forEach(p => {
        if (p.grade == this.grade) {
          if (list.indexOf(p.teacher) < 0) {
            list.push(p.teacher)
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
    getNum(teacher, time) {
      let key = `-${time}`
      let num = 0
      this.data.plans.forEach(p => {
        if (p.teacher == teacher && p.grade == this.grade) {
          num += p.planTimes.split(key).length - 1
        }
      })
      return num
    },
    getSum(teacher) {
      let num = 0
      this.data.plans.forEach(p => {
        if (p.teacher == teacher && p.grade == this.grade) {
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
