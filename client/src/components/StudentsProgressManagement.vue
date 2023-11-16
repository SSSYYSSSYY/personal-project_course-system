<script>
import ModalOfProgressInAdmin from './ModalOfProgressInAdmin.vue';
const API_URL = "http://localhost:8080";
export default{
    data() {
        return {
            // 假資料
            // 學員學號(PK)、課程代碼、課程大綱、課程項目、是否理解、問題回報、是否已解決
            // status會有「未有進度」「未解決」「已解決」的三種
            // 管理員這邊只能看到「未解決」「已解決」的兩種
            studentsProgressData: [
                {
                    studentId: "s1000000",
                    studentName: "學生1",
                    courseCode: "J0301",
                    courseOutline: "JAVA基礎概念",
                    courseStatement :"認識變數",
                    understood: false,
                    feedback: "回報內容1",
                    status: "未有進度",
                },
                {
                    studentId: "s1000001",
                    studentName: "學生2",
                    courseCode: "J0301",
                    courseOutline: "JAVA基礎概念",
                    courseStatement :"認識變數",
                    understood: false,
                    feedback: "回報內容2",
                    status: "未解決",
                },
                {
                    studentId: "s1000000",
                    studentName: "學生3",
                    courseCode: "J0301",
                    courseOutline: "JAVA基礎概念",
                    courseStatement :"認識變數",
                    understood: true,
                    feedback: "回報內容3",
                    status: "已解決",
                },
                {
                    studentId: "s1000001",
                    studentName: "學生4",
                    courseCode: "J0301",
                    courseOutline: "JAVA基礎概念",
                    courseStatement :"認識變數",
                    understood: false,
                    feedback: "回報內容2",
                    status: "未解決",
                },
                {
                    studentId: "s1000000",
                    studentName: "學生5",
                    courseCode: "J0301",
                    courseOutline: "JAVA基礎概念",
                    courseStatement :"認識變數",
                    understood: true,
                    feedback: "回報內容3",
                    status: "已解決",
                },
                {
                    studentId: "s1000001",
                    studentName: "學生6",
                    courseCode: "J0301",
                    courseOutline: "JAVA基礎概念",
                    courseStatement :"認識變數",
                    understood: false,
                    feedback: "回報內容2",
                    status: "未解決",
                },
                {
                    studentId: "s1000000",
                    studentName: "學生7",
                    courseCode: "J0301",
                    courseOutline: "JAVA基礎概念",
                    courseStatement :"認識變數",
                    understood: true,
                    feedback: "回報內容3",
                    status: "已解決",
                },
                {
                    studentId: "s1000000",
                    studentName: "學生8",
                    courseCode: "J0301",
                    courseOutline: "JAVA基礎概念",
                    courseStatement :"認識變數",
                    understood: true,
                    feedback: "回報內容4",
                    status: "未解決",
                },
            ],
            allStudentProgressData: [],
            solvedProgresses: [],
            notSolvedProgresses: [],

            isModalOfProgressShow: false,
            currentProgress: null,
            
            selected: null,
            isSolved: true,
            isNotSolved: true,

            displayPerPage: [],
            progressDataPerPage: 10,
            currentPage: 1,
            totalPage: 0,
        }
    },
    components:{
        ModalOfProgressInAdmin,
    },
    methods:{
        modalOfProgressHandler(index){
            this.isModalOfProgressShow = !this.isModalOfProgressShow;
            this.currentProgress = this.allStudentProgressData[index + (this.currentPage - 1) * this.progressDataPerPage];
        },
        prePage(){
            this.currentPage--;
            this.displayPerPage = this.allStudentProgressData.slice((this.currentPage - 1) * this.progressDataPerPage, (this.currentPage - 1) * this.progressDataPerPage + this.progressDataPerPage);
        },
        nextPage(){
            this.currentPage++;
            this.displayPerPage = this.allStudentProgressData.slice((this.currentPage - 1) * this.progressDataPerPage, (this.currentPage - 1) * this.progressDataPerPage + this.progressDataPerPage);
        },
        filterHandler(){

        },
        getProgressData(){
            fetch(`${API_URL}/get_all_progress`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                this.studentsProgressData = data;

                this.solvedProgresses = this.studentsProgressData.filter(progress => (progress.status === "已解決"));
                this.notSolvedProgresses = this.studentsProgressData.filter(progress => (progress.status === "未解決"));

                this.allStudentProgressData = [...this.solvedProgresses, ...this.notSolvedProgresses];

                if(this.allStudentProgressData.length % this.progressDataPerPage == 0){
                    this.totalPage = this.allStudentProgressData.length / this.progressDataPerPage;
                }else{
                    this.totalPage = Math.floor(this.allStudentProgressData.length / this.progressDataPerPage) + 1;
                }


                this.displayPerPage = this.allStudentProgressData.slice(0, this.progressDataPerPage);
            })
        }
    },
    created(){
        this.getProgressData();
    },
    mounted(){

    },
    watch:{
        isSolved(){
            this.allStudentProgressData = [];
            if(this.isSolved){
                this.studentsProgressData.forEach(progress => {
                    if(progress.status === "已解決"){
                        this.allStudentProgressData.push(progress);
                    }
                });
            }else{
                this.allStudentProgressData.filter(progress => (progress !== "已解決"));
            }
            if(this.isNotSolved){
                this.studentsProgressData.forEach(progress => {
                    if(progress.status === "未解決"){
                        this.allStudentProgressData.push(progress);
                    }
                });
            }else{
                this.allStudentProgressData.filter(progress => (progress !== "未解決"));
            }

            if(this.allStudentProgressData.length % this.progressDataPerPage == 0){
                this.totalPage = this.allStudentProgressData.length / this.progressDataPerPage;
            }else{
                this.totalPage = Math.floor(this.allStudentProgressData.length / this.progressDataPerPage) + 1;
            }
            
            this.displayPerPage = this.allStudentProgressData.slice(0, this.progressDataPerPage);
        },
        isNotSolved(){
            this.allStudentProgressData = [];
            if(this.isSolved){
                this.studentsProgressData.forEach(progress => {
                    if(progress.status === "已解決"){
                        this.allStudentProgressData.push(progress);
                    }
                });
            }else{
                this.allStudentProgressData.filter(progress => (progress !== "已解決"));
            }
            if(this.isNotSolved){
                this.studentsProgressData.forEach(progress => {
                    if(progress.status === "未解決"){
                        this.allStudentProgressData.push(progress);
                    }
                });
            }else{
                this.allStudentProgressData.filter(progress => (progress !== "未解決"));
            }

            if(this.allStudentProgressData.length % this.progressDataPerPage == 0){
                this.totalPage = this.allStudentProgressData.length / this.progressDataPerPage;
            }else{
                this.totalPage = Math.floor(this.allStudentProgressData.length / this.progressDataPerPage) + 1;
            }

            this.displayPerPage = this.allStudentProgressData.slice(0, this.progressDataPerPage);
        }
    }
}
</script>

<template>
<!-- fontawesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<!-- fontawesome -->
    <div class="p-4">
        <ModalOfProgressInAdmin v-if="isModalOfProgressShow"
        @closeModal="modalOfProgressHandler">
            <p class="text-lg mb-2 font-bold">進度詳細：</p>
            <p>學生姓名：{{ currentProgress.studentName }}</p>
            <p>學號：{{ currentProgress.studentId }}</p>
            <p>課程代碼：{{ currentProgress.courseCode }}</p>
            <p>課程大綱：{{ currentProgress.courseOutline }}</p>
            <p>課程項目：{{ currentProgress.courseStatement }}</p>
            <p v-if="currentProgress.understood">是否理解：是</p>
            <p v-if="!currentProgress.understood">是否理解：否</p>
            <p>回報內容：</p>
            <p class="p-3 bg-slate-200 m-2 rounded-lg">{{ currentProgress.feedback }}</p>
            <p>進度狀態：{{ currentProgress.status }}</p>
        </ModalOfProgressInAdmin>
        <h2 class="text-2xl mb-4 pb-2 border-b-2 border-green-600 font-bold text-green-900">學生進度管理</h2>
        <div>
            <div class="flex justify-center text-center">
                <div class="w-24" @click="filterHandler">
                    <input v-model="isNotSolved"
                    type="checkbox" name="isNotSolved" id="isNotSolved">
                    <label for="isNotSolved">未解決</label>
                </div>
                <div class="w-24" @click="filterHandler">
                    <input v-model="isSolved"
                    type="checkbox" name="isSolved" id="isSolved">
                    <label for="isSolved">已解決</label>
                </div>
            </div>
            <div class="my-4">
                <div class="rounded-br-md rounded-bl-md">
                    <div class="w-full flex text-center bg-green-800 text-white box-border text-sm rounded-tl-md rounded-tr-md">
                        <p class="w-2/12 py-1">學號</p>
                        <p class="w-2/12 py-1 border-l-2 border-green-100">姓名</p>
                        <p class="w-4/12 py-1 border-l-2 border-green-100">課程大綱</p>
                        <p class="w-2/12 py-1 border-l-2 border-green-100">課程項目</p>
                        <p class="w-2/12 py-1 border-l-2 border-green-100">進度狀態</p>
                    </div>
                    <!-- 以下開始渲染資料內容 -->
                    <div v-for="(progress, index) in displayPerPage" class="w-full flex text-center text-sm bg-white cursor-pointer odd:bg-green-50 duration-100 hover:bg-gray-100"
                        @click="modalOfProgressHandler(index)">
                        <p class="w-2/12 py-1">{{ progress.studentId }}</p>
                        <p class="w-2/12 py-1 border-l-2 border-green-100">{{ progress.studentName }}</p>
                        <p class="w-4/12 py-1 border-l-2 border-green-100">{{ progress.courseOutline }}</p>
                        <p class="w-2/12 py-1 border-l-2 border-green-100">{{ progress.courseStatement }}</p>
                        <p class="w-2/12 py-1 border-l-2 border-green-100">{{ progress.status }}</p>
                    </div>
                    <div class="text-center bg-green-200 p-4 text-green-800"
                    v-if="allStudentProgressData.length === 0">
                        <p>沒有找到資料喔！請嘗試放寬條件吧！</p>
                    </div>
                </div>
                <div v-if="allStudentProgressData.length > progressDataPerPage"
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