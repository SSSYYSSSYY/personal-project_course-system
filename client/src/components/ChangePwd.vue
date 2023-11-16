<script>
const API_URL = "http://localhost:8080";

export default{
    data() {
        return {
            id: null,
            oldPwd: "",
            newPwd: "",
            confirmPwd: "",
        }
    },
    props: [
        "identity"
    ],
    mounted(){
        this.id = sessionStorage.getItem("id");
    },
    methods:{
        changePwd(){

            if(this.oldPwd == "" || this.newPwd == "" || this.confirmPwd == ""){
                alert("請正確輸入資料！");
                return;
            }

            const changePwdReq = {
                id: this.id,
                oldPwd: this.oldPwd,
                newPwd: this.newPwd,
            }

            fetch(`${API_URL}/${this.identity}/change_pwd`, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(changePwdReq),
            })
            .then(response => response.json())
            .then(data => {
                if(data.code == "400"){
                    alert("密碼輸入錯誤！");
                    this.oldPwd = "";
                    this.newPwd = "";
                    this.confirmPwd = "";
                    return;
                }
                if(data.code == "200"){
                    alert("密碼修改成功！請重新登入。");
                    sessionStorage.removeItem("id");
                    sessionStorage.removeItem("loginToken");
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
        back(){
            this.$emit("back", false);
        }
    }
}
</script>

<template>
    <div class="p-4">
        <h2 class="text-2xl mb-4 pb-2 border-b-2 border-green-600 font-bold text-green-900">修改密碼</h2>
        <div class="w-80 mx-auto p-4 relative">
            <div class="flex justify-between">
                <label for="oldPwd">輸入當前密碼：</label>
                <input v-model="oldPwd"
                class="rounded text-right border-2 border-green-200"
                type="password" name="oldPwd" id="oldPwd">
            </div>
            <div class="flex justify-between my-6">
                <label for="newPwd">輸入新密碼：</label>
                <input v-model="newPwd"
                class="rounded text-right border-2 border-green-200" minlength="8"
                type="password" name="newPwd" id="newPwd">
            </div>
            <div class="flex justify-between">
                <label for="confirmPwd">確認新密碼：</label>
                <input v-model="confirmPwd"
                class="rounded text-right border-2 border-green-200" minlength="8"
                type="password" name="confirmPwd" id="confirmPwd">
            </div>
            <p class="text-right text-sm text-red-800">※密碼需大於8個字元</p>
            <span class="block text-right my-2 text-sm text-red-700 absolute right-4"
            v-show="newPwd != confirmPwd">
                密碼不相符！
            </span>
            <div class="flex justify-center">
                <button @click="changePwd"
                class=" bg-green-800 text-white rounded-md py-1 text-sm p-4 text-center my-12 text-lg"
                :disabled="newPwd != confirmPwd || oldPwd == '' || newPwd == '' || confirmPwd == ''"
                :class="{'bg-gray-300': newPwd != confirmPwd || oldPwd == '' || newPwd == '' || confirmPwd == ''}"
                type="button">
                確定修改密碼
                </button>
                <button @click="back"
                class=" bg-slate-500 text-white rounded-md py-1 text-sm p-4 text-center my-12 text-lg ml-4"
                type="button">取消</button>
            </div>

        </div>
    </div>
</template>

<style lang='scss' scoped>

</style>