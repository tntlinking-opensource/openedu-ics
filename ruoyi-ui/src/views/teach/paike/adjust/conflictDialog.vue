<template>
  <el-dialog title="课表冲突" :visible="open" width="50%" @close="close">
    <el-table :data="conflictPlans" border>
      <el-table-column label="序号" prop="index" align="center" width="50"/>
      <el-table-column label="内容" prop="content"/>
    </el-table>
    <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="close">确 定</el-button>
      </span>
  </el-dialog>
</template>

<script>
export default {
  name: "ConflictDialog",
  props: ['open', 'conflictPlans'],
  methods: {
    close() {
      this.$emit('update:open', false);
    }
  }
}
</script>

<style scoped>

</style>
