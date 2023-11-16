<script>
import { RouterLink, RouterView } from 'vue-router';
import Banner from '../components/Banner.vue';
import MainBlockInAccount from '../components/MainBlockInAccount.vue';
import AsideInAccount from '../components/AsideInAccount.vue';
import AddAdmin from '../components/AddAdmin.vue';
import AddStudent from '../components/AddStudent.vue';
import SearchUser from '../components/SearchUser.vue';
import StudentsProgressManagement from '../components/StudentsProgressManagement.vue';

const API_URL = "http://localhost:8080";


export default{
    data() {
        return {
            isHome: true,
            isAddAdmin: false,
            isAddStudent: false,
            isSearchUser:false,
            isUserManagementHoverShow: false,
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
        AddAdmin,
        AddStudent,
        SearchUser,
    },
    methods:{
        goToHome(){
            this.isHome = true;
            this.isAddAdmin = false;
            this.isAddStudent = false;
            this.isSearchUser = false;
            this.isStudentsProgressManagement = false;
        },
        goToStudentsProgressManagement(){
            this.isHome = false;
            this.isAddAdmin = false;
            this.isAddStudent = false;
            this.isSearchUser = false;
            this.isStudentsProgressManagement = true;
        },
        goToAddAdmin(){
            this.isHome = false;
            this.isAddAdmin = true;
            this.isAddStudent = false;
            this.isSearchUser = false;
            this.isStudentsProgressManagement = false;

            this.isUserManagementHoverShow = false;
        },
        goToAddStudent(){
            this.isHome = false;
            this.isAddAdmin = false;
            this.isAddStudent = true;
            this.isSearchUser = false;
            this.isStudentsProgressManagement = false;

            this.isUserManagementHoverShow = false;
        },
        goToSearchUser(){
            this.isHome = false;
            this.isAddAdmin = false;
            this.isAddStudent = false;
            this.isSearchUser = true;
            this.isStudentsProgressManagement = false;

            this.isUserManagementHoverShow = false;
        },
        showUserManagementHover(){
            this.isUserManagementHoverShow = true;
        },
        hiddenUserManagementHover(){
            this.isUserManagementHoverShow = false;
        },
    }
}
</script>

<template>
    <div
    class="">
        <Banner />
        <nav v-if="!isShowError"
        class="account-page-nav">
            <div 
            @mouseenter="showUserManagementHover"
            class="link-in-nav users-management inline-block cursor-pointer relative">人員管理
                <div class="users-management-hover-list absolute w-24 text-base -translate-x-4 translate-y-1 text-sky-700 leading-8 px-2"
                v-show="isUserManagementHoverShow"
                @mouseleave="hiddenUserManagementHover">
                <!-- 還有改善的餘地 -->
                    <button @click="goToAddAdmin"
                    class="bg-white w-24 duration-100 hover:bg-slate-100"
                    type="button">新增管理員</button>
                    <button @click="goToAddStudent"
                    class="bg-white w-24 duration-100 hover:bg-slate-100"
                    type="button">新增學生</button>
                    <button @click="goToSearchUser"
                    class="bg-white w-24 duration-100 hover:bg-slate-100"
                    type="button">使用者查詢</button>
                </div>
            </div>
            <RouterLink class="link-in-nav" to="/courses">課程管理</RouterLink>
            <button 
            @click="goToStudentsProgressManagement"
            type="button" class="link-in-nav">學生進度管理</button>
        </nav>
        <div v-if="!isShowError"
        class="flex mx-auto my-5 max-w-4xl">
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
                <AddAdmin v-if="isAddAdmin" />
                <AddStudent v-if="isAddStudent" />
                <SearchUser v-if="isSearchUser" />
                <StudentsProgressManagement v-if="isStudentsProgressManagement" />
                <!-- 右方主要區塊 -->
            </main>
        </div>
        <div class="p-20 text-center font-bold text-2xl"
        v-if="isShowError">
            <p>您沒有權限閱覽此頁面！</p>
        </div>
    </div>
</template>

<style lang='scss' scoped>




</style>