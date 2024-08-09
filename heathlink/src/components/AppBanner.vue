<template>
  <div class="app-banner">
    <div class="carousel-container">
      <swiper-container
        :options="swiperOptions"
        class="mySwiper"
      >
        <swiper-slide v-for="(image, index) in images" :key="index">
          <img :src="image" alt="Slide Image" />
        </swiper-slide>
        <div class="swiper-button-next"></div>
        <div class="swiper-button-prev"></div>
        <div class="swiper-pagination"></div>
      </swiper-container>
    </div>
    <div class="right-side">
    </div>
  </div>
</template>

<script>
import { Swiper as SwiperClass, SwiperSlide } from 'swiper/vue';
import 'swiper/swiper-bundle.css';

export default {
  components: {
    SwiperContainer: SwiperClass,
    SwiperSlide
  },
  data() {
    return {
      images: [
        require('@/assets/images/banner-bg.png'),
        require('@/assets/images/banner-bg.png'),
        require('@/assets/images/banner-bg.png')
      ],
      swiperOptions: {
        loop: true,
        autoplay: {
          delay: 1000,
          disableOnInteraction: false
        },
        pagination: {
          el: '.swiper-pagination',
          clickable: true
        },
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev'
        }
      }
    };
  }
}
</script>

<style scoped>
.app-banner {
  display: flex;
  width: 100%;
  height: 650px;
  background-image: url('@/assets/images/banner.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}

.carousel-container {
  width: 50%;
  height: 100%;
  position: relative;
  z-index: 1;
}

.swiper-slide img {
  width: 100%;
  height: 650px;
  object-fit: cover;
}

.swiper-button-next,
.swiper-button-prev {
  color: #fff; 
  width: 40px; 
  height: 40px;
}

.right-side {
  width: 50%;
  height: 100%;
}
</style>
