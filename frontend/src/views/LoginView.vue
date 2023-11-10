<script setup>
import { ref } from "vue";
import axios from "axios";


const id = ref("");
const pw = ref("");

const $axios = axios.create({
    baseURL: "http://localhost/enjoytrip",
    headers: {
        "Content-Type": "application/json;charset=utf-8",
    },

});





function login() {
    console.log(id.value, pw.value);
    $axios
        .post("/user/login", { user_id: id.value, user_password: pw.value }
        ,    {
    withCredentials: true,
  })
    .then((data) => {
      console.log(data);
     })
    .catch((err) => {
      console.log(err);
    });

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