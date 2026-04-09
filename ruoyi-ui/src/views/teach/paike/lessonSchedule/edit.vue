<template>
  <div v-if="open">
    <el-dialog :title="title" :visible="open" width="800px" :close-on-click-modal="false" append-to-body @close="close">
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="学年学期" prop="teachCalenderId">
          <el-select v-model="form.teachCalenderId" placeholder="请选择学年学期">
            <el-option v-for="v in teachCalenderOptions" :key="v.id" :label="v.name" :value="v.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" maxlength="100" show-word-limit/>
        </el-form-item>
        <el-form-item label="是否当前排课">
          <el-radio-group v-model="form.isCurrent">
            <el-radio v-for="v in isCurrentOptions" :key="v.value" :label="v.value">{{ v.name }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="教师每天课时限制" prop="hourPerDay">
          <el-input-number v-model="form.hourPerDay" controls-position="right" placeholder="请输入每天课时限制" maxlength="2" style="width:80px;"/>
          <span style="padding-left: 10px;">课时</span>
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
import {
  addLessonSchedule,
  getEditDatas,
  getLessonSchedule,
  updateLessonSchedule
} from "../../../../api/teach/paike/lessonSchedule";
import {listTeachCalendar} from "../../../../api/teach/school/teachCalendar";

export default {
  name: "LessonScheduleEdit",
  props: ['open', 'id', 'copyMode'],
  data() {
    return {
      // 弹出层标题
      title: "排课任务",
      url: process.env.VUE_APP_BASE_API + "/paike/lessonSchedule",
      loading: false,
      // 表单参数
      form: {
        teachCalenderId: null,
        isCurrent: 'Y',
        copyId: null,
        hourPerDay: 4,
      },
      // 表单校验
      rules: {
        schoolId: [{required: true, message: "学校ID不能为空", trigger: "blur"}],
        teachCalenderId: [{required: true, message: "学年学期不能为空", trigger: "change"}],
        name: [{required: true, message: "名称不能为空", trigger: "blur"}],
        isCurrent: [{required: true, message: "是否当前排课不能为空", trigger: "blur"}],
        createTime: [{required: true, message: "创建时间不能为空", trigger: "blur"}],
        updateTime: [{required: true, message: "最后更新时间不能为空", trigger: "blur"}]
      },
      teachCalenderOptions: [],
      isConflictOptions: [],
      isCurrentOptions: [],
    };
  },
  created() {
    if (this.id) {
      this.title = "修改排课任务"
      this.getForm()
    } else {
      this.title = "添加排课任务"
    }
    if (this.copyMode) {
      this.title = "复制排课任务"
    }
    listTeachCalendar().then(response => {
      if (!this.id) {
        this.form.teachCalenderId = response.rows.filter(v => v.current == 'Y')[0].id
      }
      this.teachCalenderOptions = response.rows
    });
    this.getDicts("sys_yes_no").then(response => {
      this.isCurrentOptions = response.data
    });
    getEditDatas().then(res => {
      Object.assign(this, res)
    })
  },
  methods: {
    getForm() {
      getLessonSchedule(this.id).then(res => {
        if (this.copyMode) {
          res.data.id = null
          res.data.copyId = this.id
        }
        res.data.hourPerDay = res.data.hourPerDay || 3
        this.form = res.data
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.loading = true
          if (this.form.id != null) {
            updateLessonSchedule(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit('submit');
              this.close()
            }).catch(() => {
              this.loading = false
            });
          } else {
            addLessonSchedule(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.$emit('submit');
              this.close()
            }).catch(() => {
              this.loading = false
            });
          }
        }
      });
    },
    // 取消按钮
    close() {
      this.$emit('update:open', false);
    },
  }
};
</script>
