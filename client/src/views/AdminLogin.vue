<script>
import { RouterLink, RouterView } from 'vue-router';
import Banner from '../components/Banner.vue';

const API_URL = "http://localhost:8080";


export default{
    data() {
        return {
            adminId: null,
            pwd: null,
        }
    },
    methods:{
        login(adminId, pwd){
            console.log(adminId, pwd);
            if(adminId === null || adminId == "" || pwd === null || pwd == ""){
                alert("請正確輸入資料！");
            }else{
                const loginBody = {
                    adminId: adminId,
                    pwd: pwd,
                }

                fetch(`${API_URL}/login/admin`, {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(loginBody),
                    })
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    if(data.code == "200"){
                        sessionStorage.setItem("id", adminId);
                        sessionStorage.setItem("loginToken", data.loginToken);
                        alert("登入成功！將為您導向至個人頁面。");
                        if(data.permission == 0){
                            sessionStorage.setItem("permission", 0);
                            this.$router.push({
                                name: "AccountSuperAdmin",
                            })
                        }
                        if(data.permission == 1){
                        sessionStorage.setItem("permission", 1);
                            this.$router.push({
                                name: "AccountNormalAdmin",
                            })
                        }

                    }
                    if(data.code == "400" || data.code == "404"){
                        alert("帳號或密碼錯誤！");
                    }
                })
                .catch(e => {
                    console.error(e);
                })
            }

            
        },
        removeLoginInfo(){
            //要是使用者在登入狀態下又來到登入畫面，
            //就直接讓他登出
            sessionStorage.removeItem("id");
            sessionStorage.removeItem("loginToken");
            sessionStorage.removeItem("permission");
        },
        goToForgotPwd(){
            this.$router.push({
                name: "ForgotPwd",
                params: {
                    identity: "admin",
                }
            })
        }
    },
    mounted(){
        this.removeLoginInfo();
    },
    components:{
        Banner,
    }
}
</script>

<template>
    <Banner />
    <div class="w-full py-36 text-sky-900">
        <div class="mx-auto w-1/3">
            <label for="adminId">管理員編號：</label>
            <br>
            <input v-model="adminId"
            class="border-2 rounded-md w-full p-1"
            type="text" id="adminId">
            <br><br>
            <label for="adminPsw">密碼：</label>
            <br>
            <input v-model="pwd"
            class="border-2 rounded-md w-full p-1"
            type="password">
            <br><br>
            <button @click="goToForgotPwd"
            class="block text-right mb-5 underline w-full"
            >忘記密碼</button>
            <button @click="login(adminId, pwd)"
            class="text-center bg-sky-800 py-1 px-5 rounded-md text-white mx-auto block duration-100 hover:bg-sky-600 active:bg-sky-900 w-1/2"
            >登入</button>
            <!-- 暫時先直接連到/account -->
        </div>
    </div>
</template>

<style lang='scss' scoped>

</style>