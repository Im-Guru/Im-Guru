<template>
  <div class="message-list mt-5">
    <h4><strong>{{this.messageMember}}</strong> 님과 주고 받은 쪽지</h4>

    <div class="mt-4" style="display: flex; justify-content: space-between;">
      <span>상대 [{{ this.messageMember }}]</span>
      <span>나 [{{ this.userNickname }}]</span>
    </div>

    <hr>

    <div>
      <!--      <div v-for="(message, idx) in messageList" :key="idx" class="message-container">-->
      <div v-for="(message, idx) in messageList" :key="idx"
           :class="{'message-container-left': userNickname !== message.senderNickname, 'message-container-right': userNickname === message.senderNickname}"
           class="message-container">
<!--        <div>senderNickname: {{ message.senderNickname }}</div>-->
<!--        <div>receiverNickname: {{ message.receiverNickname }}</div>-->
        <div>{{ message.content }}</div>
        <div class="message-reg-date">{{ formatDateTime(message.regDate) }}</div>

      </div>

      <hr>

      <div class="my-5" style="text-align: center;">
        <h4><i class="fa-solid fa-envelope hover-pointer" @click="toMessageWrite(messageMember)"> 메세지 보내기</i></h4>
      </div>

    </div>

  </div>
</template>

<script>
export default {
  data() { //변수생성
    return {
      userNickname: '',
      messageMember: this.$route.query.member,
      messageList: [],
    }
  },
  mounted() {
    this.fnLoginMember();
    this.fnGetMessageList();
  },
  methods: {
    fnLoginMember() {
      if (localStorage.getItem("user_token") === null) {
        alert("로그인 해야 가능한 서비스입니다.");
        window.location.href = "http://localhost:3000/login";
        return;
      }

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
      this.$axios.get('/api/v1/message/' + this.messageMember, {
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
    toMessageWrite(receiverNickname) {
      if (localStorage.getItem("user_token") === null) {
        alert("로그인 해야 가능한 서비스입니다.");
        window.location.href = "http://localhost:3000/login";
        return;
      }

      this.$router.push({
        path: '/message/write',
        query: {member: receiverNickname}
      })
    },
    formatDateTime(dateTimeStr) {
      const dateTime = new Date(dateTimeStr);
      const now = new Date();
      const timeDifferenceInSeconds = Math.floor((now - dateTime) / 1000);

      if (timeDifferenceInSeconds < 60) {
        return `${timeDifferenceInSeconds}초 전`;
      } else if (timeDifferenceInSeconds < 3600) {
        const minutes = Math.floor(timeDifferenceInSeconds / 60);
        return `${minutes}분 전`;
      } else if (timeDifferenceInSeconds < 86400) {
        const hours = Math.floor(timeDifferenceInSeconds / 3600);
        return `${hours}시간 전`;
      } else {
        const days = Math.floor(timeDifferenceInSeconds / 86400);
        return `${days}일 전`;
      }
    },

  }

}


</script>

<style scoped>
.message-container-left {
  background-color: #e0f7fa; /* 왼쪽 배경색 */
  margin-right: 60%; /* 오른쪽 여백 설정 */
  border-radius: 10px; /* 모서리 둥글게 만들기 */
  padding: 10px; /* 내용과 테두리 사이 여백 */
  margin-bottom: 10px; /* 아래쪽 여백 */
}

.message-container-right {
  background-color: #ffe0b2; /* 오른쪽 배경색 */
  margin-left: 60%; /* 왼쪽 여백 설정 */
  border-radius: 10px; /* 모서리 둥글게 만들기 */
  padding: 10px; /* 내용과 테두리 사이 여백 */
  margin-bottom: 10px; /* 아래쪽 여백 */
}

.message-reg-date {
  color: #888;
  text-align: right;
}
</style>