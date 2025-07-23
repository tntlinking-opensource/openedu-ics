<template>
  <div class="">
    <el-form>
      <el-form-item style="width: 100%;">
        <el-select v-model="form.grades" multiple clearable style="width:100%">
          <el-option v-for="v in grades" :label="v" :value="v" :key="v"/>
        </el-select>
      </el-form-item>
    </el-form>
    <div v-for="grade in form.grades">
      <teacher-day :grade="grade" :data="data"></teacher-day>
      <course-day :grade="grade" :data="data"></course-day>
      <teacher-time :grade="grade" :data="data"></teacher-time>
      <course-time :grade="grade" :data="data"></course-time>
    </div>
  </div>
</template>

<script>
import {getTimes} from "@/api/teach/paike/view.js"
import TimesMixin from "../../TimesMixin";
import TeacherDay from "./teacherDay";
import CourseDay from "./courseDay";
import TeacherTime from "./teacherTime";
import CourseTime from "./courseTime";

export default {
  name: "viewTimesIndex",
  components: {CourseTime, TeacherTime, CourseDay, TeacherDay},
  mixins: [TimesMixin],
  data() {
    return {
      form: {
        grades: [],
        showTime: false,
      },
      grades: [],
      data:{
        plans:[],
      },
    }
  },
  mounted() {
    getTimes().then(res => {
      // console.log('res', res)
      let grades = this.getByKey(res.plans, 'grade')
      this.grades = grades
      this.form.grades = grades
      this.data = res
    })
  },
}
</script>

<style scoped>
</style>
