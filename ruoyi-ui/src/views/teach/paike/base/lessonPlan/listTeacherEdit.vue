<template>
  <el-dialog :title="title" :visible="open" width="800px" :close-on-click-modal="false"
             append-to-body @close="close">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">

      <el-form-item label="任课教师">
        <el-select v-model="form.teacher" multiple filterable allow-create default-first-option
                   placeholder="请选择任课教师">
          <el-option v-for="v in teachers" :key="v" :label="v" :value="v"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm" :loading="loading">确 定</el-button>
      <el-button @click="close">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {getEditDatas, saveTeacher} from "@/api/teach/paike/base/lessonPlan"

export default {
  name: "LessonPlanListTeacherEdit",
  props: ['open', 'ids'],
  data() {
    return {
      // 弹出层标题
      title: "任课教师编辑",
      loading: false,
      // 表单参数
      form: {
        ids: [],
        teacher: [],
      },
      rules: {},
      teachers: [],
    }
  },
  created() {
    getEditDatas().then(res => {
      Object.assign(this, res)
    })
  },
  methods: {
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.loading = true
          let data = JSON.parse(JSON.stringify(this.form))
          data.teacher = data.teacher.join('+')
          data.ids = this.ids
          saveTeacher(data).then(response => {
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
