<script setup>
import { ref } from "vue";
import { friendList } from "@/api/member/friendApi";
const travelTitle = ref("여행 계획");
const friends = ref([
]);

function myFriendList() {
  friendList(res => {
    console.log(res);
    friends.value = [];
    for (let elem of res.data.data) {
      console.log(elem);
      friends.value.push({
        name: elem.to,
        isShared: false
      });
    }
  },
    err => {
      console.log(err);
  })
}
function createPlan() {
  const socket = new WebSocket("ws://localhost:80/enjoytrip/createChatRoom");
  socket.onopen = () => {
    let invited = [];
    for (let elem of friends.value) {
      if (elem.isShared) {
        invited.push(elem.name);
      }
    }
    socket.send(JSON.stringify({
      type: "invitedUser",
      data: invited,
      title: travelTitle.value
    }));
  }
  closeModal();
}
const toggleShare = function (index) {
  friends.value[index].isShared = !friends.value[index].isShared;
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
          <li v-for="(friend, index) in friends" :key="index">
            {{ friend.name }}
            <button @click="toggleShare(index)">
              {{ friend.isShared ? "해제" : "공유" }}
            </button>
          </li>
        </ul>
      </div>
      <button @click="myFriendList">친구새로고침</button>
      <!-- 완료 버튼 -->
      <button @click="createPlan">완료</button>
      <button @click="closeModal">모달 닫기</button>
    </div>
  </div>
</template>

<style scoped></style>
