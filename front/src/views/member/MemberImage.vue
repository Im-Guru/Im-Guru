<template>
  <div class="post-list my-5">

    <h2>안녕하세요, {{this.memberNickname}} 님, 이미지 파일을 업로드 해주세요.</h2>

    <hr>

    <input type="file" ref="fileInput" @change="handleFileChange" multiple/>

    <div class="d-flex justify-content-end">
      <button type="button" class="btn btn-primary btn-rounded" v-on:click="fnSave">저장</button>&nbsp;
    </div>

  </div>
</template>

<script>

export default {
  data() {
    return {
      isGuru: false,
      role: '',
      memberNickname: '',
      memberId: '',
      file: '',
    };
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

        this.memberId = res.data.data.memberId;
        this.memberNickname = res.data.data.nickname;
        this.role = res.data.data.role;

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

    handleFileChange(event) {
      // this.file = Array.from(event.target.files); // 선택한 모든 파일을 배열로 저장
      this.file = event.target.files[0];
    },

    fnSave() {
      if (localStorage.getItem("user_token") === null) {
        alert("로그인 해야 가능한 서비스입니다.");
        return;
      }

      const formData = new FormData();
      const fileCategory = "member";
      const fileKey = this.memberId;
      const file = this.file;
      const modifiedFilename = "member_" + this.memberId + "_" + this.file.name;

      formData.append("file", file, modifiedFilename);

      // 파일 업로드
      this.$axios.post("/upload", formData, {
        withCredentials: true,  // CORS 관련 설정
      }).then((uploadResponse) => {
        console.log("--upload--");
        console.log(uploadResponse);
      }).catch((uploadError) => {
        console.log("--upload error--");
        console.log(uploadError);
      });

      // file DB에 저장
      this.$axios.post(`/api/v1/file/${fileCategory}/${fileKey}/${this.file.name}`).then((res) => {
        console.log("--File DB--");
        console.log(res);
      }).catch((err) => {
        console.log(err);
      });

      alert("이미지 업로드 성공");
      this.$router.go(-1);


      // this.$axios.post("/api/v1/member/image", formData, {
      //   headers: {
      //     // 'Content-Type': 'multipart/form-data', // 헤더 설정
      //     Authorization: `Bearer ${localStorage.getItem("user_token")}`,
      //   },
      // }).then((response) => {
      //   alert("이미지 업로드 성공");
      //   console.log(response)
      //   this.$router.go(-1);
      // })
      //     .catch((err) => {
      //       if (err.response.status === 401 || err.response.status === 404) {
      //         this.$router.push({path: '/login'});
      //       } else {
      //         alert(err.response.data.message);
      //         location.reload()
      //       }
      //     });

    },
  }
};
</script>
