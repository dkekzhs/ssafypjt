<template>
  <div class="row">
    <div class="col-3">
      <h3>Travel Destinations</h3>
      <draggable
        class="dragArea list-group"
        v-model="destinations"
        :clone="cloneDestination"
        group="travel"
        @start="startDrag"
        item-key="id"
      >
        <template #item="{ element }">
          <div class="list-group-item">
            {{ element.name }}
          </div>
        </template>
      </draggable>
    </div>

    <rawDisplayer
      class="col-3"
      :value="destinations"
      title="Travel Destinations"
    />
  </div>
</template>

<script setup>
import { ref } from "vue";
import draggable from "vuedraggable";

let idGlobal = 8;

const destinations = ref([
  { name: "Paris", id: 1 },
  { name: "Tokyo", id: 2 },
  { name: "New York", id: 3 },
  // Additional travel destinations
]);

const cloneDestination = ({ name }) => {
  return { name, id: idGlobal++ };
};

const startDrag = ({ originalEvent }) => {
  // Clone on Control (Ctrl) key press
  const controlOnStart = originalEvent.ctrlKey;
  return controlOnStart ? "clone" : true;
};
</script>

<style scoped>
.dragArea {
  cursor: move;
}
.list-group-item {
  margin-bottom: 8px;
  padding: 8px;
  border: 1px solid #ccc;
  background-color: #f8f8f8;
  cursor: grab;
}
</style>
