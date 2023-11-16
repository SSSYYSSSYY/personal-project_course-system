<script>
import { RouterLink, RouterView } from 'vue-router';
import Banner from '../components/Banner.vue';
import AsideInAccount from '../components/AsideInAccount.vue';
import MainBlockInAccount from '../components/MainBlockInAccount.vue';
import Schedule from '../components/Schedule.vue';
import ProgressInStudent from '../components/ProgressInStudent.vue';

const API_URL = "http://localhost:8080";

export default{
    data() {
        return {
            isHome: true,
            isSchedule: false,
            isProgress: false,

            studentData: null,
            studentId: null,

            isShowError: false,
        }
    },
    components:{
        Banner,
        AsideInAccount,
        MainBlockInAccount,
        Schedule,
        ProgressInStudent,
    },
    methods:{
        goToSchedule(){
            this.isHome = false;
            this.isSchedule = true;
            this.isProgress = false;
        },
        goToHome(){
            this.isHome = true;
            this.isSchedule = false;
            this.isProgress = false;
        },
        goToProgress(){
            this.isHome = false;
            this.isSchedule = false;
            this.isProgress = true;
            console.log("到進度頁")
        },
        reloadAndTurnToProgress(){
            location.reload();
            this.goToProgress();
        }

    },
    created(){
        this.studentId = sessionStorage.getItem("id");

        if(this.studentId != null){
            fetch(`${API_URL}/student_detail`, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    id: sessionStorage.getItem("id"),
                    loginToken: sessionStorage.getItem("loginToken")
                }),
            })
            .then(response => response.json())
            .then(data => {
                if(data.code == "400"){
                    this.isShowError = true;
                    return;
                }
                this.studentData = data.studentData;
                // console.log(this.studentData);
                sessionStorage.setItem("schedule", JSON.stringify(this.studentData.schedule));
                sessionStorage.setItem("progressList", JSON.stringify(this.studentData.progressList));
            })
        }

    }
}
</script>

<template>
    <div class="">
        <Banner />
        <nav class="account-page-nav">
            <RouterLink class="link-in-nav" to="/courses">課程總覽</RouterLink>
            <button 
            @click="goToSchedule"
            class="link-in-nav" type="button">我的課表</button>
            <button 
            @click="goToProgress"
            type="button" class="link-in-nav">學習進度管理</button>
        </nav>
        <div class="flex mx-auto my-5 max-w-4xl">
            <AsideInAccount
            :name="studentData.studentInfo.name"
            :id="studentData.studentInfo.id"
            :createDate="studentData.studentInfo.createDate"
            :isEnable="studentData.studentInfo.enable"
            @backHome="goToHome" />
            <!-- 左側欄 -->
            <main class="bg-green-100 rounded-md w-full">
                <MainBlockInAccount 
                :name="studentData.studentInfo.name"
                :id="studentData.studentInfo.id"
                :email="studentData.studentInfo.email"
                :birthday="studentData.studentInfo.birthday"
                :isEnable="studentData.studentInfo.enable"
                :identity="studentData.studentInfo.identity"
                v-if="isHome" />
                <Schedule v-if="isSchedule" />
                <ProgressInStudent @turnToProgress="reloadAndTurnToProgress"
                v-if="isProgress" />
                <!-- 右方主要區塊 -->
            </main>
        </div>
    </div>
</template>

<style lang='scss' scoped>

</style>