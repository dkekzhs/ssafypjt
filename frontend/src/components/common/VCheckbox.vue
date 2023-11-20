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
  <div class="v-checkbox-group">
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

<style scoped>
/* Add Vuetify-like styling to the native checkboxes */
.v-checkbox-group {
  display: flex;
  flex-direction: column;
}

.v-checkbox-label {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
}

.v-checkbox-input {
  margin-right: 8px;
  /* Add other styling as needed */
}
</style>
