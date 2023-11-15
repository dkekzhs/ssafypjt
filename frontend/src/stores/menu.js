import { ref } from "vue";
import { defineStore } from "pinia";
import { memberLogin, memberLogout } from "@/api/member/memberApi";
import { setCookie, getCookie, deleteCookie } from "@/util/cookie/cookie";

let isLoggedIn = getCookie("user_name") ? false : true;

export const useUserStore = defineStore("userStore", () => {
  const user = ref({
    name: getCookie("user_name"),
  });
  return {
    user,
  };
});

export const useMenuStore = defineStore("menuStore", () => {
  const menuList = ref([
    { name: "회원가입", show: isLoggedIn, routeName: "home" },
    { name: "로그인", show: isLoggedIn, routeName: "login" },
    { name: "오늘할일", show: !isLoggedIn, routeName: "home" },
    { name: "내정보", show: !isLoggedIn, routeName: "home" },
    {
      name: "로그아웃",
      routeName: "logout",
      show: !isLoggedIn,
    },
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

export function logout() {
  if (useUserStore().user.name) {
    memberLogout();
    useMenuStore().changeMenuState();
    useUserStore().user.name = "";
    deleteCookie("user_name");
  } else {
    console.log("로그인 유저 없음");
  }
}

export function login(id, pw) {
  memberLogin(
    { user_id: id, user_password: pw },
    (res) => {
      let user_name = res.data.user_name;
      useUserStore().user.name = user_name;
      useMenuStore().changeMenuState();
      setCookie("user_name", user_name);
    },
    (err) => {
      console.log("login 중 에러 발생 >> " + err);
      useUserStore().user.name = null;
      deleteCookie("user_name");
    }
  );
}
