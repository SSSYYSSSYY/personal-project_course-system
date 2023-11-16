<script>
import ChangePwd from '../components/ChangePwd.vue';
const API_URL = "http://localhost:8080";

export default {
    data() {
        return {
            isEdit: false,

            nameForEdit: "",
            emailForEdit: "",
            birthdayForEdit: null,

            isGoToChangePwd: false,
        }
    },
    components:{
        ChangePwd,
    },
    mounted(){
        this.nameForEdit = this.name;
        this.emailForEdit = this.email;
        this.birthdayForEdit = String(this.birthday).slice(0, 10);
    },
    props: [
        "name", "id", "email", "birthday", "identity", "isEnable"
    ],
    methods:{
        edit(){
            this.isEdit = true;
        },
        submit(){
            if(!this.emailForEdit.includes("@") || this.nameForEdit.length < 1){
                alert("資料格式有誤！");
                return;
            }
            // 這裡把修改後的資料送出
            const editInfoReq = {
                identity: this.identity,
                id: this.id,
                name: this.nameForEdit,
                email: this.emailForEdit,
                birthday: new Date(this.birthdayForEdit),
            }
            console.log(editInfoReq)
            fetch(`${API_URL}/edit_info`, {
                method: "PATCH",
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(editInfoReq),
            })
            .then(response => response.json())
            .then(data => {
                if(data.code == "200"){
                    alert("修改成功！");
                    this.isEdit = false;
                    location.reload();
                    return;
                }
            })
            
        },
        goToChangePwdHandler(){
            this.isGoToChangePwd = !this.isGoToChangePwd;
        }
    }
}
</script>

<template>
    <div v-if="isEnable && !isGoToChangePwd" class="p-4">
        <h2 class="text-2xl mb-4 pb-2 border-b-2 border-green-600 font-bold text-green-900">個人資料一覽</h2>
        <!-- 等串接後端之後，input要加上v-model -->
        <div class="mx-auto mb-4 w-96">
            <div class="flex justify-between">
                <label for="name">姓名：</label>
                <input v-model="nameForEdit"
                :disabled="!isEdit" minlength="1"
                class="text-right rounded-md pr-1 w-3/5"
                :class="{'text-green-800': !isEdit}"
                type="text" name="name" id="name">
            </div>
            <div v-if="identity === 'student'"
            class="my-4 flex justify-between">
                <label for="studentId">學號：</label>
                <span
                class="text-right rounded-md pr-1 text-green-800"
                type="text" name="studentId" id="studentId"
                disabled>{{ id }}</span>
                <!-- 學號不可修改，固定為disabled -->
            </div>
            <div v-if="identity === 'admin'"
            class="my-4 flex justify-between">
                <label for="adminId">編號：</label>
                <span
                class="text-right rounded-md pr-1 text-green-800"
                type="text" name="adminId" id="adminId"
                disabled>{{ id }}</span>
                <!-- 編號不可修改，固定為disabled -->
            </div>
            <div class="my-4 flex justify-between">
                <label for="email">Email：</label>
                <input v-model="emailForEdit"
                :disabled="!isEdit"
                class="text-right rounded-md w-4/5"
                :class="{'text-green-800': !isEdit}"
                type="email" name="email" id="email">

            </div>
            <div class="flex justify-between">
                <label for="birthday">出生年月日：</label>
                <input v-model="birthdayForEdit"
                :disabled="!isEdit"
                class="text-right rounded-md w-3/5"
                :class="{'text-green-800': !isEdit}"
                type="date" name="birthday" id="birthday">
            </div>

        </div>
        <div 
        class="flex justify-center items-center w-full my-5 "
        v-if="isEdit">
            <!-- 只有在處於編輯狀態時，才顯示送出資料的按鈕 -->
            <button @click="submit"
            class="bg-green-700 text-white rounded-md py-1 text-sm duration-100 hover:bg-green-600 px-4"
            type="button">送出</button>
        </div>
        <div 
        class="flex justify-center items-center w-full my-5 "
        v-if="!isEdit">
            <!-- 只有在非處於編輯狀態時，才顯示修改按鈕 -->
            <button @click="edit"
            class="w-32 bg-green-700 text-white rounded-md py-1 text-sm duration-100 hover:bg-green-600"
            type="button">修改個人資料</button>
            <button @click="goToChangePwdHandler"
            class="w-24 bg-red-700 text-white rounded-md py-1 text-sm duration-100 ml-4 hover:bg-red-600"
            type="button">修改密碼</button>
        </div>

    </div>
    <ChangePwd :identity="identity"
    v-if="!isEnable || isGoToChangePwd" 
    @back="goToChangePwdHandler"/>
</template>

<style lang='scss' scoped>

</style>