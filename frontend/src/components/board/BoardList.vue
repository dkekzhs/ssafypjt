<script setup>
import { listArticle } from "@/api/board/boardApi"
import { onMounted, ref , reactive } from "vue";
const data = ref([])
const page = ref({
      currentPage: 1,
      totalPageCount: 1,
    })
const param = {
 pgno : 1,
 spp : 10,
key : "article_no",
word : "",
sort : "article_no"
}
const board_id = ref(0);
function fetchArticles() {
    console.log("??")
    param.pgno = page.value.currentPage;
    listArticle(param, res => {
        console.log(res)
        data.value = res.data.data;
        page.value.totalPageCount = res.data.page.totalPageCount
    },
        err => {
            console.log(res)
        }
    )
}


onMounted(() => {
    fetchArticles();
})

</script>
<template>
    <div>
      <h2>게시글 목록</h2>
      <ul>
        <li v-for="article in data" :key="article.article_no">
          <router-link :to="`board/detail/${article.article_no}`">{{ article.subject }} - {{ article.user_name }}</router-link>
        </li>
      </ul>
      <v-pagination v-model="page.currentPage" :length="page.totalPageCount" @click="fetchArticles"></v-pagination>
    </div>
  </template>

<style scoped>
</style>