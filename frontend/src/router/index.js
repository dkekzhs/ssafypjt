import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/about",
      name: "about",
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import("../views/AboutView.vue"),
    },
    {
      path: "/login",
      name: "login",
      component: () => import("../views/UserView.vue"),
    },
    {
      path: "/board",
      name: "board",
      component: () => import("../views/BoardView.vue"),
    },
    {
      path: "/board_write",
      name: "board_write",
      component: () => import("../views/boardWriteView.vue"),
    },
    {
      path: '/board/detail/:id',
      name: 'boardDetail',
      component: import("@/components/board/BoardDetail.vue"),
      props: true
    },
    {
      path: "/todo",
      name: "todo",
      component: () => import("../views/TodoView.vue"),
    },
    {
      path: "/jwtlogin",
      name: "jwtlogin",
      component: () => import("@/components/JwtLogin.vue"),
    },
    {
      path: "/regist",
      name: "regist",
      component: () => import("../views/RegistView.vue"),
    },
    
  ],
});

export default router;
