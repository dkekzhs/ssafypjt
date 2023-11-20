<script setup>
import { ref } from "vue";
import { login } from "@/stores/menu";

// 반응형을 유지하면서 스토어에서 속성을 추출하려면, storeToRefs()를 사용
// https://pinia.vuejs.kr/core-concepts/

const id = ref("");
const pw = ref("");
</script>

<template>
  <v-container fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card>
          <v-card-title class="headline">로그인</v-card-title>
          <v-card-text>
            <v-form>
              <v-text-field v-model="id" label="아이디" outlined></v-text-field>
              <v-text-field v-model="pw" label="비밀번호" type="password" outlined></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-btn @click="login(id,pw)" color="primary">로그인</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped></style>
