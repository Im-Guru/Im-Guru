<template>
  <header class="header">
    <div id="nav" class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="container">
        <router-link to="/" class="navbar-brand">
          <img :src="require('@/assets/image/imguru_logo.png')" alt="Im Guru 로고" width="100" height="100">
        </router-link>

        <div class="collapse navbar-collapse">
          <ul class="navbar-nav">
            <li class="nav-item">
              <router-link to="/post/list" class="nav-link">게시판</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/message/list" class="nav-link">메세지</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/review/write" class="nav-link">후기</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/pay/index" class="nav-link">결제 임시</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/admin/main" class="nav-link" v-if="role === 'ROLE_ADMIN'"> 관리자 페이지</router-link>
            </li>

            <!--            <li class="nav-item">-->
            <!--              <router-link to="/admin/view" class="nav-link"> 관리자 임시</router-link>-->
            <!--            </li>-->
          </ul>
        </div>

        <div class="d-flex">
          <router-link to="/join/member" v-if="!isLoggedIn" class="nav-link" style="margin-right: 10px;">회원 가입
          </router-link>
          <router-link to="/join/guru" v-if="!isLoggedIn" class="nav-link" style="margin-right: 10px;">도사 가입
          </router-link>
          <router-link to="/login" v-if="!isLoggedIn" class="no-underline" style="margin-right: 10px;">로그인</router-link>
          <router-link to="/login" v-if="isLoggedIn" @click="fnLogout" class="no-underline">로그아웃</router-link>
          <router-link to="/member/main" v-if="isLoggedIn" class="no-underline">&nbsp; 마이페이지</router-link>
        </div>
      </div>

    </div>

  </header>
</template>

<style scoped>


</style>

<script>

export default {
  data() {
    return {
      role: '',
    }
  },
  computed: {
    isLoggedIn() {
      return this.$store.state.isLogin;
    }
  },
  mounted() {
    this.fnLoginMember();
  },
  methods: {
    fnLogout() {
      localStorage.removeItem("user_token")
      localStorage.removeItem("user_expiration")
      location.reload();
      this.$router.push({
        path: '/login',
      })
    },

    fnLoginMember() {
      this.$axios.post(`/api/v1/member/myInfo`, "", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        console.log(res);
        this.role = res.data.data.role;
      }).catch((err) => {
        console.log(err);

        // if (err.response.status === 401 || err.response.status === 400) {
        //   alert("로그인을 먼저 해주세요!");
        //   this.$router.push({path: '/login'});
        // }
        // if (err.response.status === 404) {
        //   alert("잘못된 경로입니다.");
        //   alert(err.response.data.message);
        //   location.reload()
        // } else {
        //   alert(err.response.data.message);
        //   location.reload()
        // }
        this.$store.state.loadingStatus = false;
      })
    },

  }
}
</script>

<style scoped>

</style>
