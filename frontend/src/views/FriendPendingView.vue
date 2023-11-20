<script setup>
import { friendPendingList , friendAccept } from "@/api/member/friendApi";
import { onMounted, ref } from "vue";
const friendList = ref([]);
function getData() {
  friendPendingList(res => {
    console.log(res.data); 
    friendList.value = res.data.data; 
  },
    err => {
      console.log(err);
  })
}
function acceptFriend(from) {
  friendAccept(from, res => {
    console.log(res)
  },
    err => {
      console.log(err)
  }
  )
}

function deleteFriend(from) {
  
}
onMounted(() => {
  getData();
})
</script>

<template>
  <v-container>
    <v-list>
      <v-list-item-group v-if="friendList.length > 0">
        <v-list-item v-for="(friend, index) in friendList" :key="index">
          <v-list-item-content>
            <v-list-item-title>{{ friend.from }}</v-list-item-title>
          </v-list-item-content>
          <v-list-item-action>
            <v-btn @click="acceptFriend(friend)" color="success">수락</v-btn>
            <v-btn @click="deleteFriend(friend)" color="error">거절</v-btn>
          </v-list-item-action>
        </v-list-item>
      </v-list-item-group>
      <v-list-item v-else>
        <v-list-item-content>대기중인 친구가 없습니다.</v-list-item-content>
      </v-list-item>
    </v-list>
  </v-container>
</template>

<style scoped>

</style>