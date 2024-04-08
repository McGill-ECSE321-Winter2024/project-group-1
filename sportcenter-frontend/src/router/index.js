import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Fabian from '@/components/Fabian'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/fabian',
      name: 'Fabian',
      component: Fabian
    }
  ]
})
