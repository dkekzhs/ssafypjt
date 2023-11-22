<script setup>
import {friendList , friendPending, friendAccept, addFriend, refuseFriend } from "@/api/member/friendApi";
import { getUsers } from "@/api/member/memberApi";
import { onMounted, ref } from "vue";
import { logout } from "@/stores/menu";
const friendPendingList= ref([]);
const searchId = ref("");
const friendRequestList = ref([]);
const myFriend = ref([]);
function getData() {
  friendPending(
    (res) => {
      console.log(res.data);
      friendPendingList.value = res.data.data;
    },
    (err) => {
      console.log(err);
    }
  );
}
function acceptFriend(from) {
  friendAccept(
    from,
    (res) => {
      console.log(res);
    },
    (err) => {
      console.log(err);
    }
  );
}
function getUserId() {
  console.log(searchId.value);
  getUsers(
    { search: searchId.value },
    (res) => {
      friendRequestList.value = res.data.data;
    },
    (err) => {
      console.log(err);
    }
  );
}
function add(friend) {
  console.log(friend);
  addFriend(
    { "to": friend },
    (res) => {
      console.log(res.data.message);
      if ("친구 등록 성공" == res.data.message) {
        let index = friendRequestList.value.indexOf(friend);
        if (index !== -1) {
          friendRequestList.value.splice(index, 1);
        }
      }
      else {
        alert(res.data.message);
      }
    },
    (err) => {
      console.log(err);
    }
  );
}
function myFriendList() {
  friendList(res => {
    console.log(res);
    myFriend.value = [];
    for (let elem of res.data.data) {
      console.log(elem);
      myFriend.value.push({
        name: elem.to,
      });
    }
  },
    err => {
      console.log(err);
  })
}
function deleteFriend(friend) {
  console.log(friend);
  refuseFriend({ from: friend.from }, res => {
    console.log(res);
    if ("친구 거절" == res.data.message) {
        let index = friendPendingList.value.indexOf(friend);
        if (index !== -1) {
          friendPendingList.value.splice(index, 1);
        }
      }
      else {
        alert(res.data.message);
      }
  }, err => {
    console.log(err);
  }
  )

}
onMounted(() => {
  getData();
  myFriendList();
});
</script>

<template>
  <v-container>
    <!-- 유저 검색-->
    <input v-model="searchId" type="text" />
    <v-btn @click="getUserId">유저 아이디검색</v-btn>

    <v-list-item v-for="(friend, index) in friendRequestList" :key="index">
      <v-list-item-content>
        <v-list-item-title>{{ friend }}</v-list-item-title>
      </v-list-item-content>
      <v-list-item-action>
        <v-btn @click="add(friend)" color="success">요청</v-btn>
      </v-list-item-action>
    </v-list-item>
    <!-- end-->

    <v-row>
      <!-- 친구 대기 화면 -->
      <v-col cols="6">
        <v-list>
          <v-list-item-group v-if="friendPendingList.length > 0">
            <v-list-item v-for="(friend, index) in friendPendingList" :key="index">
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
      </v-col>
      <!-- 친구 요청 화면 -->
      <v-col cols="6">
        <v-list>
          <!-- 친구 요청 리스트를 불러오고, 이를 반복문으로 표시하도록 변경하세요. -->
          <v-list-item-group v-if="myFriend.length >0">
            <v-list-item v-for="(friend, index) in myFriend" :key="index">
              <v-list-item-content>
                <v-list-item-title>내친구 : {{ friend.name }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-item-group>
          <!-- 친구 요청이 없는 경우의 처리를 추가하세요. -->
          <v-list-item v-else>
            <v-list-item-content>친구가 없습니다.</v-list-item-content>
          </v-list-item>
        </v-list>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped></style>
