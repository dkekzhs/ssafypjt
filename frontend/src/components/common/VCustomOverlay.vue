<script setup>
import { ref } from "vue";
defineProps({ overlay: Object });
const emit = defineEmits(["onToggle"]);

const data = ref({});
const isOpen = ref(false);

const onClick = () => {
  // console.log(key.value + "선택!!!");
  isOpen.value = !isOpen.value;
  emit("onToggle", isOpen.value);
};
</script>

<template>
  <div v-if="isOpen" v-bind="data">
    <div>커스텀 오버레이</div>
    <button @click="onClick">exit</button>
  </div>
</template>

<style scoped></style>
