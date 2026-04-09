<template>
  <div class="app-container">
    <el-steps :active="active" process-status="finish" finish-status="wait" align-center>
      <el-step v-for="(item,index) of steps" :key="index" :title="item"
               @click.native="handleStep(index)"/>
    </el-steps>
    <el-divider></el-divider>
    <adjust-adminclass v-if="active == 0"></adjust-adminclass>
    <adjust-teacher v-if="active == 1"></adjust-teacher>
  </div>
</template>

<script>

import AdjustAdminclass from "./adminclass";
import AdjustTeacher from "./teacher";
export default {
  name: "index",
  components: { AdjustTeacher, AdjustAdminclass},
  data() {
    return {
      steps: ['按班级调课', '按教师调课'],
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
