
<template>
  <div class="chat-container" ref="chatContainer" id="chatContainer">
    <v-card class="message" v-for="message in socketStore.messages" :key="message.id" style="max-width: 300px;">
      <v-card-title>
        <span>{{ message.sender }}</span>
      </v-card-title>
      <v-card-text>{{ message.content }}</v-card-text>
    </v-card>
    <v-text-field v-model="newMessage" label="message" filled @keyup.enter="sendMessage" />
    <v-btn color="primary" @click="sendMessage">Send</v-btn>
    <v-btn color="red" @click="closeModal">모달 닫기</v-btn>
  </div>
</template>


<script setup>
import { ref, onMounted, watch } from "vue";

import { useSocketStore } from "@/api/chat/socket";


const newMessage = ref("");
const socketStore = useSocketStore();
const closeModal = function () {
  document.getElementById("chat_modal").style.display = "none";
};
const sendMessage = () => {
  console.log("샌드 메시지 호출 : "+ socketStore.messages);
  if (newMessage.value.trim() !== "") {
    socketStore.sendMessage({ type: "message", data: newMessage.value });
    newMessage.value = "";
    scrollToBottom();
  }
};

const scrollToBottom = () => {
  if (chatContainer) {
    chatContainer.scrollTop = chatContainer.scrollHeight + 30;
  }
};


onMounted(() => {
  scrollToBottom();
});
/*
watch(messages, () => {
  scrollToBottom();
});
*/
watch(
  () => socketStore.messages.length,
  () => {
    console.log("Message Changed");
    scrollToBottom();
  },
  { deep: true }
)
</script>

<style scoped>
.chat-container {
  max-height: 450px;
  overflow-x: hidden;
  overflow-y: scroll;
  background-color: rgb(255, 255, 255);
}

.message {
  margin-bottom: 3px;
}

.sender {
  font-weight: bold;
  margin-right: 8px;
}


</style>
