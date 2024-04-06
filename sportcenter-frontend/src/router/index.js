import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import ViewActivityTable from '@/components/ViewActivityTable'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/hello',
      name: 'Hello',
      component: Hello
    },

    {
      path: '/viewactivitytable',
      name: 'ViewActivityTable',
      component: ViewActivityTable
    } 
  ]
})
