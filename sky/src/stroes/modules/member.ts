import { LoginResult } from "@/types/member";
import { defineStore } from "pinia";
import { ref } from "vue";

export const useMemberStore = defineStore(
    'member',
    () => {
        //个人信息
        const profile = ref<LoginResult>()

        //保存个人信息
        const setProfile = (val: LoginResult) => {
            profile.value = val
        }

        //清理个人信息,退出登录时使用
        const clearProfile = () => {
            profile.value = undefined
        }
        return {
            profile,
            setProfile,
            clearProfile,
        }
    },
    //网页端配置
    // persist:true,
    //小程序端配置
    {
        persist: {
            storage: {
                getItem(key) {
                    return uni.getStorageSync(key)
                },
                setItem(key, value) {
                    uni.setStorageSync(key, value)
                },
            }
        }
    }
)