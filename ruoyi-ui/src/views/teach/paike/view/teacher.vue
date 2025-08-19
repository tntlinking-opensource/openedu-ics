<template>
  <div class="">
    <el-form :inline="true">
      <div style="display: flex;">
        <div style="flex-grow: 1;">
          <el-form-item>
            <el-select v-model="grade">
              <el-option v-for="v in grades" :label="v" :value="v" :key="v"/>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="course" clearable :disabled="courses.length == 0">
              <el-option v-for="v in courses" :label="v" :value="v" :key="v"/>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="teacher" clearable filterable>
              <el-option v-for="v in teachers" :label="v" :value="v" :key="v"/>
            </el-select>
          </el-form-item>
        </div>
        <div>
          <el-form-item>
            <el-checkbox v-model="showRoom">教室</el-checkbox>
            <el-checkbox v-model="showTime">时间</el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-download" size="mini" @click="exportTeacher">导出</el-button>
          </el-form-item>
        </div>
      </div>
    </el-form>
    <div v-for="teacher in teacherList" :key="teacher">
      <div class="adminclass">{{ teacher }}&nbsp;老师课程表&nbsp;-&nbsp;{{ getTeacherCourse(teacher) }}</div>
      <el-table ref="courseTable" :data="getRows(teacher)" class="time_table" border>
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
                <div>{{ scope.row['cell' + (wIndex + 1)].adminclass }}</div>
                <div>{{ scope.row['cell' + (wIndex + 1)].course }}</div>
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
import {exportTeacher, getTeacher} from "@/api/teach/paike/view.js"
import TimesMixin from "../TimesMixin";

export default {
  name: "viewTeacher",
  mixins: [TimesMixin],
  data() {
    return {
      grade: null,
      grades: [],
      course: null,
      courses: [],
      teacher: null,
      teachers: [],
      lessonPlans: [],
      rows: [],
      showRoom: false,
      showTime: false,
    }
  },
  watch: {
    grade() {
      let plans = this.lessonPlans.filter(p => p.grade == this.grade)
      this.courses = this.getByKey(plans, 'courseName')
    },
    // course() {
    //   console.log('course', this.course)
    //   let plans = this.lessonPlans
    //   if (this.course) {
    //     plans = plans.filter(p => p.courseName == this.course)
    //   }
    //   this.teachers = this.getByKey(plans, 'teacher')
    // },
  },
  computed: {
    teacherList() {
      console.log('teacherList')
      if (this.teacher) {
        return [this.teacher]
      }
      let plans = this.lessonPlans
      plans = plans.filter(p => p.grade == this.grade)
      if(this.course){
        plans = plans.filter(p => p.courseName == this.course)
      }
      let teachers = new Set()
      plans.forEach(p => teachers.add(p.teacher))
      teachers = Array.from(teachers)
      return teachers
    }
  },
  mounted() {
    getTeacher().then(res => {
      // console.log('res', res)
      this.lessonPlans = res.plans
      this.rows = res.rows
      let grades = this.getByKey(this.lessonPlans, 'grade')
      let order = '一二三四五六七八九十';
      grades.sort(function compareFunction(item1, item2) {
        return order.indexOf(item1[0]) - order.indexOf(item2[0]);
      })
      this.grade = grades[0]
      this.grades = grades
      this.timeConfig = res.timeConfig
      this.teachers = this.getByKey(res.plans, 'teacher')
      this.initList()
    })
  },
  methods: {
    getTeacherCourse(teacher) {
      let plans = this.lessonPlans.filter(p => p.teacher == teacher)
      let course = this.getByKey(plans, 'courseName')
      return course.join('、')
    },
    getRows(teacher) {
      return this.rows.filter(r => r.teacher == teacher)
    },
    exportTeacher() {
      let form = {
        grade: this.grade,
        course: this.course,
        teacher: this.teacher,
        showRoom: this.showRoom,
        showTime: this.showTime,
      }
      exportTeacher(form).then(res => {
        this.downloadFile(res.msg)
      })
    }
  }
}
</script>

<style scoped>
.adminclass {text-align: center; line-height: 40px; font-size: 18px;}
</style>
