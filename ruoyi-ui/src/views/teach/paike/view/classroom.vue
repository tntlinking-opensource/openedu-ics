<template>
  <div class="">
    <el-form>
      <div style="display: flex;">
        <div style="flex-grow: 1; padding-right: 20px;">
          <el-form-item style="width: 100%;">
            <el-select v-model="form.classrooms" multiple clearable style="width:100%">
              <el-option v-for="v in classrooms" :label="v" :value="v" :key="v"/>
            </el-select>
          </el-form-item>
        </div>
        <div class="el-form--inline">
          <el-form-item>
            <el-checkbox v-model="form.showTime">时间</el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-download" size="mini" @click="exportClassroom">导出</el-button>
          </el-form-item>
        </div>
      </div>
    </el-form>
    <el-table ref="courseTable" :data="form.classrooms" class="time_table" border height="600">
      <el-table-column label="教室" align="center" width="150" fixed>
        <template slot-scope="scope">
          <div>{{ scope.row }}</div>
        </template>
      </el-table-column>
      <template v-for="(w, wIndex) in weekdays">
        <el-table-column :label="w" align="center">
          <template v-for="(t, tIndex) in timeConfig.count">
            <el-table-column align="center" width="100">
              <template slot="header" slot-scope="scope">
                <div>第{{ t }}节</div>
                <div v-if="form.showTime">{{ getTimes(tIndex) }}</div>
              </template>
              <template slot-scope="scope">
                <div v-for="p in getPlans(scope.row, wIndex, tIndex)">
                  <div v-html="p.adminclasses.join('<br/>')"></div>
                  <div>{{ p.courseName }}</div>
                  <div>{{ p.teacher }}</div>
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
import {exportClassroom, getClassroom} from "@/api/teach/paike/view.js"
import TimesMixin from "../TimesMixin";

export default {
  name: "viewClassroom",
  mixins: [TimesMixin],
  data() {
    return {
      form: {
        classrooms: [],
        showTime: false,
      },
      classrooms: [],
      lessonPlans: [],
      times: [],
    }
  },
  watch:{
    'form.showTime':function (val){
      this.$nextTick(() => {
        this.$refs.courseTable.doLayout();
      });
    }
  },
  mounted() {
    getClassroom().then(res => {
      // console.log('res', res)
      this.lessonPlans = res.plans
      this.classrooms = res.classrooms
      this.form.classrooms = res.classrooms
      this.times = res.times
      this.initList()
    })
  },
  methods: {
    getPlans(classroom, weekday, time) {
      let times = this.getTdKey({index: time}, weekday)
      let plans = this.lessonPlans.filter(p => p.classroom == classroom
        && p.planTimes.indexOf(times) >= 0)
      // console.log('plans', plans)
      return plans
    },
    getTimes(t) {
      return this.times[t].times
    },
    exportClassroom() {
      exportClassroom(this.form).then(res => {
        this.download(res.msg)
      })
    }
  }
}
</script>

<style scoped>
.adminclass {text-align: center; line-height: 40px; font-size: 18px;}
</style>
