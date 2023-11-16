<script>
export default{
    data() {
        return {
            
        }
    },
    methods:{
        callModalHandler(){
            this.$emit("closeModal");
        }
    }
}
</script>

<template>
    <div class="w-screen h-screen modal flex justify-center items-center fixed top-0 left-0">
        <div class="border-2 fixed z-10 bg-white rounded-lg w-1/2 p-8">
            <slot></slot>
            <p class="absolute top-0 right-2 text-2xl text-slate-600 cursor-pointer"
            @click="callModalHandler">×</p>
        </div>
        <div class="bg-gray-800 opacity-40 w-screen h-screen"></div>
    </div>
</template>

<style lang='scss' scoped>

</style>