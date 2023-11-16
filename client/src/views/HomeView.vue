<script>
import Banner from '../components/Banner.vue';
import { RouterLink, RouterView } from 'vue-router';

const API_URL = "http://localhost:8080";

export default {
  data() {
    return {
      isLogin: false,
      identity: null,
    }
  },
  components:{
    Banner,
  },
  mounted(){
    this.checkLoginToken();
    this.getIdentity();
  },
  methods: {
    checkLoginToken(){
      const id = sessionStorage.getItem("id");
      const loginToken = sessionStorage.getItem("loginToken");
      const status = sessionStorage.getItem("status");

      if(id == null || loginToken == null || status == "forgotPwd"){
        this.isLogin = false;
        return;
      }
      fetch(`${API_URL}/check_login_token`, {
        method: "POST",
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          id: id,
          loginToken: loginToken,
        })
      })
      .then(response => response.json())
      .then(data => {
        this.isLogin = data.login;
      })
    },
    getIdentity(){
      if(sessionStorage.getItem("permission") == 0){
        this.identity = "superAdmin";
        return;
      }
      if(sessionStorage.getItem("permission") == 1){
        this.identity = "normalAdmin";
        return;
      }
      this.identity = "student";
    }
  }
}
</script>

<template>
  <div class="">
    <Banner />
    <div v-if="!isLogin"
    class="w-full flex justify-center items-center text-center py-48">
      <RouterLink 
      class="bg-sky-700 text-white p-10 mx-10 rounded-md w-52 text-center text-2xl shadow-md duration-100 hover:bg-sky-600 active:bg-sky-800"
      to="/admin-login">管理員登入</RouterLink>
      <RouterLink 
      class="bg-sky-700 text-white p-10 mx-10 rounded-md w-52 text-center text-2xl shadow-md duration-100 hover:bg-sky-600 active:bg-sky-800"
      to="/student-login">學生登入</RouterLink>
    </div>
    <div v-if="isLogin"
    class="w-full flex justify-center items-center text-center py-48">
      <RouterLink 
      class="bg-sky-700 text-white p-10 mx-10 rounded-md w-52 text-center text-2xl shadow-md duration-100 hover:bg-sky-600 active:bg-sky-800"
      to="/courses">前往課程頁面</RouterLink>

      <RouterLink v-if="identity == 'superAdmin'"
      class="bg-sky-700 text-white p-10 mx-10 rounded-md w-52 text-center text-2xl shadow-md duration-100 hover:bg-sky-600 active:bg-sky-800"
      to="/account-super-admin">前往個人頁面</RouterLink>
      <RouterLink v-if="identity == 'normalAdmin'"
      class="bg-sky-700 text-white p-10 mx-10 rounded-md w-52 text-center text-2xl shadow-md duration-100 hover:bg-sky-600 active:bg-sky-800"
      to="/account-normal-admin">前往個人頁面</RouterLink>
      <RouterLink v-if="identity =='student'" 
      class="bg-sky-700 text-white p-10 mx-10 rounded-md w-52 text-center text-2xl shadow-md duration-100 hover:bg-sky-600 active:bg-sky-800"
      to="/account-student">前往個人頁面</RouterLink>
    </div>
  </div>

</template>
