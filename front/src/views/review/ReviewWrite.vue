<template>

  <div class="container my-5 col-8">
    <div class="review-header">
      <h2><strong>후기</strong></h2>
    </div>

    <hr class="review-divider">

    <div class="review-input">
      <div class="form-group row align-items-center">

        <div class="row">
          <label for="guruNickname" class="col-md-2 col-form-label"><strong>도사 명: </strong></label>
          <div class="col-md-4">
            <input type="text" id="guruNickname" v-model="guruNickname" class="form-control" readonly>
          </div>
          <label for="categoryName" class="col-md-2 col-form-label"><strong>도사 전문 분야: </strong></label>
          <div class="col-md-4">
            <input type="text" id="guruSkill" v-model="guruSkill" class="form-control" readonly>
          </div>
        </div>

        <div class="row mt-3">
          <label for="userNickname" class="col-md-2 col-form-label"><strong>사용자 명: </strong></label>
          <div class="col-md-4">
            <input type="text" id="userNickname" v-model="userNickname" class="form-control" readonly>
          </div>

          <label for="rate" class="col-md-2 col-form-label"><strong>별점: </strong></label>
          <div class="col-md-4">
            <!--            <input type="text" id="rate" v-model="rate" class="form-control">-->
            <div class="star-rating">
              <div class="star" id="rate" v-for="index in 5" :key="index" @click="check(index)">
                <span v-if="index < rate">🍎</span>
                <span v-if="index >= rate">🍏</span>
              </div>
            </div>
          </div>
        </div>

        <label for="payId" class="col-md-2 col-form-label" hidden><strong>결제 ID: </strong></label>
        <div class="col-md-4" hidden>
          <input type="text" id="payId" v-model="payId" class="form-control" readonly>
        </div>

      </div>
    </div>

    <div class="review-input mt-3">
      <div class="form-group row align-items-center">
        <label for="content" class="col-md-2 col-form-label"><strong>후기 내용: </strong></label>
        <div class="col-md-10">
          <textarea id="content" cols="40" rows="10" v-model="content" class="form-control"
                    style="resize: none;" placeholder="후기 내용을 입력해주세요." required></textarea>
        </div>
      </div>
    </div>

    <div class="review-buttons mt-3 d-flex justify-content-end">
      <button type="button" class="btn btn-primary btn-rounded" @click="fnSave">보내기</button>
    </div>

  </div>
</template>

<script>
export default {
  name: "reviewWrite",

  data() { //변수생성
    return {
      guruNickname: '',
      guruSkill: '',
      userNickname: '',
      payId: '',
      content: '',
      rate: '',
    }
  },
  mounted() {
    this.fnLoginMember();
  },
  methods: {
    check(index) {
      this.rate = index + 1;
    },
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

  }
}

</script>

<style>
.star {
  display: inline-block; /* or inline-flex */
  margin-right: 5px; /* Adjust the margin as needed */
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
}
</style>

