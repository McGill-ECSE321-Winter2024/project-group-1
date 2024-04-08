import Vue from 'vue'
import Router from 'vue-router'

import Home from '@/components/Home.vue'

import Authentification from '@/components/authentification/Authentification.vue'
import Login from '@/components/authentification/Login.vue'
import ForgotPassword from '@/components/authentification/ForgotPassword.vue'
import CreateAccount from '@/components/authentification/CreateAccount.vue'

import Account from '@/components/account/Account.vue'
import CustomerAccount from '@/components/account/CustomerAccount.vue'
import InstructorAccount from '@/components/account/InstructorAccount.vue'
import OwnerAccount from '@/components/account/OwnerAccount.vue'

import Activity from '@/components/activity/Activity.vue'
import ViewActivityTable from '@/components/activity/ViewActivityTable.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/app/home',
      name: 'Home',
      component: Home
    },
    {
      path: '/app/auth',
      name: 'Authentification',
      component: Authentification
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
      path: '/app/account',
      name: 'Account',
      component: Account
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
    {
      path: '/app/activity',
      name: 'Activity',
      component: Activity
    },
    {
      path: '/app/activity/view-activity',
      name: 'ViewActivityTable',
      component: ViewActivityTable
    }
  ]
})
