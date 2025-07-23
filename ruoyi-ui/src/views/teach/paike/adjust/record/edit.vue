<template>
  <div v-if="open">
    <el-dialog :title="title" :visible="open" width="800px" :close-on-click-modal="false" append-to-body @close="close">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" maxlength="100" show-word-limit/>
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
import {getRecord, saveOrUpdate} from "@/api/paike/record"

export default {
  name: "RecordEdit",
  props: ['open', 'id'],
  data() {
    return {
      // 弹出层标题
      title: "排课存档",
      url: process.env.VUE_APP_BASE_API + "/paike/record",
      loading: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        lessonScheduleId: [{required: true, message: "排课任务ID不能为空", trigger: "blur"}],
        name: [{required: true, message: "名称不能为空", trigger: "blur"}],
        json: [{required: true, message: "数据不能为空", trigger: "blur"}],
        createTime: [{required: true, message: "创建时间不能为空", trigger: "blur"}]
      },
    }
  },
  created() {
    if (this.id) {
      this.title = "修改排课存档"
      this.getForm()
    } else {
      this.title = "添加排课存档"
    }
    // getEditDatas().then(res => {
    //   Object.assign(this, res)
    // })
  },
  methods: {
    getForm() {
      getRecord(this.id).then(res => {
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
