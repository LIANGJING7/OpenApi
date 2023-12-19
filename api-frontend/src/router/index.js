import { createRouter, createWebHashHistory } from 'vue-router'
import LoginIndex from '@/views/LoginIndex.vue'
import LoginItem1 from '@/components/LoginItem1.vue'
import ManagerIndex from '@/views/ManagerIndex.vue'
import ManagerItem1 from '@/components/ManagerItem1.vue'
import ManagerItem2 from '@/components/ManagerItem2.vue'
import DoucmentIndex from '@/components/DoucmentIndex.vue'
import LoginItem2 from '@/components/LoginItem2.vue'
// import store from '@/store'

const routes = [
  {
    path: '/',
    name: 'login',
    component: LoginIndex,
    redirect: '/loginItem',
    children: [
      {
        path: '/loginItem',
        name: 'loginItem',
        component: LoginItem1
      }, {
        path: '/log',
        name: 'loginItem2',
        component: LoginItem2
      }

    ]
  }, {
    path: '/index',
    name: 'index',
    component: ManagerIndex,
    redirect: '/managerItem2',
    children: [
      {
        path: '/managerItem1',
        name: 'managerItem1',
        component: ManagerItem1
      }, {
        path: '/managerItem2',
        name: 'managerItem2',
        component: ManagerItem2
      }, {
        path: '/doucmentIndex',
        name: 'doucmentIndex',
        component: DoucmentIndex
      }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})
// router.beforeEach((to, from) => {
//   // to 要跳转的路由信息
//   // from 要离开的路由信息
//   // 返回false不跳转。返回true允许跳转
//   // 返回路由对象，则触发重定向 return {name:'home'}
//   if (to.fullPath === '/login' || to.fullPath === '/loginItem' || to.fullPath === '/log') {
//     return true
//   }
//   if (store.state.user.id !== 0 && store.state.user.userAccount !== 'lcj' && store.state.user.userPassword !== 18) {
//     return true
//   }
//   // return { name: 'login' }
//   router.push('/')
// })

export default router
