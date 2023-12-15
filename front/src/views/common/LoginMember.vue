<template>
  <div class="container mt-5 mb-5">
    <h2><strong>로그인</strong></h2><br><br>
    <div class="row justify-content-center">
      <div class="col-md-5">
        <b-card class="mb-5">
          <b-form @submit.prevent="fnLogin" class="form-width mt-3" style="margin: auto;">
            <div class="mb-4">
              <label for="uid" class="form-label"><strong>아이디</strong></label>
              <b-form-input class="form-height" id="uid" name="uid" v-model="user_id"
                            ref="idInput"
                            placeholder="이메일을 입력해주세요" required>
              </b-form-input>
            </div>

            <div class="mb-4">
              <label for="password" class="form-label"><strong>비밀번호</strong></label>
              <b-form-input class="form-height" id="password" name="password" v-model="user_pw"
                            placeholder="비밀번호를 입력해주세요."
                            ref="passwordInput" type="password" required>
              </b-form-input>
            </div>

            <br>
            <b-button @click="fnLogin" class="button-width form-height">로그인</b-button>
            <br><br>
            <router-link to="/join/member" class="no-underline">아직 회원이 아니신가요?</router-link>
            &nbsp; &nbsp;
            <router-link to="/find-password" class="no-underline">비밀번호 찾기</router-link>

          </b-form>
        </b-card>
      </div>
    </div>
  </div>
</template>


<style scoped>
.mb-4 label {
  text-align: left;
  display: block;
  margin-bottom: 0.5rem;
}

.mb-4 b-input-group {
  display: flex;
  align-items: center;
}

.form-height {
  height: 50px;
}

.form-width {
  width: 80%;
}

.button-width {
  width: 100%;
}
</style>

<script>
import {mapActions, mapGetters} from 'vuex'   //vuex 추가

export default {
  data() {
    return {
      user_id: '',
      user_pw: ''
    }
  },
  methods: {
    ...mapActions(['login']),     //vuex/actions에 있는 login 함수

    async fnLogin() {       //async 함수로 변경
      if (this.user_id === '') {
        alert('아이디를 입력해주세요.');
        this.$refs.idInput.focus();
        return;
      }
      if (this.user_pw === '') {
        alert('비밀번호를 입력해주세요.');
        this.$refs.passwordInput.focus();
        return;
      }

      try {
        let loginResult = await this.login({user_id: this.user_id, user_pw: this.user_pw})
        if (loginResult) {
          this.goToPages();
        }
      } catch (err) {
        alert("로그인 정보가 없습니다. 다시 확인해주세요");
        this.user_pw = '';
        this.$refs.passwordInput.focus();
        this.$store.state.loadingStatus = false;
      }
    },
    goToPages() {
      this.$store.state.isLogin = true;
      // this.$router.push({
      //   name: 'PageHome'
      // })
      window.location.href = "http://localhost:3000";
    }
  },
  computed: {
    ...mapGetters({
      errorState: 'getErrorState'
    })
  }
}
</script>
