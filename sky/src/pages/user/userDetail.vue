    <template>
    <view class="user-detail-bg">
        <!-- 顶部导航 -->
        <view class="nav-bar">
        <!-- <uni-icons type="back" class="nav-back" @click="goBack" /> -->
        <view class="nav-title">个人信息</view>
        </view>
        <!-- 头像 -->
        <view class="avatar-section">
        <view class="avatar-wrapper">
            <image class="avatar" :src="profile?.avatar || defaultAvatar" mode="aspectFill" />
            <view class="avatar-mask">修改头像</view>
        </view>
        </view>
        <!-- 信息表单 -->
        <view class="info-list">
        <view class="info-item">
            <view class="label">昵称</view>
            <view class="value">{{ profile?.nickname || '' }}</view>
        </view>
        <view class="divider"></view>
        <view class="info-item clickable" @click="onBindPhone">
            <view class="label">绑定手机号</view>
            <view class="value value-arrow">
            {{ profile?.mobile || '未绑定' }}
            <uni-icons type="right" size="18" color="#888" />
            </view>
        </view>
        <view class="divider"></view>
        <view class="info-item">
            <view class="label">我的有尺物ID</view>
            <view class="value">{{ profile?.account || '' }}</view>
        </view>
        </view>
        <!-- 保存按钮 -->
        <view class="save-btn-wrap">
        <button class="save-btn" @click="onSave">保存</button>
        </view>
        <!-- 底部装饰图可后续补充 -->
    </view>
    </template>

<script setup lang="ts">
    import { useMemberStore } from '@/stroes'
    import { LoginResult } from '@/types/member'
    import { computed, ref } from 'vue'
    const memberStore = useMemberStore()
    const profile = computed(() => memberStore.profile)
    const userDetail=ref<LoginResult>(profile.value as LoginResult)
    const defaultAvatar = 'https://cdn.uviewui.com/uview/album/1.jpg'

    const goBack = () => {
    uni.navigateBack()
    }
    const onBindPhone = () => {
    uni.showToast({ title: '绑定手机号', icon: 'none' })
    }
    const onSave = () => {
    uni.showToast({ title: '保存成功', icon: 'success' })
    }
    </script>

    <style lang="scss" scoped>
    .user-detail-bg {
    min-height: 100vh;
    background: #f5f5f5;
    position: relative;
    // 点阵背景
    &::before {
        content: '';
        position: absolute;
        left: 0; top: 0; right: 0; bottom: 0;
        background-image: radial-gradient(#a8a8a8 1.5px, transparent 1.5px);
        background-size: 28px 28px;
        z-index: 0;
    }
    // > * {
    //     position: relative;
    //     z-index: 1;
    // }
    }
    .nav-bar {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100rpx;
    position: relative;
    background: transparent;
    font-weight: bold;
    font-size: 34rpx;
    color: #222;
    margin-bottom: 20rpx;
    }
    .nav-back {
    position: absolute;
    left: 32rpx;
    top: 50%;
    transform: translateY(-50%);
    }
    .nav-title {
    text-align: center;
    flex: 1;
    }
    .avatar-section {
    display: flex;
    justify-content: center;
    margin-bottom: 40rpx;
    }
    .avatar-wrapper {
    position: relative;
    width: 160rpx;
    height: 160rpx;
    border-radius: 50%;
    overflow: hidden;
    border: 4rpx solid #d0d0d0;
    background: #fff;
    box-shadow: 0 2rpx 12rpx rgba(0,0,0,0.06);
    display: flex;
    align-items: center;
    justify-content: center;
    }
    .avatar {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    }
    .avatar-mask {
    position: absolute;
    left: 0; right: 0; bottom: 0;
    height: 48rpx;
    background: rgba(0,0,0,0.32);
    color: #fff;
    font-size: 24rpx;
    text-align: center;
    line-height: 48rpx;
    }
    .info-list {
    width: 90vw;
    margin: 0 auto;
    background: #fff;
    border-radius: 16rpx;
    box-shadow: 0 10rpx 12rpx rgba(0,0,0,0.04);
    overflow: hidden;
    position: relative;
    z-index: 2;
    }
    .info-item {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 32rpx 28rpx;
    font-size: 30rpx;
    color: #222;
    background: #fff;
    }
    .label {
    color: #888;
    font-size: 28rpx;
    }
    .value {
    color: #222;
    font-size: 30rpx;
    display: flex;
    align-items: center;
    }
    .value-arrow {
    color: #222;
    font-size: 30rpx;
    display: flex;
    align-items: center;
    }
    .clickable {
    cursor: pointer;
    }
    .divider {
    height: 2rpx;
    background: #f0f0f0;
    margin: 0 28rpx;
    }
    .save-btn-wrap {
    margin: 60rpx auto 0 auto;
    display: flex;
    justify-content: center;
    }
    .save-btn {
    width: 70vw;
    height: 88rpx;
    background: #fff;
    color: #222;
    font-size: 32rpx;
    border-radius: 12rpx;
    border: 2rpx solid #222;
    box-shadow: 6rpx 10rpx 0 0 #e0e0e0;
    font-weight: bold;
    }
    </style>
