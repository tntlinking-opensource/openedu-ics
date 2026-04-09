<template>
  <div class="app-container">
    <view-adminclass></view-adminclass>
  </div>
</template>

<script>

import ViewAdminclass from "./adminclass";
export default {
  name: "index",
  components: {ViewAdminclass},
  data() {
    return {
    }
  },
  mounted() {

  },
  methods: {
    handleStep(index) {
      this.active = index
    }
  }
}
</script>

<style scoped>

</style>
