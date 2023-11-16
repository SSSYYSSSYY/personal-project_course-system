<script>
import { RouterLink, RouterView } from 'vue-router';
import Banner from '../components/Banner.vue';
const API_URL = "http://localhost:8080";
export default{
    data() {
        return {
            studentId: "",
            pwd: "",
        }
    },
    components:{
        Banner,
    },
    methods: {
        login(studentId, pwd){

            if(studentId == "" || pwd == ""){
                alert("請輸入正確資料！");
                return;
            }
            const loginBody = {
                studentId: studentId,
                pwd: pwd,
            }

            fetch(`${API_URL}/login/student`, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(loginBody),
            })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if(data.code == "400" || data.code == "404"){
                    alert("帳號或密碼錯誤！");
                    return;
                }
                sessionStorage.setItem("id", studentId);
                sessionStorage.setItem("loginToken", data.loginToken);
                alert("登入成功！將為您導向至個人頁面。");
                this.$router.push({
                    name: 'AccountStudent',
                })
            })

        },
        removeLoginInfo(){
            //要是使用者在登入狀態下又來到登入畫面，
            //就直接讓他登出
            sessionStorage.removeItem("id");
            sessionStorage.removeItem("loginToken");
        },
        goToForgotPwd(){
            this.$router.push({
                name: "ForgotPwd",
                params: {
                    identity: "student",
                }
            })
        }
    },
    mounted(){
        this.removeLoginInfo();
    }
}
</script>

<template>
    <Banner />
    <div class="w-full py-36 text-sky-900">
        <div class="mx-auto w-1/3">
            <label for="adminId">學號：</label>
            <br>
            <input v-model="studentId"
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
            to="/forgot-pwd">忘記密碼</button>
            <button @click="login(studentId, pwd)" 
            class="text-center bg-sky-800 py-1 px-5 rounded-md text-white mx-auto block duration-100 hover:bg-sky-600 active:bg-sky-900 w-1/2"
            >登入</button>
            <!-- 暫時先直接連到/account -->
        </div>
    </div>
</template>

<style lang='scss' scoped>

</style>