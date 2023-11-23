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
            :contentId="element.contentId"
            :name="element.title"
            :order="element.order"
            :image="element.firstImage"
            :description="element.addr1"
            @destinationDeleted="handleDestinationDeleted"
          />
        </div>
      </template>
    </draggable>
  </div>
</template>

<script setup>
import { defineProps, ref, defineEmits, computed } from "vue";
import draggable from "vuedraggable";
import DestinationItem from "@/components/common/VDestinationItem.vue";
import { useSocketStore } from "../../api/chat/socket";
let idGlobal = 0;
const socketStore = useSocketStore();
const emit = defineEmits();
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
  socketStore.destinations.forEach((destination, index) => {
    destination.order = index + 1;
  });
};

const handleDestinationDeleted = (selectedDestination) => {
  console.log("삭제된 여행지:", selectedDestination);
  if (socketStore.isConnected) {
    socketStore.sendMessage({
      type: "deletePlan",
      content_id: selectedDestination.contentId,
      order: selectedDestination.order,
    });
  } else {
    // 선택된 여행지 데이터를 필요에 따라 처리합니다.
    socketStore.destinations = socketStore.destinations.filter(
      (destination) => destination.title !== selectedDestination.name
    );
    console.log("socketDes : " + socketStore.destinations);
    // // 삭제 이후에 부모 컴포넌트에 이벤트를 발생시킬 수 있습니다.
    // emit("destinationDeleted", selectedDestination);
  }

  endDrag();
};

const props = defineProps(["destinations"]);
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
