<script>
import ModalOfProgressInStudent from './ModalOfProgressInStudent.vue';
const API_URL = "http://localhost:8080";
export default{
    data() {
        return {
            // 假資料
            // 實際上從後端拿來的進度資料應該會包含學生的學號
            // 只是因為每個學生都只能查看自己的進度，
            // 所以這邊就不顯示學號
            // progressList: [
            //     {
            //         courseCode: "J0301",
            //         courseOutline: "JAVA基礎概念",
            //         courseStatement: "認識變數",
            //         understood: false,
            //         status: "未有進度",
            //         feedback: "回報內容1",
            //     },
            //     {
            //         courseCode: "J0302",
            //         courseOutline: "JAVA基礎概念",
            //         courseStatement: "認識方法",
            //         understood: true,
            //         status: "未有進度",
            //         feedback: "回報內容2",
            //     },
            //     {
            //         courseCode: "J0303",
            //         courseOutline: "JAVA基礎概念",
            //         courseStatement: "認識類別",
            //         understood: false,
            //         status: "未有進度",
            //         feedback: "回報內容3",
            //     },
            //     {
            //         courseCode: "J0304",
            //         courseOutline: "JAVA基礎概念",
            //         courseStatement: "認識類別",
            //         understood: false,
            //         status: "未有進度",
            //         feedback: "回報內容4",
            //     },
            //     {
            //         courseCode: "J0305",
            //         courseOutline: "JAVA基礎概念",
            //         courseStatement: "認識類別",
            //         understood: false,
            //         status: "未有進度",
            //         feedback: "回報內容5",
            //     },
            //     {
            //         courseCode: "J0306",
            //         courseOutline: "JAVA基礎概念",
            //         courseStatement: "認識類別",
            //         understood: false,
            //         status: "未有進度",
            //         feedback: "回報內容6",
            //     },
            // ],
            progressList: [],

            displayProgressData: [],
            progressDataPerPage: 10,
            currentPage: 1,
            totalPage: 1,
            isModalOfProgressShow: false,
            currentProgress: null,

            // 搜尋功能用
            searchBy: null,
            searchValue: null,
            isInSearch: false,

            //進度modal用
            isProgressEdit: false,

            statementList: [],
            statementForEdit: null,
            understoodForEdit: false,
            statusForEdit: null,
            feedbackForEdit: null,
        }
    },
    emits: ['turnToProgress'],
    components:{
        ModalOfProgressInStudent,
    },
    created(){
        this.getProgressList();
    },
    mounted(){
        

        
        this.displayProgressData = this.progressList.slice(0, this.progressDataPerPage);
        if(this.progressList.length % this.progressDataPerPage == 0){
            this.totalPage = this.progressList.length / this.progressDataPerPage;
        }else{
            this.totalPage = Math.floor(this.progressList.length / this.progressDataPerPage) + 1;
        }
        if(this.progressList.length == 0){
            this.totalPage = 1;
        }
        
    },
    methods:{
        prePage(){
            this.currentPage--;
            this.displayProgressData = this.progressList.slice((this.currentPage - 1) * this.progressDataPerPage, (this.currentPage - 1) * this.progressDataPerPage + this.progressDataPerPage);
        },
        nextPage(){
            this.currentPage++;
            this.displayProgressData = this.progressList.slice((this.currentPage - 1) * this.progressDataPerPage, (this.currentPage - 1) * this.progressDataPerPage + this.progressDataPerPage);
        },
        modalOfProgressHandler(index){
            if(this.isInSearch){
                this.currentProgress = this.displayProgressData[index];
            }else{
                this.currentProgress = this.progressList[index + (this.currentPage - 1) * this.progressDataPerPage];
            }

            console.log(this.currentProgress)

            this.statementForEdit = this.currentProgress.courseStatement;
            this.understoodForEdit = this.currentProgress.understood;
            this.statusForEdit = this.currentProgress.status;
            this.feedbackForEdit = this.currentProgress.feedback;

            this.isModalOfProgressShow = !this.isModalOfProgressShow;
        },
        closeProgressModal(){
            this.isModalOfProgressShow = false;
            this.isProgressEdit = false;
        },
        search(){
            if(this.searchBy === null || this.searchValue === null){
                alert("請輸入要搜尋的內容！");
            }else{
                console.log(this.searchBy, this.searchValue);
                this.isInSearch = true;
                this.displayProgressData = [];
                this.progressList.forEach(progress => {
                    if(progress[this.searchBy].includes(this.searchValue)){
                        this.displayProgressData.push(progress);
                    }
                });
                this.searchBy = null;
                this.searchValue = null;
            }
        },
        clearSearch(){
            this.displayProgressData = this.progressList;
            this.isInSearch = false;
        },
        getProgressList(){
            const studentId = sessionStorage.getItem("id");
            fetch(`${API_URL}/get_progress_list/${studentId}`)
            .then(response => response.json())
            .then(data => {
                console.log(data)
                sessionStorage.setItem("progressList", JSON.stringify(this.progressList))
                console.log(this.progressList)
            })
            this.progressList = JSON.parse(sessionStorage.getItem("progressList"));
        },
        progressEditHandler(courseCode){
            this.getStatementList(courseCode);
            this.isProgressEdit = !this.isProgressEdit;
        },
        getStatementList(courseCode){
            fetch(`${API_URL}/statement/${courseCode}`)
            .then(response => response.json())
            .then(data => {
                this.statementList = data;
            })
        },
        endEdit(){
            this.isProgressEdit = false;
        },
        submitEditProgress(courseCode, courseOutline){

            if(!this.feedbackForEdit || !this.statusForEdit){
                alert("請正確輸入資料！");
                console.log(this.feedbackForEdit)
                console.log(this.statusForEdit)
                console.log(this.understoodForEdit)
                return;
            }

            const submitEditProgressReq = {
                courseCode,
                studentId: sessionStorage.getItem("id"),
                courseOutline,
                courseStatement: this.statementForEdit,
                understood: this.understoodForEdit,
                status: this.statusForEdit,
                feedback: this.feedbackForEdit,
            }

            console.log(submitEditProgressReq)

            fetch(`${API_URL}/editProgress`, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(submitEditProgressReq),
            })
            .then(response => response.json())
            .then(data => {
                if(data.code == "200"){
                    alert("修改成功！");
                    this.$emit("turnToProgress");
                    return;
                }
            })
        },

    }
}
</script>

<template>
<!-- fontawesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<!-- fontawesome -->

    <div class="p-4">
        <ModalOfProgressInStudent v-if="isModalOfProgressShow" @closeModal="closeProgressModal">
            <div v-if="!isProgressEdit">
                <p>課程代碼：{{ currentProgress.courseCode }}</p>
                <p>課程大綱：{{ currentProgress.courseOutline }}</p>
                <p>課程項目：{{ currentProgress.courseStatement }}</p>
                <p v-if="currentProgress.understood">是否理解：是</p>
                <p v-if="!currentProgress.understood">是否理解：否</p>
                <p v-if="currentProgress.status == null">進度狀態：目前無進度</p>
                <p v-else>進度狀態：{{ currentProgress.status }}</p>
                <p v-if="currentProgress.feedback == null">回報內容：目前無回報</p>
                <p v-else>回報內容：{{ currentProgress.feedback }}</p>
                <button class="block mx-auto py-1 px-4 bg-slate-700 text-white mt-4 rounded-lg"
                @click="progressEditHandler(currentProgress.courseCode)"
                type="button">
                    修改進度
                </button>
            </div>
            <div v-if="isProgressEdit">
                <p>課程代碼：{{ currentProgress.courseCode }}</p>
                <p>課程大綱：{{ currentProgress.courseOutline }}</p>
                <label for="statementForEdit">課程項目：</label>
                <select class="border-2 rounded-md border-slate-200"
                name="statementForEdit" id="statementForEdit" v-model="statementForEdit">
                    <option
                    value="null" disabled selected>--請選擇--</option>
                    <option v-for="(statement, index) in statementList" :key="index" :value="statement">{{ statement }}</option>
                </select>
                <br>

                <p>理解狀態：</p>
                <div class="flex justify-center mt-2 bg-slate-100 w-4/5 mx-auto box-border px-4 py-1 rounded-lg">
                    <input type="radio" id="notUnderstood" name="understoodForEdit" :value="false" v-model="understoodForEdit">
                    <label for="notUnderstood">未理解</label>

                    <input class="ml-10" id="understood"
                    type="radio" name="understoodForEdit" :value="true" v-model="understoodForEdit">
                    <label
                    for="understood">已理解</label>
                </div>



                <br>
                <p>進度狀態：</p>
                <div class="flex justify-center mt-2 bg-slate-100 w-4/5 mx-auto box-border px-4 py-1 rounded-lg">
                    <input type="radio" name="statusForEdit" value="未解決" id="statusNotOk" v-model="statusForEdit">
                    <label for="statusNotOk">未解決</label>
                    
                    <input class="ml-10" id="statusOk"
                    type="radio" name="statusForEdit" value="已解決" v-model="statusForEdit">
                    <label
                    for="statusOk">已解決</label>
                </div>

                <br>
                <label for="feedbackForEdit">回報內容：</label>
                <textarea v-model="feedbackForEdit"
                class="w-full border-2 rounded-md"
                name="feedbackForEdit" id="feedbackForEdit" cols="6" rows="6" placeholder="在此填寫回報內容..." required></textarea>
                <div class="flex justify-center">
                    <button class="block py-1 px-4 bg-slate-700 text-white mt-4 rounded-lg"
                    @click="submitEditProgress(currentProgress.courseCode, currentProgress.courseOutline)"
                    type="button">
                        儲存進度
                    </button>
                    <button class="block py-1 px-4 bg-white text-slate-700 mt-4 rounded-lg border-2 border-slate-700 ml-4"
                    @click="endEdit"
                    type="button">
                        取消
                    </button>
                </div>

            </div>
        </ModalOfProgressInStudent>
        <h2 class="text-2xl mb-4 pb-2 border-b-2 border-green-600 font-bold text-green-900">學習進度管理</h2>
        <div>
            <div class="">
                <div class="flex justify-center my-4">
                    <select v-model="searchBy"
                    class="rounded-md"
                    name="searchProgressBy" id="searchProgressBy">
                        <option value="null" selected disabled>--請選擇--</option>
                        <option value="courseOutline">課程大綱</option>
                        <option value="courseStatement">課程項目</option>
                    </select>
                    <input v-model="searchValue"
                    class="ml-4 rounded-md border-green-600 border-2 placeholder:text-sm"
                    type="text" name="searchProgressText" 
                    placeholder="在此輸入搜尋內容..."
                    id="searchProgressText">
                    <button @click="search"
                    class="bg-green-800 py-1 px-4 text-white rounded-md hover:bg-green-700 duration-100 font-bold ml-4"
                    type="button">搜尋</button>
                    <button v-if="isInSearch"
                    @click="clearSearch"
                    class="border-2 border-green-800 py-1 px-4 text-green-700 rounded-md block hover:text-green-600 bg-white hover:border-green-700 duration-100 font-bold ml-4"
                    type="button">顯示全部</button>
                </div>
                <div class="rounded-br-md rounded-bl-md">
                    <div class="w-full flex text-center bg-green-800 text-white box-border text-sm rounded-tl-md rounded-tr-md">
                        <p class="w-1/6 py-1">課程代碼</p>
                        <p class="w-2/6 py-1 border-l-2 border-green-100">課程大綱</p>
                        <p class="w-1/6 py-1 border-l-2 border-green-100">課程項目</p>
                        <p class="w-1/6 py-1 border-l-2 border-green-100">是否理解</p>
                        <p class="w-1/6 py-1 border-l-2 border-green-100">進度狀態</p>
                    </div>
                    <!-- 以下開始渲染資料內容 -->

                    <div class="text-center bg-green-200 p-4 text-green-800"
                    v-if="displayProgressData.length === 0">
                        <p>目前沒有資料！</p>
                    </div>

                    <div v-for="(progress, index) in displayProgressData" class="w-full flex text-center text-sm bg-white cursor-pointer odd:bg-green-50 duration-100 hover:bg-gray-100"
                    @click="modalOfProgressHandler(index)">
                        <p class="w-1/6 py-1 border-b-2 border-green-200">{{ progress.courseCode }}</p>
                        <p class="w-2/6 py-1 border-b-2 border-green-200 border-l-2 ">{{ progress.courseOutline }}</p>
                        <p class="w-1/6 py-1 border-b-2 border-green-200 border-l-2 ">{{ progress.courseStatement }}</p>
                        <p class="w-1/6 py-1 border-b-2 border-green-200 border-l-2" v-if="progress.understood">是</p>
                        <p class="w-1/6 py-1 border-b-2 border-green-200 border-l-2" v-if="!progress.understood">否</p>
                        <p v-if="progress.status == null"
                        class="w-1/6 py-1 border-b-2 border-green-200 border-l-2">目前無進度</p>
                        <p v-else class="w-1/6 py-1 border-b-2 border-green-200 border-l-2">{{ progress.status }}</p>
                    </div>
                </div>
                <div class="text-center my-2">
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