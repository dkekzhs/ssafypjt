<script setup>
import { listArticle, detailArticle } from "@/util/board/boardApi"
import { ref } from "vue";
const param = {
 pgno : 1,
 spp : 10,
key : "article_no",
word : "",
sort : "article_no"
}
const board_id = ref(0);
function getList() {
    listArticle(param, res => {
        console.log(res)
    },
        err => {
            console.log(res)
        }
    )
}
function get() {
    detailArticle(board_id.value, res => {
        console.log(res);
    }, err => {
        console.log(err);
    })
}


</script>

<template>
    <h1>보드 리스트 페이지</h1>
    <button @click="getList">글 가져오기 </button>
    <input type="text" v-model="board_id">
    <button @click="get">상세보기 </button>
</template>

<style scoped>
</style>