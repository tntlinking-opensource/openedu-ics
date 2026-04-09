<template>
  <div class="">
    <el-table v-loading="loading" :data="tableList" border @selection-change="handleSelectionChange">
      <el-table-column label="节次" prop="name" align="center"/>
      <template v-for="(w, wIndex) in weekdays">
        <el-table-column :label="w" align="center">
          <template slot-scope="scope">
            <div class="unit" @click="onUnitClick(scope.row, wIndex)">
              {{ getTimeByRowAndWeekday(scope.row, wIndex) }}
            </div>
          </template>
        </el-table-column>
      </template>
    </el-table>
    <div style="text-align: center; margin: 20px;">
      <el-button type="primary" @click="submitForm" :loading="submiting">保存</el-button>
    </div>
  </div>
</template>

<script>
import {getIndexDatas, listSchoolTimes, saveOrUpdate} from "@/api/teach/paike/intellect/schoolTimes"
import indexMixin from "@/utils/views/indexMixin"
import TimesMixin from "../../TimesMixin";


export default {
  name: "SchoolTimes",
  mixins: [indexMixin, TimesMixin],
  data() {
    return {
      schoolTimes: {},
      schoolTimesList: [],
    }
  },
  created() {
    getIndexDatas().then(res => {
      Object.assign(this, res)
      this.initList()
      this.getList()
    })
  },
  methods: {
    getList() {
      this.loading = true
      listSchoolTimes(this.queryParams).then(response => {
        response.rows.forEach(r => Object.assign(r, JSON.parse(r.json)))
        if (response.rows.length == 0) {
          let row = {code: '全校', times: this.getTimes()}
          response.rows.push(row)
        }
        this.ruleList = response.rows
        this.selectRules = this.ruleList
        this.loading = false
      })
    },
    submitForm() {
      this.submiting = true
      this.ruleList.forEach(t => {
        let json = (({times}) => ({times}))(t)
        t.content = this.getContent(t.times)
        t.json = JSON.stringify(json)
      })
      saveOrUpdate(this.ruleList).then(() => {
        this.msgSuccess("保存成功")
      }).finally(() => {
        this.submiting = false
      })
    }
  }
}
</script>
<style scoped lang="scss">
.unit {
  cursor: pointer;
  height: 40px;
  line-height: 40px;
  &:hover {
    background-color: #eee;
  }
}
</style>
