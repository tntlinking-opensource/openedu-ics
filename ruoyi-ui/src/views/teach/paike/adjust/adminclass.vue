<template>
  <div class="paike_adjust">
    <div style="display: flex; margin-bottom: 10px;">
      <div>
        <el-select v-model="grade">
          <el-option v-for="v in grades" :label="v" :value="v" :key="v"/>
        </el-select>
        <el-select v-model="adminclass" style="margin-left: 20px;">
          <el-option v-for="v in adminclasses" :label="v" :value="v" :key="v"/>
        </el-select>
      </div>
      <div style="flex-grow: 1; text-align: right;">
        <el-button v-if="conflictNum" type="primary" plan @click="conflictVisible = true">
          课表冲突({{ conflictNum }})
        </el-button>
        <el-button v-if="unScheduleNum" type="primary" plan @click="unScheduleVisible = true">
          未排课({{ unScheduleNum }})
        </el-button>
        <el-button type="primary" @click="submitForm" :loading="submiting">保存</el-button>
      </div>
    </div>
    <el-row :gutter="10">
      <el-col :span="12">
        <div class="title">{{ adminclass }}课表</div>
        <el-table ref="courseTable" :data="tableList" class="time_table" border>
          <el-table-column label="节次" prop="name" align="center" width="50"/>
          <template v-for="(w, wIndex) in weekdays">
            <el-table-column :label="w" align="center">
              <template slot-scope="scope">
                <div @click="onPlanClick(scope.row, wIndex)"
                     @mouseenter="onPlanEnter(scope.row, wIndex)"
                     class="course" :class="{'selected': isPlanSelected(scope.row, wIndex),
                     'swapable': isPlanSwapable(scope.row, wIndex),
                     'adjustable': isPlanAdjustable(scope.row, wIndex),
                       'hovered': isPlanHovered(scope.row, wIndex)}">
                  <div v-for="v in getPlansByTd(scope.row, wIndex)">
                    {{ v.courseName }}
                  </div>
                </div>
              </template>
            </el-table-column>
          </template>
        </el-table>
        <div>
          <div class="title">暂存区</div>
          <div class="temp_div" @click="onTempClick()">
            <div style="display: flex;">
              <div v-for="v in tempLessonPlans" class="temp_course"
                   @click.stop="onTempCourseClick(v)"
                   :class="{'selected': isTempPlanSelected(v)}">
                {{ v.courseName }} ({{ v.hour - v.planTimes.length }})
              </div>
            </div>
          </div>
        </div>
        <div>
          <ul class="list-inline">
            <li>
              <div class="sign-color" style="background-color: #fff273"></div>
              当前选中的课程
            </li>
            <li>
              <div class="sign-color" style="background-color: #ccff99"></div>
              可对调课程
            </li>
<!--            <li>
              <div class="sign-color" style="background-color: #ccffff"></div>
              可安排，但不可以对调，目的课程将移到暂存区
            </li>-->
          </ul>
        </div>
      </el-col>
      <el-col :span="12">
        <div>
          <div class="title">{{ hoverPlan.teacher }}教师课表</div>
          <el-table :data="tableList" class="time_table time_table2" border>
            <el-table-column label="节次" prop="name" align="center" width="50"/>
            <template v-for="(w, wIndex) in weekdays">
              <el-table-column :label="w" align="center">
                <template slot-scope="scope">
                  <div class="course" :class="{'selected': isPlanSelected(scope.row, wIndex),
                       'hovered': isPlanHovered(scope.row, wIndex)}">
                    <div v-for="v in getHoverPlanByTd(scope.row, wIndex)">
                      <div v-for="a in v.adminclasses">{{ a }}{{ v.courseName }}</div>
                    </div>
                  </div>
                </template>
              </el-table-column>
            </template>
          </el-table>
        </div>
        <div style="margin-top: 16px;">
          <div class="title">{{ selectPlan.teacher || '选中的' }}教师课表</div>
          <el-table :data="tableList" class="time_table2" border>
            <el-table-column label="节次" prop="name" align="center" width="50"/>
            <template v-for="(w, wIndex) in weekdays">
              <el-table-column :label="w" align="center">
                <template slot-scope="scope">
                  <div class="course" :class="{'selected': isPlanSelected(scope.row, wIndex),
                       'hovered': isPlanHovered(scope.row, wIndex)}">
                    <div v-for="v in getTeacherPlanByTd(scope.row, wIndex)">
                      <div v-for="a in v.adminclasses">{{ a }}{{ v.courseName }}</div>
                    </div>
                  </div>
                </template>
              </el-table-column>
            </template>
          </el-table>
        </div>
      </el-col>
    </el-row>
    <conflict-dialog :open.sync="conflictVisible" :conflictPlans="conflictPlans"></conflict-dialog>
    <un-schedule-dialog :open.sync="unScheduleVisible" :plans="unSchedulePlans"
                        @change="onUnScheduleChange"></un-schedule-dialog>
  </div>
</template>

<script>
import {getIndex} from "@/api/teach/paike/adjust.js"
import TimesMixin from "../TimesMixin";
import adjust from "./adjust";
import ConflictDialog from "./conflictDialog";
import UnScheduleDialog from "./unScheduleDialog";

export default {
  name: "adjustAdminclass",
  components: {ConflictDialog, UnScheduleDialog},
  mixins: [TimesMixin, adjust],
  data() {
    return {
      mode: 'adminclass',
      lessonPlans: [],
      grade: null,
      grades: [],
      adminclass: null,
      adminclasses: [],
      adminclassPlans: [],
      teacher: null,
      selectPlan: {},
      selectTd: '',
      selectTempPlan: {},
    }
  },
  watch: {
    grade() {
      // console.log('grade', this.grade)
      let plans = this.lessonPlans.filter(p => p.grade == this.grade)
      let adminclasses = new Set()
      plans.forEach(plan => plan.adminclasses.forEach(a => adminclasses.add(a)))
      adminclasses = Array.from(adminclasses)
      if (!this.adminclass || !this.adminclass.indexOf(this.grade) == 0) {
        this.adminclass = adminclasses[0]
      }
      this.adminclasses = adminclasses
    },
    adminclass() {
      this.adminclassPlans = this.lessonPlans.filter(p => p.adminclasses.indexOf(this.adminclass) >= 0)
      this.cleanSelectPlan()
    },
  },
  computed: {
    tempLessonPlans() {
      return this.adminclassPlans.filter(p => p.hour > p.planTimes.length)
    },
    teacherPlans() {
      if (!this.selectPlan.teacher) {
        return []
      }
      let teacher = this.selectPlan.teacher
      return this.lessonPlans.filter(p => p.teacher == teacher)
    }
  },
  created() {
    getIndex().then((res) => {
      this.init(res)
    })
  },
  methods: {
    getPlansByTd(row, weekday) {
      let key = this.getTdKey(row, weekday)
      let plans = this.adminclassPlans.filter(p => p.adminclasses.indexOf(this.adminclass) >= 0
        && p.planTimes.indexOf(key) >= 0)
      return plans
    },
    getSelectPlan() {
      return this.selectPlan.id ? this.selectPlan : this.selectTempPlan
    },
    isPlanAdjustable(row, weekday) {
      if (!this.selectTd) {
        return false
      }
      if (!this.isPlanSwapable(row, weekday)) {
        return false
      }
      let selectPlan = this.getSelectPlan()
      let currTd = this.getTdKey(row, weekday)
      let currPlans = this.getPlansByAdminclassesAndTime(selectPlan.adminclasses, currTd)
      if (!currPlans.length) {
        return false
      }
      let swapable = true
      // 判断选中课程是否有时间
      swapable = swapable && this.checkConfigTimes(currPlans, this.getIndexByKey(this.selectTd))
      swapable = swapable && !this.hasTeacherTime(currPlans, this.selectTd)
      return !swapable
    },
    getPlanByTdKey(key) {
      let plans = this.adminclassPlans.filter(p => p.planTimes.indexOf(key) >= 0)
      return plans.length ? plans[0] : null
    },
    onTempClick() {
      if (!this.selectPlan.id) {
        return
      }
      this.removePlanTime([this.selectPlan], this.selectTd)
      this.cleanSelectPlan()
    },
    onTempCourseClick(plan) {
      if (this.selectTd) {
        this.onTempClick()
      } else {
        if (this.selectPlan.id == plan.id) {
          this.selectPlan = {}
        } else {
          this.selectPlan = plan
        }
      }
    },
    isTempPlanSelected(plan) {
      return this.selectPlan.id == plan.id
    },
    onUnScheduleChange(plan) {
      // console.log('onUnScheduleChange', plan)
      this.adminclass = plan.adminclass
      this.grade = plan.grade
    },
  }
}
</script>
<style lang="scss">
@import './adjust.scss';
</style>
