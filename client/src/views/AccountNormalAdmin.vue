<script>
import { RouterLink, RouterView } from 'vue-router';
import Banner from '../components/Banner.vue';
import MainBlockInAccount from '../components/MainBlockInAccount.vue';
import AsideInAccount from '../components/AsideInAccount.vue';
import StudentsProgressManagement from '../components/StudentsProgressManagement.vue';
import SearchStudent from '../components/SearchStudent.vue';

const API_URL = "http://localhost:8080";

export default{
    data() {
        return {
            isHome: true,
            isSearchStudent: false,
            isStudentsProgressManagement: false,

            adminId: null,
            adminInfo: null,

            isShowError: false,
        }
    },
    created(){
        this.adminId = sessionStorage.getItem("id");
        
        if(this.adminId != null){
            fetch(`${API_URL}/admin_info_after_login/`,{
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
                this.adminInfo = data.adminInfo;
                console.log(this.adminInfo)
            })
        }else{
            this.isShowError = true;
        }
    },
    components:{
        Banner,
        MainBlockInAccount,
        AsideInAccount,
        StudentsProgressManagement,
        SearchStudent,
    },
    methods:{
        goToSearchStudent(){
            this.isHome = false;
            this.isSearchStudent = true;
            this.isStudentsProgressManagement = false;
        },
        goToProgressManagement(){
            this.isHome = false;
            this.isSearchStudent = false;
            this.isStudentsProgressManagement = true;
        },
        goToHome(){
            this.isHome = true;
            this.isSearchStudent = false;
            this.isStudentsProgressManagement = false;
        }
    }
}
</script>

<template>
    <div>
        <Banner />
        <nav class="account-page-nav">
            <button 
            @click="goToSearchStudent"
            class="link-in-nav" type="button">學生查詢</button>
            <RouterLink class="link-in-nav" to="/courses">課程管理</RouterLink>
            <button 
            @click="goToProgressManagement"
            type="button" class="link-in-nav">學習進度管理</button>
        </nav>
        <div class="flex mx-auto my-5 max-w-4xl">
            <AsideInAccount
            :name="adminInfo.name"
            :id="adminInfo.id"
            :createDate="adminInfo.createDate"
            :isEnable="adminInfo.enable"
            @backHome="goToHome" />
            <!-- 左側欄 -->
            <main class="bg-green-100 rounded-md w-full">
                <MainBlockInAccount 
                :name="adminInfo.name"
                :id="adminInfo.id"
                :email="adminInfo.email"
                :birthday="adminInfo.birthday"
                :isEnable="adminInfo.enable"
                :identity="adminInfo.identity"
                v-if="isHome" />
                <SearchStudent v-if="isSearchStudent" />
                <StudentsProgressManagement v-if="isStudentsProgressManagement" />
                <!-- 右方主要區塊 -->
            </main>
        </div>
    </div>
</template>

<style lang='scss' scoped>

</style>