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

import ReportPostWrite from "@/views/report/ReportPostWrite.vue";
import ReportReplyWrite from "@/views/report/ReportReplyWrite.vue";

import MessageList from "@/views/message/MessageList.vue";

import ReviewWrite from "@/views/review/ReviewWrite.vue";

import AdminView from "@/views/admin/AdminView.vue";
import AdminMain from "@/views/admin/AdminMain.vue";
import AdminSkill from "@/views/admin/AdminSkill.vue";
import AdminReportReply from "@/views/admin/AdminReportReply.vue";
import AdminReportPost from "@/views/admin/AdminReportPost.vue";
import AdminMemberPost from "@/views/admin/AdminMemberPost.vue";
import AdminMemberReply from "@/views/admin/AdminMemberReply.vue";
import AdminMemberPostDetail from "@/views/admin/AdminMemberPostDetail.vue";


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


    // Report
    {
        path: '/report/post',
        name: 'ReportPostWrite',
        component: ReportPostWrite,
    },
    {
        path: '/report/reply',
        name: 'ReportReplyWrite',
        component: ReportReplyWrite,
    },


    // Message
    {
        path: '/message/list',
        name: 'MessageList',
        component: MessageList,
        beforeEnter: requireAuth()
    },


    // Review
    {
        path: '/review/write',
        name: 'ReviewWrite',
        component: ReviewWrite,
        beforeEnter: requireAuth
    },


    // Admin
    {
        path: '/admin/view',
        name: 'AdminView',
        component: AdminView,
        beforeEnter: requireAuth
    },
    {
        path: '/admin/main',
        name: 'AdminMain',
        component: AdminMain,
        beforeEnter: requireAuth
    },
    {
        path: '/admin/reportPost/list',
        name: 'AdminReportPost',
        component: AdminReportPost,
        beforeEnter: requireAuth
    },
    {
        path: '/admin/reportReply/list',
        name: 'AdminReportReply',
        component: AdminReportReply,
        beforeEnter: requireAuth
    },
    {
        path: '/admin/skill/list',
        name: 'AdminSkill',
        component: AdminSkill,
        beforeEnter: requireAuth
    },
    {
        path: '/admin/member/post',
        name: 'AdminMemberPost',
        component: AdminMemberPost,
        beforeEnter: requireAuth
    },
    {
        path: '/admin/member/reply',
        name: 'AdminMemberReply',
        component: AdminMemberReply,
        beforeEnter: requireAuth
    },
    {
        path: '/admin/member/post/detail',
        name: 'AdminMemberPostDetail',
        component: AdminMemberPostDetail,
        beforeEnter: requireAuth
    },

]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router