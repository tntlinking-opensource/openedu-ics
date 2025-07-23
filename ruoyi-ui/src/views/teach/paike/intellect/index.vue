<template>
  <div class="app-container">
    <el-steps :active="active" process-status="finish" finish-status="wait" align-center>
      <el-step v-for="(item,index) of steps" :key="index" :title="item"
               @click.native="handleStep(index)"/>
    </el-steps>
    <el-divider></el-divider>
    <school-times v-if="active == 0"></school-times>
    <course-times v-if="active == 1"></course-times>
    <adminclass-times v-if="active == 2"></adminclass-times>
    <teacher-times v-if="active == 3"></teacher-times>
    <mixed-class v-if="active == 4"></mixed-class>
    <lesson-schedule-pre v-if="active == 5"></lesson-schedule-pre>
    <lesson-schedule-auto v-if="active == 6"></lesson-schedule-auto>
  </div>
</template>

<script>

import SchoolTimes from "./schoolTimes";
import AdminclassTimes from "./adminclassTimes";
import TeacherTimes from "./teacherTimes";
import MixedClass from "./mixedClass";
import LessonSchedulePre from "./lessonSchedulePre";
import LessonScheduleAuto from "./lessonScheduleAuto";
import CourseTimes from "./courseTimes";

export default {
  name: "index",
  components: {
    CourseTimes,
    LessonScheduleAuto, LessonSchedulePre, MixedClass, TeacherTimes, AdminclassTimes, SchoolTimes
  },
  data() {
    return {
      steps: ['全校排课时间', '课程排课时间', '班级排课时间', '教师排课时间', '合班', '预排', '自动排课'],
      active: 0,
    }
  },
  mounted() {
  },
  methods: {
    handleStep(index) {
      this.active = index
    }
  }
}
</script>

<style scoped>

</style>
