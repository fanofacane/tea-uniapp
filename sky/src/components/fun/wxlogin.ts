import { loginBywxAPI } from "@/service/home"
import { useMemberStore } from "@/stroes"
import { userLoginInfo } from "@/types/home"

export const onwxLogin = () => {
    const userInfo = getUserInfo()
    const wxCode = getLogin()
    Promise.all([userInfo, wxCode]).then(async (res) => {
        const userInfo = res[0] as userLoginInfo
        const wxCode = res[1] as string

        const result = await loginBywxAPI(wxCode, userInfo.avatarUrl, userInfo.nickName)
        console.log("前端返回结果", result.data)
        const memberStore = useMemberStore()
        memberStore.setProfile(result.data)
        console.log("用户信息", userInfo.avatarUrl)
        console.log("code" + wxCode)

    }).catch(err => {
        uni.showToast({
            title: err,
            icon: 'error',
        })
    })
}

export const getUserInfo = () => {
    return new Promise((resolve, reject) => {
        uni.getUserProfile({
            desc: '用户登录',
            success: (res: UniApp.GetUserProfileRes) => {
                resolve(res.userInfo)
            },
            fail: (err) => {
                reject(err)
            }
        })
    })
}
export const getLogin = () => {
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