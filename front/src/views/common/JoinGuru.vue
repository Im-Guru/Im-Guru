<template>
  <div>
    <div class="container mt-5">
      <h3><strong>도사님, 전-도사에 오신 것을 환영합니다.</strong></h3><br><br>
      <div class="row justify-content-center">
        <div class="col-md-5">
          <b-card class="mb-5">
            <b-form @submit.prevent="onSubmit" class="form-width mt-3" style="margin: auto;">
              <div class="mb-4">
                <label for="input-1" class="form-label"><strong>이메일</strong></label>
                <b-input-group class="form-height">
                  <b-form-input v-model="form.email" type="email" id="input-1" placeholder="이메일을 입력해주세요"
                                ref="emailInput" required :readonly="emailInputReadonly"></b-form-input>
                  <b-button class="btn btn-primary" type="button" @click="emailChkBtn(form.email)">중복확인</b-button>
                </b-input-group>

                <!-- Display a warning if the email format is invalid -->
                <div v-if="!isValidEmail(form.email) && form.email" class="mt-1">
                  <strong style="color: red;"><small>올바른 이메일 형식이 아닙니다. 다시 확인해주세요.</small></strong>
                </div>

                <div v-if="isValidEmail(form.email) && emailCheckResult !== 'valid'" class="mt-1">
                  <strong style="color: blue;"><small>올바른 이메일 형식입니다! 중복확인을 해주세요.</small></strong>
                </div>

                <div v-if="emailCheckResult === 'valid'" class="mt-1">
                  <strong style="color: green;"><small>중복확인이 완료되었습니다.</small></strong>
                </div>
              </div>

              <div class="mb-4">
                <label for="input-2" class="form-label text-md-right"><strong>비밀번호</strong></label>
                <b-form-input class="form-height" v-model="form.password" type="password" id="input-3"
                              ref="passwordInput" placeholder="비밀번호를 입력해주세요" required></b-form-input>
              </div>

              <div class="mb-4">
                <label for="input-2-1" class="form-label text-md-right"><strong>비밀번호 확인</strong></label>
                <b-form-input class="form-height" v-model="form.confirmPassword" type="password" id="input-3-1"
                              ref="confirmPasswordInput" placeholder="비밀번호 확인을 입력해주세요" required></b-form-input>

                <div v-if="!isValidPwConfirm(form.password, form.confirmPassword) && form.password && form.confirmPassword" class="mt-1">
                  <strong style="color: red;"><small>비밀번호가 일치하지 않습니다. 다시 한번 확인해주세요.</small></strong>
                </div>
              </div>

              <div class="mb-4">
                <label for="input-3" class="form-label text-md-right"><strong>이름</strong></label>
                <b-form-input class="form-height" v-model="form.name" type="text" id="input-2" placeholder="이름을 입력해주세요"
                              ref="nameInput" required></b-form-input>
              </div>

              <div class="mb-4">
                <label for="input-4" class="form-label text-md-right"><strong>닉네임</strong></label>
                <b-input-group class="form-height">
                  <b-form-input v-model="form.nickname" type="text" id="input-4" placeholder="닉네임을 입력해주세요"
                                ref="nicknameInput" required :readonly="nicknameInputReadonly"></b-form-input>
                  <b-button class="btn btn-primary" type="button" @click="nicknameChkBtn(form.nickname)">중복확인</b-button>
                </b-input-group>

                <div v-if="nicknameCheckResult !== 'valid' && form.nickname" class="mt-1">
                  <strong style="color: blue;"><small>닉네임 중복확인을 해주세요.</small></strong>
                </div>

                <div v-if="nicknameCheckResult === 'valid'" class="mt-1">
                  <strong style="color: green;"><small>중복확인이 완료되었습니다.</small></strong>
                </div>
              </div>

              <div class="mb-4">
                <label for="input-5" class="form-label text-md-right"><strong>전화번호</strong></label>
                <b-input-group class="form-height">
                  <b-form-input v-model="form.telephone" type="tel" id="input-5" ref="telephoneInput"
                                placeholder="010XXXXXXXX" required :readonly="telephoneInputReadonly"></b-form-input>
                  <b-button class="btn btn-primary" type="button" @click="telephoneChkBtn(form.telephone)">중복확인
                  </b-button>
                </b-input-group>

                <div v-if="!isValidTelephone(form.telephone) && form.telephone" class="mt-1">
                  <strong style="color: red;"><small>올바른 전화번호 형식이 아닙니다. 다시 확인해주세요.</small></strong>
                </div>

                <div v-if="isValidTelephone(form.telephone) && telephoneCheckResult !== 'valid' && form.telephone" class="mt-1">
                  <strong style="color: blue;"><small>중복확인을 해주세요.</small></strong>
                </div>

                <div v-if="telephoneCheckResult === 'valid'" class="mt-1">
                  <strong style="color: green;"><small>중복확인이 완료되었습니다.</small></strong>
                </div>
              </div>

              <div class="mb-4">
                <label for="input-2" class="form-label text-md-right"><strong>직업</strong></label>
                <b-form-input class="form-height" v-model="form.job" type="text" id="input-2" placeholder="직업을 입력해주세요"
                              ref="jobInput" required></b-form-input>
              </div>

              <div class="mb-4">
                <label for="input-6" class="form-label text-md-right"><strong>주소</strong></label>
                <b-input-group>
                  <b-form-input v-model="form.zoneCode" type="text" id="input-6-1" placeholder="우편번호" ref="zoneCodeInput"
                                readonly></b-form-input>
                  <b-button id="postcode" @click="openPostcode">검색</b-button>
                </b-input-group>
                <div class="row mt-2">
                  <div class="col-md-12">
                    <b-form-input v-model="form.roadAddress" type="text" id="input-6-2" placeholder="주소"
                                  ref="roadAddressInput" readonly></b-form-input>
                  </div>
                </div>
                <div class="row mt-2">
                  <div class="col-md-12">
                    <b-form-input v-model="form.detailAddress" type="text" id="input-6-3"
                                  ref="detailAddressInput" placeholder="상세주소"></b-form-input>
                  </div>
                </div>
              </div>

              <div class="mb-4">
                <label for="input-7" class="form-label text-md-right"><strong>생년 월일</strong></label>
                <b-form-input class="form-height" v-model="form.birthDate" type="date" id="input-7" ref="birthInput"
                              placeholder="생년 월일을 입력해주세요" required></b-form-input>
              </div>

              <div class="mb-4">
                <label for="input-8" class="form-label text-md-right"><strong>성별</strong></label>
                <div class="d-flex align-items-center">
                  <div class="mr-3">
                    <b-form-radio v-model="form.gender" name="userGender" value="MALE">남성 &nbsp; &nbsp;</b-form-radio>
                  </div>
                  <div>
                    <b-form-radio v-model="form.gender" name="userGender" value="FEMALE">여성</b-form-radio>
                  </div>
                </div>
              </div>

              <div class="mb-4">
                <label for="input-10" class="form-label text-md-right"><strong>전문 기술</strong></label>
                <b-form-select v-model="form.skillName" :options="form.skillOptions" ref="skillNameInput" required></b-form-select>
              </div>

              <br>
              <b-button @click="onSubmit" class="button-width form-height">가입하기</b-button>

            </b-form>
          </b-card>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

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
        job: '',
        address: '',
        zoneCode: '',
        roadAddress: '',
        detailAddress: '',
        birthDate: '',
        gender: 'MALE',
        role: 'USER',

        skillName: null, // 선택된 기술을 저장할 변수
        skillOptions: [] // 기술 목록을 저장할 배열
      },
      emailCheckResult: null,
      emailInputReadonly: false,

      nicknameCheckResult: null,
      nicknameInputReadonly: false,

      telephoneCheckResult: null,
      telephoneInputReadonly: false,

      pwConfirmCheckResult: null,
    }
  },
  mounted() {
    // 컴포넌트가 마운트되면 기술 목록을 가져옴
    this.fetchSkills();
  },
  methods: {
    openPostcode() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          this.form.zoneCode = data.zonecode;
          this.form.roadAddress = data.roadAddress;
        },
      }).open();
    },

    async fetchSkills() {
      try {
        const response = await axios.get('/api/v1/skill/all');
        console.log(response.data.data);
        this.form.skillOptions = response.data.data.map(skill => ({ value: skill.name, text: skill.name }));
      } catch (error) {
        console.error('Failed to fetch skills', error);
      }
    },

    // 정규 표현식을 사용하여 이메일 유효성 검사
    isValidEmail(email) {
      // 간단한 예시이며, 실제로는 복잡한 정규 표현식이 필요할 수 있습니다.
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return emailRegex.test(email);
    },
    isValidTelephone(telephone) {
      const telephoneRegex = /^\d{10,11}$/; // 10자 또는 11자의 숫자만 허용
      return telephoneRegex.test(telephone);
    },
    isValidPwConfirm(pw, confirmPw) {
      return pw === confirmPw;
    },

    emailChkBtn(email) {
      if (!this.form.email) {
        alert("이메일을 먼저 입력해주세요.");
        this.$refs.emailInput.focus();
        return;
      }
      if (!this.isValidEmail(this.form.email)) {
        alert("올바른 이메일 형식이 아닙니다. 다시 확인해주세요.");
        this.$refs.emailInput.focus();
        return;
      }
      this.$axios.post(`/api/v1/member/checkEmail/${email}`)
          .then(() => {
            alert("사용 가능한 이메일 입니다.");
            this.emailCheckResult = 'valid';
            this.emailInputReadonly = true;
            return;
          }).catch((err) => {
        alert(err.response.data.message);
        this.emailCheckResult = 'duplicate';
        this.form.email = '';
        this.$refs.emailInput.focus();
        this.$store.state.loadingStatus = false;
        return;
      });
    },

    nicknameChkBtn(nickname) {
      if (!this.form.nickname) {
        alert("닉네임을 먼저 입력해주세요.");
        this.$refs.nicknameInput.focus();
        return;
      }
      this.$axios.post(`/api/v1/member/checkNickname/${nickname}`)
          .then(() => {
            alert("사용 가능한 닉네임 입니다.");
            this.nicknameCheckResult = 'valid';
            this.nicknameInputReadonly = true;
            return;
          }).catch((err) => {
        alert(err.response.data.message);
        this.nicknameCheckResult = 'duplicate';
        this.form.nickname = '';
        this.$refs.nicknameInput.focus();
        this.$store.state.loadingStatus = false;
        return;
      });
    },

    telephoneChkBtn(telephone) {
      if (!this.form.telephone) {
        alert("전화번호를 입력해주세요.");
        this.$refs.telephoneInput.focus();
        return;
      }
      if (!this.isValidTelephone(this.form.telephone)) {
        alert("올바른 전화번호 형식이 아닙니다. 다시 확인해주세요.");
        this.$refs.telephoneInput.focus();
        return;
      }
      this.$axios.post(`/api/v1/member/checkTelephone/${telephone}`)
          .then(() => {
            alert("사용 가능한 전화번호 입니다.");
            this.telephoneCheckResult = 'valid';
            this.telephoneInputReadonly = true;
            return;
          }).catch((err) => {
        alert(err.response.data.message);
        this.telephoneCheckResult = 'duplicate';
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
      if (!this.isValidEmail(this.form.email)) {
        alert("올바른 이메일 형식이 아닙니다. 다시 확인해주세요.");
        this.$refs.emailInput.focus();
        return;
      }
      if (!this.form.password) {
        alert("비밀번호를 입력해주세요.");
        this.$refs.passwordInput.focus();
        return;
      }
      if (!this.form.confirmPassword) {
        alert("비밀번호 확인을 입력해주세요.");
        this.$refs.confirmPasswordInput.focus();
        return;
      }
      if (this.form.password != this.form.confirmPassword) {
        alert("비밀번호가 일치하지 않습니다. 다시 한번 확인해주세요.");
        this.$refs.confirmPasswordInput.focus();
        return;
      }
      if (!this.form.name) {
        alert("이름을 입력해주세요.");
        this.$refs.nameInput.focus();
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
      if (!this.form.zoneCode) {
        alert("우편번호를 입력해주세요.");
        this.$refs.zoneCodeInput.focus();
        return;
      }
      if (!this.form.roadAddress) {
        alert("도로명 주소를 입력해주세요.");
        this.$refs.roadAddressInput.focus();
        return;
      }
      if (!this.form.detailAddress) {
        alert("상세 주소를 입력해주세요.");
        this.$refs.detailAddressInput.focus();
        return;
      }
      if (!this.form.birthDate) {
        alert("생년월일을 선택해주세요.");
        this.$refs.birthInput.focus();
        return;
      }
      if (!this.form.skillName) {
        alert("전문 기술을 선택해주세요.");
        this.$refs.skillNameInput.focus();
        return;
      }

      if (this.nicknameCheckResult === 'valid' && this.telephoneCheckResult === 'valid' && this.nicknameCheckResult === 'valid') {
        this.$axios.post("/api/v1/join/guru", this.form)
            .then((res) => {
              // alert(res.data.message)
              console.log(res);
              alert("환영합니다 도사님, 회원가입이 완료되었습니다!");
              this.$router.push({
                name: 'LoginMember'
              })
            }).catch((err) => {
          alert(err.response.data.message);
          this.$store.state.loadingStatus = false;
          return;
        });
      } else {
        alert("중복확인을 해주세요.");
        return;
      }

    }
  }
}
</script>

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