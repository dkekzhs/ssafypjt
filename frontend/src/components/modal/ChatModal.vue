<template>
  <div class="chat-container" ref="chatContainer" id="chatContainer">
    <div v-for="message in messages" :key="message.id" class="message">
      <span v-if="message.type === 'received'" class="sender">{{ message.sender }}:</span>
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

const messages = ref([
  { id: 1, type: "received", sender: "John", content: "Hello!" },
  { id: 2, type: "sent", content: "Hi John! How are you?" },
  { id: 3, type: "received", sender: "John", content: "I'm good, thank you!" },
]);

const newMessage = ref("");

const sendMessage = () => {
  if (newMessage.value.trim() !== "") {
    messages.value.push({
      id: messages.value.length + 1,
      type: "sent",
      content: newMessage.value.trim(),
    });
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

watch(messages, () => {
  scrollToBottom();
});
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
