<!-- TravelList.vue -->
<template>
  <div>
    <h2>여행지 목록</h2>
    <draggable
      class="dragArea list-group"
      :list="destinations"
      group="travel"
      @start="startDrag"
      item-key="order"
      @end="endDrag"
      @update="updateOrder"
    >
      <template #item="{ element }">
        <div :key="element.contentId" class="list-group-item">
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
import { defineProps} from "vue";
import draggable from "vuedraggable";
import DestinationItem from "@/components/common/VDestinationItem.vue";
import { useSocketStore } from "../../api/chat/socket";
const socketStore = useSocketStore();
const startDrag = ({ originalEvent }) => {
  console.log("hi");
  // Clone on Control (Ctrl) key press
  const controlOnStart = originalEvent.ctrlKey;
  return controlOnStart ? "clone" : true;
};
const updateOrder = (event) => {
  const destinations = [...socketStore.destinations];
  const movedItem = destinations.splice(event.oldIndex, 1)[0];
  destinations.splice(event.newIndex, 0, movedItem);

  socketStore.destinations = destinations.map((destination, index) => {
    return {
      ...destination,
      order: index + 1,
    };
  });
};
const endDrag = () => {
  // 드래그가 끝날 때 순서를 갱신합니다.
  const updatedDestinations = socketStore.destinations.map((destination, index) => {
    return {
      ...destination,
      order: index + 1,
    };
  });
  socketStore.destinations = updatedDestinations;
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
      (destination) => destination.contentId !== selectedDestination.contentId
    );
  }
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
