<template>

  <div class="post-list">

    <div class="row mt-5 mb-5">
      <div class="row">
        <h2>내 정보</h2>
      </div>
      <div class="row">
        <div class="col-2" style="text-align: center">
          <i class="fa-solid fa-user" style="font-size: 50px"></i>
        </div>
        <div class="col">
          <table>
            <tr>
              <td>이름:</td>
              <td>{{ this.name }}</td>
            </tr>
            <tr>
              <td>닉네임:</td>
              <td>{{ this.nickname }}</td>
            </tr>
            <tr>
              <td>기술:</td>
              <td>{{ this.skillName }}</td>
            </tr>
            <tr>
              <td>직업:</td>
              <td>{{ this.job }}</td>
            </tr>
            <tr>
              <td>주소:</td>
              <td><small>{{ this.address }}</small></td>
            </tr>
          </table>
        </div>
      </div>
      <br>
      <button>
        <router-link :to="getDetailLink()">정보 수정</router-link>
      </button>

      <button v-if="role === 'ROLE_GURU'">
        <router-link to="/guru/write">도사 정보 작성</router-link>
      </button>
    </div>

    <hr>

    <div class="row post-list mt-5">
      <div>
        <!-- 네비게이션 탭 -->
        <ul class="nav nav-tabs">
          <li class="nav-item" v-if="role === 'ROLE_GURU'">
            <a class="nav-link hover-pointer" @click="showTab('guruInfo')" :class="{ active: activeTab === 'guruInfo' }">도사 정보</a>
          </li>
          <li class="nav-item">
            <a class="nav-link hover-pointer" @click="showTab('myPosts')" :class="{ active: activeTab === 'myPosts' }">내가 작성한 게시물</a>
          </li>
          <li class="nav-item">
            <a class="nav-link hover-pointer" @click="showTab('likedPosts')" :class="{ active: activeTab === 'likedPosts' }">내가 좋아요 누른
              게시물</a>
          </li>
          <li class="nav-item">
            <a class="nav-link hover-pointer" @click="showTab('myReplies')" :class="{ active: activeTab === 'myReplies' }">내가 작성한
              댓글</a>
          </li>
        </ul>
      </div>

      <div v-if="activeTab === 'guruInfo' & role === 'ROLE_GURU'">
        <div class="mt-3">
          <div>
            <div v-if="guruInfoList" class="post-item hover-pointer">
              <div>한줄소개: {{ guruInfoList.intro }}</div>
              <div>회사: {{ guruInfoList.companyName }}</div>
              <div>직급: {{ guruInfoList.position }}</div>
              <div>경력: {{ guruInfoList.careerAt }}</div>
              <div>연락가능시간: {{ guruInfoList.contactTime }}</div>
              <div>활동가능지역: {{ guruInfoList.workArea }}</div>
              <div>업무 설명: {{ guruInfoList.description }}</div>
            </div>
<!--            <div v-if="guruInfoList === ''" class="post-item hover-pointer">-->
<!--              <div>아직 기입한 도사 정보가 없습니다 !</div>-->
<!--            </div>-->
          </div>
        </div>
      </div>

      <div v-if="activeTab === 'myPosts'">
        <div class="mt-3">
          <div>
            <div v-for="(item, idx) in postList" :key="idx" @click="fnPostView(item.postId)" class="post-item hover-pointer">
              <div class="post-category">{{ item.postCategory }} - {{ item.skillName }}</div>
              <div class="post-title">
                <span v-if="item.title.length < 20">{{ item.title }} &nbsp;&nbsp;</span>
                <span v-else>{{ item.title.substring(0, 10) + "..." }}</span>
              </div>
              <div class="post-content">
                {{ truncateAndStripTags(item.content, 100) }}
              </div>

              <div class="post-status">
                <i class="fa-solid fa-comment small-icon">{{ item.replyCnt }}&nbsp;</i>
                <i class="fa-solid fa-thumbs-up small-icon">{{ item.likeCnt }}&nbsp;</i>
                <i class="fa-solid fa-eye small-icon">{{ item.viewCnt }}&nbsp;</i>
                <p class="post-reg-date">{{ formatDateTime(item.regDate) }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div v-if="activeTab === 'likedPosts'">
        <div class="mt-3">
          <div>
            <div v-for="(item, idx) in likePostList" :key="idx" @click="fnPostView(item.postId)" class="post-item hover-pointer">
              <div class="post-category">{{ item.postCategory }} - {{ item.skillName }}</div>
              <div class="post-title">
                <span v-if="item.title.length < 20">{{ item.title }} &nbsp;&nbsp;</span>
                <span v-else>{{ item.title.substring(0, 10) + "..." }}</span>
              </div>
              <!--            <div class="post-content" v-html="item.content"></div>-->
              <div class="post-content">
                {{ truncateAndStripTags(item.content, 100) }}
              </div>

              <div class="post-status">
                <i class="fa-solid fa-comment small-icon">{{ item.replyCnt }}&nbsp;</i>
                <i class="fa-solid fa-heart small-icon">{{ item.likeCnt }}&nbsp;</i>
                <i class="fa-solid fa-eye small-icon">{{ item.viewCnt }}&nbsp;</i>
                <p class="post-reg-date">{{ formatDateTime(item.regDate) }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div v-if="activeTab === 'myReplies'">
        <div class="mt-3">
          <div>
            <div v-for="(reply, idx) in replyList" :key="idx" @click="fnPostView(reply.postId)" class="reply-container">
              <div class="reply-detail">
                <strong class="mouse-cursor">[{{ reply.memberNickname }}]</strong>
                <br>
                <span class="small-font">{{reply.memberSkill}}</span>
                <p class="mt-1">{{ reply.content }}</p>
              </div>
              <span><small>{{ formatDateTime(reply.regDate) }} - </small></span>
              <i class="fa-solid fa-thumbs-up Reply-heart-icon"><small>&nbsp;좋아요 {{reply.likeCnt}}</small></i>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>


</template>

<script>

export default {

  data() {
    return {
      requestBody: {}, // 리스트 페이지 데이터전송
      postList: {}, // 내가 작성한 게시물 리스트
      likePostList: {}, // 내가 좋아요 누른 게시물 리스트
      replyList: {}, // 내가 작성한 댓글 리스트
      guruInfoList: {}, // 도사가 작성한 정보
      activeTab: 'myPosts',

      name: '',
      nickname: '',
      skillName: '',
      job: '',
      address: '',
      role:'',

      postCategory: '',
      replyCount: '',
      likeCount: '',

      memberNickname: '',
    }
  },

  mounted() {
    this.fnLoginMember();
    this.fnPostList();
    this.fnLikePostList();
    this.fnReplyList();
    this.fnGuruInfoList();
  },

  methods: {
    fnLoginMember() {
      this.$axios.post(`/api/v1/member/myInfo`, "", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        console.log(res);

        this.name = res.data.data.name;
        this.nickname = res.data.data.nickname;
        this.skillName = res.data.data.skillName;
        this.job = res.data.data.job;
        this.address = res.data.data.roadAddress + " " + res.data.data.detailAddress;
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
    fnPostList() {
      this.$axios.post(`/api/v1/posts/myWrite`, "", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      })
          .then((res) => {
            console.log(res);
            this.postList = res.data.data;
          }).catch((err) => {
            console.log(err);

          //   if (err.response.status === 401 || err.response.status === 400) {
          //     alert("로그인을 먼저 해주세요!");
          //     this.$router.push({path: '/login'});
          //   }
          //   if (err.response.status === 404) {
          //     alert("잘못된 경로입니다.");
          //     alert(err.response.data.message);
          //     location.reload()
          //   } else {
          //     alert(err.response.data.message);
          //     location.reload()
          //   }
          //   this.$store.state.loadingStatus = false;
          })
    },
    fnLikePostList() {
      this.$axios.post(`/api/v1/posts/myLike`, "", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      })
          .then((res) => {
            console.log(res);

            this.likePostList = res.data.data;
          }).catch((err) => {
            console.log(err);
          //   if (err.response.status === 401 || err.response.status === 400) {
          //     alert("로그인을 먼저 해주세요!");
          //     this.$router.push({path: '/login'});
          //   }
          //   if (err.response.status === 404) {
          //     alert("잘못된 경로입니다.");
          //     alert(err.response.data.message);
          //     location.reload()
          //   } else {
          //     alert(err.response.data.message);
          //     location.reload()
          //   }
          //   this.$store.state.loadingStatus = false;
          })
    },
    fnReplyList() {
      this.$axios.post(`/api/v1/reply/myWrite`, "", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      })
          .then((res) => {
            console.log(res);

            this.replyList = res.data.data;
            console.log(res.data.data);
          }).catch((err) => {
            console.log(err);

          //   if (err.response.status === 401 || err.response.status === 400) {
          //     alert("로그인을 먼저 해주세요!");
          //     this.$router.push({path: '/login'});
          //   }
          //   if (err.response.status === 404) {
          //     alert("잘못된 경로입니다.");
          //     alert(err.response.data.message);
          //     location.reload()
          //   } else {
          //     alert(err.response.data.message);
          //     location.reload()
          //   }
          //   this.$store.state.loadingStatus = false;
          })
    },
    fnGuruInfoList() {
      this.$axios.post(`/api/v1/guru/myInfo`, "" , {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        this.guruInfoList = res.data.data;
        console.log(res.data.data);
      }).catch((err) => {
        console.log(err);

        //   if (err.response.status === 401 || err.response.status === 400) {
        //     alert("로그인을 먼저 해주세요!");
        //     this.$router.push({path: '/login'});
        //   }
        //   if (err.response.status === 404) {
        //     alert("잘못된 경로입니다.");
        //     alert(err.response.data.message);
        //     location.reload()
        //   } else {
        //     alert(err.response.data.message);
        //     location.reload()
        //   }
        //   this.$store.state.loadingStatus = false;
      })
    },
    getDetailLink() {
      if (this.role === 'ROLE_GURU') {
        return '/member/guruDetail';
      } else {
        // You can provide a default link or handle other roles as needed
        return '/member/userDetail';
      }
    },
    showTab(tabName) {
      this.activeTab = tabName;
    },
    fnPostView(idx) {
      this.requestBody.idx = idx
      this.$router.push({
        path: '../post/detail',
        query: {idx}
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

    truncateAndStripTags: function(text, length) {
      // 태그 제거
      let strippedText = text.replace(/<[^>]+>/g, '');

      // 길이 제한 및 말줄임표 추가
      if (strippedText.length <= length) {
        return strippedText;
      } else {
        return strippedText.substring(0, length) + '...';
      }
    },

  }

};

</script>

<style scoped>
.hover-pointer {
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
}

.small-icon {
  font-size: 15px; /* Adjust the font size to your preference */
}

.post-item {
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 10px;
  margin-bottom: 10px;
  cursor: pointer;
}

.post-category {
  color: #888;
  font-size: 15px;
  margin-bottom: 5px;
}

.post-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 5px;
}

.post-content {
  color: #333;
  margin-bottom: 5px;
}

.post-status {
  color: #888;
}

.post-reg-date {
  color: #888;
  text-align: right;
}

</style>