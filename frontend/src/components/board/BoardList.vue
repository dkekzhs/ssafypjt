<script setup>
import { listArticle } from "@/api/board/boardApi"
import { onMounted, ref , reactive } from "vue";
const data = ref([])
const page = reactive({
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
    listArticle(param, res => {
        console.log(res)
        data.value = res.data.data;
        page.totalPageCount = res.data.page.totalPageCount
    },
        err => {
            console.log(res)
        }
    )
}
const prevPage = () => {
      if (page.currentPage > 1) {
          page.currentPage--
          param.pgno = page.currentPage;
        fetchArticles()
      }
}
const nextPage = () => {
      if (page.currentPage < page.totalPageCount) {
          page.currentPage++
          param.pgno = page.currentPage;
        fetchArticles()
      }
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
      <div class="pagination">
        <button @click="prevPage" :disabled="page.currentPage <= 1">이전</button>
        <span>{{ page.currentPage }} / {{ page.totalPageCount }}</span>
        <button @click="nextPage" :disabled="page.currentPage >= page.totalPageCount">다음</button>
      </div>
    </div>
  </template>

<style scoped>
</style>