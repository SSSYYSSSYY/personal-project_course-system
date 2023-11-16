<script>
import Banner from '../components/Banner.vue';
// 等到有後端，可以判斷使用者身分後，
// 在課程頁面下方做一個「回到後台」的懸浮按鈕
const API_URL = "http://localhost:8080";
export default{
    data() {
        return {
            // 假資料
            // 假設這裡會收到所有課程相關資料
            // 但這裡只顯示一部份，剩下props到由courseCode構成的動態路由
            coursesData: [
                {
                    courseCode: "J0301",//主鍵
                    startDate: "2023/08/02",
                    endDate:"2023/10/31",
                    courseOutline: "JAVA基礎概念",
                    courseStatement: "認識變數",
                    courseSummary: "當天課程內容概要說明",
                    instructor: "王老師",
                    details: "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Molestiae officiis culpa praesentium eligendi, ipsum natus velit quaerat dicta nobis mollitia voluptatum inventore accusamus necessitatibus modi doloribus voluptatibus unde adipisci amet.",
                    weekday: 0, 
                    //會有星期一~五的五種資料，
                    // 0代表星期一，以此類推
                    timeSlot:0,
                    // 預計將每天分成4個時段，
                    // 0代表第一個時段，以此類推
                    // 串上後端後，前端再由timeSlot的值來渲染文字(暫定)
                    isOffered: true,//表示是否開課
                    city: "台南市",
                    // 構想同weekday
                    addDate: "2023/08/02",
                    lastEditDate: "2023/08/02",//上一次修改的日期
                    editAdminId: "a1000000",//上一次修改的人員代碼
                    students: [],
                    //這裡放選修了此課程的學生的資料：
                    // 至少會有學生姓名、學號
                    // 其他等有後端後再來思考這裡的架構
                },
                {
                    courseCode: "J0302",
                    startDate: "2023/08/03",
                    endDate:"2023/10/31",
                    courseOutline: "JavaScript基礎概念",
                    courseStatement: "認識變數",
                    courseSummary: "當天課程內容概要說明",
                    instructor: "張老師",
                    details: "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Molestiae officiis culpa praesentium eligendi, ipsum natus velit quaerat dicta nobis mollitia voluptatum inventore accusamus necessitatibus modi doloribus voluptatibus unde adipisci amet.",
                    weekday: "星期二", 
                    timeSlot:0,
                    isOffered: true,
                    city: "高雄市",
                    addDate: "2023/08/02",
                    lastEditDate: "2023/08/02",
                    editAdminId: "a1000000",
                    students: [],
                },
                {
                    courseCode: "J0303",
                    startDate: "2023/08/03",
                    endDate:"2023/10/31",
                    courseOutline: "JavaScript基礎概念",
                    courseStatement: "認識變數",
                    courseSummary: "當天課程內容概要說明",
                    instructor: "張老師",
                    details: "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Molestiae officiis culpa praesentium eligendi, ipsum natus velit quaerat dicta nobis mollitia voluptatum inventore accusamus necessitatibus modi doloribus voluptatibus unde adipisci amet.",
                    weekday: "星期二", 
                    timeSlot:0,
                    isOffered: true,
                    city: "高雄市",
                    addDate: "2023/08/02",
                    lastEditDate: "2023/08/02",
                    editAdminId: "a1000000",
                    students: [],
                },
                {
                    courseCode: "J0304",
                    startDate: "2023/08/03",
                    endDate:"2023/10/31",
                    courseOutline: "JavaScript基礎概念",
                    courseStatement: "認識變數",
                    courseSummary: "當天課程內容概要說明",
                    instructor: "張老師",
                    details: "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Molestiae officiis culpa praesentium eligendi, ipsum natus velit quaerat dicta nobis mollitia voluptatum inventore accusamus necessitatibus modi doloribus voluptatibus unde adipisci amet.",
                    weekday: "星期二", 
                    timeSlot:0,
                    isOffered: true,
                    city: "高雄市",
                    addDate: "2023/08/02",
                    lastEditDate: "2023/08/02",
                    editAdminId: "a1000000",
                    students: [],
                },
                {
                    courseCode: "J0305",
                    startDate: "2023/08/03",
                    endDate:"2023/10/31",
                    courseOutline: "JavaScript基礎概念",
                    courseStatement: "認識變數",
                    courseSummary: "當天課程內容概要說明",
                    instructor: "張老師",
                    details: "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Molestiae officiis culpa praesentium eligendi, ipsum natus velit quaerat dicta nobis mollitia voluptatum inventore accusamus necessitatibus modi doloribus voluptatibus unde adipisci amet.",
                    weekday: "星期二", 
                    timeSlot:0,
                    isOffered: true,
                    city: "高雄市",
                    addDate: "2023/08/02",
                    lastEditDate: "2023/08/02",
                    editAdminId: "a1000000",
                    students: [],
                },
            ],

            // 搜尋功能用
            searchBy: null,
            searchValue: null,
            displayCoursesData: [],

            identity: null,

            
        }
    },
    components:{
        Banner,
    },
    methods:{
        goToCourseDetail(index){
            this.$router.push({
                name: "Course",
                params:{
                    courseCode: this.coursesData[index].code,
                    identity: this.identity,
                },
            });

        },
        search(){
            if(this.searchBy === null || this.searchValue === null){
                alert("請輸入要搜尋的內容！");
            }else{
                this.displayCoursesData = [];
                console.log(this.searchBy, this.searchValue);
                this.coursesData.forEach(course => {
                    if(course[this.searchBy].includes(this.searchValue)){
                        this.displayCoursesData.push(course);
                    }
                });
                this.searchBy = null;
                this.searchValue = null;
            }
        },
        displayAllCourses(){
            this.isSearchResultNull = false;
            this.displayCoursesData = this.coursesData;
        },
        getCurrentIdentity(){
            if(sessionStorage.getItem("permission") == 0){
                this.identity = "superAdmin";
                return;
            }
            if(sessionStorage.getItem("permission") == 1){
                this.identity = "normalAdmin";
                return;
            }
            this.identity = "student";
        },
        getAllCourses(){
            fetch(`${API_URL}/courses`)
            .then(response => response.json())
            .then(data => {
                console.log(data)
                this.coursesData = data;
                this.displayCoursesData = this.coursesData;
            })
        },
        datetimeFormatter(datetimeStr){
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
        goToNewCourse(){
            this.$router.push({
                name: 'NewCourse',
                params: {
                    identity: this.identity,
                }
            })
        }
    },
    mounted(){
        this.getAllCourses();

        this.getCurrentIdentity();

    }
}
</script>

<template>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <Banner />
    <div class="p-4 w-full max-w-5xl mx-auto relative">
        <RouterLink class="fixed bg-slate-700 text-white py-1 px-4 rounded-lg right-5 bottom-10"
        v-if="identity == 'student'"
        to="/account-student">
            <i class="fa-solid fa-house-chimney-user"></i>
            回個人首頁
        </RouterLink>

        <RouterLink class="fixed bg-slate-700 text-white py-1 px-4 rounded-lg right-5 bottom-10"
        v-if="identity == 'normalAdmin'"
        to="/account-normal-admin">
            <i class="fa-solid fa-house-chimney-user"></i>
            回個人首頁
        </RouterLink>

        <RouterLink class="fixed bg-slate-700 text-white py-1 px-4 rounded-lg right-5 bottom-10"
        v-if="identity == 'superAdmin'"
        to="/account-super-admin">
            <i class="fa-solid fa-house-chimney-user"></i>
            回個人首頁
        </RouterLink>
        <div class="flex justify-center items-center mt-4 mb-8">
            <!-- 搜尋區塊 -->
            <select class=" border-indigo-200 border-2 rounded-md text-indigo-900"
            name="searchCoursesBy" id="searchCoursesBy"
            v-model="searchBy">
                <option value="null" selected disabled>--請選擇--</option>
                <option value="code">課程代碼</option>
                <option value="outline">課程大綱</option>
                <option value="instructor">授課講師</option>
                <option value="startDate">開課日期</option>
            </select>
            <input v-model="searchValue"
            class="border-indigo-200 border-2 rounded-md mx-5 w-80"
            type="text">
            <button @click="search"
            class="bg-indigo-800 py-1 px-4 text-white rounded-md hover:bg-indigo-700 duration-100 font-bold"
            type="button">搜尋</button>
            <button @click="displayAllCourses"
            class="text-indigo-800 py-1 px-4 bg-white rounded-md  duration-100 border-2 border-indigo-800 font-bold ml-4 hover:text-indigo-500 hover:border-indigo-500"
            type="button">顯示全部課程</button>
        </div>
        <div>
            <!-- 顯示課程區塊 -->
            <div class="w-full mx-auto text-center flex justify-center">
                <p class="w-1/12 bg-indigo-700 text-white p-1 rounded-tl-md">流水號</p>
                <p class="w-2/12 bg-indigo-700 text-white p-1 border-l-2 border-indigo-200">課程代碼</p>
                <p class="w-2/12 bg-indigo-700 text-white p-1 border-l-2 border-indigo-200">授課講師</p>
                <p class="w-6/12 bg-indigo-700 text-white p-1 border-l-2 border-indigo-200">課程大綱</p>
                <p class="w-3/12 bg-indigo-700 text-white p-1 border-l-2 border-indigo-200 rounded-tr-md">開課日期</p>
            </div>
            <!-- 以下開始渲染資料 -->
            <div class="text-center bg-indigo-100 p-4 text-indigo-800"
            v-if="displayCoursesData.length === 0">
                <p>目前沒有資料！</p>
            </div>
            <div @click="goToCourseDetail(index)"
            class="w-full mx-auto text-center odd:bg-indigo-100 even:bg-indigo-50 flex justify-center duration-100 hover:bg-slate-50  cursor-pointer"
            v-for="(course, index) in displayCoursesData">
            <p class="w-1/12  text-indigo-900 p-1 ">{{ index + 1 }}</p>
                <p class="w-2/12 text-indigo-900 p-1 border-l-2 border-indigo-200">{{ course.code }}</p>
                <p class="w-2/12 text-indigo-900 p-1 border-l-2 border-indigo-200">{{ course.instructor }}</p>
                <p class="w-6/12 text-indigo-900 p-1 border-l-2 border-indigo-200">{{ course.outline }}</p>
                <p class="w-3/12 text-indigo-900 p-1 border-l-2 border-indigo-200">{{ datetimeFormatter(course.startDate) }}</p>
            </div>
        </div>
        <div class="my-10" v-if="identity == 'superAdmin' || identity == 'normalAdmin'">
            <!-- 功能按鈕區塊 -->
            <button type="button" @click="goToNewCourse"
            class="block w-24 bg-indigo-800 text-white py-1 rounded-md mx-auto duration-100 hover:bg-indigo-700 font-bold text-center"
            >新增課程</button>
            <!-- 新增課程按鈕只有在管理者登入時才顯示 -->
        </div>
    </div>
</template>

<style lang='scss' scoped>

</style>