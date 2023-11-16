<script>
import ModalOfUserDetail from './ModalOfUserDetail.vue';
const API_URL = "http://localhost:8080";
export default{
    data() {
        return {
            // 資料庫中user要分成admin和student兩邊分開存放?
            // 管理員和學生都要有一欄用來存放身分
            // 假資料
            // studentsDataArr: [
            //     {
            //         studentId: "s1000000",
            //         studentName: "王小明",
            //         studentBirthday: "2000/08/01",
            //         studentSignDate: "2023/08/02",
            //         isSubsidized: true,
            //         // 小於29歲有補助
            //         isAccountEnable: false,
            //         identity: "student",
            //         email: "123@mail.com",
            //     },
            //     {
            //         studentId: "s1000001",
            //         studentName: "林冠宇",
            //         studentBirthday: "2000/05/01",
            //         studentSignDate: "2023/08/02",
            //         isSubsidized: true,
            //         // 小於29歲有補助
            //         isAccountEnable: true,
            //         identity: "student",
            //         email: "123@mail.com",
            //     },
            //     {
            //         studentId: "s1000002",
            //         studentName: "黃俊豪",
            //         studentBirthday: "1987/11/02",
            //         studentSignDate: "2023/08/02",
            //         isSubsidized: false,
            //         // 小於29歲有補助
            //         isAccountEnable: false,
            //         identity: "student",
            //         email: "123@mail.com",
            //     },
            // ],
            // adminsDataArr: [
            //     {
            //         adminId: "a1000000",
            //         adminName: "張俊宇",
            //         adminPermission: "normalAdmin",
            //         adminBirthday: "2000/01/01",
            //         adminSignDate: "2023/07/31",
            //         isAccountEnable: true,
            //         identity: "admin",
            //         email: "123@mail.com",
            //     },
            //     {
            //         adminId: "a1000001",
            //         adminName: "葉大雄",
            //         adminPermission: "superAdmin",
            //         adminBirthday: "2000/01/01",
            //         adminSignDate: "2023/07/31",
            //         isAccountEnable: false,
            //         identity: "admin",
            //         email: "123@mail.com",
            //     },
            //     {
            //         adminId: "a1000002",
            //         adminName: "林冠偉",
            //         adminPermission: "normalAdmin",
            //         adminBirthday: "2000/03/01",
            //         adminSignDate: "2023/07/31",
            //         isAccountEnable: true,
            //         identity: "admin",
            //         email: "123@mail.com",
            //     },
            // ],
            studentsDataArr: [],
            adminsDataArr: [],

            selected: null,

            displayData: [],

            displayPerPage: [],
            userDataPerPage: 10,
            currentPage: 1,
            totalPage: 1,

            isModalOfUserDetailShow: false,
            indexOfCurrentData: null,
            currentSelectedUser: null,

            // 控制搜尋用
            searchBy: null,
            searchValue: null,
            displaySearchResult: [],
            isInSearch: false,

            //修改使用者資料用
            isEditUserInfo: false,
        }
    },
    components:{
        ModalOfUserDetail,
    },
    methods:{

        clearSelect(){
            this.selected = null;
            this.isInSearch = false;
            this.displayData = [ ...this.adminsDataArr, ...this.studentsDataArr];
            this.displayPerPage = this.displayData.slice(0, this.userDataPerPage);
        },
        modalOfUserDetailHandler(index, identity){
            this.isModalOfUserDetailShow = !this.isModalOfUserDetailShow;
            if(this.isInSearch){
                this.currentSelectedUser = this.displaySearchResult[index + (this.currentPage - 1) * this.userDataPerPage];
            }else{
                this.currentSelectedUser = this.displayData[index + (this.currentPage - 1) * this.userDataPerPage];
            }
            
            if(!this.isModalOfUserDetailShow){
                this.isEditUserInfo = false;
            }
            
        },
        search(){
            if(this.searchBy === null || this.searchValue === null){
                alert("請輸入要搜尋的資料！");
            }else{
                this.isInSearch = true;
                this.displaySearchResult= [];
                this.displayData.forEach(user => {
                    if(user[this.searchBy].includes(this.searchValue)){
                        this.displaySearchResult.push(user);
                    }
                });
                this.displayPerPage = this.displaySearchResult.slice(0, this.userDataPerPage);
                this.searchBy = null;
                this.searchValue = null;
            }
        },
        prePage(){
            this.currentPage--;
            this.displayPerPage = this.displayData.slice((this.currentPage - 1) * this.userDataPerPage, (this.currentPage - 1) * this.userDataPerPage + this.userDataPerPage);
        },
        nextPage(){
            this.currentPage++;
            this.displayPerPage = this.displayData.slice((this.currentPage - 1) * this.userDataPerPage, (this.currentPage - 1) * this.userDataPerPage + this.userDataPerPage);
        },
        timeFormatter(datetimeStr){
            const time = new Date(datetimeStr);
            let year = time.getFullYear();
            let month = time.getMonth() + 1;
            let date = time.getDate();

            if(month < 10){
                month = `0${month}`;
            }
            if(date < 10){
                date = `0${date}`;
            }

            return `${year}/${month}/${date}`;
        },
        getData(){
            fetch(`${API_URL}/get_all_admin`)
            .then(response => response.json())
            .then(data => {
                this.adminsDataArr = data;
                fetch(`${API_URL}/get_all_student`)
                .then(response => response.json())
                .then(data => {
                    this.studentsDataArr = data;
                    this.displayData = this.adminsDataArr.concat(this.studentsDataArr);
                    console.log(this.displayData)
                    this.displayPerPage = this.displayData.slice(0, this.userDataPerPage);
                });
                
            });

            

        },
        editHandler(){
            this.isEditUserInfo = !this.isEditUserInfo;
        }
    },
    created(){
        this.getData();
    },

    mounted(){
        
        this.displayData = [...this.adminsDataArr, ...this.studentsDataArr]

        console.log(this.displayData)
        // 預設顯示所有管理員和學生的資料
        this.displayPerPage = this.displayData.slice(0, this.userDataPerPage);


        if(this.displayData.length % this.userDataPerPage == 0){
            this.totalPage = this.displayData.length / this.userDataPerPage;
        }else{
            this.totalPage = Math.floor(this.displayData.length / this.userDataPerPage) + 1;
        }
    },
    watch:{
        selected(){
            if(this.selected === "admin"){
                this.displayData = this.adminsDataArr;
            }
            if(this.selected === "student"){
                this.displayData = this.studentsDataArr;
            }
            this.displayPerPage = this.displayData.slice(0, this.userDataPerPage);

            this.isInSearch = false;
            this.searchBy = null;
            this.searchValue = null;

        }
    }
}
</script>

<template>
<!-- fontawesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<!-- fontawesome -->
    <div class="p-4">
        <ModalOfUserDetail v-if="isModalOfUserDetailShow"
        @closeModal="modalOfUserDetailHandler">
            <div v-if="currentSelectedUser.identity === 'admin'">
                <p>身分：管理員</p>
                <p v-if="currentSelectedUser.permission === 0">權限：超級管理員</p>
                <p v-if="currentSelectedUser.permission === 1">權限：普通管理員</p>
                <p>人員編號：{{ currentSelectedUser.id }}</p>
                <p>姓名：{{ currentSelectedUser.name }}</p>
                <p>Email：{{ currentSelectedUser.email }}</p>
                <p>出生年月日：{{ timeFormatter(currentSelectedUser.birthday) }}</p>
                <p>創建時間：{{ timeFormatter(currentSelectedUser.createDate) }}</p>
                <p v-if="currentSelectedUser.enable">帳號啟用狀態：已啟用</p>
                <p v-if="!currentSelectedUser.enable">帳號啟用狀態：未啟用</p>
            </div>

            <div v-if="currentSelectedUser.identity === 'student'">
                <p>身分：學生</p>
                <p>學號：{{ currentSelectedUser.id }}</p>
                <p>姓名：{{ currentSelectedUser.name }}</p>
                <p>Email：{{ currentSelectedUser.email }}</p>
                <p>出生年月日：{{ timeFormatter(currentSelectedUser.birthday) }}</p>
                <p>創建時間：{{ timeFormatter(currentSelectedUser.createDate) }}</p>
                <p v-if="currentSelectedUser.enable">帳號啟用狀態：已啟用</p>
                <p v-if="!currentSelectedUser.enable">帳號啟用狀態：未啟用</p>
            </div>
        </ModalOfUserDetail>
        <h2 class="text-2xl mb-4 pb-2 border-b-2 border-green-600 font-bold text-green-900">人員管理：使用者查詢</h2>
        <div>
            <div class="mx-auto">
                <div class="text-center flex justify-around text-green-900 my-4 w-80 mx-auto">
                    <div class="">
                        <input type="radio" name="select-user-type" id="admin" value="admin" v-model="selected">
                        <label for="admin">管理員</label>
                    </div>
                    <div>
                        <input type="radio" name="select-user-type" id="student" value="student" v-model="selected">
                        <label for="student">學生</label>
                    </div>
                </div>
                <div v-if="selected === 'admin'"
                class="flex justify-center mb-6">
                    <select v-model="searchBy"
                    class="rounded-md"
                    name="searchAdminBy" id="searchAdminBy">
                        <option :value="null" selected disabled>--請選擇--</option>
                        <option value="id">編號</option>
                        <option value="name">姓名</option>
                    </select>
                    <input v-model="searchValue"
                    class="ml-4 rounded-md border-green-600 border-2 placeholder:text-sm "
                    type="text" name="searchAdminText" 
                    placeholder="在此輸入搜尋內容..."
                    id="searchAdminText">
                    <button @click="search"
                    class="bg-green-800 py-1 px-4 text-white rounded-md hover:bg-green-700 duration-100 font-bold ml-4"
                    type="button">搜尋</button>
                </div>
                <div v-if="selected === 'student'"
                class="flex justify-center mb-6">
                    <select v-model="searchBy"
                    class="rounded-md"
                    name="searchStudentBy" id="searchStudentBy">
                        <option :value="null" selected disabled>--請選擇--</option>
                        <option value="id">學號</option>
                        <option value="name">姓名</option>
                    </select>
                    <input v-model="searchValue"
                    class="ml-4 rounded-md border-green-600 border-2 placeholder:text-sm "
                    type="text" name="searchStudentText" 
                    placeholder="在此輸入搜尋內容..."
                    id="searchStudentText">
                    <button @click="search"
                    class="bg-green-800 py-1 px-4 text-white rounded-md hover:bg-green-700 duration-100 font-bold ml-4"
                    type="button">搜尋</button>
                </div>
                <!-- 顯示全部資料時 -->
                <div class="rounded-br-md rounded-bl-md">
                    <div class="w-full flex text-center bg-green-800 text-white box-border text-sm rounded-tl-md rounded-tr-md">
                        <p class="w-2/12 py-1">身分</p>
                        <p class="w-3/12 py-1 border-l-2 border-green-100">編號 / 學號</p>
                        <p class="w-3/12 py-1 border-l-2 border-green-100">姓名</p>
                        <p class="w-2/12 py-1 border-l-2 border-green-100">創建時間</p>
                        <p class="w-2/12 py-1 border-l-2 border-green-100">是否啟用</p>
                    </div>
                    <!-- 以下開始渲染資料內容 -->
                    <!-- 顯示全部資料時 -->
                    <div v-if="!isInSearch"
                    v-for="(user, index) in displayPerPage"
                    @click="modalOfUserDetailHandler(index, user.identity)"
                    class="w-full flex text-center odd:bg-green-50 even:bg-white text-sm cursor-pointer duration-100 hover:bg-gray-100 text-green-800">
                        <p class="w-1/6 py-1 border-b-2 border-green-200"
                        v-if="user.identity === 'admin'">管理員</p>
                        <p class="w-2/12 py-1 border-b-2 border-green-200"
                        v-if="user.identity === 'student'">學生</p>
                        <p class="w-3/12 py-1 border-l-2 border-green-200 border-b-2"
                        >{{ user.id }}</p>
                        <p class="w-3/12 py-1 border-l-2 border-green-200 border-b-2"
                        >{{ user.name }}</p>

                        <p class="w-2/12 py-1 border-l-2 border-green-200 border-b-2"
                        >{{ timeFormatter(user.createDate) }}</p>

                        <p class="w-2/12 py-1 border-l-2 border-green-200 border-b-2"
                        v-if="user.enable">是</p>
                        <p class="w-2/12 py-1 border-l-2 border-green-200 border-b-2"
                        v-if="!user.enable">否</p>
                    </div>
                    

                    <!-- 搜尋功能用 -->

                    <div v-if="isInSearch"
                    v-for="(user, index) in displayPerPage"
                    @click="modalOfUserDetailHandler(index, user.identity)"
                    class="w-full flex text-center odd:bg-green-50 even:bg-white text-sm cursor-pointer duration-100 hover:bg-gray-100 text-green-800">
                        <p class="w-1/6 py-1 border-b-2 border-green-200"
                        v-if="user.identity === 'admin'">管理員</p>
                        <p class="w-1/6 py-1 border-b-2 border-green-200"
                        v-if="user.identity === 'student'">學生</p>
                        <p class="w-3/12 py-1 border-l-2 border-green-200 border-b-2">{{ user.id }}</p>
                        <p class="w-3/12 py-1 border-l-2 border-green-200 border-b-2">{{ user.name }}</p>
                        <p class="w-1/6 py-1 border-l-2 border-green-200 border-b-2">{{ timeFormatter(user.createDate) }}</p>

                        <p class="w-1/6 py-1 border-l-2 border-green-200 border-b-2"
                        v-if="user.enable">是</p>
                        <p class="w-1/6 py-1 border-l-2 border-green-200 border-b-2"
                        v-if="!user.enable">否</p>
                    </div>

                    <div class="text-center bg-green-200 p-4 text-green-800"
                    v-if="isInSearch && displaySearchResult.length === 0">
                        <p>沒有找到資料喔！請嘗試放寬搜尋條件吧！</p>
                    </div>
 
                    <button v-if="selected != null"
                    @click="clearSelect"
                    class="bg-green-800 py-1 px-4 text-white rounded-md my-4 w-24 block mx-auto font-bold"
                    type="button">顯示全部</button>
                </div>
                <div v-if="displayData.length > userDataPerPage"
                class="text-center my-2">
                    <!-- 翻頁按鈕區塊 -->
                    <button @click="prePage"
                    :disabled="currentPage == 1"
                    :class="{'text-green-300': currentPage == 1, 'text-green-800': currentPage != 1}"
                    type="button">
                        <i class="fa-solid fa-angles-left"></i>
                    </button>
                    <button @click="nextPage"
                    :disabled="currentPage == totalPage"
                    :class="{'text-green-300': currentPage == totalPage, 'text-green-800': currentPage != totalPage }"
                    type="button">
                        <i class="fa-solid fa-angles-right"></i>
                    </button>
                </div>
            </div>

        </div>
    </div>
</template>

<style lang='scss' scoped>

</style>