<template>
  <el-dialog :title="upload.title" :visible="true" width="400px"
             :close-on-click-modal="false" append-to-body @close="close">
    <el-upload ref="upload" :limit="1" accept=".xlsx, .xls"
               :headers="upload.headers" :action="url + '/importData'"
               :disabled="upload.isUploading" :on-progress="handleFileUploadProgress"
               :on-success="handleFileSuccess" :auto-upload="false" drag>
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">
        将文件拖到此处，或
        <em>点击上传</em>
      </div>
      <div class="el-upload__tip" slot="tip">
        <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
      </div>
      <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xlsx”格式文件！</div>
    </el-upload>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitFileForm">确 定</el-button>
      <el-button @click="close">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import service from "@/api/teach/paike/base/classroomSpecial"
import importData from "@/utils/views/importData"

export default {
  name: "ClassroomSpecialUpload",
  mixins: [importData],
  props: ['open'],
  data() {
    return {
      url: process.env.VUE_APP_BASE_API + "/teach/paike/classroomSpecial",
    }
  },
  created() {
  },
  methods: {
    getService() {
      return service
    },
  }
}
</script>
