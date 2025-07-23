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
      this.getService().importTemplate().then(response => {
        this.download(response.msg)
      })
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
      this.$alert(response.msg, "导入结果", {dangerouslyUseHTMLString: true})
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
