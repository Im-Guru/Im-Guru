<template>
  <div class="pay-container my-5">
    <h4 class="my-3"><strong>결제 최종 승인</strong></h4>

    <b-form name="payForm" id="payForm" ref="payForm" @submit.prevent="goPayApprov">

      <div>
        <div>
          <!-- container -->
          <div class="reply-container">
            <div class="table-responsive">
              <table class="table">
                <colgroup>
                  <col style="width: 25%;">
                  <col>
                </colgroup>
                <thead></thead>
                <tbody>

                <tr>
                  <td class="fw-bold">주문번호</td>
                  <td>
                    <b-form-input type="text" name="ordNo" id="ordNo" v-model="form.ordNo" readonly />
                  </td>
                </tr>
                <tr>
                  <td class="fw-bold">승인번호</td>
                  <td>
                    <b-form-input type="text" name="authNo" id="authNo" v-model="form.authNo" readonly />
                  </td>
                </tr>
                <tr>
                  <td class="fw-bold">가격</td>
                  <td>
                    <b-form-input :value="formattedPrice" readonly/>
                  </td>
                </tr>
                <tr>
                  <td class="fw-bold">주문 시간</td>
                  <td>
                    <b-form-input type="text" readonly :value="formattedDateTime"/>
                  </td>
                </tr>


                <tr hidden>
                  <td>거래금액</td>
                  <td>
                    <b-form-input type="text" name="trPrice" id="trPrice" v-model="form.trPrice" readonly />
                  </td>
                </tr>
                <tr hidden>
                  <td>거래일자</td>
                  <td>
                    <b-form-input type="text" name="trDay" id="trDay" v-model="form.trDay" readonly />
                  </td>
                </tr>
                <tr hidden>
                  <td>거래시간</td>
                  <td>
                    <b-form-input type="text" name="trTime" id="trTime" v-model="form.trTime" readonly />
                  </td>
                </tr>
                <tr hidden>
                  <td>전문정보</td>
                  <td>
                    <b-form-input type="text" name="hdInfo" id="hdInfo" v-model="form.hdInfo" readonly/>
                  </td>
                </tr>
                <tr hidden>
                  <td>전문버전</td>
                  <td>
                    <b-form-input type="text" name="apiVer" id="apiVer" v-model="form.apiVer" readonly/>
                  </td>
                </tr>
                <tr hidden>
                  <td>결과코드</td>
                  <td>
                    <b-form-input type="text" name="resultCd" id="resultCd" v-model="form.resultCd" readonly />
                  </td>
                </tr>
                <tr hidden>
                  <td>결과메세지</td>
                  <td>
                    <b-form-input type="text" name="resultMsg" id="resultMsg" v-model="form.resultMsg" readonly />
                  </td>
                </tr>
                <tr hidden>
                  <td>가맹점아이디</td>
                  <td>
                    <b-form-input type="text" name="mercntId" id="mercntId" v-model="form.mercntId" readonly />
                  </td>
                </tr>
                <tr hidden>
                  <td>할인금액</td>
                  <td>
                    <b-form-input type="text" name="discntPrice" id="discntPrice" v-model="form.discntPrice" readonly />
                  </td>
                </tr>
                <tr hidden>
                  <td>결제금액</td>
                  <td>
                    <b-form-input type="text" name="payPrice" id="payPrice" v-model="form.payPrice" readonly />
                  </td>
                </tr>
                <tr hidden>
                  <td>가맹점데이터1</td>
                  <td>
                    <b-form-input type="text" name="mercntParam1" id="mercntParam1" v-model="form.mercntParam1" readonly />
                  </td>
                </tr>
                <tr hidden>
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
            <b-button type="button" id="send" @click="goPayApprov">결제 요청</b-button>
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
  computed: {
    // 숫자를 통화 형식으로 포맷팅하는 computed 속성
    formattedPrice() {
      // 숫자를 통화 형식으로 변환 (예: 100,000원)
      return new Intl.NumberFormat('ko-KR', {
        style: 'currency',
        currency: 'KRW',
      }).format(this.form.trPrice);
    },
    formattedDateTime() {
      const date = this.form.trDay || '';
      const time = this.form.trTime || '';

      // 주문 일자와 시간이 모두 존재할 때에만 한국 스타일로 가공
      if (date && time) {
        return `${this.formatKoreanDate(date)} ${this.formatKoreanTime(time)}`;
      } else {
        return '';
      }
    },
  },
  methods: {
    goPayApprov() {
      this.$axios.post("/api/v1/payAction", this.form)
          .then((res) => {
            console.log(res);

            if (res.data.resultCd === "0") {
              // alert("결제승인 성공. 거래번호 : " + res.data.trNo);
              alert("결제가 완료되었습니다! 전,도사를 이용해주셔서 감사합니다.");
              this.$router.push({path: '/'});
            } else {
              alert("결제 승인이 실패되었습니다. 다시 시도해주세요." + res.data.resultMsg);
              this.$router.push({path: '/'});
            }

          }).catch((err) => {
        console.log(err);
        this.$store.state.loadingStatus = false;
      });
    },

    formatKoreanDate(date) {
      // 'YYYYMMDD' 형식의 문자열을 'YYYY-MM-DD' 형식으로 변환
      const formattedDate = date.replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
      const options = { year: 'numeric', month: 'long', day: 'numeric' };
      return new Date(formattedDate).toLocaleDateString('ko-KR', options);
    },
    formatKoreanTime(time) {
      // 'hhmmss' 형식의 문자열에서 시, 분, 초 추출
      const match = time.match(/(\d{2})(\d{2})(\d{2})/);

      // 추출한 정보를 시간 형식으로 조합
      if (match) {
        const [, hours, minutes, seconds] = match;
        return `${hours}:${minutes}:${seconds}`;
      } else {
        return time;  // 정규식 매치 실패 시 그대로 반환
      }
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
