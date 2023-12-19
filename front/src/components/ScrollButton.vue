<template>
  <div class="scroll-button-container">
    <button class="btn btn-outline-dark" @click="scrollToTop">▲</button>&nbsp;
    <button class="btn btn-outline-dark" @click="scrollToBottom">▼</button>
  </div>
</template>

<script>
export default {
  methods: {
    scrollToTop() {
      window.scrollTo({ top: 0, behavior: 'smooth' });
    },
    scrollToBottom() {
      window.scrollTo({ top: document.documentElement.scrollHeight, behavior: 'smooth' });
    }
  }
}
</script>
<style scoped>
/* 스크롤 버튼 컨테이너 스타일 */
.scroll-button-container {
  position: fixed;
  z-index: 999; /* 다른 요소 위에 표시하기 위한 z-index 설정 */
  top: 800px; /* 상단 여백 조절 */
  right: 20px; /* 오른쪽 여백 조절 */
}
/* 버튼 스타일 */
button {
  cursor: pointer;
}
</style>