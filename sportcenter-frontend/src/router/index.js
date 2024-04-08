import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import sportcenter from '@/components/sportcenter'

import LoginPage from '@/components/LoginPage.vue'
import ForgotPasswordPage from '@/components/ForgotPasswordPage.vue'
import RegisterPage from '@/components/RegisterPage.vue'

import AccountPage from '@/components/AccountPage.vue'

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
      path: '/app/account',
      name: 'AccountPage',
      component: AccountPage
    },
    {
      path: '/app/login',
      name: 'LoginPage',
      component: LoginPage
    },
    {
      path: '/app/forgotpassword',
      name: 'Logout',
      component: ForgotPasswordPage
    },
    {
      path: '/app/register',
      name: 'Register',
      component: RegisterPage
    }
  ]
})
