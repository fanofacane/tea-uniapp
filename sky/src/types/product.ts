export type ProductTag = {
    tagId: number
    tagName: string
    tagDesc: string
    status: number
    createTime: Date
}
export type Product = {
    productId: number
    productName: string
    categoryId: number
    mainImage: string
    subImages: string
    originalPrice: number
    salePrice: number
    stock: number
    description: string
    status: number
    productTag: ProductTag
    createTime: Date
    updateTime: Date
}
export type ProductItem = {
    categoryId: number
    categoryName: string
    sortOrder: number
    status: number
    products: Product[]
    createTime: Date
}
export type CartProduct = Product & { count: number }

export type toOrder = {
    userId: number
    productId: number
    salePrice: number
    amount: number
    mobile: number
    nickName: string
    address: string
}