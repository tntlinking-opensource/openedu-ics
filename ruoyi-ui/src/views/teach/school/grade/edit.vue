<template>
  <div v-if="open">
    <el-dialog :title="title" :visible="open" width="800px" :close-on-click-modal="false" append-to-body @close="close">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="编号" prop="code">
          <el-input v-model="form.code" placeholder="请输入编号" maxlength="100" show-word-limit/>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" maxlength="100" show-word-limit/>
        </el-form-item>
        <el-form-item label="年级组长" prop="teacherId">
          <el-select v-model="form.teacherId" placeholder="请选择年级组长"
                     filterable clearable remote :remote-method="getTeachers">
            <el-option v-for="v in teachers" :key="v.id" :label="v.label" :value="v.id"/>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" :loading="loading">确 定</el-button>
        <el-button @click="close">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {getGrade, getEditDatas, saveOrUpdateGrade} from "@/api/teach/school/grade"
import {getTeachers} from "@/api/teach/school/schoolCommon";

export default {
  name: "GradeEdit",
  props: ['open', 'id'],
  data() {
    return {
      // 弹出层标题
      title: "年级",
      url: process.env.VUE_APP_BASE_API + "/teach/school/grade",
      loading: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        code: [{required: true, message: "编号不能为空", trigger: "blur"}],
        name: [{required: true, message: "名称不能为空", trigger: "blur"}],
        // teacherId: [{required: true, message: "年级组长不能为空", trigger: "change"}],
      },
      teachers: [],
    }
  },
  created() {
    if (this.id) {
      this.title = "修改年级"
      this.getForm()
    } else {
      this.title = "添加年级"
    }
    getEditDatas().then(res => {
      Object.assign(this, res)
    })
  },
  methods: {
    getForm() {
      getGrade(this.id).then(res => {
        if (res.data.teacher) {
          this.teachers.push(res.data.teacher)
        }
        this.form = res.data
      })
    },
    getTeachers(key) {
      getTeachers(key).then(res => {
        this.teachers = res.data
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.loading = true
          saveOrUpdateGrade(this.form).then(response => {
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
