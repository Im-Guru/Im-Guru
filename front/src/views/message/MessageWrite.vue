<template>
  <div class="container my-5 col-8">
    <div class="message-header">
      <h2><strong>쪽지 보내기</strong></h2>
    </div>

    <hr class="message-divider">

    <div class="message-input">
      <div class="form-group row align-items-center">
        <label for="receiverNickname" class="col-md-2 col-form-label"><strong>받는 사람:</strong></label>
        <div class="col-md-4">
          <input type="text" id="receiverNickname" v-model="receiverNickname" class="form-control message-text-input" readonly>
        </div>

        <label for="senderNickname" class="col-md-2 col-form-label"><strong>보내는 사람:</strong></label>
        <div class="col-md-4">
          <input type="text" id="senderNickname" v-model="senderNickname" class="form-control message-text-input" readonly>
        </div>
      </div>
    </div>

    <div class="message-input mt-3">
      <div class="form-group row align-items-center">
        <label for="content" class="col-md-2 col-form-label"><strong>내용:</strong></label>
        <div class="col-md-10">
          <textarea id="content" cols="40" rows="10" v-model="content" class="form-control message-text-input" style="resize: none;"
                    placeholder="내용을 입력해주세요." ref="contentInput" required></textarea>
        </div>
      </div>
    </div>

    <div class="message-buttons mt-3 d-flex justify-content-end">
      <button type="button" class="btn btn-primary btn-rounded" @click="fnSave()">보내기</button>
    </div>
  </div>

</template>

<script>
export default {
  name: "MessageWrite",

  data() { //변수생성
    return {
      content: '',
      receiverNickname: this.$route.query.member,
      senderNickname: '',
    }
  },
  mounted() {
    this.fnLoginMember();
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
        this.senderNickname = res.data.data.nickname;
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
    fnSave() {
      if (!this.content) {
        alert("내용을 입력해주세요.");
        this.$refs.contentInput.focus();
        return;
      }
      this.form = {
        "content": this.content,
        "receiverNickname": this.receiverNickname,
        "senderNickname": this.senderNickname,
      };

      if (this.idx === undefined) {
        //INSERT
        this.$axios.post("/api/v1/message", this.form, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('user_token')}`
          }
        }).then((res) => {
          console.log(res);
          alert("메세지 전송이 완료되었습니다.");
          this.$router.push({
            path: '../message/detail',
            query: { member: this.form.receiverNickname }  // Use the receiverNickname from the form
          })
        }).catch((err) => {
          if (err.response.status === 401 || err.response.status === 404) {
            this.$router.push({ path: '/login' });
          } else {
            alert(err.response.data.message);
            location.reload()
          }
        })
      }
    },
  }
}
</script>