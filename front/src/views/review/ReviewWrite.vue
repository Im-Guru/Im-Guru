<template>
  <div class="container my-5 col-8">
    <div class="report-header">
      <h2><strong>후기</strong></h2>
    </div>

    <hr class="report-divider">





  </div>
</template>


<script>
export default {
  name: "ReportWrite",

  data() { //변수생성
    return {

    }
  },
  mounted() {
    this.fnLoginMember();
  },
  methods: {
    fnLoginMember() {
      // this.$axios.post(`/api/v1/member/myInfo`, "", {
      //   headers: {
      //     Authorization: `Bearer ${localStorage.getItem('user_token')}`
      //   }
      // }).then((res) => {
      //   console.log(res);
      //
      //   this.memberNickname = res.data.data.nickname;
      // }).catch((err) => {
      //   console.log(err);
      //
      //   // if (err.response.status === 401 || err.response.status === 400) {
      //   //   alert("로그인을 먼저 해주세요!");
      //   //   this.$router.push({path: '/login'});
      //   // }
      //   // if (err.response.status === 404) {
      //   //   alert("잘못된 경로입니다.");
      //   //   alert(err.response.data.message);
      //   //   location.reload()
      //   // } else {
      //   //   alert(err.response.data.message);
      //   //   location.reload()
      //   // }
      //   // this.$store.state.loadingStatus = false;
      // })
    },

  }
}

</script>