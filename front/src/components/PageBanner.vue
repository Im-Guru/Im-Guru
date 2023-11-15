<template>
  <Carousel v-model="currentIndex">
    <slide v-for="(image, index) in images" :key="index">
      <img :src="getImagePath(image)" class="slide-image"/>
    </slide>

    <template #addons>
      <Navigation/>
      <Pagination/>
    </template>
  </Carousel>
</template>

<script>
import {defineComponent} from 'vue'
import {Carousel, Navigation, Pagination, Slide} from 'vue3-carousel'

import 'vue3-carousel/dist/carousel.css'

export default defineComponent({
  name: 'BasicBanner',
  components: {
    Carousel,
    Slide,
    Pagination,
    Navigation,
  },
  data() {
    return {
      images: [
        'banner1.jpg',
        'banner3.jpg',
        'banner4.jpg',
        'banner5.jpg',
      ],
      currentIndex: 0, // 현재 슬라이드의 인덱스를 추적
    };
  },
  methods: {
    getImagePath(imageFileName) {
      return require(`@/assets/image/${imageFileName}`);
    },
  },
  created() {
    // 5초마다 슬라이드를 자동으로 변경하는 타이머 설정
    setInterval(() => {
      this.currentIndex = (this.currentIndex + 1) % this.images.length;
    }, 5000);
  },
})
</script>

<style>
.slide-image {
  width: 100%;
  height: 500px; /* 원하는 높이로 조정 */
  object-fit: cover; /* 이미지 비율 유지 및 부족한 부분 채우기 */
  border-radius: 10px; /* 모서리 둥글게 설정 */
}
</style>
