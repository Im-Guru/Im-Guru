<template>
  <div class="pay-container my-5">
    <h4 class="my-3"><strong>결제</strong></h4>

    <div>
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
              <td class="fw-bold">주문 번호</td>
              <td>
                <b-form-input type="text" name="ordNo" id="ordNo" v-model="payInfo.ordNo" readonly/>
              </td>
            </tr>
            <tr>
              <td class="fw-bold">구매 서비스 명</td>
              <td>
                <b-form-input type="text" name="productNm" id="productNm" v-model="payInfo.productNm" readonly/>
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
              <td class="fw-bold">주문 일자</td>
              <td>
                <b-form-input type="text" id="trDay" name="trDay" v-model="payInfo.trDay" readonly/>
              </td>
            </tr>
            <tr hidden>
              <td class="fw-bold">주문 시간</td>
              <td>
                <b-form-input type="text" name="trTime" id="trTime" v-model="payInfo.trTime" readonly/>
              </td>
            </tr>
            <tr hidden>
              <td>가격</td>
              <td>
                <b-form-input type="text" name="price" id="price" v-model="payInfo.price" readonly/>
              </td>
            </tr>
            <tr hidden>
              <td>도사 닉네임</td>
              <td>
                <b-form-input type="text" name="authorNickname" id="authorNickname" v-model="payInfo.authorNickname" readonly/>
              </td>
            </tr>
            <tr hidden>
              <td>도사 서비스 명</td>
              <td>
                <b-form-input type="text" name="title" id="title" v-model="payInfo.title" readonly />
              </td>
            </tr>
            <tr hidden>
              <td>도사 제공 기술</td>
              <td>
                <b-form-input type="text" name="skillName" id="skillName" v-model="payInfo.skillName" readonly />
              </td>
            </tr>
            <tr hidden>
              <td>게시글Id</td>
              <td>
                <b-form-input type="text" name="postId" id="postId" v-model="payInfo.postId" readonly/>
              </td>
            </tr>
            <tr hidden>
              <td>게시글 카테고리</td>
              <td>
                <b-form-input type="text" name="postCategory" id="postCategory" v-model="payInfo.postCategory" readonly/>
              </td>
            </tr>
            <tr hidden>
              <td>게시글 내용</td>
              <td>
                <b-form-input type="text" name="content" id="content" v-model="payInfo.content" readonly />
              </td>
            </tr>
            <tr hidden>
              <td>도사 여부</td>
              <td>
                <b-form-input type="text" name="isGuru" id="isGuru" v-model="payInfo.isGuru" readonly />
              </td>
            </tr>
            <tr hidden>
              <td>생성일자</td>
              <td>
                <b-form-input type="text" name="regDate" id="regDate" v-model="payInfo.regDate" readonly/>
              </td>
            </tr>
            <tr hidden>
              <td>mercntId</td>
              <td>
                <b-form-input type="text" name="mercntId" id="mercntId" v-model="payInfo.mercntId" readonly/>
              </td>
            </tr>
            <tr hidden>
              <td>trPrice</td>
              <td>
                <b-form-input type="text" id="trPrice" name="trPrice" v-model="payInfo.trPrice" readonly/>
              </td>
            </tr>
            <tr hidden>
              <td>email</td>
              <td>
                <b-form-input type="text" id="email" name="email" v-model="payInfo.email" readonly/>
              </td>
            </tr>
            <tr hidden>
              <td>cphoneNo</td>
              <td>
                <b-form-input type="text" name="cphoneNo" id="cphoneNo" v-model="payInfo.cphoneNo" readonly/>
              </td>
            </tr>
            <tr hidden>
              <td>mercntParam1</td>
              <td>
                <b-form-input type="text" id="mercntParam1" name="mercntParam1" v-model="payInfo.mercntParam1" readonly/>
              </td>
            </tr>
            <tr hidden>
              <td>mercntParam2</td>
              <td>
                <b-form-input type="text" id="mercntParam2" name="mercntParam2" v-model="payInfo.mercntParam2" readonly/>
              </td>
            </tr>
            <tr hidden>
              <td>signature</td>
              <td>
                <b-form-input type="text" id="signature" name="signature" v-model="payInfo.signature" readonly/>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>

    </div>

    <b-form name="payForm" id="payForm" ref="payForm" @submit.prevent="goPayAuth">

      <div>
        <!-- container -->
        <div class="container" hidden>
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
                  <b-form-input type="text" name="hdInfo" id="hdInfo" placeholder="전문정보"
                                v-model="form.hdInfo" ref="hdInfoInput" readonly/>
                </td>
                <td>전문버전</td>
                <td>
                  <b-form-input type="text" name="apiVer" id="apiVer" placeholder="전문버전"
                                v-model="form.apiVer" ref="apiVerInput" readonly/>
                </td>
              </tr>
              <tr>
                <td>처리구분</td>
                <td>
                  <b-form-input type="text" name="processType" id="processType" placeholder="처리구분"
                                v-model="form.processType" ref="processTypeInput" readonly/>
                </td>
                <td>가맹점아이디</td>
                <td>
                  <b-form-input type="text" name="mercntId" id="mercntId" placeholder="가맹점아이디"
                                v-model="form.mercntId" ref="mercntIdInput" readonly/>
                </td>
              </tr>
              <tr>
                <td>주문번호</td>
                <td>
                  <b-form-input type="text" name="ordNo" id="ordNo" placeholder="주문번호"
                                v-model="form.ordNo" ref="ordNoInput"/>
                </td>
                <td>거래금액</td>
                <td>
                  <b-form-input type="text" name="trPricePlain" id="trPricePlain" placeholder="거래금액"
                                v-model="form.trPricePlain" ref="trPricePlainInput"/>
                </td>
              </tr>
              <tr>
                <td>상품명</td>
                <td>
                  <b-form-input type="text" name="productNm" id="productNm" placeholder="상품명"
                                v-model="form.productNm" ref="productNmInput"/>
                </td>
                <td>면세여부[Y/N/G]</td>
                <td>
                  <b-form-input type="text" name="dutyFreeYn" id="dutyFreeYn" placeholder="면세여부"
                                v-model="form.dutyFreeYn" ref="dutyFreeYnInput" readonly/>
                </td>
              </tr>
              <tr>
                <td>결과통보URL</td>
                <td>
                  <b-form-input type="text" name="callbackUrl" id="callbackUrl" placeholder="결과통보URL"
                                v-model="form.callbackUrl" ref="callbackUrlInput" readonly/>
                </td>
                <td>인증창취소URL</td>
                <td>
                  <b-form-input type="text" name="cancelUrl" id="cancelUrl" placeholder="인증창취소URL"
                                v-model="form.cancelUrl" ref="cancelUrlInput" readonly/>
                </td>
              </tr>
              <tr>
                <td>가맹점데이터1(선택)</td>
                <td>
                  <b-form-input type="text" name="mercntParam1" id="mercntParam1" placeholder="가맹점데이터1"
                                v-model="form.mercntParam1"/>
                </td>
                <td>가맹점데이터2(선택)</td>
                <td>
                  <b-form-input type="text" name="mercntParam2" id="mercntParam2" placeholder="가맹점데이터2"
                                v-model="form.mercntParam2"/>
                </td>
              </tr>
              <tr>
                <td>viewType[popup/self]</td>
                <td>
                  <b-form-input type="text" id="viewType" name="viewType" placeholder="결제창 팝업방식"
                                v-model="form.viewType" ref="viewTypeInput" value="form.viewType" readonly/>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- Vue 데이터와 바인딩되는 부분 -->
        <b-form-input v-model="form.email" name="email" hidden/>
        <b-form-input v-model="form.cphoneNo" name="cphoneNo" hidden/>
        <b-form-input v-model="form.trPrice" name="trPrice" hidden/>
        <b-form-input v-model="form.trDay" name="trDay" hidden/>
        <b-form-input v-model="form.trTime" name="trTime" hidden/>
        <b-form-input v-model="form.signature" name="signature" hidden/>

        <!-- fixed bottom button -->
        <div style="text-align: center !important">
          <b-button type="button" id="send" @click="goPayAuth">결제 요청</b-button>
        </div>
        <!-- //fixed bottom button -->
      </div>

    </b-form>
    <br/>
    <br/>
    <br/>
  </div>
</template>

<script>
export default {
  data() {
    return {
      idx: this.$route.query.idx,
      form: {
        hdInfo: 'IA_AUTHPAGE_1.0_1.0',
        apiVer: '1.0',
        processType: 'D',
        mercntId: 'M2266041',
        ordNo: '',
        trPricePlain: '',
        productNm: '',
        dutyFreeYn: 'N',
        callbackUrl: 'http://localhost:3000/api/v1/pay/callback',
        cancelUrl: 'http://localhost:3000/api/v1/pay/cancel',
        mercntParam1: '',
        mercntParam2: '',
        viewType: 'self',

        email: "",
        cphoneNo: "",
        trPrice: "",
        trDay: "",
        trTime: "",
        signature: "",
      },

      payInfo: {
        postId: '',
        authorNickname: '',
        postCategory: '',
        title: '',
        content: '',
        isGuru: '',
        skillName: '',
        price: '',
        regDate: '',

        mercntId: '',
        ordNo: '',
        trDay: '',
        trTime: '',
        trPrice: '',
        productNm: '',
        email: '',
        cphoneNo: '',
        mercntParam1: '',
        mercntParam2: '',
        signature: '',
      }
    };
  },
  computed: {
    // 숫자를 통화 형식으로 포맷팅하는 computed 속성
    formattedPrice() {
      // 숫자를 통화 형식으로 변환 (예: 100,000원)
      return new Intl.NumberFormat('ko-KR', {
        style: 'currency',
        currency: 'KRW',
      }).format(this.payInfo.price);
    },
    formattedDateTime() {
      const date = this.payInfo.trDay || '';
      const time = this.payInfo.trTime || '';

      // 주문 일자와 시간이 모두 존재할 때에만 한국 스타일로 가공
      if (date && time) {
        return `${this.formatKoreanDate(date)} ${this.formatKoreanTime(time)}`;
      } else {
        return '';
      }
    },
  },
  mounted() {
    this.fnGetPayInfo()
  },
  methods: {
    fnGetPayInfo() {
      this.$axios.post('/api/v1/pay/post/' + this.idx, "", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        console.log(res)
        this.payInfo = res.data.data;

        this.form.ordNo = res.data.data.ordNo;
        this.form.trPricePlain = res.data.data.trPrice;
        this.form.productNm = res.data.data.productNm;
        this.form.mercntParam1 = res.data.data.mercntParam1;
        this.form.mercntParam2 = res.data.data.mercntParam2;
        this.form.email = res.data.data.email;
        this.form.cphoneNo = res.data.data.cphoneNo;
        this.form.trPrice = res.data.data.trPrice;
        this.form.trDay = res.data.data.trDay;
        this.form.trTime = res.data.data.trTime;
        this.form.signature = res.data.data.signature;

      }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({path: '/login'});
        } else {
          alert(err.response.data.message);
        }
        this.$store.state.loadingStatus = false;
      })
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

    goPayAuth() {
      if (!this.form.hdInfo) {
        alert("전문정보를 입력해주세요.");
        this.$refs.hdInfoInput.focus();
        return
      }
      if (!this.form.apiVer) {
        alert("전문버전를 입력해주세요.");
        this.$refs.apiVerInput.focus();
        return
      }
      if (!this.form.processType) {
        alert("처리구분을 입력해주세요.");
        this.$refs.processTypeInput.focus();
        return
      }
      if (!this.form.mercntId) {
        alert("가맹점 아이디를 입력해주세요.");
        this.$refs.mercntIdInput.focus();
        return
      }
      if (!this.form.ordNo) {
        alert("주문 번호를 입력해주세요.");
        this.$refs.ordNoInput.focus();
        return
      }
      if (!this.form.trPricePlain) {
        alert("거래 금액을 입력해주세요.");
        this.$refs.trPricePlainInput.focus();
        return
      }
      if (!this.form.productNm) {
        alert("상품 명을 입력해주세요.");
        this.$refs.productNmInput.focus();
        return
      }
      if (!this.form.dutyFreeYn) {
        alert("면세 여부를 입력해주세요.");
        this.$refs.dutyFreeYnInput.focus();
        return
      }
      if (!this.form.callbackUrl) {
        alert("결과 통보 url을 입력해주세요.");
        this.$refs.callbackUrlInput.focus();
        return
      }
      if (!this.form.cancelUrl) {
        alert("결과 취소 url을 입력해주세요.");
        this.$refs.cancelUrlInput.focus();
        return
      }
      if (!this.form.viewType) {
        alert("결제창 방식을 입력해주세요.");
        this.$refs.viewTypeInput.focus();
        return
      }

      this.$axios.post("/api/v1/payReserv", this.form)
          .then((res) => {
            console.log(res)
            const obj = this.$refs.payForm.$el;

            // eslint-disable-next-line
            SettlePay.pay(obj);

          }).catch((err) => {
        console.log(err);
        this.$store.state.loadingStatus = false;
      });

    },

  },
};
</script>

<style>
b-form-input {
  width: 280px;
}
</style>

