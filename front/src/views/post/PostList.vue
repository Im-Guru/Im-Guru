<template>
  <div class="post-list">
    <div class="row my-5">

      <!-- 좌측 카테고리 네비게이션 -->
      <div class="col-md-3 mr-5">
        <!-- 카테고리 버튼들... -->
        <nav class="nav flex-column nav-pills nav-fill post-item">
          <b-button :class="{ active: skill === '' }" class="btn btn-link custom-button mb-3"
                    @click="fnSelectSkill('')">전체
          </b-button>
          <b-button :class="{ active: skill === '프로그래밍' }" class="btn btn-link custom-button mb-3"
                    @click="fnSelectSkill('프로그래밍')">프로그래밍
          </b-button>
          <b-button :class="{ active: skill === '인테리어' }" class="btn btn-link custom-button mb-3"
                    @click="fnSelectSkill('인테리어')">인테리어
          </b-button>
          <b-button :class="{ active: skill === '번역' }" class="btn btn-link custom-button mb-3"
                    @click="fnSelectSkill('번역')">번역
          </b-button>
          <b-button :class="{ active: skill === '법률' }" class="btn btn-link custom-button mb-3"
                    @click="fnSelectSkill('법률')">법률
          </b-button>
          <b-button :class="{ active: skill === '청소' }" class="btn btn-link custom-button mb-3"
                    @click="fnSelectSkill('청소')">청소
          </b-button>
          <b-button :class="{ active: skill === '문서작업' }" class="btn btn-link custom-button mb-3"
                    @click="fnSelectSkill('문서작업')">문서작업
          </b-button>
          <b-button :class="{ active: skill === '디자인' }" class="btn btn-link custom-button mb-3"
                    @click="fnSelectSkill('디자인')">디자인
          </b-button>
          <b-button :class="{ active: skill === '음악' }" class="btn btn-link custom-button mb-3"
                    @click="fnSelectSkill('음악')">음악
          </b-button>
          <b-button :class="{ active: skill === '기타' }" class="btn btn-link custom-button mb-3"
                    @click="fnSelectSkill('기타')">기타
          </b-button>
        </nav>
      </div>

      <!-- 우측 컨텐츠 영역 -->
      <div class="col-md-9">

        <div class="nav-buttons mb-3">
          <b-button :class="{ active: role === '' }" class="btn btn-link custom-button" @click="fnSelectRole('')">
            모두
          </b-button>
          <b-button :class="{ active: role === 'GURU' }" class="btn btn-link custom-button" @click="fnSelectRole('GURU')">
            도사만
          </b-button>
          <b-button :class="{ active: role === 'USER' }" class="btn btn-link custom-button" @click="fnSelectRole('USER')">
            이용자만
          </b-button>

          <!-- 글쓰기 버튼 -->
          <div class="common-buttons">
            <button type="button" class="btn-sm btn-outline-dark btn-rounded small-button" @click="fnWrite">
              <span><i class="fa-solid fa-pen small-icon"></i></span>
              글쓰기
            </button>
          </div>
        </div>

        <!-- 카테고리 필터 버튼들 -->
        <div class="nav-buttons mb-3">
          <b-button :class="{ active: postCategory === '' }" class="btn btn-link custom-button" @click="fnSelectCategory('')">
            전체글
          </b-button>
          <b-button v-if="role === ''" :class="{ active: postCategory === 'NOTICE' }" class="btn btn-link custom-button" @click="fnSelectCategory('NOTICE')">
            공지사항
          </b-button>
          <b-button :class="{ active: postCategory === 'FREE' }" class="btn btn-link custom-button" @click="fnSelectCategory('FREE')">
            자유게시판
          </b-button>
          <b-button v-if="role === 'USER'" :class="{ active: postCategory === 'QNA' }" class="btn btn-link custom-button" @click="fnSelectCategory('QNA')">
            질문있어요
          </b-button>
          <b-button :class="{ active: postCategory === 'INFO' }" class="btn btn-link custom-button" @click="fnSelectCategory('INFO')">
            정보나눠요
          </b-button>
          <b-button v-if="role === 'USER'" :class="{ active: postCategory === 'FINDGURU' }" class="btn btn-link custom-button" @click="fnSelectCategory('FINDGURU')">
            도사찾아요
          </b-button>
          <b-button v-if="role === 'GURU'" :class="{ active: postCategory === 'FINDUSER' }" class="btn btn-link custom-button" @click="fnSelectCategory('FINDUSER')">
            도와드려요
          </b-button>
          <b-button v-if="role === 'USER'" :class="{ active: postCategory === 'HELP' }" class="btn btn-link custom-button" @click="fnSelectCategory('HELP')">
            도와주세요
          </b-button>

        </div>

        <!-- 글 목록 -->
        <div v-if="totalElements !== 0">
          <div  v-for="(item, idx) in list" :key="idx" @click="fnView(item.postId)" class="post-item hover-pointer">
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

        <div v-else>
          <div class="my-5 post-item">
            <strong>검색 결과가 존재하지 않습니다!</strong>
          </div>
        </div>

        <!-- 페이징 -->
        <div v-if="totalElements !== 0" class="pagination d-flex justify-content-center">
          <ul class="pagination">
            <li class="page-item">
              <a class="page-link" @click="fnPage(0)" href="javascript:;">&lt;&lt;</a>
            </li>
            <li class="page-item">
              <a class="page-link" @click="fnPage(page - 10)" href="javascript:;">&lt;</a>
            </li>
            <li class="page-item" v-for="n in paginavigation()" :key="n">
              <a class="page-link" @click="fnPage(n)" href="javascript:;">{{ n + 1 }}</a>
            </li>
            <!-- ">" 버튼은 다음 그룹으로 이동 -->
            <li class="page-item">
              <a class="page-link" @click="nextGroup" href="javascript:;" v-if="!isLastGroup">></a>
            </li>
            <!-- ">>" 버튼은 마지막 페이지로 이동 -->
            <li class="page-item">
              <a class="page-link" @click="fnPage(totalPage - 1)" href="javascript:;">&gt;&gt;</a>
            </li>
          </ul>
        </div>

        <hr>

        <!-- 검색 영역 -->
        <div class="row">
          <div class="col-2">
            <b-form-select v-model="searchType" :options="selectedOption" id="searchType"></b-form-select>
          </div>
          <div class="col">
            <input type="text" class="form-control" id="searchText" v-model="searchText" placeholder="검색어를 입력하세요"/>
          </div>
          <div class="col-2">
            <button type="submit" class="btn btn-primary btn-rounded" @click="fnSearch(searchType, searchText)">검색
            </button>
          </div>
        </div>

      </div>

    </div>

  </div>
</template>


<script>

export default {
  components: {
  },
  data() {
    return {
      requestBody: {}, //리스트 페이지 데이터전송
      list: {}, //리스트 데이터
      postCategory: '',
      skill:'',
      replyCnt: '',
      likeCnt: '',
      viewCnt: '',
      searchType: 'title',
      searchText: '',
      totalPage: '',
      totalElements: '',
      role:'',
      selectedOption: [
        {value: 'title', text: '제목'},
        {value: 'writer', text: '작성자'},
        {value: 'titleAndWriter', text: '제목 + 작성자'},
      ],

      // URL에서 page, size 정보를 가져와서 저장
      page: this.$route.query.page ? this.$route.query.page : 0,
      size: this.$route.query.size ? this.$route.query.size : 10,

      paginavigation() {
        let pageNumber = [];
        let start_page = Math.floor(this.page / 10) * 10; // 현재 페이지 그룹의 시작 페이지
        let end_page = Math.min(start_page + 10, this.totalPage); // 현재 페이지 그룹의 끝 페이지, 최대 10개까지 표시
        for (let i = start_page; i < end_page; i++) pageNumber.push(i);
        return pageNumber;
      },


    }
  },
  mounted() {
    const postCategoryFromURL = this.$route.query.postCategory;
    const skillFromURL = this.$route.query.skill;
    const roleFromURL = this.$route.query.role;

    if (postCategoryFromURL) {
      this.postCategory = postCategoryFromURL;
    }

    if (skillFromURL) {
      this.skill = skillFromURL;
    }

    if (roleFromURL) {
      this.role = roleFromURL;
    }

    this.fnGetList();
  },
  methods: {

    fnGetList() {
      this.requestBody = {
        page: this.page,
        size: this.size,
        totalPage: this.totalPage,
        postCategory: this.postCategory,
        skill: this.skill,
        role: this.role,
        searchText: this.searchText,
        searchType: this.searchType,
      }
      this.$axios.get("/api/v1/posts", {
        params: this.requestBody,
      }).then((res) => {
        console.log(res);
        this.page = res.data.data.number;
        this.size = res.data.data.size;
        this.totalPage = res.data.data.totalPages;
        this.totalElements = res.data.data.totalElements;
        this.list = res.data.data.content;
        this.replyCnt = res.data.data.replyCnt;
        this.likeCnt = res.data.data.likeCnt;
      }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({ path: '/login' });
        } else {
          alert(err.response.data.message);
          location.reload()
        }
      })
    },
    fnView(idx) {
      this.requestBody.idx = idx
      this.$router.push({
        path: './detail',
        query: {idx}
      })
    },
    fnWrite() {
      if (localStorage.getItem("user_token") === null) {
        alert("로그인 해야 가능한 서비스입니다.");
        window.location.href = "http://localhost:3000/login";
        return;
      }

      // if (localStorage.getItem("user_token") === null) {
      //   this.$toast.error("로그인 해야 가능한 서비스입니다.");
      //   window.location.href = "http://localhost:3000/login";
      //   return;
      // }

      this.$router.push({
        path: './write'
      })
    },
    fnList() {
      delete this.requestBody.idx
      this.$router.push({
        path: './list',
        query: this.requestBody
      })
    },
    fnPage(n) {
      if (this.page !== n) {
        if (this.totalPage > n && n >= 0) {
          this.page = n;
          this.fnGetList()
        }
      }
    },
    nextGroup() {
      if (!this.isLastGroup) {
        const nextPage = this.page + 10;
        if (nextPage >= this.totalPage) {
          this.fnPage(this.totalPage - (this.totalPage%10));
        } else {
          this.fnPage(nextPage);
        }
      }
    },
    fnSelectRole(role) {
      this.role = role;
      this.page = 0;
      this.$router.push({path: this.$route.path, query: {role}});
      this.fnGetList();
    },
    fnSelectCategory(postCategory) {
      this.postCategory = postCategory;
      this.page = 0;
      this.$router.push({path: this.$route.path, query: {postCategory}});
      this.fnGetList();
    },
    fnSelectSkill(skill) {
      this.skill = skill;
      this.page = 0;
      this.$router.push({path: this.$route.path, query: {skill}});
      this.fnGetList();
    },
    fnSearch(searchType, searchText) {
      this.searchText = searchText;
      this.searchType = searchType;
      this.page = 0;
      this.$router.push({
        path: this.$route.path,
        query: {
          searchType: searchType,
          searchText: searchText
        }
      });
      this.fnGetList();
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

.custom-button.active {
  background-color: #A0E9FF; /* Set the active state background color */
  color: black; /* Set the active state text color */
  transition: background-color 0.3s ease-in-out;
}

.custom-button {
  background-color: white; /* Set the active state background color */
  color: black; /* Set the active state text color */
  text-decoration: none !important;
  font-weight: bold;
}

.nav-buttons {
  display: flex;
  gap: 10px;
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