<template>

  <div id="app">
    <h1>결제인증</h1>
    <!-- ... 나머지 HTML 코드 유지 ... -->

    <!-- Vue.js로 바인딩할 부분 -->
    <div class="fix_btm_btn">
      <button type="button" class="btn btn_lg" @click="goPayAuth">결제인증요청</button>
    </div>
    <!-- //fixed bottom button -->
  </div>

  <!-- ... 나머지 HTML 코드 유지 ... -->
</template>

<script>
export default {
  data() {
    return {
      hdInfo: '',
      apiVer: '',
      mercntId: '',
      ordNo: '',
      trPricePlain: '',
      productNm: '',
      dutyFreeYn: '',
      callbackUrl: '',
      trPrice: '',
      trDay: '',
      trTime: '',
      signature: ''
    }
  },
  methods: {
    // Vue.js에서 사용할 메서드 선언
    goPayAuth() {
      // Vue.js 데이터를 사용하여 AJAX 요청 등을 수행
      // this.hdInfo, this.apiVer 등은 Vue.js 데이터로부터 가져온 값입니다.
      // 이 부분을 기존의 goPayAuth 함수에 통합하거나 사용자의 요구에 따라 수정
      console.log('Vue.js 데이터 사용 예시:', this.hdInfo, this.apiVer, /* ... */);

      // new window.SettlePay.pay({
      //
      // }).open();

    }
  }
}
</script>

