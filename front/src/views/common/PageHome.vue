<template>
  <div>
    <PageBanner />

    <div class="container mt-3">
      <div>
<!--        <span>테스트 유저: user@test.com / 1234</span><br>-->
<!--        <span>도사 유저: guru@test.com / 1234</span><br>-->
<!--        <span>관리자 유저: admin@test.com / 1234</span><br>-->
      </div>
<!--      <br><h2>안녕하세요! <strong>IM-GURU</strong> 에 방문하신 것을 환영합니다! 😄</h2><br>-->
<!--      <hr>-->
      <h4 class="mt-5"><strong>전</strong>문가의 <strong>도</strong>움이 필요한 <strong>사</strong>람들</h4><br>

<!--      <b-img :src="require('@/assets/image/home/main_message.png')" alt="main-message" />-->

      <ul class="skill-list">
        <li>
          <router-link :to="{ path: '/post/list', query: { skill: '프로그래밍' } }" class="skill-link">
            <div class="skill-item hover-effect">
              <img :src="require('@/assets/image/skill/programming.png')" alt="Skill-Programming">
              <br><label class="mt-3">프로그래밍</label>
            </div>
          </router-link>
        </li>

        <li>
          <router-link :to="{ path: '/post/list', query: { skill: '인테리어' } }" class="skill-link">
            <div class="skill-item hover-effect">
              <img :src="require('@/assets/image/skill/interior.png')" alt="Skill-Interior">
              <br><label class="mt-3">인테리어</label>
            </div>
          </router-link>
        </li>

        <li>
          <router-link :to="{ path: '/post/list', query: { skill: '번역' } }" class="skill-link">
            <div class="skill-item hover-effect">
              <img :src="require('@/assets/image/skill/translate.png')" alt="Skill - Translate">
              <br><label class="mt-3">번역</label>
            </div>
          </router-link>
        </li>

        <li>
          <router-link :to="{ path: '/post/list', query: { skill: '법률' } }" class="skill-link">
            <div class="skill-item hover-effect">
              <img :src="require('@/assets/image/skill/law.png')" alt="Skill - Law">
              <br><label class="mt-3">법률</label>
            </div>
          </router-link>
        </li>

        <li>
          <router-link :to="{ path: '/post/list', query: { skill: '청소' } }" class="skill-link">
            <div class="skill-item hover-effect">
              <img :src="require('@/assets/image/skill/clean.png')" alt="Skill - Clean">
              <br><label class="mt-3">청소</label>
            </div>
          </router-link>
        </li>

        <li>
          <router-link :to="{ path: '/post/list', query: { skill: '문서작업' } }" class="skill-link">
            <div class="skill-item hover-effect">
              <img :src="require('@/assets/image/skill/document.png')" alt="Skill - Document">
              <br><label class="mt-3">문서작업</label>
            </div>
          </router-link>
        </li>

        <li>
          <router-link :to="{ path: '/post/list', query: { skill: '디자인' } }" class="skill-link">
            <div class="skill-item hover-effect">
              <img :src="require('@/assets/image/skill/design.png')" alt="Skill - Design">
              <br><label class="mt-3">디자인</label>
            </div>
          </router-link>
        </li>

        <li>
          <router-link :to="{ path: '/post/list', query: { skill: '음악' } }" class="skill-link">
            <div class="skill-item hover-effect">
              <img :src="require('@/assets/image/skill/music.png')" alt="Skill - Music">
              <br><label class="mt-3">음악</label>
            </div>
          </router-link>
        </li>

        <li>
          <router-link :to="{ path: '/post/list', query: { skill: '기타' } }" class="skill-link">
            <div class="skill-item hover-effect">
              <img :src="require('@/assets/image/skill/etc.png')" alt="Skill - Etc">
              <br><label class="mt-3">기타</label>
            </div>
          </router-link>
        </li>
      </ul>

      <hr class="mt-5">

      <b-img
          :src="require('@/assets/image/home/main_post.png')"
          alt="main-message"
          class="img-fluid mx-auto mt-5"
          style="width: 100%; height: auto;"/>

      <hr class="mt-5">

      <b-img
          :src="require('@/assets/image/home/main_message.png')"
          alt="main-message"
          class="img-fluid mx-auto mt-5"
          style="width: 100%; height: auto;"/>


      <br><br><br>



    </div>
  </div>

  <br>
  <br>
  <br>
  <br>
  <br>

</template>

<script>
import PageBanner from "@/components/PageBanner.vue";

export default {
  components: {
    PageBanner
  },
  data() {
    return {};
  },
  created() {},
  methods: {}
};
</script>

<style scoped>
.skill-list {
  list-style: none;
  padding: 0;
  display: flex;
  justify-content: space-between;
  margin-top: 20px; /* 이미지와 상단 간의 간격 조정 */
}

.skill-list li {
  flex: 1;
  margin-right: 10px; /* 이미지 간의 간격 조정 */
}

.skill-link {
  text-decoration: none; /* 기본 링크 효과 제거 */
}

.skill-item {
  text-align: center; /* 내용을 가운데 정렬 */
}

.hover-effect {
  transition: transform 0.3s ease-in-out;
}

.hover-effect:hover {
  transform: translateY(-10px); /* 마우스 호버 시 10px 위로 이동 */
  border-radius: 10px; /* 모서리 둥글게 설정 */

  background-color: #eeeeee;
}

.skill-item img {
  width: 70px;
  height: 70px;
  margin-top: 5px;
}

.skill-item label {
  font-size: 15px; /* 글씨 크기 조절 */
  font-weight: bold; /* 글씨 굵게 */
  color: #2c3e50; /* 글씨 색상 변경 */
  cursor: pointer;
}

</style>
