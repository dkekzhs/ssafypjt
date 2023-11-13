import { ref } from "vue";
import { defineStore } from "pinia";

export const useUserStore = defineStore("userStore", () => {
  const user = ref({
    name: "",
  });
  return {
    user,
  };
});

export const useMenuStore = defineStore("menuStore", () => {
  const menuList = ref([
    { name: "회원가입", show: true, routeName: "home" },
    { name: "로그인", show: true, routeName: "home" },
    { name: "오늘할일", show: false, routeName: "home" },
    { name: "내정보", show: false, routeName: "home" },
    { name: "로그아웃", show: false, routeName: "home" },
  ]);

  const changeMenuState = () => {
    console.log("changeMenuState");
    menuList.value = menuList.value.map((item) => ({ ...item, show: !item.show }));
  };
  return {
    menuList,
    changeMenuState,
  };
});
