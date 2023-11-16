<script>
import Banner from '../components/Banner.vue';
const API_URL = "http://localhost:8080";
export default{
    data() {
        return {

            code: "",
            outline: "",
            instructor: "",
            summary: "",
            details: "",
            weekday: null,
            timeslot: null,
            startDate: null,
            endDate: null,
            city: "",
            courseStatement: ["",],
            offered: false,

            // 因為一個課程可以有多個項目，所以存成陣列
            // 陣列中每筆資料都代表一個項目，預設一個
            
            currentAdmin: null,

            // 要讓系統自動填入的值
            // addDate: null,
            // lastEditDate: null,
            // editAdminId: null,
            // students: [],
        }
    },
    components:{
        Banner,
    },
    props: [
        "identity"
    ],
    methods:{
        addStatement(){
            this.courseStatement.push(null);
        },
        deleteStatement(index){
            this.courseStatement.splice(index, 1);
        },
        statementInputHandler(statement, index){
            this.courseStatement[index] = statement;
        },
        submit(){
            if(this.weekday == null || this.timeslot == null){
                alert("請正確輸入資料！");
                return;
            }
            if(this.startDate > this.endDate){
                alert("開始日期不可大於結束日期！");
                return;
            }
            const addCourseReq = {
                courseInfo: {
                    code: this.code,
                    outline: this.outline,
                    instructor: this.instructor,
                    summary: this.summary,
                    details: this.details,
                    weekday: Number(this.weekday),
                    timeslot: Number(this.timeslot),
                    startDate: new Date(this.startDate),
                    endDate: new Date(this.endDate),
                    city: this.city,
                    offered: this.offered,
                    editAdmin: this.currentAdmin,
                },
                statementList: this.courseStatement,
            }

            console.log(addCourseReq);

            fetch(`${API_URL}/add_course`, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(addCourseReq),
            })
            .then(response => response.json())
            .then(data => {
                if(data.code == "400"){
                    alert("該課程代碼已被使用或資料有誤！");
                    return;
                }
                if(data.code == "200"){
                    alert("新增成功！");
                    this.$router.push({
                        name: 'Course',
                        params: {
                            courseCode: this.code,
                            identity: this.identity,
                        }
                    })
                }
            })


        },
        checkcourseCode(courseCode){
            // 預定用來檢查課程代號是否可以使用的方法
        },
        getAdminId(){
            this.currentAdmin = sessionStorage.getItem("id");
        },

    },
    mounted(){
        this.getAdminId();
        console.log(this.identity)
    }
}
</script>

<template>
    <div class="">
        <Banner />
        <div class="p-4 w-3/4 bg-violet-200 mx-auto my-6 rounded-lg leading-7 text-violet-900 max-w-2xl">
            <div class="w-full mt-4 pl-7">
                <label 
                class="w-1/4"
                for="courseCode">課程代碼：</label>
                <input v-model="code"
                class="w-3/4 rounded-md"
                type="text" name="courseCode" id="courseCode" required>
                <!-- 要檢查資料庫中是否已有同樣的課程代碼，
                若有的話要顯示提醒訊息，並把送出按鈕無效化 -->
            </div>
            <div class="w-full mt-4 pl-7">
                <label 
                class="w-1/4"
                for="courseOutline">課程大綱：</label>
                <input v-model="outline"
                class="w-3/4 rounded-md"
                type="text" name="courseOutline" id="courseOutline" required>
            </div>
            <div class="w-full mt-4">
                <p class="pl-7">課程項目：</p>
                <div class="bg-violet-300 py-2 mt-4 mb-6 rounded-lg mx-7">
                    <div v-for="(statement, index) in courseStatement"
                    class="flex justify-center items-center my-4">
                        <input @input="statementInputHandler($event.target.value, index)"
                        :value="courseStatement[index]"
                        class=" rounded-md" type="text"
                        required>
                        <button @click="deleteStatement(index)"
                        class="bg-red-700 text-white px-2 rounded-md ml-2"
                        :disabled="courseStatement.length === 1"
                        :class="{'bg-slate-300': courseStatement.length === 1}"
                        type="button">刪除</button>
                        <!-- 只剩一筆項目時刪除鈕無效化 -->
                    </div>
                    <div>
                        <button @click="addStatement"
                        class="bg-violet-800 text-white text-center block mx-auto px-4 rounded-md my-2"
                        type="button">新增課程項目</button>
                    </div>
                </div>


            </div>
            <div class="w-full mt-4 pl-7">
                <label 
                class="w-1/4"
                for="startDate">開始日期：</label>
                <input v-model="startDate"
                class="rounded-md"
                type="date" name="startDate" id="startDate" required>
            </div>
            <div class="w-full mt-4 pl-7">
                <label 
                class="w-1/4"
                for="endDate">結束日期：</label>
                <input v-model="endDate"
                class="rounded-md"
                type="date" name="endDate" id="endDate"
                required>
            </div>
            <div class="w-full mt-4 pl-7">
                <label 
                class="w-1/4"
                for="courseSummary">課程簡述：</label>
                <input v-model="summary"
                class="w-3/4 rounded-md"
                type="text" name="courseSummary" id="courseSummary" required>
            </div>
            <div class="w-full mt-4 pl-7">
                <label 
                class="w-1/4"
                for="instructor">授課講師：</label>
                <input v-model="instructor"
                class="w-3/4 rounded-md"
                type="text" name="instructor" id="instructor" required>
            </div>
            <div class="w-full mt-4 pl-7">
                <label 
                class="w-1/4 align-top"
                for="details">課程詳述：</label>
                <textarea v-model="details"
                class="w-3/4 rounded-md"
                type="text" name="details" id="details"
                required>
            </textarea>
            </div>
            <div class="w-full mt-4 pl-7">
                <span>每週</span>
                <select name="weekday" id="weekday"
                v-model="weekday">
                    <option value="null" selected disabled>--</option>
                    <option value="0">一</option>
                    <option value="1">二</option>
                    <option value="2">三</option>
                    <option value="3">四</option>
                    <option value="4">五</option>
                </select>
                <span>上課</span>
            </div>
            <div class="w-full mt-4 pl-7">
                <span>上課時段：</span>
                <select name="timeslot" id="timeslot"
                v-model="timeslot">
                    <option value="null" selected disabled>請選擇一個時段</option>
                    <option value="0">08:00 ~ 10:00</option>
                    <option value="1">10:00 ~ 12:00</option>
                    <option value="2">14:00 ~ 16:00</option>
                    <option value="3">16:00 ~ 18:00</option>
                </select>
            </div>
            <div class="w-full mt-4 pl-7">
                <label for="offered">是否開課</label>
                <input class="m-1" type="checkbox" v-model="offered" id="offered">
            </div>
            <div class="w-full my-4 pl-7">
                <label 
                class="w-1/4"
                for="city">開課地點：</label>
                <input 
                class="w-3/4 rounded-md"
                type="text" id="city" v-model="city" required>
            </div>
            <div class="mt-8 mb-4">
                <button @click="submit"
                class="bg-violet-800 text-white text-center block mx-auto px-4 rounded-md my-2 p-2 text-lg font-bold hover:bg-violet-700 duration-100"
                type="button">確定新增</button>
            </div>
        </div>

    </div>
</template>

<style lang='scss' scoped>

</style>