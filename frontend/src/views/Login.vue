<script setup>
import { ref } from "vue"
import { memberJwtLogin } from "@/util/member/memberApi";
const id = ref("");
const pw = ref("");


function loginHandler() {
    let body = {id: id.value, pw: pw.value};
    alert(id.value + ": " + pw.value);
    memberJwtLogin(body, res => {
        console.log(res);
    },
        err => {
            console.log(err);
    }
    )
}
</script>

<template>
    <div>
        <input type="text" v-model="id">
        <input type="text" v-model="pw">
        <button @click="loginHandler">login</button>
    </div>
</template>

<style scoped>

</style>