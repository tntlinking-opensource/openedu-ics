import {getToken} from "@/utils/auth"

const importData = {
  data() {
    return {
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: null,
        updateSupport: 'false',
        templateName: '导入模板.xlsx',
        errorName: '错误信息.xlsx',
      },
    }
  },
  methods: {
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = this.title + "导入"
      this.upload.open = true
      this.upload.url = this.url + '/importData'
    },
    /** 下载模板操作 */
    importTemplate() {
      this.getService().importTemplate().then(res => {
        // console.log('res', res)
        this.download(res.msg, this.title + this.upload.templateName)
      })
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true
    },
    // 文件上传成功处理
    handleFileSuccess(res, file, fileList) {
      this.upload.open = false
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
      if (res.code == 500) {
        this.$alert('未知错误，请联系系统管理员', "导入失败")
        return
      }
      if (res.msg) {
        this.$alert('存在错误数据，请下载查看！', "导入失败").then(() => {
          this.download(res.msg, this.title + this.upload.errorName)
        })
      } else {
        this.msgSuccess("导入成功")
      }
      this.$emit('submit')
      this.close()
      if (this.getList) {
        this.getList()
      }
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit()
    },
    close() {
      this.$emit('update:open', false)
    },
  }
}

export default importData
