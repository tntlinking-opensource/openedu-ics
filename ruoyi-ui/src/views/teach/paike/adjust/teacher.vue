<template>
  <div class="paike_adjust">
    <div style="display: flex; margin-bottom: 10px;">
      <div>
        <el-select v-model="grade" clearable style="width: 150px;">
          <el-option v-for="v in grades" :label="v" :value="v" :key="v"/>
        </el-select>
        <el-select v-model="course" clearable style="width: 150px; margin-left: 20px;">
          <el-option v-for="v in courses" :label="v" :value="v" :key="v"/>
        </el-select>
        <el-select v-model="teacher" filterable style="width: 150px; margin-left: 20px;">
          <el-option v-for="v in teachers" :label="v" :value="v" :key="v"/>
        </el-select>
      </div>
      <div style="flex-grow: 1; text-align: right;">
        <el-button v-if="conflictNum" type="primary" plan @click="conflictVisible = true">
          课表冲突({{ conflictNum }})
        </el-button>
        <el-button type="primary" @click="submitForm" :loading="submiting">保存</el-button>
      </div>
    </div>
    <el-row :gutter="10">
      <el-col :span="12">
        <div class="title">{{ grade }}&nbsp;{{ course }}&nbsp;{{ teacher }}&nbsp;教师课表</div>
        <el-table ref="courseTable" :data="tableList" class="time_table" border>
          <el-table-column label="节次" prop="name" align="center" width="50"/>
          <template v-for="(w, wIndex) in weekdays">
            <el-table-column :label="w" align="center">
              <template slot-scope="scope">
                <div @click="onPlanClick(scope.row, wIndex)"
                     @mouseenter="onPlanEnter(scope.row, wIndex)"
                     class="course" :class="{'selected': isPlanSelected(scope.row, wIndex),
                     'swapable': isPlanSwapable(scope.row, wIndex),
                       'hovered': isPlanHovered(scope.row, wIndex)}">
                  <div v-for="v in getPlansByTd(scope.row, wIndex)">
                    <div v-for="a in v.adminclasses">{{ a }}{{ v.courseName }}</div>
                  </div>
                </div>
              </template>
            </el-table-column>
          </template>
        </el-table>
        <!--        <div>
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
                </div>-->
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
          <div class="title">{{ adminclass || '选中的' }}班级课表</div>
          <el-table :data="tableList" class="time_table time_table2" border>
            <el-table-column label="节次" prop="name" align="center" width="50"/>
            <template v-for="(w, wIndex) in weekdays">
              <el-table-column :label="w" align="center">
                <template slot-scope="scope">
                  <div class="course" :class="{'selected': isPlanSelected(scope.row, wIndex),
                       'hovered': isPlanHovered(scope.row, wIndex)}">
                    <div v-for="v in getAdminclassPlanByTd(scope.row, wIndex)">
                      <div v-for="a in v.adminclasses">{{ v.courseName }}</div>
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
  </div>
</template>

<script>
import {getIndex} from "@/api/teach/paike/adjust.js"
import TimesMixin from "../TimesMixin";
import adjust from "./adjust";
import ConflictDialog from "./conflictDialog";

export default {
  name: "adjustTeacher",
  components: {ConflictDialog},
  mixins: [TimesMixin, adjust],
  data() {
    return {
      lessonPlans: [],
      grade: null,
      grades: [],
      course: null,
      courses: [],
      teacher: null,
      teachers: [],
      selectTd: '',
      selectPlan: {},
      selectTempPlan: {},
    }
  },
  watch: {
    grade() {
      let plans = this.lessonPlans.filter(p => p.grade = this.grade)
      let courses = this.getByKey(plans, 'courseName')
      this.course = courses[0]
      this.courses = courses
    },
    course() {
      console.log('course', this.course)
      let plans = this.lessonPlans
      if(this.course){
        plans = this.lessonPlans.filter(p => p.grade = this.grade && p.courseName == this.course)
      }
      let teachers = this.getByKey(plans, 'teacher')
      this.teacher = teachers[0]
      this.teachers = teachers
      this.cleanSelectPlan()
    },
    teacher() {
      this.cleanSelectPlan()
    },
  },
  computed: {
    teacherPlans() {
      let plans = this.lessonPlans.filter(p => p.teacher == this.teacher)
      let times = []
      plans.forEach(p => p.planTimes.forEach(t => times.push(t)))
      let plan = this.selectTd ? this.selectPlan : this.selectTempPlan
      if (plan.id) {
        this.lessonPlans.forEach(p => {
          if (p.courseName == '语文' && this.hasSame(plan.adminclasses, p.adminclasses)) {
            console.log(p)
          }
          if (p.teacher != plan.teacher
            && this.hasSame(plan.adminclasses, p.adminclasses)) {
            plans.push(p)
          }
        })
      }
      return plans
    },
    adminclass() {
      if (!this.selectTd) {
        return ""
      }
      return this.selectPlan.adminclasses.join("、")
    },
    adminclassPlans() {
      if (!this.selectTd) {
        return []
      }
      let adminclasses = this.selectPlan.adminclasses
      return this.lessonPlans.filter(p => this.hasSame(adminclasses, p.adminclasses))
    },
  },
  created() {
    getIndex().then((res) => {
      this.init(res)
    })
  },
  methods: {
    getPlansByTd(row, weekday) {
      let key = this.getTdKey(row, weekday)
      let plan = this.selectPlan
      let plans = this.teacherPlans.filter(p => p.teacher == plan.teacher && p.planTimes.indexOf(key) >= 0)
      if (plans.length == 0) {
        plans = this.teacherPlans.filter(p => p.planTimes.indexOf(key) >= 0)
      }
      return plans
    },
    getSelectPlan() {
      return this.selectPlan
    },
    getPlanByTdKey(key) {
      let plans = this.teacherPlans.filter(p => p.planTimes.indexOf(key) >= 0)
      return plans.length ? plans[0] : null
    },
    getAdminclassPlanByTd(row, weekday) {
      let key = this.getTdKey(row, weekday)
      return this.adminclassPlans.filter(p => p.planTimes.indexOf(key) >= 0)
    },
  }
}
</script>

<style lang="scss">
@import './adjust.scss';
</style>
