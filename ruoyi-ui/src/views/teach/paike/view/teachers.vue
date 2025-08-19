<template>
  <div class="">
    <el-form>
      <div style="display: flex;">
        <div style="flex-grow: 1; padding-right: 20px;">
          <el-form-item style="width: 100%;">
            <el-select v-model="form.grades" multiple clearable style="width:100%">
              <el-option v-for="v in grades" :label="v" :value="v" :key="v"/>
            </el-select>
          </el-form-item>
        </div>
        <div class="el-form--inline">
          <el-form-item>
            <el-checkbox v-model="form.showTime">时间</el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-download" size="mini" @click="exportTeachers">导出</el-button>
          </el-form-item>
        </div>
      </div>
    </el-form>
    <el-table ref="courseTable" :data="teachers" class="time_table" border height="600">
      <el-table-column label="总课程表" align="center" width="150" fixed>
        <template slot-scope="scope">
          <div>{{ scope.row }}</div>
        </template>
      </el-table-column>
      <template v-for="(w, wIndex) in weekdays">
        <el-table-column :label="w" align="center">
          <template v-for="(t, tIndex) in timeConfig.count">
            <el-table-column align="center" width="110">
              <template slot="header" slot-scope="scope">
                <div>第{{ t }}节</div>
                <div v-if="form.showTime">{{ getTimes(tIndex) }}</div>
              </template>
              <template slot-scope="scope">
                <div v-for="p in getPlans(scope.row, wIndex, tIndex)">
                  <div>{{ p.courseName }}</div>
                  <div>{{ p.teacher }}</div>
                  <div>{{ p.classroom }}</div>
                </div>
              </template>
            </el-table-column>
          </template>
        </el-table-column>
      </template>
    </el-table>
  </div>
</template>

<script>
import {exportTeachers, getTeachers} from "@/api/teach/paike/view.js"
import TimesMixin from "../TimesMixin";

export default {
  name: "viewTeachers",
  mixins: [TimesMixin],
  data() {
    return {
      form: {
        grades: [],
        showTime: false,
      },
      grades: [],
      lessonPlans: [],
      times: [],
    }
  },
  watch: {
    'form.showTime': function (val) {
      this.$nextTick(() => {
        this.$refs.courseTable.doLayout();
      });
    }
  },
  computed: {
    teachers() {
      let list = []
      this.lessonPlans.forEach(p => {
        if (this.form.grades.indexOf(p.grade) >= 0) {
          if (list.indexOf(p.teacher) < 0) {
            list.push(p.teacher)
          }
        }
      })
      return list
    }
  },
  mounted() {
    getTeachers().then(res => {
      // console.log('res', res)
      this.lessonPlans = res.plans
      let grades = this.getByKey(res.plans, 'grade')
      this.grades = grades
      this.form.grades = grades
      this.times = res.times
      this.initList()
    })
  },
  methods: {
    getPlans(teacher, weekday, time) {
      let times = this.getTdKey({index: time}, weekday)
      let plans = this.lessonPlans.filter(p => p.teacher == teacher && p.planTimes.indexOf(times) >= 0)
      // console.log('plans', plans)
      return plans
    },
    getTimes(t) {
      return this.times[t].times
    },
    exportTeachers() {
      exportTeachers(this.form).then(res => {
        this.downloadFile(res.msg)
      })
    }
  }
}
</script>

<style scoped>
.adminclass {text-align: center; line-height: 40px; font-size: 18px;}
</style>
