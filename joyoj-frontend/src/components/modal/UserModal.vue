<template>
  <div class="login-box">
    <a-modal v-model:visible="dialogVisible" :width="dialogWidth" :footer="false">
      <template #title>
        <div class="owl" id="owl" :class="{ password: isPassword }">
          <div class="hand"></div>
          <div class="hand hand-r"></div>
          <div class="arms">
            <div class="arm"></div>
            <div class="arm arm-r"></div>
          </div>
        </div>
        <h1>登录</h1>
      </template>
      <div class="box-main">
        <a-form
          :model="formLabelAlign"
          :rules="rules"
          ref="longinFormRef"
          style="max-width: 400px;"
        >
          <a-form-item field="userAccount" validate-trigger="blur">
            <a-input v-model="formLabelAlign.userAccount" placeholder="用户名" allow-clear>
              <template #prefix>
                <icon-user/>
              </template>
            </a-input>
          </a-form-item>
          <a-form-item field="userPassword" validate-trigger="blur">
            <a-input-password
              v-model="formLabelAlign.userPassword"
              @focus="isPassword = true"
              @blur="isPassword = false"
              placeholder="密码"
              allow-clear
              autocomplete
              show-password>
              <template #prefix>
                <icon-lock/>
              </template>
            </a-input-password>
          </a-form-item>
          <a-form-item>
            <div class="btn-box">
              <a-checkbox v-model="formLabelAlign.type">记住我</a-checkbox>
              <a-space wrap>
                <a-link type="primary">忘记密码</a-link>
                <a-link type="primary">没有账号？</a-link>
              </a-space>
            </div>
          </a-form-item>
        </a-form>
        <!-- 登录 -->
        <div>
          <button>
            <span class="shadow"></span>
            <span class="edge"></span>
            <span class="front text">登录</span>
          </button>
        </div>
      </div>
    </a-modal>
  </div>
</template>

<script lang="ts" setup>
import useModalStore from '@/store/modules/modal'
import { storeToRefs } from 'pinia'
import { defineComponent, reactive, ref, watch } from 'vue'
import '@/assets/userModal.scss'
import { FieldRule } from '@arco-design/web-vue'

defineComponent({
  name: 'UserModal'
})

const modalStore = storeToRefs(useModalStore())
const dialogVisible = modalStore.userModal
const isPassword = ref(false)
// 模态框宽度
const dialogWidth = ref(500)
// 监视浏览器宽度变化调整合适的模态框宽度
watch(
  () => window.innerWidth,
  (newValue) => {
    if (newValue > 500) {
      dialogWidth.value = 500
    } else {
      dialogWidth.value = newValue
    }
  },
  {
    immediate: true,
    flush: 'post',
    deep: true
  }
)

const formLabelAlign = reactive<any & { type: boolean }>({
  userAccount: '',
  userPassword: '',
  type: false
})
const rules = reactive<Record<string, FieldRule | FieldRule[]>>({
  userPassword: [
    {
      required: true,
      message: '请输入密码'
    },
    {
      minLength: 8,
      maxLength: 20,
      message: '密码长度在 8 到 20 个字符'
    }
  ],
  userAccount: [
    {
      required: true,
      message: '请输入用户名'
    }
  ]
})

</script>

<style lang="scss" scoped>
.box-main {
  margin: 0 auto;
  text-align: center;

  h1 {
    text-align: center;
    margin-bottom: 20px;
  }

  .btn-box {
    display: flex;
    justify-content: space-between;
    width: 100%;
    margin-top: 5px;
  }
}
</style>
