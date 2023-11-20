import { ref } from "vue";
import { defineStore } from "pinia";
import { memberLogin, memberLogout, getPublicKey, memberRegist } from "@/api/member/memberApi";
import { setCookie, getCookie, deleteCookie } from "@/util/cookie/cookie";
import * as RSA from '@/util/encrypt/rsa.js';
import router from '@/router/index'; // Vue Router가 설정된 파일을 import

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
    { name: "jwt로그인", show: isLoggedIn, routeName: "jwtlogin", icon: 'mdi-login'},
    { name: "게시판", show: !isLoggedIn, routeName: "board", icon :'mdi-ballot' },
    { name: "회원가입", show: isLoggedIn, routeName: "regist", icon :'mdi-account-plus' },
    { name: "로그인", show: isLoggedIn, routeName: "login", icon : 'mdi-login' },
    {
      name: "로그아웃",
      routeName: "logout",
      show: !isLoggedIn,
      icon : "mdi-logout",
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
      //실패일 경우 메세지 alert 띄우기 
      console.log(res);
      if (res.data.status == 400) {
        alert(res.data.message);
      }
      else {
        let user_name = res.data.user_name;
        useUserStore().user.name = user_name;
        useMenuStore().changeMenuState();
        setCookie("user_name", user_name);
        router.push({ name: 'home' });

      }
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
  console.log(body);
  body.user_password =  encryptText(body.user_password, getCookie("exponent"), getCookie("modulus"));

  memberRegist(
    body,
    (res) => {
        console.log(res)
    },
    (err) => {
      console.log(err);

    }
  );
}
