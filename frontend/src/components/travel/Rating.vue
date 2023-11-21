<template>
  <div>
    <h2>별점 주기</h2>
    <div class="star-rating">
      <!-- 별점 표시 -->
      <span
        v-for="n in maxStars"
        :key="n"
        @click="setRating(n)"
        :class="{ filled: n <= currentRating }"
      >
        ★
      </span>
    </div>
    <p>현재 별점: {{ currentRating }}</p>
  </div>
</template>

<script setup>
import { ref } from "vue";

const maxStars = ref(5); // 기본값은 5, 원하는 값으로 변경 가능

const currentRating = ref(0);

const setRating = (rating) => {
  currentRating.value = rating;
};
</script>

<style scoped>
/* 별점 스타일링 */
.star-rating {
  font-size: 2rem;
  cursor: pointer;
}

.filled {
  color: gold;
}
</style>
