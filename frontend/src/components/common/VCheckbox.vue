<script setup>
import { ref } from "vue";
defineProps({ selectOption: Array });
const emit = defineEmits(["onKeySelect"]);

const keys = ref([]);

const onSelect = () => {
  emit("onKeySelect", keys.value);
};
</script>

<template>
  <div>
    <label v-for="option in selectOption" :key="option.value">
      <input
        type="checkbox"
        :value="option.value"
        v-model="keys"
        @change="onSelect"
        :disabled="option.value === ''"
        :checked="option.checked"
      />
      {{ option.text }}
    </label>
  </div>
</template>

<style scoped></style>
