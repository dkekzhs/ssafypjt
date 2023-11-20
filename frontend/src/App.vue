<script setup>
import { RouterView } from "vue-router";
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
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