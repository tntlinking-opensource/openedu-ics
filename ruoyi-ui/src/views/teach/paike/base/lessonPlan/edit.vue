<template>
  <el-dialog :title="title" :visible="open" width="800px" :close-on-click-modal="false"
             append-to-body @close="close">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <template v-if="!entity.adminclass">
        <el-form-item label="年级" prop="grade">
          <el-select v-model="form.grade" filterable allow-create default-first-option placeholder="请选择年级"
                     @change="onGradeChange">
            <el-option v-for="v in grades" :key="v" :label="v" :value="v"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班级" prop="adminclass">
          <el-input v-model="form.adminclass" placeholder="请输入班级" maxlength="8" show-word-limit/>
        </el-form-item>
      </template>
      <template v-else>
        <el-form-item label="年级">{{ entity.grade }}</el-form-item>
        <el-form-item label="班级">{{ entity.adminclass }}</el-form-item>
      </template>
      <el-form-item label="课程">
        <table class="el-table el-table--border">
          <thead>
          <tr>
            <th>课程</th>
            <th>课时({{ hourTotal }})</th>
            <th>任课教师</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(p, index) in form.plans" :key="p.id">
            <td>
              <el-select v-model="p.course" filterable allow-create default-first-option
                         placeholder="请选择课程">
                <el-option v-for="v in availableCourses" :key="v" :label="v" :value="v"></el-option>
              </el-select>
            </td>
            <td>
              <el-input v-model="p.hours"/>
            </td>
            <td>
              <el-select v-model="p.teacher" multiple filterable allow-create default-first-option
                         placeholder="请选择任课教师">
                <el-option v-for="v in teachers" :key="v" :label="v" :value="v"></el-option>
              </el-select>
            </td>
            <td>
              <el-button type="danger" plain @click="removePlan(index)">删除</el-button>
            </td>
          </tr>
          </tbody>
        </table>
        <div style="margin-top: 10px;">
          <el-button type="primary" plain @click="addPlan">添加课程</el-button>
        </div>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm" :loading="loading">确 定</el-button>
      <el-button @click="close">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {getEditDatas, getPlanDatas, listLessonPlan, saveBatch} from "@/api/teach/paike/base/lessonPlan"

export default {
  name: "LessonPlanEdit",
  props: ['open', 'entity'],
  data() {
    return {
      // 弹出层标题
      title: "任课信息编辑",
      url: process.env.VUE_APP_BASE_API + "/teach/paike/lessonPlan",
      loading: false,
      // 表单参数
      form: {
        adminclass: null,
        grade: null,
        plans: [{}, {}, {}, {}, {}],
        removePlanIds: [],
      },
      // 表单校验
      rules: {
        grade: [{required: true, message: "年级不能为空", trigger: "blur"}],
        adminclass: [{required: true, message: "班级不能为空", trigger: "blur"}],
      },
      grades: [],
      adminclasses: [],
      courses: [],
      teachers: [],
    }
  },
  computed: {
    availableCourses: function () {
      return this.courses.filter(c => this.form.plans.filter(p => p.course == c).length == 0)
    },
    hourTotal() {
      return this.form.plans.map(c => c.hours || 0).reduce((prev, curr) => +prev + +curr, 0);
    }
  },
  created() {
    if (this.entity.adminclass) {
      this.form.adminclass = this.entity.adminclass
      this.form.grade = this.entity.grade
      this.getForm()
      this.onGradeChange()
    }
    getEditDatas().then(res => {
      Object.assign(this, res)
    })
  },
  methods: {
    getForm() {
      listLessonPlan(this.entity).then(res => {
        res.data.forEach(p => {
          if (p.teacher) {
            p.teacher = p.teacher.split('+')
          } else {
            p.teacher = []
          }
        })
        this.form.plans = res.data
      })
    },
    onGradeChange() {
      getPlanDatas({
        grade: this.form.grade,
      }).then(res => {
        Object.assign(this, res)
      })
    },
    removePlan(index) {
      let plan = this.form.plans[index]
      if (plan.id) {
        this.form.removePlanIds.push(plan.id)
      }
      this.form.plans.splice(index, 1)
    },
    addPlan(index) {
      this.form.plans.push({})
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.loading = true
          let data = JSON.parse(JSON.stringify(this.form))
          data.plans.forEach(p => p.teacher = p.teacher.join('+'))
          saveBatch(data).then(response => {
            this.msgSuccess("保存成功")
            this.$emit('submit')
            this.close()
          }).finally(() => {
            this.loading = false
          })
        }
      })
    },
    // 取消按钮
    close() {
      this.$emit('update:open', false)
    },
  }
}
</script>
<style scoped lang="scss">
.el-table {
  td {padding: 2px 5px;}
}
</style>
