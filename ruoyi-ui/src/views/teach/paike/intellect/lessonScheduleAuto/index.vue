<template>
  <div class=" lesson_schedule_auto">
    <div style="display: flex; width: 600px; margin: auto;">
      <div style="width: 200px; padding-top: 100px;">
        <div style="font-weight: bold;">自动排课规则：</div>
        <ol class="rules">
          <li>基于用户设定的全校\课程\班级\教师排课时间，合班，预排。</li>
          <li>课时分布：尽量实现在教师可用时间内分散排课。</li>
          <li>当某个课程一天多于1节课时，则默认连上、但不连堂上下午。</li>
        </ol>
      </div>
      <div>
        <div style="text-align: center;">
          <img src="@/assets/teach/app/paike/images/startSchedule.png"/>
        </div>
        <div v-if="lessonSchedule.isAutoSchedule == 'N'">
          <div style="text-align: center; margin: 20px;">
            <el-button type="primary" @click="submitForm" :loading="submiting">开始自动排课</el-button>
          </div>
        </div>
        <div v-if="lessonSchedule.isAutoSchedule == 'Y'">
          <div class="text-center" style="font-size: 18px;">排完啦，
            <router-link to="/paike/view" style="color: #36ABF1;">查看课表</router-link>
          </div>
          <div style="text-align: center; margin: 20px;">
            <el-button type="primary" @click="saveRecord" plain>新建存档</el-button>
            <el-button type="primary" @click="submitForm" :loading="submiting">再排一次</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {getIndexDatas, startSchedule} from "@/api/teach/paike/intellect/lessonScheduleAuto.js"
import record from "@/api/paike/record"
import TimesMixin from "../../TimesMixin";

export default {
  name: "LessonScheduleAuto",
  mixins: [TimesMixin],
  data() {
    return {
      lessonSchedule: {},
      submiting: false,
    }
  },
  created() {
    getIndexDatas().then((res) => {
      this.lessonSchedule = res.lessonSchedule
    })
  },
  methods: {
    saveRecord() {
      this.$prompt('请输入存档名称', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({value}) => {
        record.saveOrUpdate({
          name: value,
        }).then(() => {
          this.msgSuccess("存档成功")
        })
      })
    },
    submitForm() {
      if (this.lessonSchedule.isAutoSchedule == 'Y') {
        this.$confirm('是否确定再排一次?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.startSchedule()
        })
      } else {
        this.startSchedule()
      }
    },
    startSchedule() {
      this.submiting = true
      startSchedule().then(() => {
        this.lessonSchedule = {
          isAutoSchedule: 'Y',
        }
        this.msgSuccess("自动排课成功")
      }).finally(() => {
        this.submiting = false
      })
    }
  }
}
</script>
<style scoped lang="scss">
.lesson_schedule_auto {
  .rules {
    padding: 0 0 0 15px;
    li {list-style: decimal;}
  }
}
</style>
