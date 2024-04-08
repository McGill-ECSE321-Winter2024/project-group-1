import Vue from 'vue'
import Router from 'vue-router'

import Home from '@/components/Home.vue'

import Login from '@/components/authentification/Login.vue'
import ForgotPassword from '@/components/authentification/ForgotPassword.vue'
import CreateAccount from '@/components/authentification/CreateAccount.vue'

import CustomerAccount from '@/components/account/CustomerAccount.vue'
import InstructorAccount from '@/components/account/InstructorAccount.vue'
import OwnerAccount from '@/components/account/OwnerAccount.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/app/home',
      name: 'Home',
      component: Home
    },
    {
      path: '/app/customer-account',
      name: 'CustomerAccount',
      component: CustomerAccount
    },
    {
      path: '/app/instructor-account',
      name: 'InstructorAccount',
      component: InstructorAccount
    },
    {
      path: '/app/owner-account',
      name: 'OwnerAccount',
      component: OwnerAccount
    },
    {
      path: '/app/auth/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/app/auth/forgotpassword',
      name: 'ForgotPassword',
      component: ForgotPassword
    },
    {
      path: '/app/auth/createaccount',
      name: 'CreateAccount',
      component: CreateAccount
    },
    {
      path: '/app/account/customer-account',
      name: 'CustomerAccount',
      component: CustomerAccount
    },
    {
      path: '/app/account/instructor-account',
      name: 'InstructorAccount',
      component: InstructorAccount
    },
    {
      path: '/app/account/owner-account',
      name: 'OwnerAccount',
      component: OwnerAccount
    },
  ]
})
