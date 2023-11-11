<script setup>
import { ref } from "vue";
import { memberLogin } from "@/util/member/memberApi"


const id = ref("");
const pw = ref("");

function login() {
    memberLogin({ "user_id": id, "user_password": pw.value }
        , res => {
            console.log(res)
        }
        , err => {
            console.log(err)
        }
    );

}


</script>

<template>
    <div>
        <input v-model="id" type="text"/>
        <input v-model="pw" type="text"/>
        <button @click="login">login</button>
    </div>
</template>

<style scoped>

</style>