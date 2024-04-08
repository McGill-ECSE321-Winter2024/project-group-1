import Vue from "vue";
import Router from "vue-router";
import Hello from "@/components/Hello";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/app/hello",
      name: "Hello",
      component: Hello,
    },
    {
      path: "/app/main",
      name: "Main",
      component: Main,
    },
  ],
});
