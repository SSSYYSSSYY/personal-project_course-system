<script>
import Stomp from "stompjs";

const API_URL = "http://localhost:8080";

export default{
    data() {
        return {
            stompClient: null,
            msgs: [],

            msgInput: "",
            currentUser: null,

            formattedTimeStr: "",
        }
    },
    props: [
        "courseCode", "isShowChat", "identity"
    ],
    created(){
        this.currentUser = sessionStorage.getItem("id");
        this.connectWs();
        this.getAllMsg();
    },
    mounted(){
        
    },
    methods: {
        connectWs(){
            this.stompClient = Stomp.client("ws://localhost:8080/chat");

            this.stompClient.connect({}, () => {

                console.log("連接到WS通道")

                this.stompClient.subscribe(`/topic/chat/${this.courseCode}`, (msg) => {
                    console.log("接收到訊息");
                    this.msgs.push(JSON.parse(msg.body))
                });
            });

            
        },
        sendMsg(){
            if(this.msgInput == ""){
                alert("請輸入訊息！");
                return;
            }

            const msg = {
                courseCode: this.courseCode,
		        senderId: sessionStorage.getItem("id"),
		        senderIdentity: this.identity,
		        content: this.msgInput,
            }

            this.stompClient.send(`/app/chat/${this.courseCode}`, {}, JSON.stringify(msg));

            this.msgInput = "";
        },
        getAllMsg(){
            fetch(`${API_URL}/get_msg_by_course_code/${this.courseCode}`)
            .then(response => response.json())
            .then(data => {
                this.msgs = data;
            })
        },
        //調整訊息時間的格式
        timeFormatter(datetimeStr){
            const date = new Date(datetimeStr);

            const currentDatetime = new Date();
            // console.log(currentDatetime);

            let hr = date.getHours();
            let min = date.getMinutes();
            if(hr < 10){
                hr = `0${hr}`;
            }
            if(min < 10){
                min = `0${min}`;
            }

            if((currentDatetime - date) / (1000 * 60 * 60) >= 24){
                return `${date.getMonth() + 1}/${date.getDate()}\n${hr}:${min}`
            }else{
                return `${hr}:${min}`;
            }
        },
    },
}
</script>

<template>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" crossorigin="anonymous" referrerpolicy="no-referrer" />

<div class="duration-200 bg-blue-50 absolute -top-80 w-64 rounded-md shadow-lg shadow-slate-400"
:class="{'-translate-x-24': isShowChat, 'translate-x-48': !isShowChat}">
    <div class="msgs h-64 bg-blue-100 rounded-tl-md rounded-tr-md p-2">
        <!-- 顯示訊息 -->
        <div class="box-border flex items-center relative max-w-xs"
        :class="{'justify-start': msg.senderId != currentUser, 'justify-end': msg.senderId == currentUser, 'my-8': msg.senderId != currentUser, 'my-4': msg.senderId == currentUser}"
        v-for="(msg, index) in msgs" :key="index">
            <p v-if="msg.senderId != currentUser"
            class="absolute text-xs -top-5 left-5">{{ msg.senderId }}</p>
            <div class="flex items-center">
                <i v-if="msg.senderIdentity.includes('Admin') && msg.senderId != currentUser"
                class="fa-solid fa-user-gear mr-1 text-xl"></i>
                <i v-if="msg.senderIdentity == 'student' && msg.senderId != currentUser"
                class="fa-solid fa-graduation-cap mr-1 text-xl"></i>
                <p class="p-1 rounded-md text-blue-900 text-sm break-all"
                :class="{'bg-white': msg.senderId == currentUser, 'bg-slate-500': msg.senderId != currentUser, 'text-white': msg.senderId != currentUser}">{{ msg.content }}</p>
                <span style="white-space: pre-line;"
                class="msgTime mx-1"
                :class="{'order-first': msg.senderId == currentUser}">
                {{ timeFormatter(msg.sendTime) }}
                </span>
            </div>
            
        </div>
        
    </div>
    <div class="flex w-full justify-around p-2">
        <!-- 輸入訊息 -->
        <input v-model="msgInput"
        class="rounded-md border-2 border-slate-200" required
        type="text" id="msgInput">
        <label @click="sendMsg"
        for="msgInput">
            <i class="fa-regular fa-paper-plane text-slate-800"></i>
        </label>
    </div>
</div>
    
</template>

<style lang='scss' scoped>

.msgs{
    overflow-y: scroll;
}
.msgTime{
    font-size: 10px;
}
</style>