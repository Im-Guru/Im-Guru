<template>
  <div class="container my-5 col-8">
    <div class="report-header">
      <h2><strong>게시글 신고하기</strong></h2>
    </div>

    <hr class="report-divider">

    <div class="report-input">
      <div class="form-group row align-items-center">
        <div class="row mb-3">
          <label for="reportPost" class="col-md-2 col-form-label"><strong>신고 게시물 제목:</strong></label>
          <div class="col-md-10">
            <input type="text" id="postTitle" v-model="postTitle" class="form-control report-text-input" readonly>
          </div>
        </div>
        <div class="row">
          <label for="title" class="col-md-2 col-form-label"><strong>신고자:</strong></label>
          <div class="col-md-4">
            <input type="text" id="memberNickname" v-model="memberNickname" class="form-control report-text-input" readonly>
          </div>
          <label for="categoryName" class="col-md-2 col-form-label"><strong>신고 카테고리:</strong></label>
          <div class="col-md-4">
            <b-form-select v-model="categoryName" :options="options" id="categoryName"></b-form-select>
          </div>
        </div>

        <label for="reportPost" class="col-md-2 col-form-label" hidden><strong>신고 게시물 Id:</strong></label>
        <div class="col-md-4" hidden>
          <input type="text" id="reportPost" v-model="postId" class="form-control report-text-input" readonly>
        </div>
      </div>
    </div>

    <div class="report-input mt-3">
      <div class="form-group row align-items-center">
        <label for="description" class="col-md-2 col-form-label"><strong>신고 내용:</strong></label>
        <div class="col-md-10">
          <textarea id="description" cols="40" rows="10" v-model="description" class="form-control report-text-input"
              style="resize: none;" placeholder="내용을 입력해주세요." required></textarea>
        </div>
      </div>
    </div>

    <div class="report-buttons mt-3 d-flex justify-content-end">
      <button type="button" class="btn btn-primary btn-rounded" @click="fnSave">보내기</button>
    </div>

  </div>
</template>


<script>
export default {
  name: "ReportWrite",

  data() { //변수생성
    return {
      memberNickname: '', //신고하는 사람 nickname
      categoryName: 'ABUSE',
      postId: this.$route.query.reportPost,
      description: '',
      idx: '',
      postTitle: this.$route.query.postTitle,
      options: [
        {value: 'ABUSE', text: '욕설'},
        {value: 'DEFAMATION', text: '명예훼손'},
        {value: 'PORNOGRAPHY', text: '음란물'},
        {value: 'ADVERTISEMENT', text: '광고'},
        {value: 'SPAMMER', text: '도배'},
      ]
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

        this.memberNickname = res.data.data.nickname;
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
    fnSave() {
      if (!this.description) {
        alert("내용을 입력해주세요.");
        // 내용이 비어있을 때 해당 textarea에 포커스 설정
        this.$nextTick(() => {
          const textarea = document.getElementById('description');
          textarea.focus();
        });
        return;
      }

      this.form = {
        "memberNickname": this.memberNickname,
        "description": this.description,
        "postId": this.postId,
        "categoryName": this.categoryName,
      }

      this.$axios.post(`/api/v1/reportPost`, this.form, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        alert(res.data.message)
        this.fnPostList()
      }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({path: '/login'});
        } else {
          alert(err.response.data.message);
          this.fnView(this.postId)
        }

      })
    },
    fnPostList() {
      this.$router.push({
        path: '/post/list',
      })
    },
    fnView(postId) {
      this.idx = postId
      this.$router.push({
        path: '/post/detail',
        query: {
          idx: this.idx
        }
      })
    },
  }
}

</script>