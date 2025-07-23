<template>
  <div class=" mixed_class">
    <h3 style="margin-bottom: 15px;">设置合班课程</h3>
    <div style="display: flex;">
      <div style="width: 33.33%;">
        <el-card class="box-card" :body-style="{ padding: '0px' }">
          <div slot="header" class="clearfix" style="text-align: center;">
            <span>第一步：选择课程</span>
          </div>
          <div class="box">
            <div v-for="c in courses" class="list-group-item"
                 :class="{'active' : c == course}" @click="onCourseClick(c)">
              {{ c }}
            </div>
          </div>
        </el-card>
      </div>
      <div style="width:100px; padding: 190px 10px; text-align: center;">
        <i class="el-icon-d-arrow-right"></i>
      </div>
      <div style="width: 33.33%;">
        <el-card class="box-card" :body-style="{ padding: '0px' }">
          <div slot="header" class="clearfix" style="text-align: center;">
            <span>第二步：选择班级</span>
          </div>
          <div class="box">
            <div v-for="p in adminclasses" class="list-group-item"
                 :class="{'active' : hasAdminclass(p)}" @click="onAdminclassClick(p)">
              {{ p.adminclass }} （{{ p.teacher }}）
            </div>
          </div>
        </el-card>
      </div>
      <div style="width:100px; padding: 190px 10px; text-align: center;">
        <div>
          <el-button type="primary" @click="onMixedClick">合班<i class="el-icon-d-arrow-right el-icon--right"></i>
          </el-button>
        </div>
        <div style="margin-top: 10px;">
          <el-button icon="el-icon-d-arrow-left" @click="onUnMixedClick">拆分</el-button>
        </div>
      </div>
      <div style="width: 33.33%;">
        <el-card class="box-card" :body-style="{ padding: '0px' }">
          <div slot="header" class="clearfix" style="text-align: center;">
            <span>已存在合班课</span>
          </div>
          <div class="box">
            <div v-for="v in mixedClassList" class="list-group-item"
                 :class="{'active' : v == mixedClassSelected}" @click="mixedClassSelected = v">
              {{ v.content }}
            </div>
          </div>
        </el-card>
      </div>
    </div>
    <div style="text-align: center; margin: 20px;">
      <el-button type="primary" @click="submitForm" :loading="submiting">保存</el-button>
    </div>
  </div>
</template>

<script>
import {getIndexDatas, listMixedClass, saveOrUpdate} from "@/api/teach/paike/intellect/mixedClass"

export default {
  name: "MixedClass",
  data() {
    return {
      lessonPlans: [],
      course: null,
      courses: [],
      adminclasses: [],
      adminclassesSelected: [],
      mixedClassList: [],
      mixedClassSelected: null,
      submiting: false,
    }
  },
  created() {
    this.getList()
    getIndexDatas().then(res => {
      Object.assign(this, res)
      this.init()
    })
  },
  methods: {
    getList() {
      listMixedClass().then(res => {
        res.rows.forEach(r => Object.assign(r, JSON.parse(r.json)))
        this.mixedClassList = res.rows
      })
    },
    init() {
      let courses = []
      this.lessonPlans.forEach(p => {
        p.adminclassName = p.grade + p.adminclass
        let course = p.course
        if (courses.indexOf(course) < 0) {
          courses.push(course)
        }
      })
      courses.sort()
      this.courses = courses
    },
    onCourseClick(course) {
      this.course = course
      let adminclasses = this.lessonPlans.filter(p => p.course == course)
        .filter(p => {
          let mixed = this.mixedClassList.filter(m => m.course == course && m.adminclassNames.indexOf(p.adminclassName) >= 0)
          return mixed.length == 0
        })
      this.adminclasses = adminclasses
      this.adminclassesSelected = []
    },
    hasAdminclass(plan) {
      return this.adminclassesSelected.indexOf(plan) >= 0
    },
    onAdminclassClick(plan) {
      // console.log(plan)
      if (this.hasAdminclass(plan)) {
        this.adminclassesSelected.splice(this.adminclassesSelected.indexOf(plan), 1)
      } else {
        this.adminclassesSelected.push(plan)
      }
    },
    onMixedClick() {
      if (this.adminclassesSelected.length <= 1) {
        return
      }
      let course = this.course
      let teachers = this.getByKey(this.adminclassesSelected, 'teacher')
      let adminclassNames = this.getByKey(this.adminclassesSelected, 'adminclass')
      let content = `${course}：${teachers.join('、')}（${adminclassNames.join('、')}）`
      let mixedClass = {
        code: '', content, course, teachers, adminclassNames
      }
      this.mixedClassList.push(mixedClass)
      this.onCourseClick(this.course)
    },
    onUnMixedClick() {
      if (this.mixedClassSelected) {
        this.mixedClassList.splice(this.mixedClassList.indexOf(this.mixedClassSelected), 1)
        this.onCourseClick(this.mixedClassSelected.course)
        this.mixedClassSelected = null
      }
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
      values.sort()
      return values
    },
    submitForm() {
      this.submiting = true
      this.mixedClassList.forEach(t => {
        let json = (({course, teachers, adminclassNames}) => ({course, teachers, adminclassNames}))(t)
        t.json = JSON.stringify(json)
      })
      saveOrUpdate(this.mixedClassList).then(() => {
        this.msgSuccess("保存成功")
      }).finally(() => {
        this.submiting = false
      })
    }
  }
}
</script>
<style scoped lang="scss">
.list-group-item {
  text-align: center; cursor: pointer;
  &:hover {
    background-color: #B1D3EC;
    color: #fff;
  }
  &.active {
    background-color: #57baf5;
    color: #fff;
  }
}
.box {
  height: 400px; overflow-y: auto;
}
</style>
<style lang="scss">
.mixed_class {
  .el-card__header {
    text-align: center;
    background-color: #FAFAFA;
  }
}
</style>
