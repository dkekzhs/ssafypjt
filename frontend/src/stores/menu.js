import { ref } from "vue";
import { defineStore } from "pinia";
import { memberLogin, memberLogout, getPublicKey } from "@/api/member/memberApi";
import { setCookie, getCookie, deleteCookie } from "@/util/cookie/cookie";
import * as RSA from '@/util/encrypt/rsa.js';
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
    { name: "회원가입", show: isLoggedIn, routeName: "regist" },
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
export function encryptText(text, exponent, modulus) {
  var rsa = new RSA.RSAKey();
  rsa.setPublic(modulus, exponent);
  return rsa.encrypt(text);
}


export async function login(id, pw) {
  const data = await getPublicKey();
  console.log(data.data.exponent);
  if (getCookie("exponent")== null && getCookie("modulus") == null) {
    setCookie("exponent", data.data.exponent);
    setCookie("modulus", data.data.modulus);
  }
  pw = encryptText(pw, getCookie("exponent"), getCookie("modulus"));
  console.log("userPw " + pw);
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

export async function regist(body) {
  const data = await getPublicKey();
  console.log(data.data.exponent);
  if (getCookie("exponent")== null && getCookie("modulus") == null) {
    setCookie("exponent", data.data.exponent);
    setCookie("modulus", data.data.modulus);
  }
  body.user_password = encryptText(pw, getCookie("exponent"), getCookie("modulus"));
  console.log("userPw " + body);
  memberRegist(
    body,
    (res) => {
        console.log(res)
    },
    (err) => {
      console.log("login 중 에러 발생 >> " + err);

    }
  );
}
