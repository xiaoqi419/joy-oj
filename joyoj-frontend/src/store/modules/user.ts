import { defineStore } from 'pinia'
import { ref } from 'vue'

const useUserStore = defineStore('user', () => {
  const userInfo = ref()

  const updateUserInfo = (obj: any) => {
    userInfo.value = obj
  }

  return {
    userInfo,
    updateUserInfo
  }
}, {
  persist: {
    key: 'userInfo',
    storage: sessionStorage
  }
})

export default useUserStore
