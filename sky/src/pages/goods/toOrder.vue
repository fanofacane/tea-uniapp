<template>
    <view class="order-page">
        <view class="order-mode-bar">
        <view :class="['order-mode', mode === 'dinein' ? 'active' : '']" @click="mode = 'dinein'">
            <image src="https://img.yzcdn.cn/vant/cafe.png" class="order-mode-icon" />
            <text>堂食现喝</text>
            <uni-icons v-if="mode === 'dinein'" type="checkmarkempty" size="18" class="order-mode-check" />
        </view>
        <view :class="['order-mode', mode === 'takeout' ? 'active' : '']" @click="mode = 'takeout'">
            <image src="https://img.yzcdn.cn/vant/bag.png" class="order-mode-icon" />
            <text>打包带走</text>
            <uni-icons v-if="mode === 'dinein'" type="checkmarkempty" size="18" class="order-mode-check" />
        </view>
        </view>
        <view class="order-status-row">
        <text>前方<text class="order-status-num">{{ totalCount }}</text>杯/<text class="order-status-num">{{ totalCount }}</text>单制作中</text>
        </view>
        <view class="order-section">
        <view class="order-section-title">商品明细</view>
        <view v-for="item in cartList" :key="item.productId" class="order-product-row">
            <image :src="item.subImages" class="order-product-img" />
            <view class="order-product-info">
            <view class="order-product-title">{{ item.productName }}</view>
            <view class="order-product-desc">{{ item.description }}</view>
            </view>
            <view class="order-product-price">￥{{ item.originalPrice }}</view>
            <view class="order-product-count">x{{ item.count }}</view>
        </view>
        <view class="order-product-summary">共{{ totalCount }}件商品 总计 ￥{{ totalOriginPrice }}</view>
        </view>
        <view class="order-section">
        <view class="order-section-title">优惠明细</view>
        <view class="order-coupon-row">
            <view class="order-coupon-tag">券</view>
            <text>优惠券</text>
            <view class="order-coupon-amount">-￥{{ discount }}</view>
        </view>
        <view class="order-coupon-tip">当前为最优结算方案</view>
        </view>
        <view class="order-section">
        <view class="order-section-title">联系人</view>
        <text class="order-phone">{{ profile?.nickname }}</text>
        </view>
        <view class="order-section">
        <view class="order-section-title">预留电话</view>
        <text class="order-phone">{{ profile?.mobile }}</text>
        </view>
        <view class="order-section">
        <view class="order-section-title">收获地址</view>
        <input
            v-model="deliveryAddress"
            placeholder="请输入收获地址"
            placeholder-class="input-placeholder"
            @input="onAddressInput"
        />
        </view>
        <view class="order-section">
        <view class="order-section-title">备注</view>
        <text class="order-remark">暂不支持通过备注定制口味</text>
        </view>
        <view class="order-bottom-bar">
  <view class="order-bottom-left">
    <view class="order-bottom-summary">
      <text>实付</text>
      <text class="order-bottom-pay">￥{{ totalPay }}</text>
      <text class="order-bottom-origin">￥{{ totalOriginPrice }}</text>
    </view>
    <view class="order-bottom-discount">总计优惠 ￥{{ discount }}</view>
  </view>
  <button class="order-pay-btn" @click="pay">立即支付</button>
</view>
    </view>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import type { CartProduct } from '@/types/product'
import { onLoad as uniOnLoad } from '@dcloudio/uni-app'
import { useMemberStore } from '@/stroes'
const memberStore=useMemberStore();
const profile = computed(() => memberStore.profile)
const cartList = ref<CartProduct[]>([])
const mode = ref<'dinein' | 'takeout'>('dinein')
const deliveryAddress =ref(profile.value?.address)

const totalCount = computed(() => cartList.value.reduce((sum, item) => sum + item.count, 0))
const totalOriginPrice = computed(() => cartList.value.reduce((sum, item) => sum + item.count * item.originalPrice, 0).toFixed(2))
const totalPay = computed(() => cartList.value.reduce((sum, item) => sum + item.count * item.salePrice, 0).toFixed(2))
const discount = computed(() => (Number(totalOriginPrice.value) - Number(totalPay.value)).toFixed(2))

const onAddressInput = (event: { detail: { value: string } }) => {
  deliveryAddress.value = event.detail.value;
}
function pay() {
  uni.showToast({ title: '支付功能待实现', icon: 'none' })
}

uniOnLoad((options = {}) => {
  if (options?.cart) {
    try {
      cartList.value = JSON.parse(decodeURIComponent(options.cart))
    } catch (e) {
      cartList.value = []
    }
  }
})
</script>

<style scoped>
.order-page {
  background: #fafafa;
  min-height: 100vh;
  padding-bottom: 80px;
}
.order-header {
  display: flex;
  align-items: center;
  padding: 18px 0 10px 12px;
  background: #fff;
}
.order-title {
  font-size: 22px;
  font-weight: bold;
  margin-left: 16px;
}
.order-mode-bar {
  display: flex;
  background: #fff;
  padding: 12px 0 0 0;
  border-bottom: 1px solid #f0f0f0;
}
.order-mode {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px 0;
  border: 1px solid #eee;
  border-radius: 10px 10px 0 0;
  margin: 0 8px;
  background: #f7f7f7;
  position: relative;
}
.order-mode.active {
  background: #fffbe6;
  border-color: #d19a3a;
}
.order-mode-icon {
  width: 28px;
  height: 28px;
  margin-right: 6px;
}
.order-mode-check {
  color: #d19a3a;
  position: absolute;
  right: 8px;
  top: 8px;
}
.order-status-row {
  padding: 8px 16px;
  color: #7a9a1a;
  font-size: 15px;
  background: #fff;
}
.order-status-num {
  color: #7a9a1a;
  font-weight: bold;
}
.order-section {
  background: #fff;
  margin-top: 14px;
  border-radius: 10px;
  padding: 12px 16px 8px 16px;
}
.order-section-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 8px;
}
.order-product-row {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
}
.order-product-img {
  width: 48px;
  height: 48px;
  border-radius: 8px;
  margin-right: 10px;
}
.order-product-info {
  flex: 1;
}
.order-product-title {
  font-size: 15px;
  font-weight: bold;
}
.order-product-desc {
  font-size: 12px;
  color: #888;
}
.order-product-price {
  font-size: 15px;
  color: #222;
  margin-right: 8px;
}
.order-product-count {
  font-size: 15px;
  color: #888;
}
.order-product-summary {
  text-align: right;
  color: #888;
  font-size: 14px;
  margin-top: 4px;
}
.order-coupon-row {
  display: flex;
  align-items: center;
  margin-bottom: 4px;
}
.order-coupon-tag {
  background: #f5d488;
  color: #222;
  border-radius: 4px;
  padding: 2px 6px;
  font-size: 13px;
  margin-right: 6px;
}
.order-coupon-amount {
  color: #e02020;
  margin-left: auto;
  font-size: 15px;
}
.order-coupon-tip {
  color: #bbb;
  font-size: 13px;
  margin-top: 2px;
}
.order-phone {
  font-size: 15px;
  color: #222;
}
.order-remark {
  font-size: 14px;
  color: #bbb;
}
.order-bottom-bar {
  position: fixed;
  left: 0; right: 0; bottom: 0;
  background: #fff;
  box-shadow: 0 -2px 8px rgba(0,0,0,0.04);
  padding: 10px 16px 12px 16px;
  z-index: 100;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.order-bottom-left {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  flex: 1;
}
.order-bottom-summary {
  display: flex;
  align-items: baseline;
  font-size: 16px;
  font-weight: bold;
}
.order-bottom-pay {
  color: #222;
  font-size: 22px;
  margin: 0 8px;
}
.order-bottom-origin {
  color: #bbb;
  font-size: 15px;
  text-decoration: line-through;
}
.order-bottom-discount {
  color: #e02020;
  font-size: 14px;
  margin-top: 2px;
}
.order-pay-btn {
  background: #222;
  color: #fff;
  border-radius: 8px;
  font-size: 18px;
  font-weight: bold;
  margin-left: 16px;
  flex-shrink: 0;
}
</style>
