<template>
  <view class="user-bg">
    <!-- 头部信息 -->
    <view class="user-header" @click="profile ? onGetUserDetail() : onwxLogin()">
      <image class="avatar" :src="profile?.avatar || defaultAvatar" mode="aspectFill"/>
      <view class="user-info">
        <view class="nickname-row">
          <text class="nickname">{{ profile?.nickname || '请点击登录' }}</text>
          <uni-icons type="compose" size="20" color="#888" class="edit-icon" />
        </view>
        <view class="userid">ID: {{ profile?.account || '未登录' }}</view>
      </view>
    </view>

    <!-- 我的订单卡片 -->
    <view class="card">
      <view class="card-title">我的订单</view>
      <view class="card-content order">
        <view class="order-item" @click="onOrderManage">
          <uni-icons type="list" size="36" color="#333" />
          <text class="order-label">订单管理</text>
        </view>
        <view class="divider-vert"></view>
        <view class="order-item" @click="onDeliveryManage">
          <uni-icons type="cart" size="36" color="#333" />
          <text class="order-label">发货管理</text>
        </view>
      </view>
    </view>

    <!-- 其他卡片 -->
    <view class="card">
      <view class="card-title">其他</view>
      <view class="card-content other">
        <view class="other-item" @click="onCoupon">
          <uni-icons type="gift" size="36" color="#FFD600" />
          <text class="other-label">我的优惠券</text>
        </view>
        <view class="divider-vert"></view>
        <view class="other-item" @click="onAfterSale">
          <uni-icons type="paperplane" size="36" color="#333" />
          <text class="other-label">售后管理</text>
        </view>
        <view class="divider-vert"></view>
        <view class="other-item" @click="onService">
          <uni-icons type="chat" size="36" color="#333" />
          <text class="other-label">联系客服</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { loginBywxAPI } from '@/service/home'
import { useMemberStore } from '@/stroes'
import { userLoginInfo } from '@/types/home'
import { computed } from 'vue'
const memberStore = useMemberStore()
const profile = computed(() => memberStore.profile)
const defaultAvatar = 'https://cdn.uviewui.com/uview/album/1.jpg'
    const onwxLogin=()=>{
      const userInfo=getUserInfo()
      const wxCode=getLogin()
      Promise.all([userInfo,wxCode]).then(async (res)=>{
        const userInfo= res[0] as userLoginInfo
        const wxCode=res[1] as string
        
        const result=await loginBywxAPI(wxCode,userInfo.avatarUrl,userInfo.nickName)
        console.log("前端返回结果",result.data)
        const memberStore=useMemberStore()
        memberStore.setProfile(result.data)
        console.log("用户信息",userInfo.avatarUrl)
        console.log("code"+wxCode)

      }).catch(err=>{
        uni.showToast({
          title: err,
          icon: 'error',
        })
      })
    }

    const getUserInfo=()=> {
          return new Promise((resolve, reject) => {
            uni.getUserProfile({
              desc: '用户登录', 
              success: (res:UniApp.GetUserProfileRes) => {
                resolve(res.userInfo)
              },
              fail: (err) => {
                reject(err)
              }
            })
          })
        }
    const getLogin=()=> {
          return new Promise((resolve, reject) => {
            uni.login({
              success(res) {
                resolve(res.code)
              },
              fail: (err) => {
                reject(err)
              }
            })
          })
        }
const onGetUserDetail=()=>{
  uni.navigateTo({url:"/pages/user/userDetail"})
}
const onOrderManage = () => {
  uni.showToast({ title: '订单管理', icon: 'none' })
}
const onDeliveryManage = () => {
  uni.showToast({ title: '发货管理', icon: 'none' })
}
const onCoupon = () => {
  uni.showToast({ title: '我的优惠券', icon: 'none' })
}
const onAfterSale = () => {
  uni.showToast({ title: '售后管理', icon: 'none' })
}
const onService = () => {
  uni.showToast({ title: '联系客服', icon: 'none' })
}
</script>

<style lang="scss" scoped>
.user-bg {
  min-height: 100vh;
  background: #f5f5f5;
  position: relative;
  overflow-x: hidden;
  // 点阵背景
  &::before {
    content: '';
    position: absolute;
    left: 0; top: 0; right: 0; bottom: 0;
    background-image: radial-gradient(#a8a8a8 1.5px, transparent 1.5px);
    background-size: 28px 28px;
    z-index: 0;
  }
  padding-bottom: 40rpx;
}
.user-header {
  display: flex;
  align-items: center;
  margin: 60rpx 0 40rpx 40rpx;
  position: relative;
  z-index: 2;
}
.avatar {
  width: 120rpx;
  height: 120rpx;
  border-radius: 50%;
  border: 4rpx solid #e0e0e0;
  background: #fff;
}
.user-info {
  margin-left: 32rpx;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.nickname-row {
  display: flex;
  align-items: center;
}
.nickname {
  font-size: 36rpx;
  font-weight: bold;
  color: #222;
  margin-right: 12rpx;
}
.edit-icon {
  margin-top: 2rpx;
}
.userid {
  font-size: 22rpx;
  color: #888;
  margin-top: 8rpx;
}

.card {
  width: 90vw;
  margin: 0 auto 32rpx auto;
  background: #fff;
  border-radius: 18rpx;
  box-shadow: 0 6rpx 24rpx rgba(0,0,0,0.08);
  overflow: hidden;
  border-bottom: 8rpx solid #1de9b6;
  position: relative;
  z-index: 2;
}
.card-title {
  background: #333;
  color: #fff;
  font-size: 28rpx;
  font-weight: bold;
  padding: 18rpx 0 18rpx 32rpx;
}
.card-content {
  display: flex;
  background: #fff;
  align-items: stretch;
  justify-content: space-between;
  padding: 24rpx 0;
}
.order {
  .order-item {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    font-size: 24rpx;
    color: #333;
    font-weight: 500;
    .order-label {
      margin-top: 12rpx;
    }
  }
}
.other {
  .other-item {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    font-size: 24rpx;
    color: #333;
    font-weight: 500;
    .other-label {
      margin-top: 12rpx;
    }
  }
}
.divider-vert {
  width: 2rpx;
  background: #e0e0e0;
  margin: 0 0;
  height: 60rpx;
  align-self: center;
}
</style>
