import { ProductItem } from "@/types/product"
import { http } from "@/utils/http"

export const getProductList = () => {
    return http<ProductItem[]>({
        method: 'GET',
        url: '/product/list'
    })
}