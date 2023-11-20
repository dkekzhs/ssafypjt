<script setup>
import { ref } from "vue";
import { registArticle } from "@/api/board/boardApi";
import { useRouter } from "vue-router";
const data = ref({ "subject": "", "content": "" });
const router = useRouter();
function createArticle() {
  if (data.value.subject == "" || data.value.content == "") {
    alert("제목과 내용을 입력하세요");
    return;
  }
  console.log(data.value.subject + " " + data.value.content);
    registArticle(data.value, res => {
      console.log(res);
      alert(res.data.message);
      router.push("/");
    }, err => {
      console.log(err);
      alert(res.data.message);
    })
}
</script>
<!-- 글작성은 로그인 된 사용자만 보여야한다. 혹시 로그인 하지 않은 사용자가 요청을 보낼 경우 서버에서는 로그인해주라는 메시지를 받아야한다.-->
<template>
    <v-container>
      <v-row justify="center">
        <v-col cols="12">
          <h2 class="my-3 py-3 shadow-sm bg-light text-center">
            <v-mark class="sky">글쓰기</v-mark>
          </h2>
        </v-col>
        <v-col cols="12">
          <v-row>
            <v-col cols="12" md="6">
              <v-text-field
                v-model="data.subject"
                label="제목"
                placeholder="제목..."
                outlined
                class="mb-3"
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="12">
              <v-textarea
                v-model="data.content"
                label="내용"
                rows="7"
                outlined
                class="mb-3"
              ></v-textarea>
            </v-col>
          </v-row>
          <v-row>
            <v-col class="text-center" cols="12">
              <v-btn @click="createArticle" class="btn-outline-primary mb-3">
                글작성
              </v-btn>
              <v-btn @click="resetForm" class="btn-outline-danger mb-3">
                초기화
              </v-btn>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </template>
<style scoped>
</style>