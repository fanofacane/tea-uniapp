<template>
  <div class="container">
    <!-- 轮播图组件 -->
    <swiper 
      class="swiper-container"
      :current="current"
      :autoplay="autoplay"
      :interval="interval"
      :duration="duration"
      :circular="circular"
      @change="onSwiperChange"
    >
      <!-- 轮播图片 -->
      <swiper-item v-for="(item, index) in swiperList" :key="item.imageUrl">
        <div class="swiper-item">
          <img :src="item.imageUrl" mode="aspectFill" class="swiper-image" alt="轮播图片">
        </div>
      </swiper-item>
    </swiper>
    
    <!-- 自定义指示器 -->
    <div class="indicator-container" v-if="showIndicator">
      <div 
        class="indicator-dot" 
        :class="{ 'active': current === index }" 
        v-for="(item, index) in swiperList" 
        :key="item.imageUrl"
        @click="jumpToSlide(index)"
      ></div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed, onMounted, onUnmounted } from 'vue';

// 定义轮播图项的接口
interface SwiperItem {
  imageUrl: string;
  title?: string;
}

// 响应式数据
const current = ref(0); // 当前轮播图索引
const autoplay = ref(true); // 是否自动播放
const interval = ref(3000); // 自动切换时间间隔，单位ms
const duration = ref(500); // 滑动动画时长，单位ms
const circular = ref(true); // 是否采用衔接滑动
const showIndicator = ref(true); // 是否显示指示器

// 轮播图数据
const swiperList = ref<SwiperItem[]>([
  { imageUrl: 'https://g.gumingnc.com/u/fchjReZ/YQ.JPG'},
  { imageUrl: 'https://g.gumingnc.com/u/8BM4KAg/series-2.jpg'},
  { imageUrl: 'https://g.gumingnc.com/u/BBM4QFH/series-3.jpg'}
]);

// 轮播图切换事件处理
const onSwiperChange = (e: any) => {
  current.value = e.detail.current;
};

// 点击指示器跳转到对应轮播页
const jumpToSlide = (index: number) => {
  current.value = index;
};

// 计算指示器的样式
const indicatorStyle = computed(() => {
  return {
    'display': showIndicator.value ? 'flex' : 'none'
  };
});

// 自动播放逻辑
let autoplayTimer: number | null = null;

const startAutoplay = () => {
  if (autoplay.value && swiperList.value.length > 1) {
    autoplayTimer = setInterval(() => {
      current.value = (current.value + 1) % swiperList.value.length;
    }, interval.value) as unknown as number;
  }
};

const stopAutoplay = () => {
  if (autoplayTimer) {
    clearInterval(autoplayTimer);
    autoplayTimer = null;
  }
};

// 生命周期钩子
onMounted(() => {
  startAutoplay();
});

onUnmounted(() => {
  stopAutoplay();
});
</script>

<style lang="scss" scoped>
.container {
  position: relative;
  width: 100%;
  height: 400rpx; // 轮播图高度，根据需要调整
}

.swiper-container {
  width: 100%;
  height: 100%;
}

.swiper-item {
  width: 100%;
  height: 100%;
  position: relative;
}

.swiper-image {
  width: 100%;
  height: 100%;
}



.indicator-container {
  position: absolute;
  bottom: 20rpx;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.indicator-dot {
  width: 16rpx;
  height: 16rpx;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.5);
  margin: 0 8rpx;
  transition: all 0.3s;
  
  &.active {
    width: 32rpx;
    border-radius: 8rpx;
    background-color: white;
  }
}
</style>  