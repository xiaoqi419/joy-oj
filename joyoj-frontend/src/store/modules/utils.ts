import { defineStore } from "pinia";
import { ref } from "vue";

const useUtilsStore = defineStore(
  "utils",
  () => {
    const counter = ref(60);

    return {
      counter
    };
  },
  {
    persist: {
      key: "utils",
      storage: sessionStorage
    }
  }
);

export default useUtilsStore;
