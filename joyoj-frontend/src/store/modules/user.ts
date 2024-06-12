import { defineStore } from 'pinia'
import { ref } from 'vue'
import { LoginUserVO, UserControllerService } from '../../../generated'
import ACCESS_ENUM from '@/assets/enum/accessEnum'

const useUserStore = defineStore('user', () => {
  const userInfo = ref<LoginUserVO>({
    userName: '',
    userAvatar: ''
  })

  const updateUserInfo = (obj: any) => {
    userInfo.value = obj
  }
  const resetUserInfo = () => {
    userInfo.value = {
      userName: '',
      userAvatar: ''
    }
  }
  // 获取登录用户信息
  const getLoginUser = async () => {
    const res = await UserControllerService.getLoginUserUsingGet()
    if (res.code === 20000) {
      userInfo.value = res.data!
    } else {
      userInfo.value = {
        userName: '',
        userAvatar: '',
        userRole: ACCESS_ENUM.NOT_LOGIN
      }
    }
  }

  return {
    userInfo,
    updateUserInfo,
    getLoginUser,
    resetUserInfo
  }
}, {
  persist: {
    key: 'userInfo',
    storage: sessionStorage
  }
})

export default useUserStore
