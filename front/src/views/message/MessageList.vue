<template>
  <div class="message-list mt-5">

    <h1>Message List Page</h1>
    <div>{{this.userNickname}} 님 쪽지함 목록</div>
    <hr>

    <div>
      <div v-for="(message, idx) in messageList" :key="idx" class="message-container">
        <div class="message-detail hover-pointer" @click="fnMessageDetail(message.memberNickname)">
          <strong>[{{ message.memberNickname }}]</strong>
          <br>
          <span class="small-font">전문 기술: {{ message.memberSkill }}</span>
        </div>
      </div>
    </div>

  </div>
</template>
<script>

export default {

  data() { //변수생성
    return {
      userNickname: '',

      messageList: [],

    }
  },
  mounted() {
    this.fnLoginMember();
    this.fnGetMessageList();
  },
  methods: {
    fnLoginMember() {
      this.$axios.post(`/api/v1/member/myInfo`, "", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        console.log(res);

        this.userNickname = res.data.data.nickname;
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
        // this.$store.state.loadingStatus = false;
      })
    },
    fnGetMessageList() {
      this.$axios.post('/api/v1/message/list', "", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        console.log(res.data.data);
        this.messageList = res.data.data
      }).catch((err) => {
        alert(err.response.data.message);
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({path: '/login'});
        } else {
          alert(err.response.data.message);
          location.reload()
        }
        this.$store.state.loadingStatus = false;
      })
    },

    fnMessageDetail(member) {
      this.$router.push({
        path: '../message/detail',
        query: {member}
      })
    },

  }

}


</script>

<style scoped>
</style>