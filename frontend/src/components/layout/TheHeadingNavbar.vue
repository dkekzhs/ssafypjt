<script setup>
import { friendPendingCount } from "@/api/member/friendApi";
import { useMenuStore, useUserStore, FriendRequestsPage } from "@/stores/menu";
import { storeToRefs } from "pinia";
import { logout } from "@/stores/menu";
import { ref, watch, computed } from "vue";
import { useWindowSize } from "@vueuse/core";

const menuStore = useMenuStore();
const userStore = useUserStore();

// 반응형을 유지하면서 스토어에서 속성을 추출하려면, storeToRefs()를 사용
// https://pinia.vuejs.kr/core-concepts/
const { menuList } = storeToRefs(menuStore);
const { user } = storeToRefs(userStore);
const { changeMenuState } = menuStore;
console.log(menuList.value);

const appTitle = ref("enjoyTrip");
const sidebar = ref(false);
const toolbar = ref(false);

const { width } = useWindowSize();

watch(width, (newVal) => {
  if (newVal <= 600) {
    // 화면이 작을 때 사이드바를 활성화하고 툴바를 비활성화합니다.
    sidebar.value = true;
    toolbar.value = false;
  } else {
    // 화면이 클 때 사이드바를 비활성화하고 툴바를 활성화합니다.
    sidebar.value = false;
    toolbar.value = true;
  }
});

//친구 카운트 세기
const login = computed(() => menuStore.login)
const count = ref(0);
watch(login, (newValue) => {
  console.log(newValue)
  if (newValue == true) {
    countFriend();
  }
});
function countFriend() {
  friendPendingCount(res => {
    count.value = res.data.message;
    
  },
    err => {
      logout();
      console.log(err);
}
  )
}

</script>

<template>
  <v-navigation-drawer v-model="sidebar">
    <v-list>
      <template v-for="item in menuList" :key="item.name">
        <v-list-tile v-if="item.show">
          <template v-if="item.routeName == 'logout'">
            <v-btn @click="logout()">{{ item.name }}</v-btn> 
          </template>
          <template v-else>
            <v-list-tile-action>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-tile-action>
            <v-list-tile-content>
              <router-link :to="item.routeName" tag="span" style="cursor: pointer">
                {{ item.name }}
              </router-link>
            </v-list-tile-content>
            <br />
          </template>
        </v-list-tile>
      </template>
    </v-list>
  </v-navigation-drawer>

  <v-toolbar>
    <span class="hidden-sm-and-up">
      <v-toolbar @click="sidebar = !sidebar">
        <v-icon>mdi-table-of-contents</v-icon>
      </v-toolbar>
    </span>
    <v-toolbar-title>
      <router-link to="/" tag="span" style="cursor: pointer">
        {{ appTitle }}
      </router-link>
    </v-toolbar-title>
    <v-spacer></v-spacer>
    <v-toolbar-items class="hidden-xs">

      <!--Frined Count-->
      <template v-if="menuStore.login">
        <v-btn icon @click="FriendRequestsPage">
          <v-icon>mdi-account-multiple</v-icon>
            <span class="badge" slot="badge" >{{count}}</span>
        </v-btn>
      </template>

      <!-- end -->
      <template v-for="item in menuList" :key="item.name">
        <template v-if="item.routeName == 'logout' && item.show">
            <v-btn @click="logout()">{{ item.name }}</v-btn> 
          </template>
        <v-btn flat :to="item.routeName" v-if="item.show && item.routeName != 'logout'">
            <v-icon left dark>{{ item.icon }}</v-icon>
            <v-list-tile-content>{{ item.name }}</v-list-tile-content>
        </v-btn>
      </template>
    </v-toolbar-items>
  </v-toolbar>
</template>

<style scoped>
.badge {
  background-color: #ff6f61; /* 약간의 빨간색 배경 */
  color: white; /* 텍스트 색상 */
  border-radius: 12px; /* 동그란 형태로 만들기 위한 border-radius 설정 */

}
</style>
