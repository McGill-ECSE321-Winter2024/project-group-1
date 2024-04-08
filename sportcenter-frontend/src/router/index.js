import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import sportcenter from '@/components/sportcenter'
import AccountPage from '@/components/AccountPage.vue'
import LoginPage from '@/components/LoginPage.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/app',
      name: 'SportCenter',
      component: sportcenter
    },
    {
      path: '/app/account', // :id is a dynamic parameter
      name: 'AccountPage',
      component: AccountPage
    },
    {
      path: '/app/login',
      name: 'LoginPage',
      component: LoginPage
    }
  ]
})
