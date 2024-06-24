import { createRouter, createWebHistory } from "vue-router";
import type { RouteRecordRaw } from "vue-router";
const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "HomeView",
    component: () => import("../views/HomeView.vue"),
  },
  {
    path: "/",
    name: "UserHomeView",
    component: () => import("../views/LayOut.vue"),
    // redirect: "/home/test",
    children: [
      {
        path: "home",
        component: () => import("../views/UserHomeView.vue"),
        name: "home",
        meta: {
          title: "Home",
          key: "home",
        },
      },
    ],
  },
  {
    path: "/home",
    component: () => import("../views/LayOut.vue"),
    children: [
      {
        path: "dashboard",
        // component: () => import('../components/addBook.vue'),
        component: () =>
          import("../components/Layout/Header/MenuCollapseIcon.vue"),
        name: "dashboard",
        meta: {
          title: "Dashboard",
          key: "dashboard",
        },
      },
    ],
  },
  {
    path: "/home",
    component: () => import("../views/LayOut.vue"),
    children: [
      {
        path: "myBooks",
        // component: () => import('../components/addBook.vue'),
        component: () => import("../views/BookList.vue"),
        name: "myBooks",
        meta: {
          title: "MyBooks",
          key: "myBooks",
        },
      },
      {
        path: "myItems",
        name: "myItems",
        component: () => import("../views/ItemList.vue"),
        meta: {
          title: "MyItems",
          key: "myItems",
        },
      },
    ],
  },
  {
    path: "/register",
    name: "RegisterView",
    component: () => import("../views/RegisterView.vue"),
  },
  {
    path: "/:catchAll(.*)",
    name: "404NotFound",
    component: () => import("../views/404NotFound.vue"),
  },
  // {
  //   path: "/test",
  //   name: "test",
  //   // component: () => import("../views/test.vue"),
  //   // meta: {
  //   //   title: "test",
  //   //   key: "test",
  //   // },
  // },
  {
    path: "/card",
    name: "card",
    component: () => import("../components/valueRenderer/bookCard.vue"),
  },
  {
    path: "/add",
    name: "add",
    component: () => import("../components/addBook.vue"),
  },
];
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
