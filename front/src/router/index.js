import { createRouter, createWebHistory } from 'vue-router'
import store from "@/vuex/store";

import PageHome from '@/views/common/PageHome.vue'
import JoinGuru from "@/views/common/JoinGuru.vue";
import JoinMember from "@/views/common/JoinMember.vue";
import LoginMember from "@/views/common/LoginMember.vue";

import PostList from "@/views/post/PostList.vue";

import MessageList from "@/views/message/MessageList.vue";


const requireAuth = () => (from, to, next) => {
    const token = localStorage.getItem('user_token')
    if (token) {
        store.state.isLogin = true
        return next()
    } // isLogin === true면 페이지 이동
    next('/login') // isLogin === false면 다시 로그인 화면으로 이동
}

const routes = [
    // common
    {
        path: '/',
        name: 'PageHome',
        component: PageHome
    },
    {
        path: '/about',
        name: 'About',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/common/PageAbout.vue')
    },
    {
        path: '/join/member',
        name: 'JoinMember',
        component: JoinMember
    },
    {
        path: '/join/guru',
        name: 'JoinGuru',
        component: JoinGuru
    },
    {
        path: '/login',
        name: 'LoginMember',
        component: LoginMember
    },



    // Post
    {
        path: '/post/list',
        name: 'PostList',
        component: PostList
    },



    // Message
    {
        path: '/message/list',
        name: 'MessageList',
        component: MessageList,
        beforeEnter: requireAuth()
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router