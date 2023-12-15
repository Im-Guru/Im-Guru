<template>
  <div>
    <h1>결제 Callback</h1>

    <b-form name="payForm" id="payForm" ref="payForm" @submit.prevent="goPayApprov">

      <div>
        <div>
          <!-- container -->
          <div class="container">
            <div class="table-responsive">
              <table class="table">
                <colgroup>
                  <col style="width: 15%; background-color: lightgray;">
                  <col style="width: 35%;">
                  <col style="width: 15%; background-color: lightgray;">
                  <col style="width: 35%;">
                </colgroup>
                <thead>
                <tr>
                  <th>항목</th>
                  <th>값</th>
                  <th>항목</th>
                  <th>값</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                  <td>전문정보</td>
                  <td>
                    <b-form-input type="text" name="hdInfo" id="hdInfo" v-model="form.hdInfo" readonly/>
                  </td>
                  <td>전문버전</td>
                  <td>
                    <b-form-input type="text" name="apiVer" id="apiVer" v-model="form.apiVer" readonly/>
                  </td>
                </tr>
                <tr>
                  <td>결과코드</td>
                  <td>
                    <b-form-input type="text" name="resultCd" id="resultCd" v-model="form.resultCd" readonly />
                  </td>
                  <td>결과메세지</td>
                  <td>
                    <b-form-input type="text" name="resultMsg" id="resultMsg" v-model="form.resultMsg" readonly />
                  </td>
                </tr>
                <tr>
                  <td>가맹점아이디</td>
                  <td>
                    <b-form-input type="text" name="mercntId" id="mercntId" v-model="form.mercntId" readonly />
                  </td>
                  <td>거래금액</td>
                  <td>
                    <b-form-input type="text" name="trPrice" id="trPrice" v-model="form.trPrice" readonly />
                  </td>
                </tr>
                <tr>
                  <td>할인금액</td>
                  <td>
                    <b-form-input type="text" name="discntPrice" id="discntPrice" v-model="form.discntPrice" readonly />
                  </td>
                  <td>결제금액</td>
                  <td>
                    <b-form-input type="text" name="payPrice" id="payPrice" v-model="form.payPrice" readonly />
                  </td>
                </tr>
                <tr>
                  <td>주문번호</td>
                  <td>
                    <b-form-input type="text" name="ordNo" id="ordNo" v-model="form.ordNo" readonly />
                  </td>
                  <td>승인번호</td>
                  <td>
                    <b-form-input type="text" name="authNo" id="authNo" v-model="form.authNo" readonly />
                  </td>
                </tr>
                <tr>
                  <td>거래일자</td>
                  <td>
                    <b-form-input type="text" name="trDay" id="trDay" v-model="form.trDay" readonly />
                  </td>
                  <td>거래시간</td>
                  <td>
                    <b-form-input type="text" name="trTime" id="trTime" v-model="form.trTime" readonly />
                  </td>
                </tr>
                <tr>
                  <td>가맹점데이터1</td>
                  <td>
                    <b-form-input type="text" name="mercntParam1" id="mercntParam1" v-model="form.mercntParam1" readonly />
                  </td>
                  <td>가맹점데이터2</td>
                  <td>
                    <b-form-input type="text" name="mercntParam2" id="mercntParam2" v-model="form.mercntParam2" readonly />
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
          <!-- //container -->

          <!-- fixed bottom button -->
          <div class="fix_btm_btn">
            <button type="button" id="send" class="btn btn_lg" @click="goPayApprov">결제승인요청</button>
          </div>
          <!-- //fixed bottom button -->
        </div>

      </div>

    </b-form>
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
      form: {
        hdInfo: 'IA_APPROV',
        apiVer: '3.0',
        resultCd: '',
        resultMsg: '',
        mercntId: 'M2266041',
        trPrice: '',
        discntPrice: '',
        payPrice: '',
        ordNo: '',
        authNo: '',
        trDay: "",
        trTime: "",
        mercntParam1: '',
        mercntParam2: '',

      },

    };
  },
  mounted() {
    this.handleCallbackData();
  },
  methods: {
    goPayApprov() {
      this.$axios.post("/api/v1/payAction", this.form)
          .then((res) => {
            console.log(res);

            if (res.data.resultCd === "0") {
              alert("결제승인 성공. 거래번호 : " + res.data.trNo);
              this.$router.push({path: '/'});
            } else {
              alert("결제승인 실패. " + res.data.resultMsg);
              this.$router.push({path: '/'});
            }

          }).catch((err) => {
        console.log(err);
        this.$store.state.loadingStatus = false;
      });
    },

    handleCallbackData() {
      // 페이지 로드 시에만 리다이렉션 수행
      if (this.$route.query.redirected !== 'true') {

        // 라우트 쿼리 파라미터에서 값을 가져와서 form에 설정
        this.form.resultCd = this.$route.query.resultCd || '';
        this.form.resultMsg = this.$route.query.resultMsg || '';
        this.form.mercntId = this.$route.query.mercntId || '';
        this.form.trPrice = this.$route.query.trPrice || '';
        this.form.discntPrice = this.$route.query.discntPrice || '';
        this.form.payPrice = this.$route.query.payPrice || '';
        this.form.ordNo = this.$route.query.ordNo || '';
        this.form.authNo = this.$route.query.authNo || '';
        this.form.trDay = this.$route.query.trDay || '';
        this.form.trTime = this.$route.query.trTime || '';
        this.form.mercntParam1 = this.$route.query.mercntParam1 || '';
        this.form.mercntParam2 = this.$route.query.mercntParam2 || '';

      }
    },
  },


};
</script>

<style>
b-form-input {
  width: 280px;
}
</style>

