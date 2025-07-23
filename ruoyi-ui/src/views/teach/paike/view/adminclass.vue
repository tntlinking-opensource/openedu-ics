<template>
  <div class="">
    <el-form :inline="true">
      <div style="display: flex;">
        <div style="flex-grow: 1;">
          <el-form-item>
            <el-select v-model="grade" clearable>
              <el-option v-for="v in grades" :label="v" :value="v" :key="v"/>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="adminclass" clearable :disabled="adminclasses.length == 0" style="margin-left: 20px;">
              <el-option v-for="v in adminclasses" :label="v" :value="v" :key="v"/>
            </el-select>
          </el-form-item>
        </div>
        <div>
          <el-form-item>
            <el-checkbox v-model="showRoom">教室</el-checkbox>
            <el-checkbox v-model="showTeacher">教师</el-checkbox>
            <el-checkbox v-model="showTime">时间</el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-download" size="mini" @click="exportAdminclass">导出</el-button>
          </el-form-item>
        </div>
      </div>
    </el-form>
    <div v-for="adminclass in adminclassList" :key="adminclass">
      <div class="adminclass">{{ adminclass }}</div>
      <el-table ref="courseTable" :data="getRows(adminclass)" class="time_table" border>
        <el-table-column label="节次" prop="time" align="center" width="100">
          <template slot-scope="scope">
            <div class="course">
              <div>{{ scope.row.time }}</div>
              <div v-if="showTime">{{ scope.row.times }}</div>
            </div>
          </template>
        </el-table-column>
        <template v-for="(w, wIndex) in weekdays">
          <el-table-column :label="w" align="center">
            <template slot-scope="scope">
              <div class="course">
                <div>{{ scope.row['cell' + (wIndex + 1)].course }}</div>
                <div v-if="showTeacher">{{ scope.row['cell' + (wIndex + 1)].teacher }}</div>
                <div v-if="showRoom">{{ scope.row['cell' + (wIndex + 1)].room }}</div>
              </div>
            </template>
          </el-table-column>
        </template>
      </el-table>
    </div>
  </div>
</template>

<script>
import {exportAdminclass, getAdminclass} from "@/api/teach/paike/view.js"
import TimesMixin from "../TimesMixin";
import {sortList} from "../../../../utils/ruoyi";

export default {
  name: "viewAdminclass",
  mixins: [TimesMixin],
  data() {
    return {
      grade: null,
      grades: [],
      adminclass: null,
      adminclasses: [],
      lessonPlans: [],
      rows: [],
      showRoom: false,
      showTeacher: true,
      showTime: false,
    }
  },
  watch: {
    grade() {
      let plans = this.lessonPlans.filter(p => p.grade == this.grade)
      console.log('plans', plans)
      let adminclasses = new Set()
      plans.forEach(plan => adminclasses.add(plan.adminclass))
      adminclasses = Array.from(adminclasses)
      sortList(adminclasses)
      this.adminclasses = adminclasses
    },
  },
  computed: {
    adminclassList() {
      if (this.adminclass) {
        return [this.adminclass]
      }
      let plans = this.lessonPlans
      if (this.grade) {
        plans = plans.filter(p => p.grade == this.grade)
      }
      let adminclasses = new Set()
      plans.forEach(p => p.adminclasses.forEach(a => adminclasses.add(a)))
      adminclasses = Array.from(adminclasses)
      return adminclasses
    }
  },
  mounted() {
    getAdminclass().then(res => {
      // console.log('res', res)
      this.lessonPlans = res.plans
      this.rows = res.rows
      let grades = this.getByKey(this.lessonPlans, 'grade')
      this.grades = grades
      this.timeConfig = res.timeConfig
      this.initList()
    })
  },
  methods: {
    getRows(adminclass) {
      return this.rows.filter(r => r.adminclass == adminclass)
    },
    exportAdminclass() {
      let form = {
        grade: this.grade,
        adminclass: this.adminclass,
        showRoom: this.showRoom,
        showTeacher: this.showTeacher,
        showTime: this.showTime,
      }
      exportAdminclass(form).then(res => {
        this.download(res.msg)
      })
    }
  }
}
</script>

<style scoped>
.adminclass {text-align: center; line-height: 40px; font-size: 18px;}
</style>
