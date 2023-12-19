<template>

  <div class="post-list">

    <div class="row mt-5 mb-3">
      <div class="row">
        <div class="col-2" style="text-align: center">
          <h4 class="mb-2"><strong>내 정보</strong></h4>

          <!-- 이미지가 있을 때 -->
          <img v-if="fileFormat && fileFormat.fileUrl" :src="fileFormat.fileUrl" alt="이미지 파일"
               style="width: 120px; height: 120px; border-radius: 50%; border: 1px solid #888888;"/>

          <!-- 이미지가 없을 때 -->
          <i v-else class="fa-solid fa-user" style="font-size: 50px"></i><br>

          <button class="mt-2 btn-sm btn-outline-dark btn-rounded small-button">
            <router-link to="/member/uploadImage" class="no-underline">프로필 사진</router-link>
          </button>
        </div>

        <div class="col">
          <br><br>
          <table>
            <tr>
              <td><strong>이름:&nbsp;</strong></td>
              <td>{{ name }}</td>
            </tr>
            <tr>
              <td><strong>닉네임:&nbsp;</strong></td>
              <td>{{ this.nickname }}</td>
            </tr>
            <tr v-if="role === 'ROLE_GURU'">
              <td><strong>기술:&nbsp;</strong></td>
              <td>{{ this.skillName }}</td>
            </tr>
            <tr>
              <td><strong>직업:&nbsp;</strong></td>
              <td>{{ this.job }}</td>
            </tr>
            <tr>
              <td><strong>주소:&nbsp;</strong></td>
              <td>{{ this.address }}</td>
            </tr>
          </table>
        </div>
      </div>

      <div class="common-buttons">
        <button class="btn btn-outline-dark btn-rounded small-button" style="width:100px" @click="getDetailLink">
          정보 수정
        </button>
      </div>

    </div>

    <hr>

    <div class="row post-list mt-5">
      <div>
        <!-- 네비게이션 탭 -->
        <ul class="nav nav-tabs">
          <li class="nav-item" v-if="role === 'ROLE_GURU'">
            <a class="nav-link hover-pointer" @click="showTab('guruInfo')"
               :class="{ active: activeTab === 'guruInfo' }">도사 정보</a>
          </li>
          <li class="nav-item">
            <a class="nav-link hover-pointer" @click="showTab('myPosts')" :class="{ active: activeTab === 'myPosts' }">내가
              작성한 게시물</a>
          </li>
          <li class="nav-item">
            <a class="nav-link hover-pointer" @click="showTab('likedPosts')"
               :class="{ active: activeTab === 'likedPosts' }">내가 좋아요 누른
              게시물</a>
          </li>
          <li class="nav-item">
            <a class="nav-link hover-pointer" @click="showTab('myReplies')"
               :class="{ active: activeTab === 'myReplies' }">내가 작성한
              댓글</a>
          </li>
          <li class="nav-item" v-if="role !== 'ROLE_GURU'">
            <a class="nav-link hover-pointer" @click="showTab('myReviews')"
               :class="{ active: activeTab === 'myReviews' }">내가 작성한
              후기</a>
          </li>
          <li class="nav-item" v-if="role === 'ROLE_GURU'">
            <a class="nav-link hover-pointer" @click="showTab('myGuruReviews')"
               :class="{ active: activeTab === 'myGuruReviews' }">내가 받은
              후기</a>
          </li>
          <li class="nav-item">
            <a class="nav-link hover-pointer" @click="showTab('myPays')" :class="{ active: activeTab === 'myPays' }">내
              결제 내역</a>
          </li>
        </ul>
      </div>

      <div v-if="activeTab === 'guruInfo' & role === 'ROLE_GURU'">
        <div class="mt-3" v-if="existGuruInfo === false">
          <div>아직 작성 된 도사 정보가 없습니다.</div>
          <router-link to="/guru/write">도사 정보 작성하러 가기</router-link>
        </div>
        <div class="mt-3" v-if="existGuruInfo === true">
          <div>
            <router-link to="/guru/update">도사 정보 수정하기</router-link>

            <div v-if="guruInfoList" class="post-item hover-pointer">
              <div>한줄 소개: {{ guruInfoList.intro }}</div>
              <div>회사: {{ guruInfoList.companyName }}</div>
              <div>직급: {{ guruInfoList.position }}</div>
              <div>경력: {{ guruInfoList.careerAt }}</div>
              <div>연락 가능 시간: {{ guruInfoList.contactTime }}</div>
              <div>활동 가능 지역: {{ guruInfoList.workArea }}</div>
              <div>업무 설명: {{ guruInfoList.description }}</div>
            </div>
          </div>
        </div>
      </div>

      <div v-if="activeTab === 'myPosts'">
        <div class="mt-3">
          <div>
            <div v-if="postList.length === 0" class="post-item">
              작성된 게시물이 존재하지 않습니다!
            </div>

            <div v-else>
              <div v-for="(item, idx) in postList" :key="idx" @click="fnPostView(item.postId)" class="post-item hover-pointer">
                <div class="post-category">{{ item.postCategory }} - {{ item.skillName }}</div>
                <div class="post-title">
                  <span v-if="item.title.length < 40">{{ item.title }} &nbsp;&nbsp;</span>
                  <span v-else>{{ item.title.substring(0, 40) + "..." }}</span>
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
      </div>

      <div v-if="activeTab === 'likedPosts'">
        <div class="mt-3">
          <div>
            <div v-if="likePostList.length === 0" class="post-item">
              좋아요 누른 게시물이 존재하지 않습니다!
            </div>

            <div v-else>
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
      </div>

      <div v-if="activeTab === 'myReplies'">
        <div class="mt-3">
          <div>
            <div v-if="replyList.length === 0" class="post-item">
              작성한 댓글이 존재하지 않습니다!
            </div>

            <div v-else>
              <div v-for="(reply, idx) in replyList" :key="idx" @click="fnPostView(reply.postId)" class="reply-container hover-pointer">
                <div class="reply-detail">
                  <strong class="mouse-cursor">[{{ reply.memberNickname }}]</strong> - <span class="small-font">{{ reply.memberSkill }}</span>
                  <p class="mt-1">{{ reply.content }}</p>
                </div>
                <span><small>{{ formatDateTime(reply.regDate) }} - </small></span>
                <i class="fa-solid fa-thumbs-up Reply-heart-icon"><small>&nbsp;좋아요 {{ reply.likeCnt }}</small></i>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div v-if="activeTab === 'myReviews'">
        <div class="mt-3">
          <div>
            <div v-if="reviewList.length === 0" class="post-item">
              작성한 후기가 존재하지 않습니다!
            </div>

            <div v-else>
              <div v-for="(review, idx) in reviewList" :key="idx" class="reply-container">
                <div class="reply-detail">
                  <div style="float: left;">
                    <strong>{{ review.guruNickname }}</strong> - <span class="small-font">{{ review.guruSkill }}</span><br>
                    <div class="star-rating">
                      <div class="review-star" v-for="index in 5" :key="index">
                        <span v-if="index < review.rate"><small>🍎</small></span>
                        <span v-if="index >= review.rate"><small>🍏</small></span>
                      </div>
                    </div>
                    {{ review.content }}<br>
                    <span><small>{{ formatDateTime(review.regDate) }}</small></span>
                  </div>
                  <div style="float: right;">
                    <button class="btn-sm btn-outline-dark btn-rounded small-button">수정</button>&nbsp;
                    <button class="btn-sm btn-outline-danger btn-rounded small-button">삭제</button>
                  </div>
                  <div style="clear: both;"></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div v-if="activeTab === 'myGuruReviews'">
        <div class="mt-3">
          <div>
            <div v-if="guruReviewList.length === 0" class="post-item">
              작성한 후기가 존재하지 않습니다!
            </div>

            <div v-else>
              <div v-for="(review, idx) in guruReviewList" :key="idx" class="reply-container">
                <div class="reply-detail">
                  <div>
                    <strong>{{ review.userNickname }}</strong><br>
                    <div class="star-rating">
                      <div class="review-star" v-for="index in 5" :key="index">
                        <span v-if="index < review.rate"><small>🍎</small></span>
                        <span v-if="index >= review.rate"><small>🍏</small></span>
                      </div>
                    </div>
                    {{ review.content }}<br>
                    <span><small>{{ formatDateTime(review.regDate) }}</small></span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div v-if="activeTab === 'myPays'">
        <div class="mt-3">
          <div>
            <div v-if="payList.length === 0" class="post-item">
              결제 내역이 존재하지 않습니다!
            </div>

            <div v-else>
              <div v-for="(pay, idx) in payList" :key="idx" class="pay-item">
                <div class="reply-detail">
                  <div style="float: left;">
                    <strong>주문번호</strong>: {{ pay.ordNo }}<br>
                    <strong>상품명:</strong> {{ pay.productNm }}<br>
                    <strong>거래금액:</strong> {{ pay.trPrice }}<br>
                    <strong>거래시간:</strong> {{ payDateTime(pay.trDay, pay.trTime) }}<br>
                  </div>
                  <div v-if="pay.payStatus !== 'C'" style="float: right;">
                    <button class="btn-sm btn-outline-dark btn-rounded small-button" @click="goReview(pay.id)">후기 남기기
                    </button>&nbsp;
                    <button class="btn-sm btn-outline-dark btn-rounded small-button"
                            @click="goCancel(pay.ordNo, pay.trNo)">결제 취소
                    </button>
                  </div>
                  <div v-else style="float: right;">
                    <span><small>결제 취소 완료</small></span>
                  </div>
                  <div style="clear: both;"></div>
                </div>
              </div>
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
      reviewList: {}, // 내가 작성한 후기 리스트
      guruReviewList: {}, // 도사가 받은 후기 리스트
      payList: {},  // 내 결제내역

      activeTab: 'myPosts',

      name: '',
      nickname: '',
      skillName: '',
      job: '',
      address: '',
      role: '',
      fileFormat: '',

      postCategory: '',
      replyCount: '',
      likeCount: '',

      memberNickname: '',

      existGuruInfo: '',

      existReview: '',
    }
  },

  mounted() {
    this.fnLoginMember();
    this.fnPostList();
    this.fnLikePostList();
    this.fnReplyList();
    this.fnGuruInfoList();
    this.fnPayList();
    this.fnReviewList();
    this.fnGuruReviewList();
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
        this.fileFormat = res.data.data.file;

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
    fnReviewList() {
      this.$axios.post(`/api/v1/review/myWrite`, "", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      })
          .then((res) => {
            console.log(res);

            this.reviewList = res.data.data;
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
    fnGuruReviewList() {
      this.$axios.post(`/api/v1/review/guru/myWrite`, "", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      })
          .then((res) => {
            console.log(res);

            this.guruReviewList = res.data.data;
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
    fnPayList() {
      this.$axios.post(`/api/v1/pay/myPay`, "", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      })
          .then((res) => {
            console.log(res);

            this.payList = res.data.data;
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
    goReview(payId) {
      if (localStorage.getItem("user_token") === null) {
        alert("로그인 해야 가능한 서비스입니다.");
        window.location.href = "http://localhost:3000/login";
        return;
      }

      // 작성된 후기가 있는지 체크
      this.$axios.post(`/api/v1/review/check/${payId}`)
          .then((res) => {
            this.existReview = res.data.data;
            console.log(this.existReview)

            if (this.existReview === false) {
              alert("이미 작성된 후기가 존재합니다.");
            } else {
              this.$router.push({
                path: '../review/write',
                query: {
                  pay: payId,
                }
              });
            }
          }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({path: '/login'});
        } else {
          alert(err.response.data.message);
        }
        this.$store.state.loadingStatus = false;
      })


    },
    goCancel(ordNo, trNo) {
      if (!confirm("해당 결제를 취소하시겠습니까?")) return;

      this.$axios.post(`/api/v1/payCancel/${ordNo}/${trNo}`)
          .then((res) => {
            console.log(res.data);

            if (res.data.resultCd === "0") {
              alert("결제 취소 성공");
              location.reload();
            } else {
              alert("결제 취소 실패 : " + res.data.resultMsg);
            }

          }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({path: '/login'});
        } else {
          alert(err.response.data.message);
        }
        this.$store.state.loadingStatus = false;
      })
    },

    fnGuruInfoList() {
      this.$axios.post(`/api/v1/guru/myInfo`, "", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        this.guruInfoList = res.data.data;
        this.existGuruInfo = true;
        console.log("-------");
        console.log(res.data.data);
      }).catch((err) => {
        console.log(err);
        this.existGuruInfo = false;
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
        // return '/member/guruDetail';
        this.$router.push({
          path: '../member/guruDetail'
        })
      } else {
        // You can provide a default link or handle other roles as needed
        // return '/member/userDetail';
        this.$router.push({
          path: '../member/userDetail'
        })
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

    payDateTime(date, time) {
      const dateTimeString = `${date.slice(0, 4)}-${date.slice(4, 6)}-${date.slice(6)} ${time.slice(0, 2)}:${time.slice(2, 4)}:${time.slice(4)}`;

      // Date 객체를 생성하고, Intl.DateTimeFormat을 사용하여 원하는 형식으로 포맷
      return new Date(dateTimeString).toLocaleString('ko-KR', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
      });
    },

    truncateAndStripTags: function (text, length) {
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

.pay-item {
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 10px;
  margin-bottom: 10px;
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

.review-star {
  display: inline-block; /* or inline-flex */
  margin-right: 5px; /* Adjust the margin as needed */
  transition: background-color 0.3s ease-in-out;
}


table {
  width: 50%;
  border-collapse: collapse;
}

table, th, td {
  border: 2px solid #ddd; /* 테두리 스타일 및 색상 설정 */
}

th, td {
  padding: 3px; /* 셀 내부 여백 설정 */
  text-align: left; /* 텍스트 정렬 설정 */
}

</style>