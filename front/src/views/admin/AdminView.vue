<template>
  <div class="admin-list">

    <div class="row">
      <div class="col-2 my-5 post-container">
        <div class="row">
          <h4>관리자 정보</h4>
          <div class="col-2" style="text-align: center">
            <i class="fa-solid fa-user" style="font-size: 50px"></i>
          </div>
          <div>이름: asdf</div>
        </div>

        <hr>

        <div class="col mr-5">
          <!-- 카테고리 버튼들... -->
          <nav class="nav flex-column nav-pills nav-fill">
            <b-button class="nav-link d-block mb-2">회원 관리</b-button>
            <b-button class="nav-link d-block mb-2">도사 정보 관리</b-button>
            <b-button class="nav-link d-block mb-2">게시글 관리</b-button>
            <b-button class="nav-link d-block mb-2">댓글 관리</b-button>
            <b-button class="nav-link d-block mb-2">후기 관리</b-button>
            <b-button class="nav-link d-block mb-2">메세지 관리</b-button>
            <b-button class="nav-link d-block mb-2">게시글 신고 관리</b-button>
            <b-button class="nav-link d-block mb-2">댓글 신고 관리</b-button>
            <b-button class="nav-link d-block mb-2">전문 기술 관리</b-button>
          </nav>
        </div>
      </div>

      <div class="col my-5 post-container">
        <div>sdfasdf</div>

      </div>

    </div>

  </div>


</template>

<script>

export default {

  data() {
    return {}
  },

  mounted() {

  },

  methods: {}

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