<script setup>
import {  computed, ref , onMounted, watch} from 'vue'
import { detailArticle, likeBoard } from "@/api/board/boardApi"

import {  addComment } from "@/api/board/commentApi"
import { useRoute } from 'vue-router'

const board = ref("");
const comments = ref([]); 
const editFlag = ref(false);
const route = useRoute()
const id = route.params.id;
const like = ref({});
const user_id = ref("");
const commentInput = ref([]); 
const comments_content = ref("");
onMounted(() => {
    fetchDetails(id);
})

function fetchDetails(id){
    detailArticle(id , res => {
        console.log(res);
        board.value = res.data.board;
      comments.value = res.data.comments;
      editFlag.value = res.data.edit;
      like.value = res.data.likes;
      user_id.value = res.data.user_id;
    },
        err => {
            console.log(err);      
    })
}
function setLike(status) {
  likeBoard({
    article_no: board.value.article_no
    , like_status: status
  }, res => {
    console.log(res);
    if (like.value.like_status == 1) {
      like.value.like_status = -1;
      like.value.like_count = like.value.like_count - 1;
      like.value.dislike_count = like.value.dislike_count + 1;
    }
    else {
      like.value.like_status = 1;
      like.value.dislike_count = like.value.dislike_count - 1;
      like.value.like_count = like.value.like_count + 1;
     }
  }
    , err => {
      console.log(err);
  }
  )
}
watch(() => like.value.like_status, (newStatus) => {
    // 변경된 상태에 따라 UI 갱신 로직을 수행
    console.log("like.like_status가 변경되었습니다. 새로운 상태:", newStatus);
    like.value.like_status = newStatus;

});
function setComment(parent_id, root_id, article_no) {
  const content = this.commentInput[parent_id];
  console.log(content);
  addComment({
    "article_no": article_no, "parent_comment_id": parent_id,
    "root_id": root_id, content: content
  },
    res => {
      console.log(res);
    },
    err => {
      console.log(err);
    }
  )
  
}
function rootComment() {

  addComment({
    content: comments_content.value,
    article_no : board.value.article_no
  },
    res => {
      console.log(res);
    },
    err => {
      console.log(err);
    }
  )
  
}

</script>

<template>
  <div class="article-container">
    <h2 class="article-title">{{ board.subject }} - {{ board.user_name }}</h2>
    <p class="article-content">{{ board.content }}</p>

    <div v-if="editFlag" class="edit-button">
      <button>글 수정하러가기</button>
    </div>

    <div class="like-section">
      <v-btn @click="setLike(1)" :class="{ selected: like.like_status === 1 }">좋아요</v-btn>
      <v-btn @click="setLike(-1)" :class="{ selected: like.like_status === -1 }">싫어요</v-btn>
      <p class="like-count">
        좋아요: {{ like.like_count }} / 싫어요: {{ like.dislike_count }}
      </p>
    </div>

    <div class="comments-section">
      <h3 class="comments-title">댓글</h3>
      <div v-for="comment in comments" :key="comment.comment_id" class="comment" :style="{ marginLeft: `${(comment.level - 1) * 20}px` }">
        <p>{{ comment.content }}</p>
        <div class="comment-actions">
          <input type="text" v-model="commentInput[comment.comment_id]"/>
          <v-btn @click="setComment(comment.comment_id,comment.root_id,board.article_no)">댓글달기</v-btn>
        </div>
      </div>
    </div>
    <div class="comment-actions">
          <input type="text" v-model="comments_content"/>
          <v-btn @click="rootComment()">댓글달기</v-btn>
        </div>
  </div>
  
</template>

<style scoped>
 .v-btn.selected { background-color: #007bff !important; }
 .article-container {
  max-width: 600px;
  margin: 0 auto;
}

.article-title {
  font-size: 1.5rem;
  margin-bottom: 10px;
}

.article-content {
  margin-bottom: 20px;
}

.edit-button {
  margin-bottom: 20px;
}

.like-section {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.like-section v-btn {
  margin-right: 10px;
}

.like-count {
  font-size: 0.8rem;
  margin: 0;
}

.comments-section {
  margin-top: 20px;
}

.comments-title {
  font-size: 1.2rem;
  margin-bottom: 10px;
}

.comment {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #e0e0e0;
  border-radius: 5px;
}

.comment-actions v-btn {
  margin-right: 10px;
}

.comment-actions v-btn:last-child {
  margin-right: 0;}
</style>