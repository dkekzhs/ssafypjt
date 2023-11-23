<!-- TravelList.vue -->
<template>
  <div>
    <h2>여행지 목록</h2>
    <draggable
      class="dragArea list-group"
      :list="destinations"
      :clone="cloneDestination"
      group="travel"
      @start="startDrag"
      item-key="order"
      @end="endDrag"
    >
      <template #item="{ element }">
        <div class="list-group-item">
          <DestinationItem
            :name="element.title"
            :order="element.order"
            :image="element.firstImage"
            :description="element.addr1"
            @destinationSelected="handleDestinationSelected"
          />
        </div>
      </template>
    </draggable>
  </div>
</template>

<script setup>
import { defineProps, ref } from "vue";
import draggable from "vuedraggable";
import DestinationItem from "@/components/common/VDestinationItem.vue";
let idGlobal = 0;
const cloneDestination = ({ name }) => {
  return { name, id: idGlobal++ };
};

const startDrag = ({ originalEvent }) => {
  console.log("hi");
  // Clone on Control (Ctrl) key press
  const controlOnStart = originalEvent.ctrlKey;
  return controlOnStart ? "clone" : true;
};

const endDrag = () => {
  // 드래그가 끝날 때 순서를 갱신합니다.
  props.destinations.forEach((destination, index) => {
    destination.order = index + 1;
  });

  console.log(props.destinations[0]);
  console.log(props.destinations[1]);
};

const handleDestinationSelected = (selectedDestination) => {
  console.log("선택된 여행지:", selectedDestination);
  // 선택된 여행지 데이터를 필요에 따라 처리합니다.
};

const props = defineProps(["destinations"]);

const destinations = ref(props.destinations);
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
