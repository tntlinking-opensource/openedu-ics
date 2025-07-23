<template>
  <div v-if="open">
    <el-dialog :title="title" :visible="open" width="800px" :close-on-click-modal="false" append-to-body @close="close">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <!--        <el-form-item label="课程" prop="course">
                  <el-input v-model="form.course" placeholder="请输入课程" maxlength="10" show-word-limit/>
                </el-form-item>-->
        <el-form-item label="教学楼" prop="building">
          <el-input v-model="form.building" placeholder="请输入教学楼" maxlength="10" show-word-limit/>
        </el-form-item>
        <el-form-item label="教室号" prop="room">
          <el-input v-model="form.room" placeholder="请输入教室号" maxlength="10" show-word-limit/>
        </el-form-item>
        <el-form-item label="最大开班数" prop="lessonLimit">
          <el-input v-model.number="form.lessonLimit" placeholder="请输入最大开班数" maxlength="2" show-word-limit/>
        </el-form-item>
        <el-form-item label="课程" prop="courseList">
          <el-select v-model="form.courseList" placeholder="请选择课程" multiple clearable filterable style="width:100%;">
            <el-option v-for="v in courses" :key="v" :label="v" :value="v"/>
          </el-select>
        </el-form-item>
        <el-form-item label="年级">
          <el-checkbox :indeterminate="gradeIndeterminate" v-model="gradeCheckAll" @change="onGradeCheckAllChange">全选
          </el-checkbox>
          <el-checkbox-group v-model="form.gradeList" @change="onGradeChange">
            <el-checkbox v-for="v in gradeOptions" :key="v" :label="v"> {{ v }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="班级">
          <el-checkbox :indeterminate="adminclassIndeterminate" v-model="adminclassCheckAll"
                       @change="onAdminclassCheckAllChange">全选
          </el-checkbox>
          <el-checkbox-group v-model="form.adminclassList" @change="onAdminclassChange">
            <el-checkbox v-for="v in adminclassOptions" :key="v" :label="v"> {{ v }}</el-checkbox>
          </el-checkbox-group>
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
import {getClassroomSpecial, getEditDatas, saveOrUpdate} from "@/api/teach/paike/base/classroomSpecial"

export default {
  name: "ClassroomSpecialEdit",
  props: ['open', 'id'],
  data() {
    return {
      // 弹出层标题
      title: "特殊教室",
      url: process.env.VUE_APP_BASE_API + "/teach/paike/classroomSpecial",
      loading: false,
      // 表单参数
      form: {
        gradeList: [],
        adminclassList: [],
        courseList: [],
      },
      // 表单校验
      rules: {
        courses: [{required: true, message: "课程不能为空", trigger: "change"}],
        building: [{required: true, message: "教学楼不能为空", trigger: "blur"}],
        lessonLimit: [{required: true, message: "最大开班数不能为空", trigger: "blur"}, {
          type: 'number', message: "最大开班数必须为数字"
        }],
      },
      courses: [],
      gradeIndeterminate: false,
      gradeCheckAll: false,
      adminclassIndeterminate: false,
      adminclassCheckAll: false,
      gradeOptions: [],
      adminclassOptions: [],
    }
  },
  created() {
    if (this.id) {
      this.title = "修改特殊教室"
      this.getForm()
    } else {
      this.title = "添加特殊教室"
    }
    // this.getDicts("sys_yes_no").then(response => {
    //   this.gradeOptions = response.data
    // })
    // this.getDicts("sys_yes_no").then(response => {
    //   this.adminclassOptions = response.data
    // })
    getEditDatas().then(res => {
      Object.assign(this, res)
    })
  },
  methods: {
    getForm() {
      getClassroomSpecial(this.id).then(res => {
        res.data.courseList = (res.data.course || '').split('、')
        res.data.gradeList = (res.data.grade || '').split('、')
        res.data.adminclassList = (res.data.adminclass || '').split('、')
        this.form = res.data
        this.$nextTick(() => {
          this.$refs["form"].clearValidate()
        })
      })
    },
    onGradeCheckAllChange(val) {
      this.form.gradeList = val ? this.gradeOptions : [];
      this.gradeIndeterminate = false;
    },
    onGradeChange(val) {
      let checkedCount = val.length;
      this.gradeCheckAll = checkedCount === this.gradeOptions.length;
      this.gradeIndeterminate = checkedCount > 0 && checkedCount < this.gradeOptions.length;
    },
    onAdminclassCheckAllChange(val) {
      this.form.adminclassList = val ? this.adminclassOptions : [];
      this.adminclassIndeterminate = false;
    },
    onAdminclassChange(val) {
      let checkedCount = val.length;
      this.adminclassCheckAll = checkedCount === this.adminclassOptions.length;
      this.adminclassIndeterminate = checkedCount > 0 && checkedCount < this.adminclassOptions.length;
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.loading = true
          this.form.course = this.form.courseList.join("、")
          this.form.grade = this.form.gradeList.join("、")
          this.form.adminclass = this.form.adminclassList.join("、")
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
