<template>
  <div class="app-container">
    <el-steps :active="active" process-status="finish" finish-status="wait" align-center>
      <el-step v-for="(item,index) of steps" :key="index" :title="item"
               @click.native="handleStep(index)"/>
    </el-steps>
    <el-divider></el-divider>
    <time-index v-if="active == 0"></time-index>
    <lesson-plan v-if="active == 1"></lesson-plan>
    <classroom v-if="active == 2"></classroom>
    <classroom-special v-if="active == 3"></classroom-special>
  </div>
</template>

<script>
import TimeIndex from "./time";
import LessonPlan from "./lessonPlan";
import Classroom from "./classroom";
import ClassroomSpecial from "./classroomSpecial";

export default {
  name: "index",
  components: {ClassroomSpecial, Classroom, LessonPlan, TimeIndex},
  data() {
    return {
      steps: ['作息时间', '任课信息', '班级教室', '特殊教室'],
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
