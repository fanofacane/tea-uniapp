<template>
  <view class="goods-page">
    <!-- 顶部门店信息 -->
    <view class="store-bar-flex">
      <view class="store-info">
        <view class="store-title-row">
          <uni-icons type="star" size="20" class="store-star" />
          <text class="store-name">遂昌江泰领悦店</text>
          <uni-icons type="right" size="16" class="store-arrow" />
        </view>
        <view class="store-status-row">
          <text class="store-status">前方7杯/4单制作用</text>
          <text class="store-distance">距离您15.03km</text>
        </view>
      </view>
      <view class="store-pintuan">
        <image class="pintuan-img" src="https://img.yzcdn.cn/vant/logo.png" />
        <text class="pintuan-text">拼单</text>
      </view>
    </view>

    <view class="goods-main">
      <!-- 左侧分类栏 -->
      <view class="category-bar">
        <view v-for="(cat, idx) in productItem" :key="cat.categoryId" :class="['cat-item', idx === activeCat ? 'active' : '']" @click="onCatChange(idx)">
          <uni-icons type="gift" size="22" :color="idx === activeCat ? '#d19a3a' : '#bbb'" />
          <text class="cat-text">{{ cat?.categoryName }}</text>
        </view>
      </view>

      <!-- 右侧内容区：每个大类下有多个子分类标题和商品列表 -->
      <view class="goods-content">
        <view class="goods-group">
          <view class="group-title-row">
            <!-- <image v-if="sub.icon" :src="sub.icon" class="group-title-icon" /> -->
            <text class="group-title">{{ filteredProduct?.categoryName }}</text>
          </view>
          <view v-for="item in filteredProduct?.products" :key="item.productId" class="goods-card-v2">
            <image class="goods-img-v2" :src="item.subImages" mode="aspectFill" />
            <view class="goods-info-v2">
              <view class="goods-title-v2">{{ item.productName }}</view>
              <view class="goods-tags-v2">
                <!-- <text v-for="tag in item.tags" :key="tag" class="goods-tag-v2" :class="tag.type ? 'tag-'+tag.type : ''">{{ tag.text || tag }}</text> -->
              </view>
              <text class="goods-desc-v2">{{ item.description }}</text>
              <view class="goods-price-row-v2">
                <text class="goods-price-v2">￥{{ item.salePrice }}<text class="goods-price-unit">起</text></text>
                <text class="goods-origin-v2">￥{{ item.originalPrice }}起</text>
                <view class="add-btn-v2" @click="addToCart(item)"><uni-icons type="plusempty" size="24" /></view>
              </view>
            </view>
          </view>
        </view>
      </view>
    </view>

    <!-- 购物车组件 -->
    <SkyShopCar
      :cart-list="cartList"
      @update="updateCart"
      @clear="clearCart"
      @checkout="checkout"
    />
  </view>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { getProductList } from '@/service/product'
import type { ProductItem, Product, CartProduct } from '@/types/product'
import { onLoad } from '@dcloudio/uni-app';
import SkyShopCar from '@/components/skyShopCar.vue';

const productItem = ref<ProductItem[]>([])
const cartList = ref<CartProduct[]>([])

const getproductItemData = async () => {
  const res = await getProductList()
  productItem.value = res.data
}
onLoad(getproductItemData)

const filteredProduct = computed(() => productItem.value[activeCat.value])
const activeCat = ref(0)
function onCatChange(idx: number) {
  activeCat.value = idx
}

// 商品加购
function addToCart(product: Product) {
  const idx = cartList.value.findIndex(item => item.productId == product.productId)
  if (idx > -1) {
    cartList.value[idx].count += 1
  } else {
    cartList.value.push({ ...product, count: 1 })
  }
}

// 购物车商品数量变更
function updateCart(item: CartProduct) {
  const idx = cartList.value.findIndex(p => p.productId === item.productId)
  if (item.count <= 0) {
    if (idx > -1) cartList.value.splice(idx, 1)
  } else if (idx > -1) {
    cartList.value[idx].count = item.count
  }
}

// 清空购物车
function clearCart() {
  cartList.value = []
}

// 结算
function checkout() {
  // 跳转到订单结算页，传递购物车商品数据
  uni.navigateTo({
    url: '/pages/goods/toOrder?cart=' + encodeURIComponent(JSON.stringify(cartList.value))
  })
}
</script>

<style scoped>
.goods-page {
  background: #fafafa;
  min-height: 100vh;
}
.store-bar-flex {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  padding: 14px 12px 6px 12px;
  background: #fff;
  position: relative;
}
.store-info {
  flex: 1;
  min-width: 0;
}
.store-title-row {
  display: flex;
  align-items: center;
  white-space: nowrap;
}
.store-star {
  color: #222;
  margin-right: 2px;
}
.store-name {
  font-size: 18px;
  font-weight: bold;
  margin-right: 4px;
  white-space: nowrap;
}
.store-arrow {
  color: #bbb;
  margin-left: 2px;
}
.store-status-row {
  display: flex;
  align-items: center;
  margin-top: 2px;
}
.store-status {
  background: #e6efc7;
  color: #7a9a1a;
  border-radius: 4px;
  padding: 2px 6px;
  font-size: 13px;
  margin-right: 8px;
}
.store-distance {
  color: #bbb;
  font-size: 13px;
}
.store-pintuan {
  display: flex;
  align-items: center;
  margin-left: 12px;
  margin-top: 2px;
}
.pintuan-img {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  margin-right: 2px;
}
.pintuan-text {
  font-size: 14px;
  color: #222;
}
.goods-main {
  display: flex;
  flex-direction: row;
  height: calc(100vh - 60px);
}
.category-bar {
  width: 80px;
  background: #f7f7f7;
  padding-top: 12px;
  border-right: 1px solid #f0f0f0;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.cat-item {
  width: 100%;
  padding: 16px 0 10px 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
  border-radius: 8px 0 0 8px;
  margin-bottom: 2px;
  transition: background 0.2s;
}
.cat-item.active {
  background: #fff;
  color: #d19a3a;
  font-weight: bold;
}
.cat-text {
  font-size: 13px;
  margin-top: 4px;
  color: #888;
}
.cat-item.active .cat-text {
  color: #d19a3a;
  font-weight: bold;
}
.goods-content {
  flex: 1;
  padding: 0 8px 0 8px;
  background: #fff;
  min-width: 0;
}
.goods-group {
  margin-bottom: 32px;
}
.group-title-row {
  display: flex;
  align-items: center;
  margin: 18px 0 12px 0;
}
.group-title-icon {
  width: 38px;
  height: 38px;
  margin-right: 8px;
}
.group-title {
  font-size: 20px;
  font-weight: bold;
  color: #222;
}
.goods-card-v2 {
  display: flex;
  flex-direction: row;
  background: #fff;
  border-radius: 12px;
  margin-bottom: 18px;
  padding: 0 0 0 0;
}
.goods-img-v2 {
  width: 120px;
  height: 120px;
  border-radius: 10px;
  margin-right: 16px;
  background: #f5f5f5;
  object-fit: cover;
}
.goods-info-v2 {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  padding: 6px 0 0 0;
}
.goods-title-v2 {
  font-size: 18px;
  font-weight: bold;
  color: #222;
  margin-bottom: 4px;
}
.goods-tags-v2 {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 4px;
}
.goods-tag-v2 {
  font-size: 13px;
  color: #a88b3b;
  background: #f5e7c2;
  border-radius: 4px;
  padding: 2px 6px;
  margin-right: 6px;
  margin-bottom: 2px;
}
.goods-tag-v2.tag-red {
  background: #fbe6e6;
  color: #e02020;
}
.goods-tag-v2.tag-yellow {
  background: #f5e7c2;
  color: #a88b3b;
}
.goods-desc-v2 {
  font-size: 14px;
  color: #888;
  margin-bottom: 8px;
}
.goods-price-row-v2 {
  display: flex;
  align-items: center;
  margin-top: 2px;
}
.goods-price-v2 {
  font-size: 20px;
  color: #d19a3a;
  font-weight: bold;
  margin-right: 8px;
}
.goods-price-unit {
  font-size: 14px;
  color: #d19a3a;
  margin-left: 2px;
}
.goods-origin-v2 {
  font-size: 14px;
  color: #bbb;
  text-decoration: line-through;
  margin-right: 12px;
}
.add-btn-v2 {
  margin-left: auto;
  color: #d19a3a;
  background: #fff;
  border-radius: 50%;
  border: 1px solid #eee;
  padding: 2px;
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
