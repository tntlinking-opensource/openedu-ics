<template>
  <div v-if="open">
    <el-dialog :title="title" :visible="open" width="800px" :close-on-click-modal="false" append-to-body @close="close">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="年级" prop="grade">
          <el-input v-model="form.grade" placeholder="请输入年级" maxlength="10" show-word-limit/>
        </el-form-item>
        <el-form-item label="班级" prop="adminclass">
          <el-input v-model="form.adminclass" placeholder="请输入班级" maxlength="10" show-word-limit/>
        </el-form-item>
        <el-form-item label="教学楼" prop="building">
          <el-input v-model="form.building" placeholder="请输入教学楼" maxlength="10" show-word-limit/>
        </el-form-item>
        <el-form-item label="教室号" prop="room">
          <el-input v-model="form.room" placeholder="请输入教室号" maxlength="10" show-word-limit/>
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
import {getClassroom, saveOrUpdate} from "@/api/teach/paike/base/classroom"

export default {
  name: "ClassroomEdit",
  props: ['open', 'id'],
  data() {
    return {
      // 弹出层标题
      title: "班级教室",
      url: process.env.VUE_APP_BASE_API + "/teach/paike/classroom",
      loading: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        grade: [{required: true, message: "年级不能为空", trigger: "blur"}],
        adminclass: [{required: true, message: "班级不能为空", trigger: "blur"}],
        building: [{required: true, message: "教学楼不能为空", trigger: "blur"}],
        room: [{required: true, message: "教室号不能为空", trigger: "blur"}]
      },
    }
  },
  created() {
    if (this.id) {
      this.title = "修改班级教室"
      this.getForm()
    } else {
      this.title = "添加班级教室"
    }
    // getEditDatas().then(res => {
    //   Object.assign(this, res)
    // })
  },
  methods: {
    getForm() {
      getClassroom(this.id).then(res => {
        this.form = res.data
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.loading = true
          saveOrUpdate(this.form).then(response => {
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
