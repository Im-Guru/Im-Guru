<template>
  <div>
    <h1>결제 목록</h1>

    <hr>

    <div>
      <div class="container">
        <div class="table-responsive">
          <table class="table">
            <colgroup>
              <col>
              <col>
              <col>
              <col>
            </colgroup>
            <thead>
            <tr>
              <th>ID</th>
              <th>주문번호</th>
              <th>승인번호</th>
              <th>거래번호</th>
              <th>상태</th>
              <th></th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="pay in payInfo" :key="pay.id">
              <td>{{ pay.id }}</td>
              <td>{{ pay.ordNo }}</td>
              <td>{{ pay.authNo }}</td>
              <td>{{ pay.trNo }}</td>
              <td v-if="pay.payStatus === 'P'">결제완료</td>
              <td v-else-if="pay.payStatus === 'C'">결제취소완료</td>
              <td v-if="pay.payStatus === 'P'">
                <button type="button" @click="goCancel(pay.ordNo, pay.trNo)">결제취소</button>
              </td>
              <td v-else></td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>

    </div>


    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
  </div>
</template>

<script>
export default {
  data() {
    return {
      payInfo: [],
    };
  },
  mounted() {
    this.fnGetPayInfo()
  },
  methods: {
    fnGetPayInfo() {
      this.$axios.post('/api/v1/pay/list/all').then((res) => {
        console.log(res)
        this.payInfo = res.data.data;
      }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({path: '/login'});
        } else {
          alert(err.response.data.message);
        }
        this.$store.state.loadingStatus = false;
      })
    },

    goCancel(ordNo, trNo) {
      this.$axios.post(`/api/v1/payCancel/${ordNo}/${trNo}`)
          .then((res) => {
            console.log(res.data);

            if(res.data.resultCd === "0"){
              alert("결제 취소 성공");
              location.reload();
            }else{
              alert("결제 취소 실패 : " + res.data.resultMsg);
            }

          }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({path: '/login'});
        } else {
          alert(err.response.data.message);
        }
        this.$store.state.loadingStatus = false;
      })
    },

  },
};
</script>

<style>
b-form-input {
  width: 280px;
}
</style>

