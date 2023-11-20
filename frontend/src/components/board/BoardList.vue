<script setup>
import { listArticle } from "@/api/board/boardApi"
import { onMounted, ref, reactive } from "vue";
import { useRouter } from "vue-router";
import VSelect from "@/components/common/VSelect.vue";
const router = useRouter();
const data = ref([])
//페이지정보
const page = ref({
      currentPage: 1,
      totalPageCount: 1,
})

//검색 파라미터
const param = {
 pgno : 1,
 spp : 10,
key : "article_no",
word : "",
sort : "article_no"
}
//검색 내용
const selectOption = ref([
  { text: "검색조건", value: "" },
  { text: "글번호", value: "article_no" },
  { text: "제목", value: "subject" },
  { text: "작성자아이디", value: "user_id" },
]);


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
const changeKey = (val) => {
  console.log("BoarList에서 선택한 조건 : " + val);
  param.key = val;
};
const moveWrite = () => {
  router.push({ name: "board_write" });
};
onMounted(() => {
    fetchArticles();
})
const getArticleList = () => {
  param.pgno = 1;
  console.log("서버에서 글목록 얻어오자!!!", param);
  listArticle(param, res => {
    console.log(res)
    data.value = res.data.data;
    page.value.totalPageCount = res.data.page.totalPageCount
  },
    err => {
      console.log(res)
    }
  );
};

</script>


<template>
  <!--검색창-->
  <v-row class="align-self-center mb-2">
    <v-col cols="12" md="2" class="text-start">
      <v-btn @click="moveWrite" outlined small color="primary">
        글쓰기
      </v-btn>
    </v-col>

    <v-col cols="12" md="5" offset-md="5">
      <v-form class="d-flex">
        <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />

        <v-text-field
          v-model="param.word"
          placeholder="검색어..."
          class="ms-1"
          solo-inverted
          outlined
          dense
        ></v-text-field>

        <v-btn @click="getArticleList" dark small color="dark" class="ms-1">
          검색
        </v-btn>
      </v-form>
    </v-col>
  </v-row>
  <!--게시글-->
      <v-list>
        <v-list-item v-for="article in data" :key="article.article_no">
            <router-link :to="`board/detail/${article.article_no}`" class="link">{{ article.subject }}</router-link>
            <v-list-item-content class="user_id">{{ article.user_id }}</v-list-item-content>
        </v-list-item>
      </v-list>
      <!--페이지-->
      <v-pagination v-model="page.currentPage" :length="page.totalPageCount" @click="fetchArticles"></v-pagination>
  </template>

<style scoped>

.v-list {
  background: #f9f9f9;
  border-radius: 5px;
  margin: 10px;
  padding: 20px;
}

.v-list-item {
  border-bottom: 1px solid #ddd;
  padding: 10px 0;
}

.v-list .link {
  border-bottom: none;
}

.v-list .link {
  color: #333;
  text-decoration: none;
}

.v-list .link:hover {
  color: #007BFF;
}
.v-list .user_id{
  float : right;
}
.v-pagination {
  display: flex;
  justify-content: center;
  padding: 20px 0;
}

</style>