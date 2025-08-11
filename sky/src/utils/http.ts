import { useMemberStore } from "@/stroes"

//请求后端地址
const baseURL = 'http://localhost:8090'

//添加拦截器
const httpInterceptor = {
    invoke(options: UniApp.RequestOptions) {
        //非http请求开头,拼接完整地址
        if (!options.url.startsWith('http')) {
            options.url = baseURL + options.url
        }
        //请求超时
        options.timeout = 10000
        /* 添加请求头标识
        options.header ={
            ...options.header,
            'source-client' : 'miniapp',
        } */

        //添加token请求头
        const memberStore = useMemberStore()
        const token = memberStore.profile?.token
        if (token) {
            if (!options.header) options.header = {}
            options.header.Authorization = token
        }
    }
}
uni.addInterceptor('request', httpInterceptor)
uni.addInterceptor('uploadFile', httpInterceptor)

export const http = <T>(options: UniApp.RequestOptions) => {
    return new Promise<Data<T>>((resolve, reject) => {
        uni.request({
            ...options,
            success(res) {
                //响应200返回数据
                if (res.statusCode >= 200 && res.statusCode < 300) {
                    resolve(res.data as Data<T>)
                    //响应401重新登录
                } else if (res.statusCode == 401) {
                    const memberStore = useMemberStore()
                    memberStore.clearProfile()
                    uni.navigateTo({ url: '/pages/' })
                    reject(res)
                } else {
                    //其他,返回错误消息
                    uni.showToast({
                        title: (res.data as Data<T>).msg || '请求错误',
                        icon: 'error'
                    })
                }
            },
            fail(error) {
                uni.showToast({
                    title: '网络错误,稍后重试',
                    icon: 'error',
                })
                reject(error)
            },
        })
    })
}

interface Data<T> {
    code: string
    msg: string
    data: T
}
