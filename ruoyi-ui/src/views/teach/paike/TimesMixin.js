import {sortList} from "../../../utils/ruoyi";

const WEEK_DAYS = ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
const TIMES_SPLIT = ';'

const TimesMixin = {
  data() {
    return {
      timeConfig: {
        weekdays: 5,
        morning: 4,
        afternoon: 3,
        night: 0,
        count: 7,
      },
      ruleList: [],
      tableList: [],
      submiting: false,
      weekdays: [],
      selectRules: [],
    }
  },
  methods: {
    initList() {
      let weekdays = JSON.parse(JSON.stringify(WEEK_DAYS))
      weekdays.length = this.timeConfig.weekdays
      this.weekdays = weekdays
      let tableList = []
      for (let i = 0; i < this.timeConfig.count; i++) {
        tableList.push({
          index: i,
          name: i + 1,
        })
      }
      this.tableList = tableList
    },
    getTimeByRowAndWeekday(row, weekday) {
      if (this.selectRules.length == 0) {
        return '';
      }
      let index = this.getIndex(row, weekday)
      return this.isAllZero(index) ? '不排课' : '';
    },
    getIndex(row, weekday) {
      return weekday * (this.timeConfig.count + 1) + row.index
    },
    isAllZero(index) {
      let count = this.selectRules.filter(c => c.times[index] == '0').length
      return count == this.selectRules.length;
    },
    getTimes() {
      let times = ''
      for (let i = 0; i < this.timeConfig.weekdays; i++) {
        if (i > 0) {
          times += TIMES_SPLIT
        }
        for (let j = 0; j < this.timeConfig.count; j++) {
          times += '1'
        }
      }
      return times
    },
    getTimeOfDay() {
      return this.timeConfig.morning + this.timeConfig.afternoon + this.timeConfig.night
    },
    getTimeLength() {
      return this.timeConfig.weekdays * (this.getTimeOfDay() + 1) - 1
    },
    getRuleByCode(code) {
      let adminclassTimes = this.ruleList.filter(c => c.code == code)
      return adminclassTimes.length > 0 ? adminclassTimes[0] : null
    },
    onUnitClick(row, weekday) {
      if (this.selectRules.length == 0) {
        this.msgError('请选择需要配置的项目')
        return;
      }
      let index = this.getIndex(row, weekday)
      let time = this.isAllZero(index) ? '1' : '0'
      this.selectRules.forEach(c => {
        c.times = c.times.substring(0, index) + time + c.times.substring(index + 1);
      })
    },
    getContent(times) {
      let tt = times.split(TIMES_SPLIT)
      let content = ''
      for (let i = 0; i < tt.length; i++) {
        let t = tt[i]
        for (let j = 0; j < t.length; j++) {
          if (t[j] == '0') {
            if (content.length > 0) {
              content += '，'
            }
            content += `${WEEK_DAYS[i]}第${j + 1}节`
          }
        }
      }
      return content
    },
    getByKey(datas, key) {
      let values = []
      datas.forEach(d => {
        let value = d[key]
        if(value == null){
          return
        }
        if (values.indexOf(value) < 0) {
          values.push(value)
        }
      })
      // values.sort()
      sortList(values)
      return values
    },
    getTdKey(row, weekday) {
      return `${weekday + 1}-${row.index + 1}`
    },
    hasSame(list1, list2) {
      let has = false
      list1.forEach(v1 => has = has || list2.indexOf(v1) >= 0)
      return has
    },
    formatTime(time) {
      let ss = time.split('-')
      return `${WEEK_DAYS[ss[0] - 1]}第${ss[1]}节`
    },
    selectAll() {
      this.setSelectRulesTime(0, this.getTimeLength(), '0')
    },
    clean() {
      this.setSelectRulesTime(0, this.getTimeLength(), '1')
    },
    setSelectRulesTime(start, end, time) {
      this.selectRules.filter(c => {
        let times = c.times.split('')
        for (let i = start; i < end; i++) {
          if (times[i] != ';') {
            times[i] = time
          }
        }
        c.times = times.join('')
      })
    },
    isMorningSelect(week) {
      let select = this.isTimeSelectBetween(0, this.timeConfig.morning, week)
      // console.log('isMorningSelect', week, select)
      return select
    },
    isAfternoonSelect(week) {
      let select = this.isTimeSelectBetween(this.timeConfig.morning, this.timeConfig.morning + this.timeConfig.afternoon, week)
      // console.log('isMorningSelect', week, select)
      return select
    },
    isTimeSelectBetween(start, end, week) {
      let zero = true
      for (let i = start; i < end; i++) {
        zero = zero && (this.getTimeByRowAndWeekday({index: i}, week) != '')
      }
      return zero
    },
    onMorningChange(week) {
      // console.log('onMorningChange', week)
      let start = week * (this.getTimeOfDay() + 1)
      let end = start + this.timeConfig.morning
      let time = this.isMorningSelect(week) ? '1' : '0'
      this.setSelectRulesTime(start, end, time)
    },
    onAfternoonChange(week) {
      // console.log('onMorningChange', week)
      let start = week * (this.getTimeOfDay() + 1) + this.timeConfig.morning
      let end = start + this.timeConfig.afternoon
      let time = this.isAfternoonSelect(week) ? '1' : '0'
      this.setSelectRulesTime(start, end, time)
    },
  }
}

export default TimesMixin
