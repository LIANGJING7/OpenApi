import { createRouter, createWebHashHistory } from 'vue-router'
import ApiIndex from '../views/ApiIndex.vue'
import HomePage from '../components/HomePage.vue'
import IndentIndex from '../components/IndentIndex.vue'
import InterfaceIndex from '@/components/InterfaceIndex.vue'
import DocumentIndex from '@/components/DocumentIndex.vue'
import LoginIndex from '@/views/LoginIndex.vue'
import LoginItem1 from '@/components/LoginItem1.vue'
import LoginItem2 from '@/components/LoginItem2.vue'

const routes = [
  {
    path: '/api',
    name: 'apiIndex',
    component: ApiIndex,
    redirect: '/api/homePage',
    children: [
      {
        path: 'homePage',
        name: 'HomePage',
        component: HomePage
      }, {
        path: 'indentIndex',
        name: 'indentIndex',
        component: IndentIndex
      }, {
        path: 'interfaceIndex',
        name: 'interfaceIndex',
        component: InterfaceIndex
      }, {
        path: 'documentIndex',
        name: 'documentIndex',
        component: DocumentIndex
      }, {
        path: '/api/DocumentIndex/:id/:name/:description/:method/:requestHeader/:responseHeader/:status/:url/:params',
        name: 'documentIndex1',
        component: DocumentIndex
      }
    ]
  }, {
    path: '/',
    name: 'loginIndex',
    component: LoginIndex,
    redirect: '/LoginItem1',
    children: [
      {
        path: 'LoginItem1',
        name: 'loginItem1',
        component: LoginItem1
      }, {
        path: 'LoginItem2',
        name: 'loginItem2',
        component: LoginItem2
      }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

// router.beforeEach((to, from) => {
//   if (to.fullPath === '/LoginItem1' || to.fullPath === '/LoginItem2') {
//     return true
//   }
//   return false
// })

export default router
