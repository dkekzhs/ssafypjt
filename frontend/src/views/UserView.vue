<script setup>
import { ref } from "vue";
import { memberLogin } from "@/util/member/memberApi";

const id = ref("");
const pw = ref("");

const message = ref("");
const username = ref("");

function login() {
  memberLogin(
    { user_id: id.value, user_password: pw.value },
    (res) => {
      console.log(res.data.message);
      console.log(res.data.user_name);
      message.value = res.data.message;
      username.value = res.data.user_name;
    },
    (err) => {
      message.value = err.response;
      username.value = null;
      console.log(err);
    }
  );
}
</script>

<template>
  <div>
    <input v-model="id" type="text" />
    <input v-model="pw" type="text" />
    <button @click="login">login</button>
  </div>
</template>

<style scoped></style>
