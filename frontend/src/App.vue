<script setup>
import { RouterView } from "vue-router";
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
import { useSocketStore } from "@/api/chat/socket";
import { watch } from "vue";
const socketState = useSocketStore();
watch(
  () => socketState.isConnected,
  (newValue) => {
    if (newValue === false) {
      // 소켓이 끊겼다는 메시지를 보내는 로직을 구현합니다.
      alert('소켓이 끊겼습니다.');
    }
  }
);
</script>

<template>
  <v-app>
    <TheHeadingNavbar></TheHeadingNavbar>
    <v-content>
    <router-view></router-view>
  </v-content>
  </v-app>
</template>

<style scoped></style>