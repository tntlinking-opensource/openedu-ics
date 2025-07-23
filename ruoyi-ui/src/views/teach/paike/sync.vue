<template>
  <div class="app-container lesson_schedule_auto">
    <div style="text-align: center;">
      <img src="@/assets/teach/app/paike/images/startSchedule.png"/>
    </div>
    <div>
      <div style="text-align: center; margin: 20px;">
        <el-button type="primary" @click="submitForm" :loading="submiting">开始同步</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import {lessonScheduleSync} from "@/api/teach/paike/intellect/lessonScheduleSync.js"
import TimesMixin from "./TimesMixin";

export default {
  name: "LessonScheduleSync",
  mixins: [TimesMixin],
  data() {
    return {
      submiting: false,
    }
  },
  created() {
  },
  methods: {
    submitForm() {
      this.submiting = true
      lessonScheduleSync().then(() => {
        this.msgSuccess("同步成功")
      }).finally(() => {
        this.submiting = false
      })
    }
  }
}
</script>
<style scoped lang="scss">
.pre_div {
  font-family: "Microsoft YaHei", Helvetica, Arial, sans-serif;
  padding: 8px;
  min-height: 40px;
  cursor: pointer;
  font-size: 14px;
  &:hover {
    background-color: #F5F7FA;
  }
  .remove {
    cursor: pointer;
    font-size: 16px;
    font-weight: bold;
    color: #aaa;
  }
}
</style>
<style lang="scss">
.lesson_schedule_pre {
  .el-table__body tr {
    &.selected > td {
      background-color: #57baf5;
      color: #fff;
    }
  }
  .time_table .el-table__body {
    tr:hover td {
      background-color: unset;
    }
    td {
      padding: 0;
    }
  }
}
</style>
