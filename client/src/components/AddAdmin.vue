<script>
const API_URL = "http://localhost:8080";


export default{
    data() {
        return {
            newAdminPermission: null,
            newAdminName: null,
            newAdminId: null,
            newAdminPwd: null,
            newAdminBirthday: null,
            newAdminEmail: null,
        }
    },
    methods:{
        addAdmin(){
            if(this.newAdminPermission === null ||
            this.newAdminName === null ||
            this.newAdminId === null ||
            this.newAdminPwd === null ||
            this.newAdminBirthday === null || this.newAdminPwd.length < 8){
                alert("請正確輸入資料！");
            }else{
                const newAdminInfo = {
                    id: this.newAdminId,
                    pwd: this.newAdminPwd,
                    name: this.newAdminName,
                    email: this.newAdminEmail,
                    permission: Number(this.newAdminPermission),
                    birthday: new Date(this.newAdminBirthday),
                }

                fetch(`${API_URL}/add_admin`, {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(newAdminInfo),
                })
                .then(response => response.json())
                .then(data => {
                    if(data.code == "400"){
                        alert("此編號已被使用！");
                        return;
                    }
                    if(data.code == "200"){
                        alert("新增成功！");
                        this.newAdminPermission = null;
                        this.newAdminName = null;
                        this.newAdminId = null;
                        this.newAdminPwd = null;
                        this.newAdminBirthday = null;
                        this.newAdminEmail = null;
                        return;
                    }
                })
                .catch(e => {
                    console.error(e);
                })
            }

        }
    }
}
</script>

<template>
    <div class="p-4">
        <h2 class="text-2xl mb-4 pb-2 border-b-2 border-green-600 font-bold text-green-900">人員管理：新增管理員</h2>
        <div class="w-96 mx-auto">
            <div class="text-center flex justify-around text-green-900 my-6">
                <div>
                    <input type="radio" name="adminType" id="superAdmin" value="0" v-model="newAdminPermission">
                    <label for="superAdmin">超級管理員</label>
                </div>
                <div>
                    <input type="radio" name="adminType" id="normalAdmin" value="1" v-model="newAdminPermission">
                    <label for="normalAdmin">普通管理員</label>
                </div>
            </div>
            <div class="my-8 text-green-900">
                <div class="flex justify-between my-2">
                    <label for="newAdminName">姓名：</label>
                    <input v-model="newAdminName"
                    class="rounded-md border-2 border-green-200
                     text-right w-3/5"
                    type="text" id="newAdminName">
                </div>
                <div class="flex justify-between my-2">
                    <label for="newAdminId">編號：</label>
                    <input v-model="newAdminId"
                    class="rounded-md border-2 border-green-200
                    text-right w-3/5"
                    type="text" id="newAdminId">
                </div>
                <div class="flex justify-between my-2">
                    <label for="newAdminPwd">密碼：</label>
                    <input v-model="newAdminPwd"
                    class="rounded-md border-2 border-green-200
                    text-right w-3/5"
                    type="text" id="newAdminPwd" minlength="8">
                </div>
                <p class="text-right text-sm text-red-800">※密碼需大於8個字元</p>
                <div class="flex justify-between my-2">
                    <label for="newAdminEmail">Email：</label>
                    <input v-model="newAdminEmail"
                    class="rounded-md border-2 border-green-200
                    text-right w-4/5"
                    type="email" id="newAdminEmail">
                </div>
                <div class="flex justify-between my-2">
                    <label for="newAdminBirthday">出生年月日：</label>
                    <input v-model="newAdminBirthday"
                    class="rounded-md border-2 border-green-200 
                    w-3/5 text-right"
                    type="date" id="newAdminBirthday">
                </div>
            </div>
            <button @click="addAdmin"
            class="w-32 bg-green-800 text-white py-1 rounded-md mx-auto block duration-100 hover:bg-green-700"
            type="button">確定新增</button>
        </div>
    </div>
</template>

<style lang='scss' scoped>

</style>