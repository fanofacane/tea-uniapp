import { userLoginInfo } from "@/types/home"
import { LoginResult } from "@/types/member"
import { ProductItem } from "@/types/product"
import { http } from "@/utils/http"

export const loginBywxAPI = (code: string, imgUrl: string, nickName: string) => {
    return http<LoginResult>({
        method: 'POST',
        url: '/wxLogin',
        data: {
            code: code,
            imgUrl: imgUrl,
            nickName: nickName
        }
    })
}