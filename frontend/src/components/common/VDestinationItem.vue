<script setup>
import { defineProps, defineEmits, ref, watch } from "vue";

const { name, order, image, description, contentId } = defineProps([
  "name",
  "order",
  "image",
  "description",
  "contentId",
]);
const emit = defineEmits();

const deleteDestination = () => {
  // 삭제된 여행지를 알리기 위해 이벤트를 발생시킵니다.
  emit("destinationDeleted", { name, order, image, description, contentId });
};

const elementRef = ref(null);

// Watch for changes in the props and update the HTML content
watch(
  () => [name, order, image, description, contentId],
  () => {
    updateHtmlContent();
  }
);

const updateHtmlContent = () => {
  if (elementRef.value) {
    elementRef.value.innerHTML = `
      <h3>${order}. ${name}</h3>
      <img src="${image}" alt="여행지 이미지" style="max-width: 200px" />
      <p>${description}</p>
      <button @click="selectDestination">선택</button>
    `;
  }
};
</script>

<!-- DestinationItem.vue -->
<template>
  <div ref="elementRef">
    <h3>{{ order }}. {{ name }}</h3>
    <img :src="image" alt="여행지 이미지" style="max-width: 200px" />
    <p>{{ description }}</p>
    <button @click="deleteDestination">삭제</button>
  </div>
</template>
