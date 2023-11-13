<script setup>
import { ref } from "vue";
import { login } from "@/stores/menu";

// 반응형을 유지하면서 스토어에서 속성을 추출하려면, storeToRefs()를 사용
// https://pinia.vuejs.kr/core-concepts/

const id = ref("");
const pw = ref("");
</script>

<template>
  <div>
    <input v-model="id" type="text" />
    <input v-model="pw" type="text" />
    <button @click="login(id, pw)">login</button>
  </div>
</template>

<style scoped></style>
