<script>
import ModalOfUserDetail from './ModalOfUserDetail.vue';
const API_URL = "http://localhost:8080";
export default{
    data() {
        return {
            // 資料庫中user要分成admin和student兩邊分開存放?
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
            //         email: "123@mail.com",
            //     },
            //     {
            //         studentId: "s1000003",
            //         studentName: "王小明",
            //         studentBirthday: "2000/08/01",
            //         studentSignDate: "2023/08/02",
            //         isSubsidized: true,
            //         // 小於29歲有補助
            //         isAccountEnable: false,
            //         email: "123@mail.com",
            //     },
            //     {
            //         studentId: "s1000004",
            //         studentName: "林冠宇",
            //         studentBirthday: "2000/05/01",
            //         studentSignDate: "2023/08/02",
            //         isSubsidized: true,
            //         // 小於29歲有補助
            //         isAccountEnable: true,
            //         email: "123@mail.com",
            //     },
            //     {
            //         studentId: "s1000005",
            //         studentName: "黃俊豪",
            //         studentBirthday: "1987/11/02",
            //         studentSignDate: "2023/08/02",
            //         isSubsidized: false,
            //         // 小於29歲有補助
            //         isAccountEnable: false,
            //         email: "123@mail.com",
            //     },
            // ],

            studentsDataArr: [],
            selected: "student",

            displayData: [],

            displayPerPage: [],
            userDataPerPage: 10,
            currentPage: 1,
            totalPage: 0,

            isModalOfUserDetailShow: false,
            indexOfCurrentData: null,
            currentSelectedUser: null,

            // 控制搜尋用
            searchBy: null,
            searchValue: null,
            isInSearch: false,
            displaySearchResult: [],
        }
    },
    components:{
        ModalOfUserDetail,
    },
    methods:{

        clearSelect(){
            this.selected = null;
            this.displayData = this.studentsDataArr;
            this.displayPerPage = this.displayData.slice(0, this.userDataPerPage);
            this.isInSearch = false;

            if(this.displayData.length % this.userDataPerPage == 0){
                this.totalPage = this.displayData.length / this.userDataPerPage;
            }else{
                this.totalPage = Math.floor(this.displayData.length / this.userDataPerPage) + 1;
            }
            this.currentPage = 1;
        },
        modalOfUserDetailHandler(index, identity){
            this.isModalOfUserDetailShow = !this.isModalOfUserDetailShow;
            console.log(this.currentPage);
            if(this.isInSearch){
                this.currentSelectedUser = this.displaySearchResult[index + (this.currentPage - 1) * this.userDataPerPage];

            }else{
                this.currentSelectedUser = this.studentsDataArr[index + (this.currentPage - 1) * this.userDataPerPage];

            }

            
        },
        search(){
            if(this.searchBy === null || this.searchValue === null){
                alert("請輸入要搜尋的內容！");
            }else{
                this.isInSearch = true;
                this.displayData = [];
                this.displaySearchResult = [];
                console.log(this.searchBy, this.searchValue)
                this.studentsDataArr.forEach(student => {
                    if(student[this.searchBy].includes(this.searchValue)){
                        this.displaySearchResult.push(student);
                    }
                });
                if(this.displaySearchResult.length % this.userDataPerPage == 0){
                    this.totalPage = this.displaySearchResult.length / this.userDataPerPage;
                }else{
                    this.totalPage = Math.floor(this.displaySearchResult.length / this.userDataPerPage) + 1;
                }
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
        getData(){
            fetch(`${API_URL}/get_all_student`)
            .then(response => response.json())
            .then(data => {
                console.log(data)
                this.studentsDataArr = data;
                this.displayData = this.studentsDataArr;
                this.isInSearch = false;
                this.displayPerPage = this.displayData.slice(0, this.userDataPerPage);
            })
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
    },
    created(){
        this.getData();
    },
    mounted(){


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
                console.log(this.displayData);
            }
            if(this.selected === "student"){
                this.displayData = this.studentsDataArr;
                console.log(this.displayData);
            }
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
            <div>
                <p>學號：{{ currentSelectedUser.id }}</p>
                <p>姓名：{{ currentSelectedUser.name }}</p>
                <p>Email：{{ currentSelectedUser.email }}</p>
                <p>註冊時間：{{ timeFormatter(currentSelectedUser.createDate) }}</p>
                <p v-if="currentSelectedUser.enable">帳號啟用狀態：已啟用</p>
                <p v-if="!currentSelectedUser.enable">帳號啟用狀態：未啟用</p>
            </div>
        </ModalOfUserDetail>
        <h2 class="text-2xl mb-4 pb-2 border-b-2 border-green-600 font-bold text-green-900">學生查詢</h2>
        <div>
            <div class="mx-auto">
                <div
                class="flex justify-center mb-6">
                    <select v-model="searchBy"
                    class="rounded-md"
                    name="searchStudentBy" id="searchStudentBy">
                        <option :value="null" selected disabled>--請選擇--</option>
                        <option value="id">學號</option>
                        <option value="name">姓名</option>
                    </select>
                    <input v-model="searchValue"
                    class="ml-4 rounded-md border-green-600 border-2 placeholder:text-sm"
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
                        <p class="w-1/6 py-1">身分</p>
                        <p class="w-3/12 py-1 border-l-2 border-green-100">學號</p>
                        <p class="w-3/12 py-1 border-l-2 border-green-100">姓名</p>
                        <p class="w-1/6 py-1 border-l-2 border-green-100">註冊時間</p>
                        <p class="w-1/6 py-1 border-l-2 border-green-100">是否啟用</p>
                    </div>
                    <!-- 以下開始渲染資料內容，修改一下思維 -->
                    <!-- 顯示全部資料時 -->
                    <div class="text-center bg-green-200 p-4 text-green-800"
                    v-if="displaySearchResult.length === 0 && isInSearch">
                        <p>沒有找到資料喔！請嘗試放寬搜尋條件吧！</p>
                    </div>
                    <div v-if="!isInSearch"
                    v-for="(student, index) in displayPerPage" class="w-full flex text-center text-sm bg-white cursor-pointer odd:bg-green-50 duration-100 hover:bg-gray-100"
                    @click="modalOfUserDetailHandler(index, 'student')">
                        <p class="w-1/6 py-1 border-b-2 border-green-200">學生</p>
                        <p class="w-3/12 py-1 border-b-2 border-l-2 border-green-200">{{ student.id }}</p>
                        <p class="w-3/12 py-1 border-b-2 border-green-200 border-l-2">{{ student.name }}</p>
                        <p class="w-1/6 py-1 border-b-2 border-green-200 border-l-2">{{ timeFormatter(student.createDate) }}</p>
                        <p v-if="student.enable" class="w-1/6 py-1 border-b-2 border-green-200 border-l-2">是</p>
                        <p v-if="!student.enable" class="w-1/6 py-1 border-b-2 border-green-200 border-l-2">否</p>
                    </div>
                    <!-- 搜尋用 -->
                    <div v-if="isInSearch"
                    v-for="(student, index) in displayPerPage" class="w-full flex text-center text-sm bg-white cursor-pointer odd:bg-green-50 duration-100 hover:bg-gray-100"
                    @click="modalOfUserDetailHandler(index, 'student')">
                        <p class="w-1/6 py-1 border-b-2 border-green-200">學生</p>
                        <p class="w-2/6 py-1 border-b-2 border-l-2 border-green-200">{{ student.id }}</p>
                        <p class="w-1/6 py-1 border-b-2 border-green-200 border-l-2">{{ student.name }}</p>
                        <p class="w-1/6 py-1 border-b-2 border-green-200 border-l-2">{{ timeFormatter(student.createDate) }}</p>
                        <p v-if="student.enable" class="w-1/6 py-1 border-b-2 border-green-200 border-l-2">是</p>
                        <p v-if="!student.enable" class="w-1/6 py-1 border-b-2 border-green-200 border-l-2">否</p>
                    </div>
                    <button v-if="isInSearch"
                    @click="clearSelect"
                    class="bg-green-800 py-1 px-4 text-white rounded-md my-4 w-24 block mx-auto hover:bg-green-700 duration-100 font-bold"
                    type="button">顯示全部</button>
                </div>
                <div v-if="(displayData.length > userDataPerPage) || (displaySearchResult.length > userDataPerPage)"
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