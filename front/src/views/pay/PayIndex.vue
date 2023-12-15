<template>
  <div>
    <h1>결제인증</h1>

    <hr>

    <div>
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
              <td>게시글Id</td>
              <td>
                <b-form-input type="text" name="postId" id="postId" v-model="payInfo.postId" readonly/>
              </td>
              <td>게시글 작성자</td>
              <td>
                <b-form-input type="text" name="authorNickname" id="authorNickname" v-model="payInfo.authorNickname" readonly/>
              </td>
            </tr>
            <tr>
              <td>게시글 카테고리</td>
              <td>
                <b-form-input type="text" name="postCategory" id="postCategory" v-model="payInfo.postCategory" readonly/>
              </td>
              <td>게시글 제목</td>
              <td>
                <b-form-input type="text" name="title" id="title" v-model="payInfo.title" readonly />
              </td>
            </tr>
            <tr>
              <td>게시글 내용</td>
              <td>
                <b-form-input type="text" name="content" id="content" v-model="payInfo.content" readonly />
              </td>
              <td>도사 여부</td>
              <td>
                <b-form-input type="text" name="isGuru" id="isGuru" v-model="payInfo.isGuru" readonly />
              </td>
            </tr>
            <tr>
              <td>도사 기술</td>
              <td>
                <b-form-input type="text" name="skillName" id="skillName" v-model="payInfo.skillName" readonly />
              </td>
              <td>가격</td>
              <td>
                <b-form-input type="text" name="price" id="price" v-model="payInfo.price" readonly/>
              </td>
            </tr>
            <tr>
              <td>생성일자</td>
              <td>
                <b-form-input type="text" name="regDate" id="regDate" v-model="payInfo.regDate" readonly/>
              </td>
              <td></td>
              <td></td>
            </tr>
            <tr>
              <td>mercntId</td>
              <td>
                <b-form-input type="text" name="mercntId" id="mercntId" v-model="payInfo.mercntId" readonly/>
              </td>
              <td>ordNo</td>
              <td>
                <b-form-input type="text" name="ordNo" id="ordNo" v-model="payInfo.ordNo" readonly/>
              </td>
            </tr>
            <tr>
              <td>trDay</td>
              <td>
                <b-form-input type="text" id="trDay" name="trDay" v-model="payInfo.trDay" readonly/>
              </td>
              <td>trTime</td>
              <td>
                <b-form-input type="text" name="trTime" id="trTime" v-model="payInfo.trTime" readonly/>
              </td>
            </tr>
            <tr>
              <td>trPrice</td>
              <td>
                <b-form-input type="text" id="trPrice" name="trPrice" v-model="payInfo.trPrice" readonly/>
              </td>
              <td>productNm</td>
              <td>
                <b-form-input type="text" name="productNm" id="productNm" v-model="payInfo.productNm" readonly/>
              </td>
            </tr>
            <tr>
              <td>email</td>
              <td>
                <b-form-input type="text" id="email" name="email" v-model="payInfo.email" readonly/>
              </td>
              <td>cphoneNo</td>
              <td>
                <b-form-input type="text" name="cphoneNo" id="cphoneNo" v-model="payInfo.cphoneNo" readonly/>
              </td>
            </tr>
            <tr>
              <td>mercntParam1</td>
              <td>
                <b-form-input type="text" id="mercntParam1" name="mercntParam1" v-model="payInfo.mercntParam1" readonly/>
              </td>
              <td>mercntParam2</td>
              <td>
                <b-form-input type="text" id="mercntParam2" name="mercntParam2" v-model="payInfo.mercntParam2" readonly/>
              </td>
            </tr>
            <tr>
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

    <hr>

    <b-form name="payForm" id="payForm" ref="payForm" @submit.prevent="goPayAuth">

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
        <div class="fix_btm_btn">
          <button type="button" id="send" class="btn btn_lg" @click="goPayAuth">결제인증요청</button>
        </div>
        <!-- //fixed bottom button -->
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
        // callbackUrl: window.location.href + '/callback',
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

