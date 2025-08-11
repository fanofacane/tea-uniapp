<template>
  <view class="home-container">
    <skySwiper/>
    <!-- 用户信息卡片 -->
    <view class="user-card">
      <view class="user-profile" @click="profile ? onGetUserDetail() : onwxLogin()">
        <image class="avatar" :src="profile?.avatar || defaultAvatar" mode="aspectFill" />
        <view class="profile-info">
          <view class="nickname-row">
            <text class="nickname">{{ profile?.nickname || '请点击登录' }}</text>
            <uni-icons type="compose" size="16" class="edit-icon" />
          </view>
          <text class="user-id">ID: {{ profile?.account || '未登录' }}</text>
        </view>
      </view>
      <view class="user-info-extra-strict">
        <view class="info-col">
          <text class="info-num">0.7</text>
          <text class="info-label">葫芦</text>
        </view>
        <view class="info-col coupon-col">
          <view class="coupon-dot"></view>
          <text class="info-num">1</text>
          <text class="info-label">优惠券</text>
        </view>
      </view>
    </view>

    <!-- 自取/外卖功能按钮区 -->
    <view class="pickup-delivery-block">
      <view class="pickup-block">
        <text class="pickup-title" @click="toGoods()">自取</text>
        <text class="pickup-desc">下单享优惠</text>
      </view>
      <view class="block-divider"></view>
      <view class="delivery-block">
        <van-tag type="warning" class="free-tag">最高享免配！</van-tag>
        <text class="delivery-title">外卖</text>
        <text class="delivery-desc">新鲜送上门</text>
      </view>
    </view>

    <!-- 四大功能入口（图标+文字，严格参照图片） -->
    <view class="feature-row-icon">
      <view class="feature-item-icon">
        <uni-icons type="staff" size="30"></uni-icons>
        <text class="feature-text-icon">好友拼单</text>
      </view>
      <view class="feature-item-icon">
        <uni-icons type="gift" size="30"></uni-icons>
        <text class="feature-text-icon">积分乐园</text>
      </view>
      <view class="feature-item-icon">
        <uni-icons type="vip" size="30"></uni-icons>
        <text class="feature-text-icon">超值券包</text>
      </view>
      <view class="feature-item-icon">
        <uni-icons type="shop" size="30"></uni-icons>
        <text class="feature-text-icon">兑换中心</text>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { useMemberStore } from '@/stroes';
import { computed } from 'vue';
import { onwxLogin } from '@/components/fun/wxlogin';
const defaultAvatar = 'https://cdn.uviewui.com/uview/album/1.jpg'
const memberStore=useMemberStore()
const profile = computed(() => memberStore.profile)
const onGetUserDetail=()=>{
  uni.navigateTo({url:"/pages/user/userDetail"})
}
const toGoods=()=>{
  uni.navigateTo({url:"/pages/goods/goods"})
}
</script>

<style scoped>
.home-container {
  background: #fff;
  min-height: 100vh;
  padding-bottom: 16px;
}
.user-card {
  display: flex;
  align-items: center;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 8px #f5f5f5;
  margin: 10px 8px 0 8px;
  padding: 8px 12px 8px 8px;
  justify-content: space-between;
}
.user-profile {
  display: flex;
  align-items: center;
  flex: 1;
}
.avatar {
  width: 54px;
  height: 54px;
  border-radius: 50%;
  border: 1px solid #eee;
  margin-right: 12px;
  background: #f5f5f5;
}
.profile-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.nickname-row {
  display: flex;
  align-items: center;
}
.nickname {
  font-size: 20px;
  font-weight: bold;
  color: #222;
  margin-right: 4px;
}
.edit-icon {
  color: #bbb;
  margin-left: 2px;
}
.user-id {
  font-size: 13px;
  color: #bdbdbd;
  margin-top: 2px;
}
.user-avatar {
  position: relative;
  margin-right: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.user-level {
  position: absolute;
  left: 0;
  bottom: -8px;
  font-size: 10px;
  padding: 0 4px;
}
.user-info-main {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.user-title-row {
  display: flex;
  align-items: center;
}
.user-title {
  font-size: 15px;
  color: #222;
  font-weight: 500;
  margin-right: 6px;
}
.s1-tag {
  font-size: 11px;
  height: 16px;
  line-height: 16px;
  margin-left: 2px;
}
.user-progress-row {
  margin-top: 2px;
}
.user-progress {
  font-size: 12px;
  color: #888;
}
.user-info-extra-strict {
  display: flex;
  flex-direction: row;
  align-items: center;
  min-width: 100px;
  margin-left: 8px;
}
.info-col {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  flex: 1;
  position: relative;
}
.info-num {
  font-size: 16px;
  color: #222;
  font-weight: bold;
  line-height: 1.1;
}
.info-label {
  font-size: 12px;
  color: #888;
  margin-top: 2px;
}
.coupon-col {
  margin-left: 18px;
}
.coupon-dot {
  position: absolute;
  right: 10px;
  top: 0px;
  width: 7px;
  height: 7px;
  background: #e02020;
  border-radius: 50%;
  z-index: 2;
}

.pickup-delivery-block {
  display: flex;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 8px #f5f5f5;
  margin: 12px 8px 0 8px;
  position: relative;
  overflow: hidden;
}
.pickup-block, .delivery-block {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 18px 0 14px 0;
  position: relative;
}
.pickup-title, .delivery-title {
  font-size: 22px;
  font-weight: 600;
  color: #222;
}
.pickup-desc, .delivery-desc {
  font-size: 13px;
  color: #888;
  margin-top: 4px;
}
.block-divider {
  width: 1px;
  background: #ececec;
  margin: 12px 0;
}
.free-tag {
  position: absolute;
  right: 12px;
  top: 8px;
  font-size: 12px;
  background: #f5e7c2;
  color: #8d6e36;
  border: none;
  padding: 0 6px;
  z-index: 2;
}

/* 四大功能入口（图标+文字，严格参照图片） */
.feature-row-icon {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 18px 8px 0 8px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 8px #f5f5f5;
  padding: 10px 0;
}
.feature-item-icon {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-width: 0;
}
.feature-item-icon .van-icon {
  margin-bottom: 2px;
  color: #222;
}
.feature-text-icon {
  font-size: 13px;
  color: #222;
  margin-top: 2px;
  text-align: center;
}
</style>
