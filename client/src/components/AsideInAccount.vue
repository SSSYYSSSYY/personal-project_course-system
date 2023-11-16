<script>
import { RouterLink } from 'vue-router';

export default {
    data() {
        return {
            initial: "",
        }
    },
    props: [
        "name", "id", "createDate", "isEnable"
    ],
    mounted(){
        this.initial = this.name.slice(0, 2);
        this.changeIconColor("red");
    },
    methods:{
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
        logout(){
            sessionStorage.removeItem("id");
            sessionStorage.removeItem("loginToken");
            sessionStorage.removeItem("permission");
            sessionStorage.removeItem("schedule");
            sessionStorage.removeItem("progressList");
            alert("您已登出！畫面將跳轉至首頁。");
            this.$router.push({
                name: "home"
            })
        },
        backHome(){
            this.$emit("backHome")
        },
        changeIconColor(color){
            const iconEl = document.querySelector(".icon");
            iconEl.classList.remove("bg-red-800");
            iconEl.classList.remove("bg-green-800");
            iconEl.classList.remove("bg-violet-800");
            iconEl.classList.remove("bg-slate-800");

            iconEl.classList.add(`bg-${color}-800`);
        }
    }
}
</script>

<template>
    <aside class="bg-sky-100 p-4 rounded-md mr-4">
        <div class="border-white mb-4 rounded-full h-36 w-36 flex items-center justify-center icon">
            <p class="text-4xl font-bold text-white">{{ initial }}</p>
        </div>
        <div class="my-4 p-2 bg-white rounded-md flex justify-around items-center">
            <button @click="changeIconColor('red')"
            class="bg-red-800 w-4 h-4 rounded-full" type="button"></button>
            <button @click="changeIconColor('green')"
            class="bg-green-800 w-4 h-4 rounded-full" type="button"></button>
            <button @click="changeIconColor('violet')"
            class="bg-violet-800 w-4 h-4 rounded-full" type="button"></button>
            <button @click="changeIconColor('slate')"
            class="bg-slate-800 w-4 h-4 rounded-full" type="button"></button>
        </div>
        <div>
            <h2 class="mb-2 text-sm">
                姓名：
                <br>
                <span class="text-lg">
                    {{ name }}
                </span>
            </h2>
            <p class="mb-2 text-sm">
                編號：
                <br>
                <span class="text-lg">
                    {{ id }}
                </span>
            </p>
            <p class="mb-2 text-sm">
                註冊時間：
                <br>
                <span class="text-lg">
                    {{ timeFormatter(createDate) }}
                </span>

            </p>
            <p class="mb-2 text-sm">
                帳號狀態：
                <br>
                <span v-if="isEnable"
                class="text-lg">
                    已啟用
                </span>
                <span v-if="!isEnable"
                class="text-lg">
                    未啟用
                </span>

            </p>
            <button type="button" @click="backHome"
            class="bg-sky-800 text-white py-1 px-2 rounded-md mx-auto block w-24 text-center my-3"
            >
                回個人首頁
            </button>
            <button type="button" @click="logout"
            class="bg-red-800 text-white py-1 px-2 rounded-md mx-auto block w-24 text-center my-6">
                登出
            </button>
        </div>
    </aside>
</template>

<style lang='scss' scoped>

</style>