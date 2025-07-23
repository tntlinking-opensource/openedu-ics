<template>
  <div class="app-container">
    <el-steps :active="active" process-status="finish" finish-status="wait" align-center>
      <el-step v-for="(item,index) of steps" :key="index" :title="item"
               @click.native="handleStep(index)"/>
    </el-steps>
    <el-divider></el-divider>
    <view-adminclass v-if="active == 0"></view-adminclass>
    <view-teacher v-if="active == 1"></view-teacher>
    <view-classroom v-if="active == 2"></view-classroom>
    <view-adminclasses v-if="active == 3"></view-adminclasses>
    <view-teachers v-if="active == 4"></view-teachers>
    <view-times-index v-if="active == 5"></view-times-index>
  </div>
</template>

<script>

import ViewAdminclass from "./adminclass";
import ViewTeacher from "./teacher";
import ViewClassroom from "./classroom";
import ViewAdminclasses from "./adminclasses";
import ViewTeachers from "./teachers";
import ViewTimesIndex from "./times";
export default {
  name: "index",
  components: {ViewTimesIndex, ViewTeachers, ViewAdminclasses, ViewClassroom, ViewTeacher, ViewAdminclass},
  data() {
    return {
      steps: ['班级课表', '教师课表', '教室课表', '总课表', '总任课表', '课时分布'],
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
