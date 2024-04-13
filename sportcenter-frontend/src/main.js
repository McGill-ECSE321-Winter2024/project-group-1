// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import BootstrapVue from "bootstrap-vue"
import App from './App'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)

// 4 types: Guest, Customer, Instructor, Owner
// Check if global variables are already initialized in localStorage
if (!localStorage.getItem('accountType')) {
  // Initialize global variables
  localStorage.setItem('accountType', 'Guest');
  localStorage.setItem('username', '');
  localStorage.setItem('loggedIn', false);
  localStorage.setItem('id', '0');
  localStorage.setItem('time', new Date().getDate() + "/" + new Date().getMonth() + "/" + new Date().getFullYear());
  localStorage.setItem('debugging_mode', true);
}

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
