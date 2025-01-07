import { defineStore } from "pinia";
import { ref } from "vue";

const useModalStore = defineStore(
  "modal",
  () => {
    const userModal = ref(false);
    const registerModal = ref(false);
    const forgetModal = ref(false);
    const resultModal = ref(false);

    const setUserModal = (value: boolean) => {
      userModal.value = value;
    };
    const setRegisterModal = (value: boolean) => {
      registerModal.value = value;
    };
    const setForgetModal = (value: boolean) => {
      forgetModal.value = value;
    };
    const setResultModal = (value: boolean) => {
      resultModal.value = value;
    };

    return {
      userModal,
      registerModal,
      forgetModal,
      resultModal,
      setResultModal,
      setRegisterModal,
      setUserModal,
      setForgetModal
    };
  },
  {
    persist: {
      key: "modal",
      storage: sessionStorage
    }
  }
);

export default useModalStore;
