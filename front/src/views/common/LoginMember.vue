<template>
  <div>
    <div class="container my-5">
      <div class="row justify-content-center">
        <div class="col-md-6">
          <div class="card">
            <div class="card-body">
              <h2 class="card-title text-center"><strong>로그인</strong></h2>
              <hr>

              <form @submit.prevent="fnLogin">
                <div class="form-group row mb-3">
                  <label for="uid" class="col-md-4 col-form-label text-md-right"><strong>아이디</strong></label>
                  <div class="col-md-8">
                    <input id="uid" class="form-control" name="uid" placeholder="아이디를 입력해주세요."
                           ref="idInput" v-model="user_id">
                  </div>
                </div>
                <div class="form-group row mb-3">
                  <label for="password" class="col-md-4 col-form-label text-md-right"><strong>비밀번호</strong></label>
                  <div class="col-md-8">
                    <input id="password" name="password" class="form-control"
                           placeholder="비밀번호를 입력해주세요."
                           ref="passwordInput" v-model="user_pw" type="password">
                  </div>
                </div>
                <hr>

                <div class="form-group text-center mt-3">
                  <button type="submit" class="btn btn-primary btn-block">로그인</button>
                </div>

                <div class="form-group text-center mt-3">
                  <router-link to="/signup">아직 회원이 아니신가요?</router-link>
                </div>

              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<style scoped>
.card {
  border: none;
}

.card-title {
  font-size: 24px;
}

.btn-primary {
  background-color: #007bff;
  border: none;
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
        if (loginResult) this.goToPages()
      } catch (err) {
        alert("로그인 정보가 없습니다. 다시 확인해주세요");
        this.user_pw = '';
        this.$refs.passwordInput.focus();
        this.$store.state.loadingStatus = false;
      }
    },
    goToPages() {
      this.$router.push({
        name: 'PageHome'
      })
    }
  },
  computed: {
    ...mapGetters({
      errorState: 'getErrorState'
    })
  }
}
</script>
