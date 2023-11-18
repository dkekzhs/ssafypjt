<script setup>
import {  computed, ref , onMounted} from 'vue'
import { detailArticle } from "@/api/board/boardApi"
import { useRoute } from 'vue-router'

const board = ref("");
const comments = ref([]); 

const route = useRoute()
const id = route.params.id;
onMounted(() => {
    fetchDetails(id);
})

function fetchDetails(id){
    detailArticle(id , res => {
        console.log(res);
        board.value = res.data.board;
        comments.value = res.data.comments;
    },
        err => {
            console.log(err);      
    })
}
const sortedComments = computed(() => {
      const commentsWithLevel = comments.value.map((comment) => ({
        ...comment,
        level: getCommentLevel(comment.comment_id),
      }))
      return commentsWithLevel.sort((a, b) => a.comment_id - b.comment_id)
    })

    function getCommentLevel(commentId) {
      let level = 1
      let comment = comments.value.find((c) => c.comment_id === commentId)
      while (comment && comment.parent_comment_id !== 0) {
        level++
        comment = comments.value.find((c) => c.comment_id === comment.parent_comment_id)
      }
      return level
    }
</script>

<template>
    <div>
      <h2>{{ board.subject }} - {{ board.user_name }}</h2>
      <p>{{ board.content }}</p>
      <div v-for="comment in sortedComments" :key="comment.comment_id" :style="{ marginLeft: `${(comment.level - 1) * 20}px` }">
        <p>{{ comment.content }} - {{ comment.user_id }}</p>
      </div>
    </div>
  </template>

<style scoped>

</style>