<script>
import { RouterLink, RouterView } from 'vue-router';
import HomeView from "./views/HomeView.vue";
export default{
  data() {
    return {
      
    }
  },
  components:{
    HomeView,
  }
}
</script>

<template>
  <div class="relative">
    <RouterView />
  </div>

</template>

<style scoped>

</style>
