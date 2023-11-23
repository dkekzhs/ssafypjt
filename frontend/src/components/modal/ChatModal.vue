<template>
  <div class="chat-container" ref="chatContainer" id="chatContainer">
    <div v-for="message in socketStore.messages" :key="message.id" class="message">
      <span class="sender">{{ message.sender }}:</span>
      <span class="content">{{ message.content }}</span>
    </div>
    <div class="input-area">
      <input v-model="newMessage" @keyup.enter="sendMessage" placeholder="Type a message..." />
      <button @click="sendMessage">Send</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";

import { useSocketStore } from "@/api/chat/socket";


const newMessage = ref("");
const socketStore = useSocketStore();

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
  max-height: 300px;
  overflow-x: hidden;
  overflow-y: scroll;
}

.message {
  margin-bottom: 8px;
}

.sender {
  font-weight: bold;
  margin-right: 8px;
}

.input-area {
  margin-top: 16px;
}

input {
  padding: 8px;
  margin-right: 8px;
}

button {
  padding: 8px;
}
</style>
