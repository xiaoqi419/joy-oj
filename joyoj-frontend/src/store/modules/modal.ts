import { defineStore } from 'pinia'
import { ref } from 'vue'

const useModalStore = defineStore(
  'modal',
  () => {
    const userModal = ref(false)

    const setUserModal = (value: boolean) => {
      userModal.value = value
    }

    return {
      userModal,
      setUserModal
    }
  },
  {
    persist: {
      key: 'modal',
      storage: sessionStorage
    }
  }
)

export default useModalStore
