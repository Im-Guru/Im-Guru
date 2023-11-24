<template>
  <div>
    <div class="container mt-5">
      <h3><strong>안녕하세요 {{this.form.memberNickname}} 도사님, 정보를 입력해주세요 !</strong></h3><br><br>
      <div class="row justify-content-center">
        <div class="col-md-5">
          <b-card class="mb-5">
            <b-form @submit.prevent="onSubmit" class="form-width mt-3" style="margin: auto;">
              <div class="mb-4">
                <b-form-textarea v-model="form.memberNickname" hidden></b-form-textarea>
              </div>

              <div class="mb-4">
                <label for="input-1" class="form-label"><strong>자기소개</strong></label>
                  <b-form-textarea v-model="form.intro" id="input-1" placeholder="자기소개를 입력해주세요" ref="introInput" required></b-form-textarea>
              </div>

              <div class="mb-4">
                <label for="input-2" class="form-label text-md-right"><strong>회사명</strong></label>
                <b-form-input class="form-height" v-model="form.companyName" type="text" id="input-3"
                              ref="companyInput" placeholder="회사명을 입력해주세요" required></b-form-input>
              </div>

              <div class="mb-4">
                <label for="input-3" class="form-label text-md-right"><strong>회사 직급</strong></label>
                <b-form-input class="form-height" v-model="form.position" type="text" id="input-3" placeholder="회사 직급을 입력해주세요"
                              ref="positionInput" required></b-form-input>
              </div>

              <div class="mb-4">
                <label for="input-4" class="form-label text-md-right"><strong>회사 경력</strong></label>
                <b-input-group class="form-height">
                  <b-form-input v-model="form.careerAt" type="text" id="input-4" placeholder="회사 경력을 입력해주세요"
                                ref="careerAtInput" required></b-form-input>
                </b-input-group>
              </div>

              <div class="mb-4">
                <label for="input-5" class="form-label text-md-right"><strong>연락 가능 시간</strong></label>
                <b-input-group class="form-height">
                  <b-form-input v-model="form.contactTime" type="text" id="input-5" ref="contactTimeInput"
                                placeholder="연락 가능 시간을 입력해주세요" required></b-form-input>
                </b-input-group>
              </div>

              <div class="mb-4">
                <label for="input-6" class="form-label text-md-right"><strong>활동 가능 지역</strong></label>
                <b-form-input class="form-height" v-model="form.workArea" type="text" id="input-6" placeholder="활동 가능 지역을 입력해주세요"
                              ref="workAreaInput" required></b-form-input>
              </div>

              <div class="mb-4">
                <label for="input-7" class="form-label text-md-right"><strong>서비스 설명</strong></label>
                    <b-form-textarea v-model="form.description" id="input-7" placeholder="제공 가능한 서비스에 대한 설명을 입력해주세요" ref="descriptionInput" required>
                    </b-form-textarea>
              </div>

              <br>

              <b-button @click="onSubmit" class="button-width form-height">작성완료</b-button>

            </b-form>
          </b-card>

        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      form: {
        email: '',
        telephone: '',
        role: '',
        memberNickname: '',

        intro: '',
        companyName: '',
        position: '',
        careerAt: '',
        contactTime: '',
        workArea: '',
        description: '',

      }
    }
  },
  mounted() {
    this.fnLoginMember();
  },
  methods: {
    fnLoginMember() {
      this.$axios.post(`/api/v1/member/myInfo`, "", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        console.log(res);

        this.form.memberNickname = res.data.data.nickname;
        this.form.role = res.data.data.role;

      }).catch((err) => {
        console.log(err);

        // if (err.response.status === 401 || err.response.status === 400) {
        //   alert("로그인을 먼저 해주세요!");
        //   this.$router.push({path: '/login'});
        // }
        // if (err.response.status === 404) {
        //   alert("잘못된 경로입니다.");
        //   alert(err.response.data.message);
        //   location.reload()
        // } else {
        //   alert(err.response.data.message);
        //   location.reload()
        // }
        // this.$store.state.loadingStatus = false;
      })
    },

    onSubmit() {
      if (!this.form.intro) {
        alert("자시소개를 입력해주세요.");
        this.$refs.introInput.focus();
        return;
      }
      if (!this.form.companyName) {
        alert("회사를 입력해주세요.");
        this.$refs.companyNameInput.focus();
        return;
      }
      if (!this.form.position) {
        alert("회사 직급을 입력해주세요.");
        this.$refs.positionInput.focus();
        return;
      }
      if (!this.form.careerAt) {
        alert("회사 경력을 입력해주세요.");
        this.$refs.careerAtInput.focus();
        return;
      }
      if (!this.form.contactTime) {
        alert("연락 가능 시간을 입력해주세요.");
        this.$refs.contactTimeInput.focus();
        return;
      }
      if (!this.form.workArea) {
        alert("활동 가능 지역을 입력해주세요.");
        this.$refs.workAreaInput.focus();
        return;
      }
      if (!this.form.description) {
        alert("제공 가능한 서비스에 대한 설명을 입력해주세요.");
        this.$refs.descriptionInput.focus();
        return;
      }

      this.$axios.post("/api/v1/guru/write", this.form, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      })
          .then((res) => {
            alert(res.data.message);
            this.$router.go(-1);
            // this.$router.push({
            //   name: 'LoginMember'
            // })
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
