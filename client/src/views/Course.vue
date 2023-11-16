<script>
import Banner from '../components/Banner.vue';
import ModalOfProgressInAdmin from '../components/ModalOfProgressInAdmin.vue';
import CourseChat from '../components/CourseChat.vue';
import { RouterLink } from 'vue-router';


const API_URL = "http://localhost:8080";
export default{
    data() {
        return {
            courseData: null,
            statementList: [],
            courseStudentList: [],

            currentStudentSchedule: null,

            isShowStudents: false,
            isStudentProgressShow: false,
            indexOfCurrentStudent: null,

            isEdit: false,
            currentAdmin: null,

            isSelect: false,

            isShowMenu: false,

            isShowChat: false,

        }
        // 修改課程的邏輯：
        // 按下編輯按鈕後跳出modal，在modal按下送出後資料送到後端
        // 接著因為此頁畫面是從後端取得，
        // 所以成功送出後這裡的資料應該也要跟著變動
        // 為保險起見，可以設置成送出資料後重整頁面?
    },
    props:[
        "courseCode", "identity"
    ],
    components:{
        Banner,
        ModalOfProgressInAdmin,
        CourseChat,
    },
    created(){
        this.getCurrentCourse();
        if(this.identity == "student"){
            this.currentStudentSchedule = JSON.parse(sessionStorage.getItem("schedule"));
        }else if(this.identity == "superAdmin" || this.identity == "normalAdmin"){
            this.currentAdmin = sessionStorage.getItem("id");
        }else{
            this.$router.push({
                name: 'ErrorPage'
            });
        }
        
        
    },
    methods:{
        showStudentsHandler(){
            this.isShowStudents = !this.isShowStudents;
        },
        studentProgressShowHandler(){
            this.isStudentProgressShow = !this.isStudentProgressShow;
        },
        async getCurrentCourse(){
            this.courseData = await fetch(`${API_URL}/courses/${this.courseCode}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if(data.code == "200"){
                    this.statementList = data.courseData.statementList;
                    this.courseStudentList = data.courseData.studentList;
                    return data.courseData.courseInfo;
                }else{
                    this.$router.push({
                        name: 'ErrorPage'
                    })
                }
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
        goToEdit(){
            this.isEdit = true;
            console.log(this.courseData.startDate)
            console.log(this.courseData.endDate)

            this.courseData.startDate = String(this.courseData.startDate).slice(0, 10);
            this.courseData.endDate = String(this.courseData.endDate).slice(0, 10);
        },
        editSubmit(){
            if(this.courseData.startDate > this.courseData.endDate){
                alert("開始日期不可大於結束日期！");
                return;
            }

            const editCourseReq = {
                editAdmin: this.currentAdmin,
                outline: this.courseData.outline,
                summary: this.courseData.summary,
                details: this.courseData.details,
                instructor: this.courseData.instructor,
                city: this.courseData.city,
                startDate: new Date(this.courseData.startDate),
                endDate: new Date(this.courseData.endDate),
                offered: this.courseData.offered,

            }

            console.log(editCourseReq)

            fetch(`${API_URL}/courses/${this.courseCode}`, {
                method: "PATCH",
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(editCourseReq),
            })
            .then(response => response.json())
            .then(data => {
                console.log(editCourseReq)
                if(data.code == "200"){
                    alert("修改成功！");
                    this.isEdit = false;
                    location.reload();
                    return;
                }
            });
        },
        selectCourse(){
            const selectCourseReq = {
                studentId: sessionStorage.getItem("id"),
                courseCode: this.courseCode,
                weekday: this.courseData.weekday,
                timeslot: this.courseData.timeslot,
            }
            console.log(selectCourseReq);

            fetch(`${API_URL}/select`, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(selectCourseReq),
            })
            .then(response => response.json())
            .then(data => {
                if(data.code == "200"){
                    alert("選修成功！");
                    this.isSelect = true;
                    this.currentStudentSchedule[this.courseData.weekday][this.courseData.timeslot] = {
                        code: data.courseCode,
                        outline: data.courseOutline,
                    }
                    this.isSelect = true;
                    sessionStorage.setItem("schedule", JSON.stringify(this.currentStudentSchedule))
                    location.reload();
                    return;
                }else{
                    console.log(data)
                }
            })
        },
        checkIsSelect(){
            if(this.identity == "student"){
                if(this.currentStudentSchedule[this.courseData.weekday][this.courseData.timeslot] != null && this.currentStudentSchedule[this.courseData.weekday][this.courseData.timeslot].code == this.courseCode){
                    console.log("已選修此課程");
                    this.isSelect = true;
                }
            }

        },
        showMenuHandler(){
            this.isShowMenu = !this.isShowMenu;
            if(!this.isShowMenu && this.isShowChat){
                this.isShowChat = false;
            }
        },
        chatHandler(){
            this.isShowChat = !this.isShowChat;
        }

    },
    mounted(){
        console.log(this.courseData)
        this.checkIsSelect();
        
    },
    updated(){
        this.checkIsSelect();
    }
}
</script>

<template>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <div class="">

        <div
        class="menu fixed bg-slate-200 p-4 rounded-lg right-0 bottom-5 duration-100 pl-6 shadow-md shadow-slate-600"
        :class="{'translate-x-40': !isShowMenu, 'translate-x-0': isShowMenu}">
            <button class="text-xl absolute left-0 bottom-0 top-0 duration-100 text-slate-700"
            :class="{'rotate-180': isShowMenu}"
            @click="showMenuHandler"
            type="button">
                ◂
            </button>
            <CourseChat id="courseChat"
            :courseCode="courseCode"
            :identity="identity"
            v-bind:isShowChat="isShowChat"/>
            <button @click="chatHandler"
            class="bg-white text-slate-700 py-1 px-4 rounded-lg right-5 mb-5 block border-2 border-slate-700"
            type="button">
                <i class="fa-solid fa-comments"></i>
                看課程討論
            </button>
            <a 
            class=" bg-slate-700 text-white py-1 px-4 rounded-lg right-5 bottom-24 block"
            v-if="identity == 'student'"
            href="/account-student">
                <i class="fa-solid fa-house-chimney-user"></i>
                回個人首頁
            </a>

            <a 
            class=" bg-slate-700 text-white py-1 px-4 rounded-lg right-5 bottom-24 block"
            v-if="identity == 'normalAdmin'"
            href="/account-normal-admin">
                <i class="fa-solid fa-house-chimney-user"></i>
                回個人首頁
            </a>

            <a 
            class=" bg-slate-700 text-white py-1 px-4 rounded-lg right-5 bottom-24 block"
            v-if="identity == 'superAdmin'"
            href="/account-super-admin">
                <i class="fa-solid fa-house-chimney-user"></i>
                回個人首頁
            </a>
            <a 
            class=" bg-slate-700 text-white py-1 px-4 rounded-lg right-5 bottom-10 block mt-5"
            href="/courses" >
                <i class="fa-regular fa-calendar-days"></i>
                回課程總覽
            </a>
        </div>

        <Banner />
        <div v-if="!isEdit"
        class="p-4 w-3/4 bg-indigo-50 mx-auto my-6 rounded-lg leading-7">
            <h2 class="text-2xl font-bold text-center text-indigo-900 mb-4">{{ courseData.outline }}</h2>
            <div class="text-right text-xs text-gray-400">
                <p>新增日期：{{ datetimeFormatter(courseData.createDate) }}</p>
                <p v-if="courseData.editDate == null">更新日期：{{ datetimeFormatter(courseData.createDate) }}</p>
                <p v-else>更新日期：{{ datetimeFormatter(courseData.editDate) }}</p>
                <p>更新人員：{{ courseData.editAdmin }}</p>
            </div>
            <div class="text-indigo-900 p-4">
                <p>課程代碼：{{ courseData.code }}</p>
                <p>授課講師：{{ courseData.instructor }}</p>
                <p>課程簡述：</p>
                <p class="p-4 bg-slate-200 m-4 rounded-md text-slate-800">{{ courseData.summary }}</p>
                <p>課程細項：</p>
                <div class="p-4 bg-indigo-100 m-4 rounded-md">
                    <p v-for="(statement, index) in statementList" :key="index">{{ index + 1 }}：{{ statement }}</p>
                </div>
                <p>課程詳述：</p>
                <p class="p-4 bg-indigo-100 m-4 rounded-md">{{ courseData.details }}</p>
                <p v-if="courseData.offered">開課狀態：開課中</p>
                <p v-if="!courseData.offered">開課狀態：目前未開放</p>
                <p>
                    每週<span v-if="courseData.weekday === 0"
                    class="text-indigo-800 font-bold"
                    >星期一</span><span v-if="courseData.weekday === 1"
                    class="text-indigo-800 font-bold"
                    >星期二</span><span v-if="courseData.weekday === 2"
                    class="text-indigo-800 font-bold"
                    >星期三</span><span v-if="courseData.weekday === 3"
                    class="text-indigo-800 font-bold"
                    >星期四</span><span v-if="courseData.weekday === 4"
                    class="text-indigo-800 font-bold"
                    >星期五</span>上課
                </p>
                <!-- 這邊先假設每天課程是08:00~18:00 -->
                <!-- 0 => 08:00 ~ 10:00 -->
                <!-- 1 => 10:00 ~ 12:00 -->
                <!-- 2 => 14:00 ~ 16:00 -->
                <!-- 3 => 16:00 ~ 18:00 -->
                <p v-if="courseData.timeslot === 0">上課時段：08:00 ~ 10:00</p>
                <p v-if="courseData.timeslot === 1">上課時段：10:00 ~ 12:00</p>
                <p v-if="courseData.timeslot === 2">上課時段：14:00 ~ 16:00</p>
                <p v-if="courseData.timeslot === 3">上課時段：16:00 ~ 18:00</p>
                <p>課程期間：</p>
                <p class="text-center mt-2 mb-4 bg-indigo-100 rounded">{{ `${datetimeFormatter(courseData.startDate)} ~ ${datetimeFormatter(courseData.endDate)}` }}</p>
                <p>上課地點：{{ courseData.city }}</p>
            </div>
            <!-- 以下是功能按鈕區 -->
            <div 
            class="w-full flex justify-center pt-6 pb-4 border-t-2 border-dashed border-indigo-800"
            v-if="identity === 'superAdmin' || identity === 'normalAdmin'">
                <!-- 若目前登入者是管理員，則顯示此區 -->
                <button @click="goToEdit"
                class="bg-indigo-800 text-white py-1 px-4 rounded-md font-bold duration-100 hover:bg-indigo-700"
                type="button">編輯課程</button>
                <button 
                class="mx-5 bg-indigo-800 text-white py-1 px-4 rounded-md font-bold duration-100 hover:bg-indigo-700"
                type="button">刪除課程</button>
                <button @click="showStudentsHandler"
                class="py-1 px-4 rounded-md font-bold duration-100 hover:bg-indigo-100 border-2 border-indigo-800 bg-white text-indigo-800 "
                type="button">查看學生資料</button>
            </div>
            <div 
            class="w-full flex justify-center pt-6 pb-4 border-t-2 border-dashed border-indigo-800"
            v-if="identity === 'student'">
                <!-- 若目前登入者是學生，則顯示此區 -->
                <p v-if="!courseData.offered"
                class="bg-red-100 py-1 px-4 rounded-md border-2 border-red-300 text-red-800 text-sm"
                >此課程目前還未開放！</p>
                <button @click="selectCourse"
                v-if="currentStudentSchedule[courseData.weekday][courseData.timeslot] === null &&!isSelect && courseData.offered"
                class="bg-indigo-800 text-white py-1 px-4 rounded-md font-bold duration-100 hover:bg-indigo-700"
                type="button">選修此課程</button>
                <!-- 只有在學生該時段還沒有選課時，才顯示這個按鈕 -->

                <p v-if="currentStudentSchedule[courseData.weekday][courseData.timeslot] !== null && !isSelect"
                class="bg-red-100 py-1 px-4 rounded-md border-2 border-red-300 text-red-800 text-sm"
                >本時段已有選修<span class="font-bold">{{ currentStudentSchedule[courseData.weekday][courseData.timeslot].outline }}</span>，因此無法選修本課程！</p>
                <!-- 若學生該時段已經有選課，則顯示提醒訊息 -->
                <p class="bg-green-100 py-1 px-4 rounded-md border-2 border-green-300 text-green-800"
                v-if="isSelect">
                    您已選修此課程！
                </p>
            </div>
            <!-- 按下「查看學生資料」後要顯示以下區塊 -->
            <div 
            class="w-3/5 mx-auto"
            v-if="isShowStudents">
                <div class="w-full flex justify-center text-center text-white mt-4">
                    <p class="w-1/2 bg-violet-800 rounded-tl-md">學號</p>
                    <p class="w-1/2 bg-violet-800 border-l-2 border-violet-100 rounded-tr-md">姓名</p>
                </div>
                <div v-if="courseStudentList.length == 0">
                    <p class="bg-violet-100 p-4 text-center text-violet-900">目前還沒有學生選修這堂課！</p>
                </div>
                <div 
                class="w-full flex justify-center text-center items-center border-violet-300 odd:bg-violet-50 even:bg-violet-100"
                v-for="(student, index) in courseStudentList">
                    <p class="w-1/2 ">{{ student.id }}</p>
                    <p class="w-1/2 border-l-2 border-violet-200">{{ student.name }}</p>
                    
                </div>
            </div>
        </div>
        <div v-if="isEdit"
        class="p-4 w-3/4 bg-indigo-50 mx-auto my-6 rounded-lg leading-7">
            <!-- 可以改的資料：
            outline summary details instructor
            city startDate endDate offered -->
            <div class="text-indigo-900 p-4">
                <p>課程標題：
                    <input
                    required type="text" class="rounded" v-model="courseData.outline"></p>
                <p>課程代碼：{{ courseData.code }}</p>
                <p>授課講師：
                    <input required type="text" class="rounded" v-model="courseData.instructor"></p>
                <p>課程簡述：</p>
                <p class="p-4 bg-slate-200 m-4 rounded-md text-slate-800">
                    <input required class="w-full rounded-md"
                    type="text" v-model="courseData.summary"></p>
                <p>課程詳述：</p>
                <p class="p-4 bg-indigo-100 m-4 rounded-md">
                    <textarea required class="w-full rounded-md"
                    v-model="courseData.details"></textarea></p>
                <div class="w-full mt-4">
                    <label for="offered">是否開課</label>
                    <input class="m-1"
                    type="checkbox" id="offered" 
                    v-model="courseData.offered">
                </div>

                <p>課程期間：</p>
                <p class="mb-4 mt-2 text-center">
                    <input required type="date" class="rounded" v-model="courseData.startDate"> ~ <input required type="date" class="rounded" v-model="courseData.endDate"></p>
                <p>上課地點：
                    <input required type="text" class="rounded" v-model="courseData.city"></p>
            </div>
            <button @click="editSubmit"
            class="bg-indigo-800 text-white py-1 px-4 rounded-md font-bold duration-100 hover:bg-indigo-700 block mx-auto"
            type="button">確定修改</button>
        </div>
    </div>
</template>

<style lang='scss' scoped>

</style>