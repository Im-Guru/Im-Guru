<template>
<!--  <div class="post-container">-->
  <div class="post-detail mt-5">
    <div class="common-buttons mb-3">
      <i v-if="isAuthor" @click="fnUpdate" class="fa-2x fa-solid fa-pen-to-square mouse-cursor small-icon">&nbsp;수정&nbsp;</i>
      <i v-if="isAuthor" @click="fnDelete" class="fa-2x fa-solid fa-trash mouse-cursor small-icon">&nbsp;삭제&nbsp;</i>
      <i @click="fnList" class="fa-2x fa-solid fa-list mouse-cursor small-icon">&nbsp;목록&nbsp;</i>
    </div>
    <p>{{ category }} - {{ skill }}</p>

    <h2><strong>{{ title }}</strong></h2>
    <div>

      <span class="mouse-cursor d-flex " @click="fnMemberView(author)" style="align-items: center">
        <img v-if="memberImage && memberImage.fileUrl" :src="memberImage.fileUrl" alt="이미지 파일"
             style="width: 40px; height: 40px; border-radius: 50%; border: 1px solid #888888;"/>
        <i v-else class="fa-solid fa-user" style="font-size: 30px; "></i>
        <strong>&nbsp;{{ author }}</strong>
      </span>

      <span class="small-font">{{ formatDateTime(created_at) }}</span>
      <span class="small-font">&nbsp; 조회: {{ viewCnt }}</span>

      <div class="icon-container">
        <i class="fa-solid fa-envelope mouse-cursor" v-if="!isAuthor" @click="toMessageWrite(author)">&nbsp;메세지&nbsp;&nbsp;</i>
        <i class="fa-solid fa-triangle-exclamation mouse-cursor" v-if="!isAuthor" @click="toReportPost(postId, title)">&nbsp;신고</i>&nbsp;
      </div>

    </div>

    <hr>
    <div class="board-contents">
      <div v-if="hasImages">
        <img v-for="(file, index) in files" :key="index" :src="file.fileUrl" :alt="'게시글 이미지 ' + (index + 1)"
             class="img-fluid"/>
      </div>
      <div v-html="content"></div>

<!--      <div v-if="isGuru && this.price !== '0'">가격: {{ price }}</div>-->
      <div v-if="isGuru && this.price !== '0'" class="my-3" style="text-align: end">
        <h4><strong>가격: {{ parseFloat(price).toLocaleString('ko-KR', { style: 'currency', currency: 'KRW' }) }}</strong></h4>
      </div>
    </div>

    <div class="button-container">
      <i class="fa-3x fa-solid fa-thumbs-up heart-icon" @click="toPostLike(postId)">&nbsp;좋아요 {{likeCnt}}&nbsp;&nbsp;&nbsp;</i>
      <i v-if="isGuru && this.price !== '0'" class="fa-3x fa-solid fa-money-check-dollar pay-icon" @click="toPay(postId)">&nbsp;결제&nbsp;</i>
    </div>

    <div>
      <ul>
        <li v-for="file in files" :key="file.fileUrl">
          첨부파일 : <a :href="file.fileUrl" download>{{ file.fileName }}</a>
        </li>
      </ul>
    </div>

    <hr>

    <!-- --------------- -->
    <div v-for="(reply, idx) in replyList" :key="idx" class="reply-container">
      <div class="icon-container">
        <i class="fa-solid fa-trash mouse-cursor small-icon" v-if="isReplierArray[idx]" @click="removeReply(reply.replyId, reply.postId)">&nbsp;삭제&nbsp;</i>
        <i class="fa-solid fa-envelope mouse-cursor small-icon" v-if="!isReplierArray[idx]" @click="toMessageWrite(reply.memberNickname)">&nbsp;메세지&nbsp;</i>
        <i class="fa-solid fa-triangle-exclamation mouse-cursor small-icon" v-if="!isReplierArray[idx]" @click="toReportReply(reply.replyId, reply.content)">&nbsp;신고&nbsp;</i>
      </div>

      <div class="reply-detail" >
        <img v-if="reply.memberImage && reply.memberImage.fileUrl" :src="reply.memberImage.fileUrl" alt="이미지 파일"
             style="width: 40px; height: 40px; border-radius: 50%; border: 1px solid #888888;"/>
        <i v-else class="fa-solid fa-user" style="font-size: 30px"></i>
        <strong class="mouse-cursor" style="align-items: center" @click="fnMemberView(reply.memberNickname)">&nbsp;[{{ reply.memberNickname }}]</strong>
        <br>
        <span class="small-font">{{reply.memberSkill}}</span>
        <p class="mt-1">{{ reply.content }}</p>
      </div>

      <span><small>{{ formatDateTime(reply.regDate) }} - </small></span>
      <i class="fa-solid fa-thumbs-up Reply-heart-icon" @click="toReplyLike(reply.replyId)"><small>&nbsp;좋아요 {{reply.likeCnt}}</small></i>
    </div>

    <div class="mt-5">
      <label for="reply"><strong>댓글</strong></label>
      <div style="position: relative;">
        <textarea id="reply" ref="replyInput" rows="5" v-model="reply" class="form-control" style="resize: none;"
                  placeholder="댓글을 남겨보세요."></textarea>
        <button type="button" class="btn btn-outline-primary btn-rounded" @click="replySave"
                style="position: absolute; right: 10px; bottom: 10px;">댓글 저장
        </button>
      </div>
    </div>

    <!-- --------------- -->


  </div>

<!--  </div>-->

</template>

<script>

export default {
  data() { //변수생성
    return {
      requestBody: this.$route.query,
      idx: this.$route.query.idx,
      postId: '',
      category: '',
      skill: '',
      title: '',
      author: '',
      content: '',
      price: '',
      created_at: '',
      viewCnt: '',
      files: [],
      memberImage: '',
      //댓글
      replyList: [],
      reply: '',
      replyAuthorNickname: '',
      replyAuthorImage: '',
      // 좋아요
      loginUserNickname: localStorage.getItem('user_nickname'),
      isPostLiked: false,
      isReplyLiked: false,
      likeCnt: 0,
      isAuthor: false,
      isGuru: '',
      isReplierArray: [], // 각 댓글에 대한 isReplier를 저장할 배열 추가
    }
  },
  mounted() {
    this.fnGetView()
    this.fnGetReply()
    this.checkAuthor(this.idx)
  },
  computed: {
    hasImages() {
      return this.files && this.files.length > 0;
    },
    contentWithoutImages() {
      // 이미지를 제외한 내용을 반환
      return this.content.replace(/!\[.*?\]\(.*?\)/g, ''); // 이미지 링크 제거
    },
  },
  methods: {
    fnGetView() {
      this.$axios.get('/api/v1/post/' + this.idx, {
        params: this.requestBody
      }).then((res) => {
        console.log(res.data)
        this.postId = res.data.data.postId
        this.title = res.data.data.title
        this.author = res.data.data.memberNickname
        this.content = res.data.data.content
        this.price = res.data.data.price
        this.created_at = res.data.data.regDate
        this.category = res.data.data.postCategory
        this.skill = res.data.data.skillName
        this.viewCnt = res.data.data.viewCnt
        this.isGuru = res.data.data.guru;
        // 서버에서 게시물의 좋아요 상태와 개수 가져오기
        // this.isLiked = res.data.data.isLiked;
        this.likeCnt = res.data.data.likeCnt;
        //파일 가져오기
        this.files = res.data.data.fileList;
        console.log(this.files);
        this.memberImage = res.data.data.memberImage
      }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({path: '/login'});
        } else {
          alert(err.response.data.message);
        }
        this.$store.state.loadingStatus = false;
      })
    },

    fnList() {
      delete this.requestBody.idx
      this.$router.push({
        path: './list',
        query: this.requestBody
      })
    },
    fnPost(postId) {
      this.requestBody.idx = postId
      this.$router.go(this.$router.currentRoute)
    },
    fnUpdate() {
      if (localStorage.getItem("user_token") === null) {
        alert("로그인 해야 가능한 서비스입니다.");
        window.location.href = "http://localhost:3000/login";
        return;
      }
      this.$router.push({
        path: './write',
        query: this.requestBody
      })
    },
    fnDelete() {
      if (!confirm("게시글을 삭제하시겠습니까?")) return

      if (localStorage.getItem("user_token") === null) {
        alert("로그인 해야 가능한 서비스입니다.");
        window.location.href = "http://localhost:3000/login";
        return;
      }

      this.$axios.delete('/api/v1/post/' + this.idx, {

      }).then((res) => {
        // alert(res.data.message)
        console.log(res);
        alert("게시글 삭제가 완료되었습니다.");
        this.fnList();
      }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 400) {
          alert("로그인을 먼저 해주세요!");
          this.$router.push({path: '/login'});
        } if (err.response.status === 404) {
          alert("잘못된 경로입니다.");
          alert(err.response.data.message);
          location.reload()
        } else {
          alert(err.response.data.message);
          location.reload()
        }
        this.$store.state.loadingStatus = false;
      })
    },
    isImageFile(file) {
      // 파일이 이미지 파일인지 확인 (확장자 기준)
      const imageExtensions = ['.jpg', '.jpeg', '.png', '.gif', '.bmp', '.svg'];
      const fileExtension = file.fileName.split('.').pop().toLowerCase();
      return imageExtensions.includes('.' + fileExtension);
    },


    // ------------------------
    async fnGetReply() {
      await this.$axios.get('/api/v1/reply/post/' + this.idx, {
        params: this.requestBody
      }).then(async (res) => {
        console.log(res.data.data);
        console.log("-----sdsdf-----");
        console.log(res.data.data.memberImage);
        this.replyList = res.data.data
        this.replyAuthorNickname = res.data.data.memberNickname
        this.replyAuthorImage = res.data.data.memberImage

        // 각 댓글에 대해 checkReplier 호출
        for (const reply of this.replyList) {
           await this.checkReplier(reply.replyId);
        }

      }).catch((err) => {
        alert(err.response.data.message);
        // if (err.response.status === 401 || err.response.status === 404) {
        //   this.$router.push({path: '/login'});
        // } else {
        //   alert(err.response.data.message);
        //   location.reload()
        // }
        this.$store.state.loadingStatus = false;
      })
    },
    async checkReplier(replyId) {
      await this.$axios.post(`/api/v1/member/checkReplier/${replyId}`, null, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("user_token")}`,
        },
      }).then((res) => {
        console.log("replier: " + res.data.data);
        // 각 댓글에 대한 isReplier를 저장할 배열에 추가
        this.isReplierArray.push(res.data.data);
      }).catch((err) => {
        console.log("======checkReplier======")
        console.log(err.response.data.message);
      })
      this.$store.state.loadingStatus = false;
    },

    removeReply(replyId, postId) {
      if (!confirm("댓글을 삭제하시겠습니까?")) return

      if (localStorage.getItem("user_token") === null) {
        alert("로그인 해야 가능한 서비스입니다.");
        window.location.href = "http://localhost:3000/login";
        return;
      }

      this.$axios.delete(`/api/v1/reply/${postId}/${replyId}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        // alert(res.data.message)
        console.log(res);
        alert("댓글 삭제가 완료되었습니다.");
        this.fnPost(postId);
      }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 400) {
          alert("로그인을 먼저 해주세요!");
          this.$router.push({path: '/login'});
        } if (err.response.status === 404) {
          alert("잘못된 경로입니다.");
          alert(err.response.data.message);
          location.reload()
        } else {
          alert(err.response.data.message);
          location.reload()
        }
        this.$store.state.loadingStatus = false;
      })
    },
    replySave() {
      if (!this.reply) {
        alert("댓글을 입력해주세요.");
        this.$refs.replyInput.focus();
        return;
      }

      if (localStorage.getItem("user_token") === null) {
        alert("로그인 해야 가능한 서비스입니다.");
        window.location.href = "http://localhost:3000/login";
        return;
      }

      this.form = {
        "content": this.reply
      }
      console.log(this.form.content)

      this.$axios.post(`/api/v1/reply/` + this.idx, this.form, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        // alert(res.data.message)
        console.log(res);
        // alert("게시글 삭제가 완료되었습니다.");
        this.fnPost(this.idx);
      }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 400) {
          alert("로그인을 먼저 해주세요!");
          this.$router.push({path: '/login'});
        } if (err.response.status === 404) {
          alert("잘못된 경로입니다.");
          alert(err.response.data.message);
          location.reload()
        } else {
          alert(err.response.data.message);
          location.reload()
        }
        this.$store.state.loadingStatus = false;

      })
    },
    // ------------------------

    toMessageWrite(receiverNickname) {
      if (localStorage.getItem("user_token") === null) {
        alert("로그인 해야 가능한 서비스입니다.");
        window.location.href = "http://localhost:3000/login";
        return;
      }

      this.$router.push({
        path: '/message/write',
        query: {member: receiverNickname}
      })
    },
    toReportPost(postId, title) {
      if (localStorage.getItem("user_token") === null) {
        alert("로그인 해야 가능한 서비스입니다.");
        window.location.href = "http://localhost:3000/login";
        return;
      }

      if (!confirm("해당 게시글을 신고하시겠습니까?")) return

      this.$router.push({
        path: '/report/post',
        query: {
          reportPost: postId,
          postTitle: title,
        }

      })
    },

    toPostLike(postId) {
      // 좋아요 상태 토글
      this.isPostLiked = !this.isPostLiked;

      if (localStorage.getItem("user_token") === null) {
        alert("로그인 해야 가능한 서비스입니다.");
        window.location.href = "http://localhost:3000/login";
        return;
      }

      this.$axios.post(`/api/v1/post/like/${postId}`, {like: this.isPostLiked}, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("user_token")}`,
        },
      }).then(() => {
        alert("좋아요!");
      }).catch((err) => {
        // if (err.response.status === 401) {
        //   alert("로그인을 먼저 해주세요!");
        //   this.$router.push({path: '/login'});
        // } if (err.response.status === 404) {
        //   alert("잘못된 경로입니다.");
        //   alert(err.response.data.message);
        //   location.reload()
        // } else {
        //   alert(err.response.data.message);
        //   location.reload()
        // }
        alert(err.response.data.message);
        this.$store.state.loadingStatus = false;
      });
    },

    toPay(postId) {
      if (localStorage.getItem("user_token") === null) {
        alert("로그인 해야 가능한 서비스입니다.");
        window.location.href = "http://localhost:3000/login";
      }

      this.$router.push({
        path: '/pay/index',
        query: {
          idx: postId,
        }
      });

    },

    toReplyLike(replyId) {
      // 좋아요 상태 토글
      this.isReplyLiked = !this.isReplyLiked;

      if (localStorage.getItem("user_token") === null) {
        alert("로그인 해야 가능한 서비스입니다.");
        window.location.href = "http://localhost:3000/login";
        return;
      }

      this.$axios.post(`/api/v1/reply/like/${replyId}`, {like: this.isReplyLiked}, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("user_token")}`,
        },
      }).then(() => {
        // if (this.isLiked) {
        //   this.likeCnt++;
        // } else {
        //   this.likeCnt--;
        // }
        alert("좋아요!");
        location.reload()
      }).catch((err) => {
        // if (err.response.status === 401 || err.response.status === 400) {
        //   alert("로그인을 먼저 해주세요!");
        //   this.$router.push({path: '/login'});
        // } if (err.response.status === 404) {
        //   alert("잘못된 경로입니다.");
        //   alert(err.response.data.message);
        //   location.reload()
        // } else {
        //   alert(err.response.data.message);
        //   location.reload()
        // }
        alert(err.response.data.message);
        this.$store.state.loadingStatus = false;
      });
    },
    toReportReply(replyId, replyContent) {
      if (localStorage.getItem("user_token") === null) {
        alert("로그인 해야 가능한 서비스입니다.");
        window.location.href = "http://localhost:3000/login";
        return;
      }

      if (!confirm("해당 댓글을 신고하시겠습니까?")) return

      this.$router.push({
        path: '/report/reply',
        query: {
          reportReply: replyId,
          replyContent: replyContent,
        }
      })
    },

    async checkAuthor(postId) {
      this.$axios.post(`/api/v1/member/checkAuthor/${postId}`, null, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("user_token")}`,
        },
      }).then((res) => {
        console.log(res.data.data);
        this.isAuthor = res.data.data;
      }).catch((err) => {
        console.log("======checkAuthor======")
        console.log(err.response.data.message);
        // if (err.response.status === 401 || err.response.status === 404) {
        //   this.$router.push({path: '/login'});
        // } else {
        //   alert(err.response.data.message);
        // }
        // this.$store.state.loadingStatus = false;
      })
    },

    fnMemberView(author) {
      this.$router.push({
        path: '../member/view',
        query: {author}
      })
    },

    formatDateTime(dateTimeStr) {
      const dateTime = new Date(dateTimeStr);
      const now = new Date();

      const timeDifferenceInSeconds = Math.floor((now - dateTime) / 1000);

      if (timeDifferenceInSeconds < 60) {
        return `${timeDifferenceInSeconds}초 전`;
      } else if (timeDifferenceInSeconds < 3600) {
        const minutes = Math.floor(timeDifferenceInSeconds / 60);
        return `${minutes}분 전`;
      } else if (timeDifferenceInSeconds < 86400) {
        const hours = Math.floor(timeDifferenceInSeconds / 3600);
        return `${hours}시간 전`;
      } else {
        const days = Math.floor(timeDifferenceInSeconds / 86400);
        return `${days}일 전`;
      }
    },

  }
}
</script>


<style scoped>
.small-font {
  font-size: 0.8rem;
  color: rgb(128, 128, 128);
}

.small-icon {
  font-size: 15px; /* Adjust the font size to your preference */
}

.mouse-cursor {
  cursor: pointer; /* 마우스 포인터가 포인팅 형태로 변경됩니다. */
}

/* 하트 아이콘 스타일링 */
.heart-icon {
  color: blue; /* 원하는 색상(빨간색 또는 다른 원하는 색상)으로 설정합니다. */
  font-size: 3rem; /* 원하는 크기로 설정합니다. */
  cursor: pointer; /* 마우스 포인터가 포인팅 형태로 변경됩니다. */
  font-size: 20px;
}

.Reply-heart-icon {
  color: deepskyblue; /* 원하는 색상(빨간색 또는 다른 원하는 색상)으로 설정합니다. */
  cursor: pointer; /* 마우스 포인터가 포인팅 형태로 변경됩니다. */
  font-size: 15px;
  //vertical-align: -10px;
}

.pay-icon {
  color: forestgreen; /* 원하는 색상(빨간색 또는 다른 원하는 색상)으로 설정합니다. */
  font-size: 3rem; /* 원하는 크기로 설정합니다. */
  cursor: pointer; /* 마우스 포인터가 포인팅 형태로 변경됩니다. */
  font-size: 20px;
}

/* 하트 아이콘과 버튼 컨테이너 스타일 */
.button-container {
  display: flex;
  justify-content: center;
  align-items: center; /* 수직 정렬 추가 */
  padding: 10px; /* 원하는 패딩 값으로 조정 */
}

/* 좋아요 버튼 스타일 */
.btn {
  margin: 0 auto; /* 가운데 정렬 */
  /* 나머지 스타일 유지 */
}

/* 좋아요 버튼 스타일 */
.btn-liked {
  background-color: transparent; /* 배경색을 투명으로 설정합니다. */
  border: none; /* 테두리 제거 */
}

/* 이미지가 내용의 최대 높이를 넘지 않도록 스타일링 */

/* 게시글 내용 스타일링 */
.board-contents {
  position: relative;
  height: auto; /* 내용의 크기에 따라 늘어남 */
  overflow: hidden; /* 내용이 특정 크기를 넘어갈 경우 스크롤 표시 */
}

/* 이미지 스타일 유지 */
.img-fluid {
  display: block;
  margin: 0 auto; /* 가운데 정렬 */
  max-width: 100%; /* 이미지의 최대 너비 설정 */
  height: auto; /* 이미지의 높이 자동 조정 */
  object-fit: contain; /* 이미지 비율 유지 */
}

.icon-container {
  float: right;
}
</style>