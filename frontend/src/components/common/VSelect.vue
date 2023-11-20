<script setup>
import { ref } from "vue";
defineProps({ selectOption: Array });
const emit = defineEmits(["onKeySelect"]);

const key = ref("");

const onSelect = () => {
  // console.log(key.value + "선택!!!");
  emit("onKeySelect", key.value);
};
</script>

<template>
  <select v-model="key" class="form-select form-select-sm w-50 v-select" @change="onSelect">
    <option
      v-for="option in selectOption"
      :key="option.value"
      :value="option.value"
      :disabled="option.value === '' ? true : false"
    >
      {{ option.text }}
    </option>
  </select>
</template>

<style scoped>
.v-select {
  padding: 10px; /* Add padding or other styling as needed */
  border: 1px solid #ced4da; /* Example border styling */
  border-radius: 4px; /* Example border-radius styling */
  background-color: #ffffff; /* Example background color */
  color: #495057; /* Example text color */
}

</style>
