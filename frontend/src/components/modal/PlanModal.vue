<script setup>
import { ref } from "vue";
import { friendList } from "@/api/member/friendApi";
import { useSocketStore } from "../../api/chat/socket";

const travelTitle = ref("여행 계획");
const friends = ref([]);
const socketStore = useSocketStore();
function myFriendList() {
  friendList(
    (res) => {
      console.log(res);
      friends.value = [];
      for (let elem of res.data.data) {
        console.log(elem);
        friends.value.push({
          name: elem.to,
          isShared: false,
        });
      }
    },
    (err) => {
      console.log(err);
    }
  );
}
 async function createPlan() {
  await socketStore.connect("/createChatRoom",socketStore.handlePacket);
  console.log("chat Room created");
  let invited = [];
  for (let elem of friends.value) {
    if (elem.isShared) {
      invited.push(elem.name);
    }
  }
  const data = {
    type: "invitedUser",
    title: travelTitle.value,
    data: invited
  };
  console.log(data);
  socketStore.sendMessage(data);
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
    <div class="modal-content2" @click.stop>
      <!-- 모달 내용 -->
      <h2>{{ travelTitle }}</h2>
      <v-text-field v-model="travelTitle" placeholder="여행지 제목 입력" />

      <!-- 친구 목록 -->
      <div>
        <h3>친구 목록</h3>
        <ul>
          <v-list v-for="(friend, index) in friends" :key="index">
            {{ friend.name }}
            <v-btn  @click="toggleShare(index)">
              {{ friend.isShared ? "해제" : "공유" }}
            </v-btn>
          </v-list>
        </ul>
      </div>
      <v-btn color="blue" @click="myFriendList">친구새로고침</v-btn>
      <!-- 완료 버튼 -->
      <br>
      <v-btn color="green" @click="createPlan">완료</v-btn>
      <v-btn color="red" @click="closeModal">모달 닫기</v-btn>
    </div>
  </div>
</template>

<style scoped>
.modal-content2{
  background-color: rgb(255, 255, 255);
}
</style>
