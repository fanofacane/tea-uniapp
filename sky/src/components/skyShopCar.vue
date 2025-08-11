<template>
  <view>
    <!-- 底部栏：仅购物车有商品时显示 -->
    <view
      v-if="cartList.length > 0"
      class="shopcar-bar"
      @click="showDetail = true"
    >
      <view class="cart-info">
        <view class="cart-icon">
          <image src="https://img.yzcdn.cn/vant/logo.png" class="cart-bag" />
          <view v-if="totalCount > 0" class="cart-count">{{ totalCount }}</view>
        </view>
        <view class="cart-price">
          到手约<text class="price">￥{{ totalPrice }}</text>
          <text class="discount">共减￥{{ discount }}</text>
        </view>
      </view>
      <view class="checkout-btn" @click.stop="onCheckout">去结算</view>
    </view>

    <!-- 购物车详情弹窗 -->
    <view
      v-if="showDetail"
      class="cart-detail-mask"
      @click.self="closeDetail"
    >
      <view class="cart-detail-panel" @click.stop>
        <view class="cart-detail-header">
          <text>全部 ({{ totalCount }}件)</text>
          <view class="clear-btn" @click="onClear">清空</view>
        </view>
        <scroll-view class="cart-detail-list" scroll-y>
          <view
            v-for="item in cartList"
            :key="item.productId"
            class="cart-detail-item"
          >
            <image :src="item.subImages" class="cart-detail-img" />
            <view class="cart-detail-info">
              <view class="cart-detail-title">{{ item.productName }}</view>
              <view class="cart-detail-desc">{{ item.description }}</view>
              <view class="cart-detail-price-row">
                <text class="cart-detail-price">￥{{ item.salePrice }}</text>
                <text class="cart-detail-origin">￥{{ item.originalPrice }}</text>
                <view class="cart-detail-ctrl">
                  <button class="cart-btn" @click="onMinus(item)">-</button>
                  <text>{{ item.count }}</text>
                  <button class="cart-btn" @click="onPlus(item)">+</button>
                </view>
              </view>
            </view>
          </view>
        </scroll-view>
        <view class="cart-detail-footer">
          <view>
            <text>合计：</text>
            <text class="footer-price">￥{{ totalPrice }}</text>
            <text class="footer-discount">共减￥{{ discount }}</text>
          </view>
          <view class="footer-checkout" @click="onCheckout">去结算</view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import type { CartProduct } from '@/types/product'

const props = defineProps<{
  cartList: CartProduct[]
}>()
const emit = defineEmits(['update', 'checkout', 'clear'])

const showDetail = ref(false)

const totalCount = computed(() =>
  props.cartList.reduce((sum, item) => sum + item.count, 0)
)
const totalPrice = computed(() =>
  props.cartList.reduce((sum, item) => sum + item.count * item.salePrice, 0).toFixed(2)
)
const discount = computed(() =>
  props.cartList.reduce((sum, item) => sum + item.count * (item.originalPrice - item.salePrice), 0).toFixed(2)
)
// 只有点击遮罩层才关闭弹窗
function closeDetail() {
  showDetail.value = false
}
// 监听购物车商品数量，变为0时自动关闭弹窗
watch(() => props.cartList.length,
  (len) => {
    if (len === 0) showDetail.value = false
  }
)

function onPlus(item: CartProduct) {
  emit('update', { ...item, count: item.count + 1 })
}
function onMinus(item: CartProduct) {
  if (item.count > 1) {
    emit('update', { ...item, count: item.count - 1 })
  } else {
    emit('update', { ...item, count: 0 }) // 0时移除
  }
}
function onClear() {
  emit('clear')
}
function onCheckout() {
  emit('checkout')
  showDetail.value = false
}
</script>

<style scoped>
.shopcar-bar {
  display: flex;
  align-items: center;
  background: #222;
  color: #fff;
  padding: 10px 12px;
  position: fixed;
  left: 0; right: 0; bottom: 0;
  z-index: 100;
}
.cart-info {
  flex: 1;
  display: flex;
  align-items: center;
}
.cart-icon {
  position: relative;
  margin-right: 8px;
}
.cart-bag {
  width: 32px;
  height: 32px;
}
.cart-count {
  position: absolute;
  top: -8px; right: -8px;
  background: #fff;
  color: #d19a3a;
  border-radius: 50%;
  font-size: 12px;
  padding: 2px 6px;
}
.cart-price {
  font-size: 10px;
}
.price {
  font-size: 15px;
  color: #fff;
  font-weight: bold;
  margin: 0 4px;
}
.discount {
  color: #f5d488;
  margin-left: 4px;
  font-size: 10px;
}
.checkout-btn {
  background: #656565;
  color: #fff;
  border-radius: 8px;
  padding: 10px 24px;
  font-size: 18px;
  font-weight: bold;
  margin-left: 10px;
}
.cart-detail-mask {
  position: fixed;
  left: 0; right: 0; top: 0; bottom: 0;
  background: rgba(0,0,0,0.4);
  z-index: 200;
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
}
.cart-detail-panel {
  width: 100%;
  max-height: 60vh;
  background: #fff;
  border-radius: 16px 16px 0 0;
  animation: slideUp .3s;
  display: flex;
  flex-direction: column;
}
@keyframes slideUp {
  from { transform: translateY(100%);}
  to { transform: translateY(0);}
}
.cart-detail-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 16px 8px 16px;
  font-size: 16px;
  border-bottom: 1px solid #eee;
}
.clear-btn {
  color: #d19a3a;
  font-size: 14px;
  cursor: pointer;
}
.cart-detail-list {
  flex: 1;
  max-height: 260px;
  overflow-y: auto;
  padding: 8px 0;
}
.cart-detail-item {
  display: flex;
  align-items: flex-start;
  padding: 10px 16px;
  border-bottom: 1px solid #f5f5f5;
}
.cart-detail-img {
  width: 60px;
  height: 60px;
  border-radius: 8px;
  margin-right: 10px;
}
.cart-detail-info {
  flex: 1;
}
.cart-detail-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 2px;
}
.cart-detail-desc {
  font-size: 13px;
  color: #888;
  margin-bottom: 4px;
}
.cart-detail-price-row {
  display: flex;
  align-items: center;
}
.cart-detail-price {
  color: #d19a3a;
  font-size: 16px;
  font-weight: bold;
  margin-right: 8px;
}
.cart-detail-origin {
  color: #bbb;
  font-size: 13px;
  text-decoration: line-through;
  margin-right: 12px;
}
.cart-detail-ctrl {
  display: flex;
  align-items: center;
  margin-left: auto;
}
.cart-btn {
  width: 24px;
  height: 24px;
  border: none;
  background: #f5f5f5;
  color: #151515;
  font-size: 18px;
  border-radius: 50%;
  margin: 0 4px;
}
.cart-detail-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 16px;
  border-top: 1px solid #eee;
  background: #fafafa;
}
.footer-price {
  color: #d19a3a;
  font-size: 18px;
  font-weight: bold;
  margin: 0 4px;
}
.footer-discount {
  color: #a88b3b;
  font-size: 14px;
  margin-left: 4px;
}
.footer-checkout {
  background: #d19a3a;
  color: #fff;
  border-radius: 8px;
  padding: 8px 24px;
  font-size: 16px;
  font-weight: bold;
}
</style>