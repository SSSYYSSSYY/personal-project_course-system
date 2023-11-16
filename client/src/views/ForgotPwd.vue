<script>
import Banner from '../components/Banner.vue';
const API_URL = "http://localhost:8080";
export default{
    data() {
        return {
            id: "",
            email: "",

            isSendedVerify: false,
            encodedVerifyCode: null,

            inputVerifyCode: "",

            isCanChangePwd: false,

            newPwd: "",
            confirmPwd: "",
        }
    },
    components:{
        Banner,
    },
    props: [
        "identity"
    ],
    methods: {
        sendVerifyEmail(){
            console.log(this.identity)
            if(this.id == "" || this.email == ""){
                alert("請正確輸入資料！");
                return;
            }
            console.log(`id: ${this.id} email: ${this.email}`);
            const forgotPwdReq = {
                id: this.id,
                email: this.email,
            }
            fetch(`${API_URL}/forgot_pwd_${this.identity}`, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(forgotPwdReq),
            })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if(data.code == "404"){
                    alert("此帳號不存在！");
                    return;
                }
                if(data.code == "400"){
                    alert("電子信箱有誤！");
                    return
                }
                alert("驗證碼已寄送至您的信箱！");
                sessionStorage.setItem("id", data.id);
                sessionStorage.setItem("loginToken", data.loginToken);
                sessionStorage.setItem("sendTime", JSON.stringify(Date.now()));
                sessionStorage.setItem("status", "forgotPwd");
                this.encodedVerifyCode = data.encodedVerifyCode;
                this.isSendedVerify = true;
            });
        },
        verify(){
            if(this.inputVerifyCode == ""){
                alert("請輸入驗證碼！");
                return;
            }

            const checkVerifyCodeReq = {
                id: sessionStorage.getItem("id"),
                loginToken: sessionStorage.getItem("loginToken"),
                inputVerifyCode: this.inputVerifyCode,
                encodedVerifyCode: this.encodedVerifyCode,
            }

            if((Date.now() - JSON.parse(sessionStorage.getItem("sendTime")) >= (1000*60*3))){
                alert("此驗證碼已超時！");
                this.isSendedVerify = false;
                this.isCanChangePwd = false;
                this.inputVerifyCode = "";
                return;
            }

            fetch(`${API_URL}/check_verify_code`, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(checkVerifyCodeReq),
            })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if(data.code == "400" || data.code == "403"){
                    alert("驗證失敗！");
                    this.inputVerifyCode = "";
                    return;
                }
                if(data.code == "200"){
                    alert("驗證成功！");
                    this.isSendedVerify = false;
                    this.isCanChangePwd = true;
                    
                    return;
                }
            })

        },
        changePwdSubmit(){
            //檢查新密碼跟確認密碼是否相同
            if(this.newPwd == "" || this.confirmPwd == ""){
                alert("請正確輸入資料！");
                return;
            }
            const forgotPwdNewReq = {
                id: sessionStorage.getItem("id"),
                loginToken: sessionStorage.getItem("loginToken"),
                newPwd: this.newPwd,
            }

            fetch(`${API_URL}/change_pwd_after_verify_${this.identity}`, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(forgotPwdNewReq),
            })
            .then(response => response.json())
            .then(data => {
                if(data.code == "400"){
                    alert("新密碼與舊密碼不得相同！");
                    return;
                }
                if(data.code == "404"){
                    alert("帳號資料有誤！是不是偷改session？壞壞！");
                    return;
                }
                if(data.code == "200"){
                    alert("修改成功！請再次登入。");
                    sessionStorage.removeItem("id");
                    sessionStorage.removeItem("loginToken");
                    sessionStorage.removeItem("status");
                    if(this.identity == "admin"){
                        this.$router.push({
                            name: "AdminLogin"
                        });
                    }
                    if(this.identity == "student"){
                        this.$router.push({
                            name: "StudentLogin"
                        });
                    }

                }
            })
        },
        cancel(){
            const isUserCancel = confirm("按下後將會回到首頁！你確定要取消嗎？");
            if(isUserCancel){
                sessionStorage.removeItem("status");
                sessionStorage.removeItem("id");
                sessionStorage.removeItem("loginToken");
                sessionStorage.removeItem("sendTime");
                this.$router.push({
                    name: 'home',
                });
            }else{
                return;
            }
        }
    }
}
</script>

<template>
    <Banner />
    <div v-if="!isSendedVerify && !isCanChangePwd" 
    class="bg-blue-100 w-96 m-10 rounded-lg mx-auto text-blue-900 p-2 box-content">
        <div class="p-4 mx-auto flex justify-between">
            <label for="id">編號：</label>
            <input v-model="id"
            class="rounded w-56 text-right"
            type="text" id="id">
        </div>
        <div class="p-4 mx-auto flex justify-between">
            <label for="email">電子信箱：</label>
            <input v-model="email"
            class="rounded w-56 text-right"
            type="email" id="email">
        </div>
        <p class="w-80 mx-auto text-center text-sm">※請輸入與註冊時相同的電子信箱。</p>
        <button @click="sendVerifyEmail"
        class="text-white bg-blue-900 py-1 px-4 rounded-lg mx-auto block my-4"
        type="button">
            寄送驗證碼
        </button>
    </div>
    <div v-if="isSendedVerify"
    class="bg-blue-100 w-96 m-10 rounded-lg mx-auto text-blue-900 p-2 box-content"
    >
        <div class="p-4 mx-auto flex justify-between">
            <label for="verifyCode">輸入驗證碼：</label>
            <input class="rounded w-56 text-right"
            v-model="inputVerifyCode"
            type="text" id="verify">
        </div>
        <div class="flex justify-center">
            <button @click="verify"
            class="text-white bg-blue-900 py-1 px-4 rounded-lg  my-4"
            type="button">
                進行驗證
            </button>
            <button @click="cancel"
            class="text-white bg-gray-500 py-1 px-4 rounded-lg my-4 ml-6"
            type="button">
                取消
            </button>
        </div>

    </div>
    <div v-if="isCanChangePwd"
    class="bg-blue-100 w-96 m-10 rounded-lg mx-auto text-blue-900 p-2 box-content">
        <div class="p-4 mx-auto flex justify-between">
            <label for="newPwd">輸入新密碼：</label>
            <input v-model="newPwd"
            class="rounded w-56 text-right" 
            type="password" id="newPwd" minlength="8">
        </div>
        <div class="p-4 mx-auto flex justify-between">
            <label for="confirmPwd">確認新密碼：</label>
            <input v-model="confirmPwd"
            class="rounded w-56 text-right" 
            type="password" id="confirmPwd" minlength="8">
        </div>
        <p class="text-right text-sm text-red-800">※密碼需大於8個字元</p>
        <span class="block text-right my-2 text-sm text-red-700 absolute w-96"
        v-if="newPwd != confirmPwd"
        >
            密碼不相符！
        </span>
        <div class="flex justify-center">
            <button @click="changePwdSubmit"
            class="text-white bg-blue-900 py-1 px-4 rounded-lg my-4 "
            :disabled="newPwd != confirmPwd || newPwd == '' || confirmPwd == ''"
            :class="{'bg-gray-300': newPwd != confirmPwd || newPwd == '' || confirmPwd == ''}"
            type="button">
                確定修改
            </button>
            <button @click="cancel"
            class="text-white bg-gray-500 py-1 px-4 rounded-lg my-4 ml-6"
            type="button">
                取消
            </button>
        </div>

    </div>
</template>

<style lang='scss' scoped>

</style>