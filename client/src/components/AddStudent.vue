<script>
const API_URL = "http://localhost:8080";
export default{
    data() {
        return {
            newStudentName: null,
            newStudentId: null,
            newStudentPwd: null,
            newStudentBirthday: null,
            newStudentEmail: null,
        }
    },
    methods:{
        addStudent(){
            if(this.newStudentName === null ||
            this.newStudentId === null ||
            this.newStudentPwd === null ||
            this.newStudentBirthday === null){
                alert("請正確輸入資料！");
            }else{
                const studentInfo = {
                    id: this.newStudentId,
		            pwd: this.newStudentPwd,
		            name: this.newStudentName,
		            email: this.newStudentEmail,
		            birthday: new Date(this.newStudentBirthday),
                }
                fetch(`${API_URL}/add_student`, {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(studentInfo),
                })
                .then(response => response.json())
                .then(data => {
                    if(data.code == "400"){
                        alert("此學號已被使用！");
                        return;
                    }
                    if(data.code == "200"){
                        alert("新增成功！");
                        this.newStudentId = null;
		                this.newStudentPwd = null;
		                this.newStudentName = null;
		                this.newStudentEmail = null;
                        return;
                    }
                })
            }
        }
    }
}
</script>

<template>
    <div class="p-4">
        <h2 class="text-2xl mb-4 pb-2 border-b-2 border-green-600 font-bold text-green-900">人員管理：新增學生</h2>
        <div class="w-96 mx-auto">
            <div class="my-8 text-green-900 ">
                <div class="flex justify-between my-2">
                    <label for="newStudentName">姓名：</label>
                    <input v-model="newStudentName"
                    class="rounded-md border-2 border-green-200 w-3/5 text-right"
                    type="text" id="newStudentName">
                </div>
                <div class="flex justify-between my-2">
                    <label for="newStudentId">學號：</label>
                    <input v-model="newStudentId"
                    class="rounded-md border-2 border-green-200 w-3/5 text-right"
                    type="text" id="newStudentId">
                </div>
                <div class="flex justify-between my-2">
                    <label for="newStudentPwd">密碼：</label>
                    <input v-model="newStudentPwd"
                    class="rounded-md border-2 border-green-200 w-3/5 text-right"
                    type="text" id="newStudentPwd" minlength="8">
                </div>
                <p class="text-right text-sm text-red-800">※密碼需大於8個字元</p>
                <div class="flex justify-between my-2">
                    <label for="newStudentEmail">Email：</label>
                    <input v-model="newStudentEmail"
                    class="rounded-md border-2 border-green-200 w-4/5 text-right"
                    type="email" id="newStudentEmail">
                </div>
                <div class="flex justify-between my-2">
                    <label for="newStudentBirthday">出生年月日：</label>
                    <input v-model="newStudentBirthday"
                    class="rounded-md border-2 border-green-200 w-3/5 text-right"
                    type="date" id="newStudentBirthday">
                </div>
            </div>
            <button @click="addStudent"
            class="w-32 bg-green-800 text-white py-1 rounded-md mx-auto block duration-100 hover:bg-green-700"
            type="button">確定新增</button>
        </div>
    </div>
</template>

<style lang='scss' scoped>

</style>