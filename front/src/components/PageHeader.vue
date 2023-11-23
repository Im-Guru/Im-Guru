<template>
  <header class="header">
    <div id="nav" class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="container">
        <router-link to="/" class="navbar-brand">
          <img :src="require('@/assets/image/imguru_logo.png')" alt="Im Guru 로고" width="100" height="100">
        </router-link>

        <!--        <div class="collapse navbar-collapse" id="navbarNav">-->
        <!--          <ul class="navbar-nav">-->
        <!--            <li class="nav-item">-->
        <!--              <router-link to="/board/list" v-if="this.$store.state.isLogin" class="nav-link">게시판</router-link>-->
        <!--            </li>-->
        <!--            <li class="nav-item">-->
        <!--              <router-link to="/message/list" v-if="this.$store.state.isLogin" class="nav-link">보낸 메세지</router-link>-->
        <!--            </li>-->
        <!--            <li class="nav-item">-->
        <!--              <router-link to="/admin/main" v-if="Role()" class="nav-link">관리자 페이지</router-link>-->
        <!--            </li>-->
        <!--          </ul>-->
        <!--        </div>-->

        <div class="collapse navbar-collapse">
          <ul class="navbar-nav">
            <li class="nav-item">
              <router-link to="/post/list" class="nav-link">전체게시판</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/message/list" class="nav-link">메세지</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/admin/main" class="nav-link"> <!-- v-if="Role()" --> 관리자 페이지</router-link>
            </li>
          </ul>
        </div>


        <div class="d-flex">
          <router-link to="/join/member" v-if="!isLoggedIn" class="nav-link" style="margin-right: 10px;">회원 가입</router-link>
          <router-link to="/join/guru" v-if="!isLoggedIn" class="nav-link" style="margin-right: 10px;">도사 가입</router-link>
          <router-link to="/login" v-if="!isLoggedIn" class="no-underline" style="margin-right: 10px;">로그인</router-link>
          <router-link to="/login" v-if="isLoggedIn" @click="fnLogout" class="no-underline">로그아웃</router-link>
          <router-link to="/member/main" v-if="isLoggedIn" class="no-underline">&nbsp; 마이페이지</router-link>
        </div>
<!--        <div class="d-flex">-->
<!--                    <router-link to="/signup" v-if="!isLoggedIn" class="nav-link" style="margin-right: 10px;">회원 가입-->
<!--                    </router-link>-->
<!--                    <router-link to="/login" v-if="!isLoggedIn" class="no-underline" style="margin-right: 10px;">-->
<!--                      로그인-->
<!--                    </router-link>-->
<!--                    <router-link to="/login" v-if="isLoggedIn" @click="fnLogout" class="no-underline">로그아웃-->
<!--                    </router-link>-->
<!--                    <router-link to="/member/main" v-if="isLoggedIn" class="no-underline">&nbsp; 마이페이지-->
<!--                    </router-link>-->
<!--          <ul class="navbar-nav">-->
<!--            <li class="nav-item">-->
<!--              <router-link to="/join/member" class="nav-link">회원가입</router-link>-->
<!--            </li>-->
<!--            <li class="nav-item">-->
<!--              <router-link to="/join/guru" class="nav-link">도사가입</router-link>-->
<!--            </li>-->
<!--            <li class="nav-item">-->
<!--&lt;!&ndash;              <router-link to="/login" class="nav-link no-underline">로그인</router-link>&ndash;&gt;-->
<!--              <router-link to="/login" v-if="!this.$store.state.isLogin" class="nav-link no-underline">-->
<!--                로그인-->
<!--              </router-link>-->
<!--            </li>-->
<!--            <li class="nav-item">-->
<!--&lt;!&ndash;              <router-link to="/login" @click="fnLogout" class="nav-link no-underline">로그아웃</router-link>&ndash;&gt;-->
<!--              <router-link to="/login" v-if="this.$store.state.isLogin" @click="fnLogout" class="nav-link no-underline">로그아웃-->
<!--              </router-link>-->
<!--            </li>-->
<!--            <li class="nav-item">-->
<!--              <router-link to="/member/main" class="nav-link no-underline">마이페이지</router-link>-->
<!--            </li>-->
<!--            <li class="nav-item">-->
<!--              <router-link to="/guru/main" class="nav-link no-underline">도사페이지</router-link>-->
<!--            </li>-->
<!--          </ul>-->

        </div>

      </div>
<!--    </div>-->
  </header>
</template>

<style scoped>


</style>

<script>

export default {
  data() {
    return {
      role: localStorage.getItem("user_role")
    }
  },
  computed: {
    isLoggedIn() {
      return this.$store.state.isLogin;
    }
  },
  methods: {
    fnLogout() {
      localStorage.removeItem("user_token")
      localStorage.removeItem("user_role")
      localStorage.removeItem("user_expiration")
      location.reload();
      this.$router.push({
        path: '/login',
      })
    },
    Role() {
      if (localStorage.getItem("user_role") === "ROLE_ADMIN") {
        return true;
      } else return false;
    },
  }
}
</script>

<style scoped>

</style>