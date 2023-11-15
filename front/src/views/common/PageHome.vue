<template>
  <div>
    <div class="container mt-3 page-back">
      <PageBanner />
      <br><h2>안녕하세요! <strong>IM-GURU</strong> 에 방문하신 것을 환영합니다! 😄</h2><br>
      <hr>
      <h4><strong>전</strong>문가의 <strong>도</strong>움이 필요한 <strong>사</strong>람들</h4><br>

      <ul class="skill-list">
        <li>
          <router-link to="/post/all/category=programming" class="skill-link">
            <div class="skill-item hover-effect">
              <img :src="require('@/assets/image/skill/programming.png')" alt="Skill-Programming">
              <br><label class="mt-3">프로그래밍</label>
            </div>
          </router-link>
        </li>

        <li>
          <router-link to="/post/all/category=interior" class="skill-link">
            <div class="skill-item hover-effect">
              <img :src="require('@/assets/image/skill/interior.png')" alt="Skill-Interior">
              <br><label class="mt-3">인테리어</label>
            </div>
          </router-link>
        </li>

        <li>
          <router-link to="/post/all/category=translate" class="skill-link">
            <div class="skill-item hover-effect">
              <img :src="require('@/assets/image/skill/translate.png')" alt="Skill - Translate">
              <br><label class="mt-3">번역</label>
            </div>
          </router-link>
        </li>

        <li>
          <router-link to="/post/all/category=law" class="skill-link">
            <div class="skill-item hover-effect">
              <img :src="require('@/assets/image/skill/law.png')" alt="Skill - Law">
              <br><label class="mt-3">법률</label>
            </div>
          </router-link>
        </li>

        <li>
          <router-link to="/post/all/category=clean" class="skill-link">
            <div class="skill-item hover-effect">
              <img :src="require('@/assets/image/skill/clean.png')" alt="Skill - Clean">
              <br><label class="mt-3">청소</label>
            </div>
          </router-link>
        </li>

        <li>
          <router-link to="/post/all/category=document" class="skill-link">
            <div class="skill-item hover-effect">
              <img :src="require('@/assets/image/skill/document.png')" alt="Skill - Document">
              <br><label class="mt-3">문서작업</label>
            </div>
          </router-link>
        </li>

        <li>
          <router-link to="/post/all/category=design" class="skill-link">
            <div class="skill-item hover-effect">
              <img :src="require('@/assets/image/skill/design.png')" alt="Skill - Design">
              <br><label class="mt-3">디자인</label>
            </div>
          </router-link>
        </li>

        <li>
          <router-link to="/post/all/category=music" class="skill-link">
            <div class="skill-item hover-effect">
              <img :src="require('@/assets/image/skill/music.png')" alt="Skill - Music">
              <br><label class="mt-3">음악</label>
            </div>
          </router-link>
        </li>

        <li>
          <router-link to="/post/all/category=etc" class="skill-link">
            <div class="skill-item hover-effect">
              <img :src="require('@/assets/image/skill/etc.png')" alt="Skill - Etc">
              <br><label class="mt-3">기타</label>
            </div>
          </router-link>
        </li>
      </ul>
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
