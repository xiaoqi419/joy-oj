import { defineStore } from "pinia";
import { ref } from "vue";

const useSystemStore = defineStore(
  "system",
  () => {
    const basic = ref({});

    const setBasic = (obj: any) => {
      basic.value = obj;
    };

    return {
      basic,
      setBasic
    };
  },
  {
    persist: {
      key: "systems",
      storage: localStorage
    }
  }
);

export default useSystemStore;
