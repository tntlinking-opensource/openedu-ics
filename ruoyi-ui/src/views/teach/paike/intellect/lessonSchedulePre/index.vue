<template>
  <div class=" lesson_schedule_pre">
    <div style="display: flex;overflow-x: auto; position: relative; min-width: 1000px;">
      <div style="width: 460px;">
        <div>
          <el-select v-model="grade" style="width: 100px;" clearable>
            <el-option v-for="v in grades" :label="v" :value="v" :key="v"/>
          </el-select>
          <el-select v-model="adminclass" style="width: 100px; margin-left: 20px;" clearable>
            <el-option v-for="v in adminclasses" :label="v" :value="v" :key="v"/>
          </el-select>
          <el-select v-model="course" style="width: 100px; margin-left: 20px;" filterable clearable>
            <el-option v-for="v in courses" :label="v" :value="v" :key="v"/>
          </el-select>
          <el-select v-model="teacher" style="width: 100px; margin-left: 20px;" filterable clearable>
            <el-option v-for="v in teachers" :label="v" :value="v" :key="v"/>
          </el-select>
        </div>
        <div style="margin-top: 10px;">
          <el-table :data="courseLessonPlans" @row-click="onCourseClick" :row-class-name="courseLessonPlanRowClassName"
                    height="500">
            <el-table-column label="课程" prop="course" align="center"/>
            <el-table-column label="年级" prop="grade" align="center"/>
            <el-table-column label="班级" prop="adminclass" align="center"/>
            <el-table-column label="教师" prop="teacher" align="center"/>
            <el-table-column label="课时" align="center">
              <template slot-scope="scope">{{ getHourLeft(scope.row) }}/{{ scope.row.hour }}</template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div style="flex: 1; padding-left: 10px; position: relative;">
        <div style="position: absolute; width: calc(100% - 10px);">
          <div>
            <!--          <el-button type="primary">导入</el-button>-->
            <el-button @click="cleanLessonSchedulePre">清空</el-button>
            <!--          <el-button>导出</el-button>-->
          </div>
          <div style="margin-top: 10px;">
            <el-table :data="tableList" border class="time_table">
              <el-table-column label="节次" prop="name" align="center" width="50"/>
              <template v-for="(w, wIndex) in weekdays">
                <el-table-column :label="w" align="center">
                  <template slot-scope="scope">
                    <div class="pre_div" @click="addLessonSchedulePre(scope.row, wIndex)">
                      <div v-for="v in getLessonPlanByTd(scope.row, wIndex)">
                      <span class="remove" title="删除"
                            @click.stop="removeLessonSchedulePre(scope.row, wIndex, v)">×</span>
                        <span>{{ v.course }}-{{ v.adminclass }}</span>
                      </div>
                    </div>
                  </template>
                </el-table-column>
              </template>
            </el-table>
          </div>
        </div>
      </div>
    </div>
    <div style="text-align: center; margin: 20px;">
      <el-button type="primary" @click="submitForm" :loading="submiting">保存</el-button>
    </div>
  </div>
</template>
<script>
import {getIndexDatas, saveOrUpdate} from "@/api/teach/paike/intellect/lessonSchedulePre.js"
import TimesMixin from "../../TimesMixin";

export default {
  name: "LessonSchedulePre",
  mixins: [TimesMixin],
  data() {
    return {
      lessonPlans: [],
      grades: [],
      grade: null,
      adminclasses: [],
      adminclass: null,
      // courses: [],
      course: null,
      teachers: [],
      teacher: null,
      // courseLessonPlans: [],
      courseLessonPlanSelected: null,
      submiting: false,
    }
  },
  watch: {
    // grade() {
    //   // console.log('grade', this.grade)
    //   let courses = []
    //   this.lessonPlans.forEach(p => {
    //     if (p.grade == this.grade && courses.indexOf(p.course) < 0) {
    //       courses.push(p.course)
    //     }
    //   })
    //   // this.course = courses[0]
    //   this.courses = courses
    // },
    // course() {
    //   // console.log('course', this.course)
    //   this.courseLessonPlans = this.lessonPlans.filter(p => p.grade == this.grade && p.course == this.course)
    //   this.courseLessonPlanSelected = null
    // },
  },
  computed: {
    courseLessonPlans: function () {
      let {grade, adminclass, course, teacher} = this
      return this.lessonPlans.filter(p => {
        if (grade && p.grade != grade) {
          return false
        }
        if (course && p.course != course) {
          return false
        }
        if (adminclass && p.adminclass != adminclass) {
          return false
        }
        if (teacher && p.teacher != teacher) {
          return false
        }
        return true
      })
    },
    courses: function () {
      return this.getByKey(this.courseLessonPlans, 'course')
    }
  },
  created() {
    getIndexDatas().then((res) => {
      this.init(res)
    })
  },
  methods: {
    init(res) {
      // console.log('init', res)
      res.lessonPlans.forEach(p => {
        p.selected = false
        p.pres = p.timesPres ? p.timesPres.split("、") : []
      })
      this.lessonPlans = res.lessonPlans
      this.timeConfig = res.timeConfig
      let grades = this.getByKey(this.lessonPlans, 'grade')
      // this.grade = grades[0]
      this.grades = grades
      this.initList()
      this.teachers = this.getByKey(this.lessonPlans, 'teacher').filter(t => !!t)
      this.adminclasses = this.getByKey(this.lessonPlans, 'adminclass')
    },
    getHourLeft(plan) {
      return plan.hour - plan.pres.length
    },
    onCourseClick(plan) {
      // console.log('lessonPlan', plan)
      this.courseLessonPlans.forEach(p => p.selected = false)
      plan.selected = true
      this.courseLessonPlanSelected = plan
    },
    courseLessonPlanRowClassName({row}) {
      // console.log('courseLessonPlanRowClassName', row)
      return row.selected ? 'selected' : ''
    },
    addLessonSchedulePre(row, weekday) {
      // console.log('addLessonSchedulePre', row, weekday, this.courseLessonPlanSelected)
      if (this.courseLessonPlanSelected == null) {
        return
      }
      if (this.getHourLeft(this.courseLessonPlanSelected) <= 0) {
        return
      }
      let key = this.getTdKey(row, weekday)
      let plan = this.courseLessonPlanSelected
      if (plan.pres.indexOf(key) < 0) {
        plan.pres.push(key)
      }
    },
    removeLessonSchedulePre(row, weekday, pre) {
      let key = this.getTdKey(row, weekday)
      pre.pres.splice(pre.pres.indexOf(key), 1)
    },
    getLessonPlanByTd(row, weekday) {
      // console.log('row weekday', row, weekday)
      let key = this.getTdKey(row, weekday)
      // return this.lessonPlans.filter(p => p.pres.indexOf(key) >= 0)
      return this.courseLessonPlans.filter(p => p.pres.indexOf(key) >= 0)
    },
    cleanLessonSchedulePre() {
      this.lessonPlans.forEach(p => p.pres = [])
    },
    submitForm() {
      let lessonPlans = JSON.parse(JSON.stringify(this.lessonPlans))
      lessonPlans.forEach(p => {
        p.timesPres = p.pres.join('、')
        delete p.pres
      })
      this.submiting = true
      saveOrUpdate(lessonPlans).then(() => {
        this.msgSuccess("保存成功")
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
