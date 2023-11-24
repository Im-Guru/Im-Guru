import { createRouter, createWebHistory } from 'vue-router'
import store from "@/vuex/store";

import PageHome from '@/views/common/PageHome.vue'
import JoinGuru from "@/views/common/JoinGuru.vue";
import JoinMember from "@/views/common/JoinMember.vue";
import LoginMember from "@/views/common/LoginMember.vue";

import MemberMain from "@/views/member/MemberMain.vue";
import MemberView from "@/views/member/MemberView.vue";
import MemberUserView from "@/views/member/MemberUserView.vue";
import MemberGuruView from "@/views/member/MemberGuruView.vue";
import MemberUserDetail from "@/views/member/MemberUserDetail.vue";
import MemberGuruDetail from "@/views/member/MemberGuruDetail.vue";
import GuruInfoWrite from "@/views/member/GuruInfoWrite.vue";

import PostList from "@/views/post/PostList.vue";
import PostDetail from "@/views/post/PostDetail.vue";
import PostWrite from "@/views/post/PostWrite.vue";

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


    // Member - mypage
    {
        path: '/member/main',
        name: 'MemberMain',
        component: MemberMain,
        beforeEnter: requireAuth()
    },
    {
        path: '/member/view',
        name: 'MemberView',
        component: MemberView
    },
    {
        path: '/member/userView',
        name: 'MemberUserView',
        component: MemberUserView
    },
    {
        path: '/member/guruView',
        name: 'MemberGuruView',
        component: MemberGuruView
    },
    {
        path: '/member/userDetail',
        name: 'MemberUserDetail',
        component: MemberUserDetail,
        beforeEnter: requireAuth()
    },
    {
        path: '/member/guruDetail',
        name: 'MemberGuruDetail',
        component: MemberGuruDetail,
        beforeEnter: requireAuth()
    },
    {
        path: '/guru/write',
        name: 'GuruInfoWrite',
        component: GuruInfoWrite,
        beforeEnter: requireAuth()
    },





    // Post
    {
        path: '/post/list',
        name: 'PostList',
        component: PostList
    },
    {
        path: '/post/detail',
        name: 'PostDetail',
        component: PostDetail
    },
    {
        path: '/post/write',
        name: 'PostWrite',
        component: PostWrite,
        beforeEnter: requireAuth()
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