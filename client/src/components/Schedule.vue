<script>
export default{
    data() {
        return {
            // 假資料
            // mySchedule: [
            //     [{
            //         code: "J0301",
            //         outline: "JAVA基礎課程",
            //     }, {
            //         code: "J0302",
            //         outline: "JS基礎課程",
            //     }, {
            //         code: "J0303",
            //         outline: "網頁設計",
            //     }, {
            //         code: "J0304",
            //         outline: "JS基礎課程",
            //     }],//代表星期一
            //     [{
            //         code: "J0305",
            //         outline: "JS基礎課程",
            //     }, 
            //     null
            //     , {
            //         code: "J0307",
            //         outline: "JS基礎課程",
            //     }, {
            //         code: "J0308",
            //         outline: "JS基礎課程",
            //     }],//代表星期二
            //     [{
            //         code: "J0309",
            //         outline: "JS基礎課程",
            //     }, {
            //         code: "J0310",
            //         outline: "JS基礎課程",
            //     }, {
            //         code: "J0311",
            //         outline: "JS基礎課程",
            //     }, {
            //         code: "J0312",
            //         outline: "JS基礎課程",
            //     }],//代表星期三
            //     [{
            //         code: "J0313",
            //         outline: "JS基礎課程",
            //     }, {
            //         code: "J0314",
            //         outline: "JS基礎課程",
            //     }, 
            //     null
            //     , {
            //         code: "J0316",
            //         outline: "JS基礎課程",
            //     }],//代表星期四
            //     [{
            //         code: "J0317",
            //         outline: "JS基礎課程",
            //     }, {
            //         code: "J0318",
            //         outline: "JS基礎課程",
            //     }, {
            //         code: "J0319",
            //         outline: "JS基礎課程",
            //     }, {
            //         code: "J0320",
            //         outline: "JS基礎課程",
            //     }],//代表星期五
            // ],
            mySchedule: [],
        }
    },
    mounted(){
        this.getSchedule();
    },
    methods:{
        getSchedule(){
            this.mySchedule = JSON.parse(sessionStorage.getItem("schedule"));
            console.table(this.mySchedule);
        },
        goToCourse(courseCode){
            this.$router.push({
                name: "Course",
                params: {
                    courseCode: courseCode,
                    identity: "student"
                }
            })
        },

    },

}
</script>

<template>
<!-- fontawesome用 -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<!-- fontawesome用 -->



    <div class="p-4 relative">

        <h2 class="text-2xl mb-4 pb-2 border-b-2 border-green-600 font-bold text-green-900">我的課表</h2>
        <div class="text-center box-border p-2 rounded-lg text-red-900 bg-red-50 min-w-full"
        id="schedule-table">
            <!-- 之後選的課程會存進MySQL，
            並且後端用JAVA操作 -->
            <div class="flex w-full justify-center items-center bg-red-400 font-bold text-white">
                <p class="w-1/6 bg-red-100 h-10 leading-10 border-b-2 border-white text-red-900">時段</p>
                <p class="w-1/6 h-10 leading-10 border-b-2 border-white">一</p>
                <p class="w-1/6 h-10 leading-10 border-b-2 border-l-2 border-white">二</p>
                <p class="w-1/6 h-10 leading-10 border-b-2 border-l-2 border-white">三</p>
                <p class="w-1/6 h-10 leading-10 border-b-2 border-l-2 border-white">四</p>
                <p class="w-1/6 h-10 leading-10 border-b-2 border-l-2 border-white">五</p>
            </div>
            <div class="flex w-full">
                <div class="flex flex-col w-1/6">
                    <p class="schedule-time bg-red-300 text-sm text-center border-r-2 border-white">
                        08:00 ~ 10:00
                    </p>
                    <p class="schedule-time bg-red-300 text-sm text-center border-r-2 border-white">
                        10:00 ~ 12:00
                    </p>
                    <p class="schedule-time bg-rose-200 text-sm text-center">
                        
                    </p>
                    <p class="schedule-time bg-red-300 text-sm text-center border-r-2 border-white">
                        14:00 ~ 16:00
                    </p>
                    <p class="schedule-time bg-red-300 text-sm text-center border-r-2 border-white">
                        16:00 ~ 18:00
                    </p>
                </div>
                <div 
                v-for="weekday in mySchedule"
                class="flex flex-col w-1/6">
                    <div v-for="(timeSlot, index) in weekday">
                        <div @click="goToCourse(timeSlot.code)"
                        v-if="timeSlot !== null"
                        class="w-full bg-red-200 text-sm schedule-item flex justify-center items-center flex-col rounded-md border-2 border-red-50 hover:underline cursor-pointer">
                            <p>{{ timeSlot.code }}</p>
                            <p v-if="timeSlot.outline.length > 5">{{ timeSlot.outline.slice(0, 6) + "..." }}</p>
                            <p v-else>{{ timeSlot.outline }}</p>
                        </div>
                        <p 
                        class="bg-red-50 schedule-item"
                        v-if="timeSlot === null"></p>
                        <p 
                        class="schedule-time w-full bg-rose-200 border-t-2 border-white" 
                        v-if="index == 1"></p>
                    </div>
                </div>
            </div>



        </div>
    </div>
</template>

<style lang='scss' scoped>
.schedule-time{
    height: 60px;
    line-height: 60px;
}
.schedule-time + .schedule-time{
    border-top: 2px solid white;
}
.schedule-item{
    height: 60px;
}
</style>