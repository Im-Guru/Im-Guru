<template>
  <div>
    <div class="container my-5">
      <div class="row justify-content-center">
        <div class="col-md-6">
          <h4 class="text-center mb-4"><strong>일반 회원 가입</strong></h4>
          <hr/>
          <b-form @submit.prevent="onSubmit">

            <div class="form-group row mb-3">
              <label for="input-1" class="col-md-4 col-form-label text-md-right"><strong>이메일</strong></label>
              <div class="col-md-8">
                <div class="input-group">
                  <b-form-input v-model="form.email" type="email" id="input-1" placeholder="이메일을 입력해주세요" ref="emailInput" required></b-form-input>
                  <div class="input-group-append">
                    <button class="btn btn-primary" type="button" @click="emailChkBtn(form.email)">중복확인</button>
                  </div>
                </div>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-3" class="col-md-4 col-form-label text-md-right"><strong>비밀번호</strong></label>
              <div class="col-md-8">
                <b-form-input v-model="form.password" type="password" id="input-3"
                              ref="passwordInput" placeholder="비밀번호를 입력해주세요" required></b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-3-1" class="col-md-4 col-form-label text-md-right"><strong>비밀번호 확인</strong></label>
              <div class="col-md-8">
                <b-form-input v-model="form.confirmPassword" type="password" id="input-3-1"
                              ref="confirmPasswordInput" placeholder="비밀번호 확인을 입력해주세요" required></b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-2" class="col-md-4 col-form-label text-md-right"><strong>이름</strong></label>
              <div class="col-md-8">
                <b-form-input v-model="form.name" type="text" id="input-2" placeholder="이름을 입력해주세요"
                              ref="nameInput" required></b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-4" class="col-md-4 col-form-label text-md-right"><strong>닉네임</strong></label>
              <div class="col-md-8">
                <div class="input-group">
                  <b-form-input v-model="form.nickname" type="text" id="input-4" placeholder="닉네임을 입력해주세요" ref="nicknameInput" required></b-form-input>
                  <div class="input-group-append">
                    <button class="btn btn-primary" type="button" @click="nicknameChkBtn(form.nickname)">중복확인</button>
                  </div>
                </div>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-5" class="col-md-4 col-form-label text-md-right"><strong>전화번호</strong></label>
              <div class="col-md-8">
                <div class="input-group">
                  <b-form-input v-model="form.telephone" type="tel" id="input-5" ref="telephoneInput" placeholder="전화번호를 입력해주세요" required></b-form-input>
                  <div class="input-group-append">
                    <button class="btn btn-primary" type="button" @click="telephoneChkBtn(form.telephone)">중복확인</button>
                  </div>
                </div>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-2" class="col-md-4 col-form-label text-md-right"><strong>직업</strong></label>
              <div class="col-md-8">
                <b-form-input v-model="form.job" type="text" id="input-2" placeholder="직업을 입력해주세요"
                              ref="jobInput" required></b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-6" class="col-md-4 col-form-label text-md-right"><strong>주소</strong></label>
              <div class="col-md-8">
                <div class="input-group">
                  <b-form-input v-model="form.zonecode" type="text" id="input-6-1" placeholder="우편번호" ref="addressInput" readonly></b-form-input>
                  <div class="input-group-append">
                    <b-button id="postcode" @click="openPostcode">검색</b-button>
                  </div>
                </div>
                <div class="row mt-2">
                  <div class="col-md-12">
                    <b-form-input v-model="form.roadAddress" type="text" id="input-6-2" placeholder="주소" readonly></b-form-input>
                  </div>
                </div>
                <div class="row mt-2">
                  <div class="col-md-12">
                    <b-form-input v-model="form.detailAddress" type="text" id="input-6-3" placeholder="상세주소"></b-form-input>
                  </div>
                </div>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-7" class="col-md-4 col-form-label text-md-right"><strong>생년 월일</strong></label>
              <div class="col-md-8">
                <b-form-input v-model="form.birthDate" type="date" id="input-7"
                              ref="birthInput" placeholder="생년 월일을 입력해주세요" required></b-form-input>
              </div>
            </div>

            <div class="form-group row mb-4">
              <label for="input-8" class="col-md-4 col-form-label text-md-right"><strong>성별</strong></label>
              <div class="col-md-8 d-flex align-items-center">
                <div class="mr-3">
                  <b-form-radio v-model="form.gender" name="userGender" value="MALE">남성</b-form-radio>
                </div>
                <div>
                  <b-form-radio v-model="form.gender" name="userGender" value="FEMALE">여성
                  </b-form-radio>
                </div>
              </div>
            </div>

            <hr>

            <div class="text-center">
              <b-button type="button" @click="onSubmit" variant="primary" style="margin-right: 10px;">
                가입하기
              </b-button>
              <b-button type="reset" variant="danger">초기화</b-button>
            </div>

          </b-form>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import {options} from 'axios';

export default {
  data() {
    return {
      form: {
        email: '',
        password: '',
        confirmPassword: '',
        name: '',
        nickname: '',
        telephone: '',
        address: '',
        zonecode: '',
        roadAddress: '',
        detailAddress: '',
        birthDate: '',
        gender: 'MALE',
        role: 'USER',
        job: '',
      }
    }
  },
  methods: {
    options,
    openPostcode() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          this.form.zonecode = data.zonecode;
          this.form.roadAddress = data.roadAddress;
        },
      }).open();
    },

    emailChkBtn(email) {
      this.$axios.post(`/api/v1/member/checkEmail/${email}`)
          .then(() => {
            alert("사용 가능한 이메일 입니다.");
            return;
          }).catch((err) => {
            alert(err.response.data.message);
            this.form.email = '';
            this.$refs.emailInput.focus();
            this.$store.state.loadingStatus = false;
            return;
      });
    },

    nicknameChkBtn(nickname) {
      this.$axios.post(`/api/v1/member/checkNickname/${nickname}`)
          .then(() => {
            alert("사용 가능한 닉네임 입니다.");
            return;
          }).catch((err) => {
        alert(err.response.data.message);
        this.form.nickname = '';
        this.$refs.nicknameInput.focus();
        this.$store.state.loadingStatus = false;
        return;
      });
    },

    telephoneChkBtn(telephone) {
      this.$axios.post(`/api/v1/member/checkTelephone/${telephone}`)
          .then(() => {
            alert("사용 가능한 전화번호 입니다.");
            return;
          }).catch((err) => {
        alert(err.response.data.message);
        this.form.telephone = '';
        this.$refs.telephoneInput.focus();
        this.$store.state.loadingStatus = false;
        return;
      });
    },

    onSubmit() {
      if (!this.form.email) {
        alert("이메일을 입력해주세요.");
        this.$refs.emailInput.focus();
        return;
      }
      if (!this.form.name) {
        alert("이름을 입력해주세요.");
        this.$refs.nameInput.focus();
        return;
      }
      if (!this.form.password) {
        alert("비밀번호를 입력해주세요.");
        this.$refs.passwordInput.focus();
        return;
      }
      if(!this.form.confirmPassword) {
        alert("비밀번호 확인을 입력해주세요.");
        this.$refs.confirmPasswordInput.focus();
        return;
      }
      if(this.form.password != this.form.confirmPassword) {
        alert("비밀번호가 일치하지 않습니다. 다시 한번 확인해주세요.");
        this.$refs.confirmPasswordInput.focus();
        return;
      }
      if (!this.form.nickname) {
        alert("닉네임을 입력해주세요.");
        this.$refs.nicknameInput.focus();
        return;
      }
      if (!this.form.telephone) {
        alert("전화번호를 입력해주세요.");
        this.$refs.telephoneInput.focus();
        return;
      }
      if (!this.form.job) {
        alert("직업을 입력해주세요.");
        this.$refs.jobInput.focus();
        return;
      }
      if (!this.form.zonecode) {
        alert("주소를 입력해주세요.");
        this.$refs.addressInput.focus();
        return;
      }
      if (!this.form.birthDate) {
        alert("생년월일을 선택해주세요.");
        this.$refs.birthInput.focus();
        return;
      }


      this.form.address = (this.form.roadAddress + " " + this.form.detailAddress);

      this.$axios.post("/api/v1/join/member", this.form)
          .then((res) => {
            alert(res.data.message)
            this.$router.push({name: 'Login'})
          }).catch((err) => {
        alert(err.response.data.message);
        this.$store.state.loadingStatus = false;
        return;
      });
    }
  }
}
</script>

<style scoped>

</style>
