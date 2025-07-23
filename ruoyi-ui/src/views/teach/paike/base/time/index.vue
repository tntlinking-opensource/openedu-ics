<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>作息安排</span>
    </div>
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-form-item label="每周天数" prop="weekdays">
        <el-input v-model.number="form.weekdays" placeholder="请输入每周天数" maxlength="1" show-word-limit/>
      </el-form-item>
      <el-form-item label="上午节数" prop="morning">
        <el-input v-model.number="form.morning" placeholder="请输入上午节数" maxlength="1" show-word-limit/>
      </el-form-item>
      <el-form-item label="下午节数" prop="afternoon">
        <el-input v-model.number="form.afternoon" placeholder="请输入下午节数" maxlength="1" show-word-limit/>
      </el-form-item>
      <el-form-item label="晚上节数" prop="night">
        <el-input v-model.number="form.night" placeholder="请输入晚上节数" maxlength="1" show-word-limit/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm" :loading="loading">确 定</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import {addTime, getTime, updateTime} from "@/api/teach/paike/base/time"


export default {
  name: "TimeIndex",
  data() {
    return {
      // 弹出层标题
      title: "作息安排",
      url: process.env.VUE_APP_BASE_API + "/teach/paike/time",
      loading: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        weekdays: [{required: true, message: "每周天数不能为空"},
          {type: 'number', message: '天数必须为数字值'}],
        morning: [{required: true, message: "上午节数不能为空"},
          {type: 'number', message: '节数必须为数字值'}],
        afternoon: [{required: true, message: "下午节数不能为空"},
          {type: 'number', message: '节数必须为数字值'}],
        night: [{required: true, message: "晚上节数不能为空"},
          {type: 'number', message: '节数必须为数字值'}]
      },
    }
  },
  created() {
    getTime().then(res => {
      this.form = res.data
    })
  },
  methods: {
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.loading = true
          if (this.form.id != null) {
            updateTime(this.form).then(response => {
              this.msgSuccess("保存成功")
            }).finally(() => {
              this.loading = false
            })
          } else {
            addTime(this.form).then(response => {
              this.msgSuccess("保存成功")
            }).finally(() => {
              this.loading = false
            })
          }
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
