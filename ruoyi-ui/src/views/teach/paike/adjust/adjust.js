import {saveOrUpdate} from "@/api/teach/paike/adjust.js"

const AdjustMixin = {
  data() {
    return {
      mode: 'teacher',
      hoverTd: null,
      hoverTeacher: null,
      hoverPlan: {},
      conflictVisible: false,
      unScheduleVisible: false,
      rules: [],
    }
  },
  computed: {
    tempLessonPlans() {
      return this.lessonPlans.filter(p => p.hour > p.planTimes.length)
    },
    hoverPlans() {
      if (!this.hoverTd) {
        return []
      }
      let teacher = this.hoverPlan.teacher
      return this.lessonPlans.filter(p => p.teacher == teacher)
    },
    conflictPlans() {
      console.log('conflictPlans', this.rules)
      let list = []
      let teachers = this.getByKey(this.lessonPlans, 'teacher')
      teachers.forEach(teacher => {
        if (!teacher) {
          return
        }
        let ps = this.lessonPlans.filter(p => p.teacher == teacher)
        this.getTimes().forEach(time => {
          let pst = ps.filter(p => p.planTimes.indexOf(time) >= 0)
          if (pst.length > 1) {
            let adminclass = this.getAdminclassByPlans(pst)
            list.push({
              index: list.length + 1,
              content: `${teacher}老师：${this.formatTime(time)}，${adminclass}${pst[0].courseName}冲突`
            })
          }
        })
      })
      let adminclasses = this.getAdminclasses()
      // console.log('adminclasses', adminclasses)
      adminclasses.forEach(adminclass => {
        let ps = this.lessonPlans.filter(p => p.adminclasses.indexOf(adminclass) >= 0)
        this.getTimes().forEach(time => {
          let pst = ps.filter(p => p.planTimes.indexOf(time) >= 0)
          if (pst.length > 1) {
            let courses = pst.map(p => p.courseName).join('、')
            list.push({
              index: list.length + 1,
              content: `${adminclass}：${this.formatTime(time)}，${courses}冲突`
            })
          }
        })
      })
      this.rules.forEach(rule => {
        if ("课程不排课时间" != rule.name) {
          return
        }
        this.lessonPlans.forEach(plan => {
          let code = plan.course
          if (code != rule.code) {
            return
          }
          plan.planTimes.forEach(time => {
            if (rule.planTimes.indexOf(time) < 0) {
              list.push({
                index: list.length + 1,
                content: `${plan.adminclass}${plan.courseName}(${time})不是指定上课时间`
              })
            }
          })
        })
      })
      return list;
    },
    conflictNum() {
      return this.conflictPlans.length
    },
    unSchedulePlans() {
      let plans = this.lessonPlans.filter(p => p.hour > p.planTimes.length)
      let list = []
      plans.forEach(plan => {
        let adminclass = plan.adminclasses.join('、')
        let num = plan.hour - plan.planTimes.length
        let course = plan.courseName
        let times = plan.planTimes.join('、')
        list.push({
          index: list.length + 1,
          grade: plan.grade,
          adminclass: plan.adminclasses[0],
          content: `${adminclass}${course}，总课时：${plan.hour}，已排：${times}，未排(${num})`
        })
      })
      return list;
    },
    unScheduleNum() {
      return this.unSchedulePlans.length
    },
  },
  methods: {
    init(res) {
      // console.log('init', res)
      res.lessonPlans.forEach(p => {
        p.selected = false
        p.planTimes = p.planTimes ? p.planTimes.split("、") : []
      })
      res.rules.forEach(p => {
        p.planTimes = p.planTimes ? p.planTimes.split("、") : []
      })
      this.lessonPlans = res.lessonPlans
      this.timeConfig = res.timeConfig
      this.rules = res.rules
      let grades = this.getByKey(this.lessonPlans, 'grade')
      this.grade = grades[0]
      this.grades = grades
      this.initList()
    },
    onPlanEnter(row, weekday) {
      // console.log('onPlanEnter', row, weekday)
      let key = this.getTdKey(row, weekday)
      let plan = this.getPlanByTd(row, weekday)
      if (!plan) {
        return
      }
      this.hoverTd = key
      this.hoverPlan = plan
    },
    onPlanClick(row, weekday) {
      console.log('onPlanClick', row, weekday)
      let currTd = this.getTdKey(row, weekday)
      let selectPlan = this.getSelectPlan()
      if (!selectPlan.id) {
        // 第一次选中
        selectPlan = this.getPlanByTdKey(currTd)
        if (!selectPlan) {
          // 空白格忽略
          return
        }
        this.selectTd = currTd
        this.selectPlan = this.getPlanByTdKey(currTd) || {}
      } else {
        let currPlans = this.getPlansByAdminclassesAndTime(selectPlan.adminclasses, currTd)
        let adminclasses = this.getAllAdminclasses(currPlans)
        // 与空白格交换
        if (adminclasses.length == 0) {
          adminclasses = selectPlan.adminclasses
        }
        let selectPlans = this.getPlansByAdminclassesAndTime(adminclasses, this.selectTd || currTd)
        if (this.selectTd) {
          // 课表内交换
          // 不检测冲突
          // if (this.isPlanSwapable(row, weekday)) {
          if (currPlans.length) {
            if (!this.isPlanAdjustable(row, weekday)) {
              this.addPlanTime(currPlans, this.selectTd)
              this.removePlanTime(currPlans, currTd)
            }
          }
          this.removePlanTime(selectPlans, this.selectTd)
          this.addPlanTime(selectPlans, currTd)
          saveOrUpdate(currPlans)
          saveOrUpdate(selectPlans)
          // }
        } else {
          // 从暂存区调换
          // 不检测冲突
          // if (this.isPlanSwapable(row, weekday)) {
          selectPlan.planTimes.push(currTd)
          this.removePlanTime(currPlans, currTd)
          saveOrUpdate([selectPlan])
          saveOrUpdate(currPlans)
          // }
        }
        this.cleanSelectPlan()
      }
    },
    isPlanSelected(row, weekday) {
      return this.selectTd == this.getTdKey(row, weekday)
    },
    isPlanHovered(row, weekday) {
      return this.hoverTd == this.getTdKey(row, weekday)
    },
    isPlanSwapable(row, weekday) {
      let selectPlan = this.getSelectPlan()
      if (!selectPlan.id) {
        return false
      }
      // console.log('isPlanSwapable', row, weekday, selectPlan)
      let currTd = this.getTdKey(row, weekday)
      let currPlans = this.getPlansByAdminclassesAndTime(selectPlan.adminclasses, currTd)
      let swapable = true
      // 判断选中课程是否有时间
      swapable = swapable && this.checkConfigTimes([selectPlan], this.getIndexByKey(currTd))
      swapable = swapable && !this.hasTeacherTime([selectPlan], currTd)
      if (swapable && currPlans.length) {
        let adminclasses = this.getAllAdminclasses(currPlans)
        let selectPlans = this.getPlansByAdminclassesAndTime(adminclasses, currTd)
        if (this.selectTd) {
          swapable = swapable && this.checkConfigTimes(selectPlans, this.getIndexByKey(this.selectTd))
          swapable = swapable && !this.hasTeacherTime(selectPlans, this.selectTd)
        }
        // if (currPlan.teacher == '陈纪' && currTd == '1-4') {
        //   console.log(currPlan)
        // }
        if (this.equalsAll(currPlans, 'teacher', selectPlan.teacher)
          && this.equalsAll(currPlans, 'courseName', selectPlan.courseName)) {
          return false
        }
        // if (currPlan.teacher == selectPlan.teacher
        //   && currPlan.adminclasses.join(',') == selectPlan.adminclasses.join(',')) {
        //   return true
        // }
        if (this.mode == 'teacher') {
          // 判断选中课程是否有时间
          swapable = swapable && this.checkConfigTimes(currPlans, this.getIndexByKey(this.selectTd))
          swapable = swapable && !this.hasTeacherTime(currPlans, this.selectTd)
        }
      }
      return swapable
    },
    getAllAdminclasses(plans) {
      let list = []
      plans.forEach(p => p.adminclasses.forEach(a => {
        if (list.indexOf(a) < 0) {
          list.push(a)
        }
      }))
      return list
    },
    isPlanAdjustable(row, weekday) {
      return false
    },
    checkConfigTimes(plans, index) {
      let result = true
      plans.forEach(p => result = result && p.configTimes[index] == '1')
      return result
    },
    equalsAll(plans, prop, teacher) {
      let result = true
      plans.forEach(p => result = result && p[prop] == teacher)
      return result
    },
    getPlansByAdminclassesAndTime(adminclasses, time) {
      return this.lessonPlans.filter(p => this.hasSame(p.adminclasses, adminclasses)
        && p.planTimes.indexOf(time) >= 0)
    },
    getTeacherPlanByTd(row, weekday) {
      let key = this.getTdKey(row, weekday)
      return this.teacherPlans.filter(p => p.teacher == this.selectPlan.teacher && p.planTimes.indexOf(key) >= 0)
    },
    getHoverPlanByTd(row, weekday) {
      let key = this.getTdKey(row, weekday)
      return this.hoverPlans.filter(p => p.planTimes.indexOf(key) >= 0)
    },
    hasTeacherTime(plans, time) {
      let has = false
      plans.forEach(plan => {
        this.lessonPlans.forEach(p => {
          if (p.teacher == plan.teacher) {
            // if(plan.teacher == '张芬'){
            //   console.log(plan)
            // }
            has = has || p.planTimes.indexOf(time) >= 0
          }
        })
      })
      return has
    },
    hasDifferent(list1, list2) {
      let has = false
      list2.forEach(v1 => has = has || list1.indexOf(v1) >= 0)
      return has
    },
    getTimes() {
      let times = []
      for (let i = 0; i < this.timeConfig.weekdays; i++) {
        for (let j = 0; j < this.timeConfig.count; j++) {
          let time = `${i + 1}-${j + 1}`
          times.push(time)
        }
      }
      return times
    },
    getAdminclassByPlans(plans) {
      return plans.map(p => p.adminclasses).map(a => a.join('、')).join('、')
    },
    getAdminclasses() {
      let list = []
      this.lessonPlans.forEach(p => p.adminclasses.forEach(a => {
        if (list.indexOf(a) < 0) {
          list.push(a)
        }
      }))
      return list
    },
    getIndexByKey(key) {
      let keys = key.split('-')
      let row = {index: keys[1] - 1}
      let weekday = keys[0] - 1
      return this.getIndexByTd(row, weekday)
    },
    getIndexByTd(row, weekday) {
      return weekday * this.getTimeOfDay() + row.index
    },
    addPlanTime(plans, time) {
      plans.forEach(plan => {
        if (plan.planTimes.indexOf(time) < 0) {
          plan.planTimes.push(time)
        }
      })
    },
    removePlanTime(plans, time) {
      plans.forEach(plan => {
        let index = plan.planTimes.indexOf(time)
        plan.planTimes.splice(index, 1)
      })
    },
    cleanSelectPlan() {
      this.selectTd = null
      this.selectPlan = {}
      this.selectTempPlan = {}
    },
    getPlanByTd(row, weekday) {
      let key = this.getTdKey(row, weekday)
      return this.getPlanByTdKey(key)
    },
    submitForm() {
      this.submiting = true
      saveOrUpdate(this.lessonPlans).then(() => {
        this.msgSuccess("保存成功")
      }).finally(() => {
        this.submiting = false
      })
    }
  }
}

export default AdjustMixin
