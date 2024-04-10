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

import Instructors from '@/components/instructors/Instructors.vue'
import ViewInstructors from '@/components/instructors/ViewInstructors.vue'
import ManageInstructors from '@/components/instructors/ManageInstructors.vue'

import MyActivitiesTableCustomer from '@/components/myActivities/MyActivitiesTableCustomer.vue'
import MyActivitiesTableInstructor from '@/components/myActivities/MyActivitiesTableInstructor.vue'

import ManageActivities from '@/components/activity/ManageActivities.vue'
import Activity from '@/components/activity/Activity.vue'
import ViewActivityTable from '@/components/activity/ViewActivityTable.vue'
import ProposeActivity from '@/components/activity/ProposeActivity.vue'
import ScheduleActivity from '@/components/activity/ScheduleActivity.vue'
import UpdateDeleteActivity from '@/components/activity/UpdateDeleteActivity.vue'
import UpdateDeleteScheduledActivity from '@/components/activity/UpdateDeleteScheduledActivity.vue'

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
      path: '/app/activity/propose-activity',
      name: 'ProposeActivity',
      component: ProposeActivity
    },
    {
      path: '/app/activity/schedule-activity',
      name: 'ScheduleActivity',
      component: ScheduleActivity
    },
    {
      path: '/app/activity/view-activity',
      name: 'ViewActivityTable',
      component: ViewActivityTable
    },
    {
      path: '/app/activity/update-delete-activity',
      name: 'UpdateDeleteActivity',
      component: UpdateDeleteActivity
    },
    {
      path: '/app/activity/update-delete-scheduled-activity',
      name: 'UpdateDeleteScheduledActivity',
      component: UpdateDeleteScheduledActivity
    },
    {
      path: '/app/my-activities/customer',
      name: 'MyActivitiesTableCustomer',
      component: MyActivitiesTableCustomer
    },
        {
      path: '/app/my-activities/instructor',
      name: 'MyActivitiesTableInstructor',
      component: MyActivitiesTableInstructor
    },
    {
      path: '/app/instructors',
      name: 'Instructors',
      component: Instructors
    },
    {
      path: '/app/instructors/view-instructors',
      name: 'ViewInstructors',
      component: ViewInstructors
    },
    {
      path: '/app/instructors/manage-instructors',
      name: 'ManageInstructors',
      component: ManageInstructors
    },
    {
      path: '/app/activity/manage-activities',
      name: 'ManageActivities',
      component: ManageActivities
    }
  ]
})
