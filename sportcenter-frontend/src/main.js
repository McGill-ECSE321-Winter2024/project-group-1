// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import BootstrapVue from "bootstrap-vue"
import App from './App'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
Vue.config.productionTip = false

// 4 types: Guest, Customer, Instructor, Owner
localStorage.setItem($accountType, 'Owner');
localStorage.setItem($username, 'Instr1');
localStorage.setItem($loggedIn, true);
localStorage.setItem($time, new Date().getDate() + "/" + new Date().getMonth() + "/" + new Date().getFullYear());
localStorage.setItem($debugging_mode, true);
// Vue.prototype.$accountType = "Owner";
// Vue.prototype.$username = 'Instr1'; // guest = JoeMama
// Vue.prototype.$loggedIn = true;
// Vue.prototype.$time = new Date().getDate() + "/" + new Date().getMonth() + "/" + new Date().getFullYear();
// Vue.prototype.$debugging_mode = true;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
