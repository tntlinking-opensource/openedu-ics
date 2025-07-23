<template>
  <div></div>
</template>

<script>
import {checkRole} from "@/utils/permission";

export default {
  name: "paikeIndex",
  created() {
    // console.log('paikeIndex', checkRole(['paike_admin']))
    if (checkRole(['paike_admin'])) {
      this.$router.push({path: '/paike/lessonSchedule'})
    } else {
      this.$router.push({path: '/paike/view'})
    }
  }
}
</script>
