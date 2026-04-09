<template>
  <el-dialog title="课表冲突" :visible="open" width="50%" @close="close">
    <el-table :data="plans" border>
      <el-table-column label="序号" prop="index" align="center" width="50"/>
      <el-table-column label="内容" prop="content">
        <template slot-scope="scope">
          <div class="plan" @click="onPlanClick(scope.row)">{{ scope.row.content }}</div>
        </template>
      </el-table-column>
    </el-table>
    <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="close">确 定</el-button>
      </span>
  </el-dialog>
</template>

<script>
export default {
  name: "UnScheduleDialog",
  props: ['open', 'plans'],
  methods: {
    onPlanClick(plan){
      this.$emit('change', plan);
      this.close()
    },
    close() {
      this.$emit('update:open', false);
    }
  }
}
</script>

<style scoped lang="scss">
.plan{
  cursor: pointer; color: rgb(24, 144, 255);
}
</style>
