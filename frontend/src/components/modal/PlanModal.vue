<script setup>
import { ref } from "vue";

const travelTitle = ref("여행 계획");
const friendList = ref([
  { name: "친구1", isShared: false },
  { name: "친구2", isShared: false },
  { name: "친구3", isShared: false },
]);
const newFriend = ref("");

const toggleShare = function (index) {
  friendList.value[index].isShared = !friendList.value[index].isShared;
};

const closeModal = function () {
  document.getElementById("modal").style.display = "none";
};
</script>

<template>
  <div>
    <div class="modal-content" @click.stop>
      <!-- 모달 내용 -->
      <h2>{{ travelTitle }}</h2>
      <input v-model="travelTitle" placeholder="여행지 제목 입력" />

      <!-- 친구 목록 -->
      <div>
        <h3>친구 목록</h3>
        <ul>
          <li v-for="(friend, index) in friendList" :key="index">
            {{ friend.name }}
            <button @click="toggleShare(index)">
              {{ friend.isShared ? "해제" : "공유" }}
            </button>
          </li>
        </ul>
      </div>

      <!-- 완료 버튼 -->
      <button @click="closeModal">완료</button>
      <button @click="closeModal">모달 닫기</button>
    </div>
  </div>
</template>

<style scoped></style>
