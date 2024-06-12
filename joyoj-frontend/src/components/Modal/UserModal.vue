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
          @submit="doLogin"
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
                <a-link type="primary" @click="doRegister">没有账号？</a-link>
              </a-space>
            </div>
          </a-form-item>
          <a-form-item content-flex>
            <!-- 登录 -->
            <div style="width: 100%;justify-content: center">
              <button @submit="doLogin" v-if="loading">
                <span class="shadow"></span>
                <span class="edge"></span>
                <span class="front text">登录</span>
              </button>
              <a-spin v-else dot class="mr-4"/>
            </div>
          </a-form-item>
        </a-form>
      </div>
    </a-modal>
  </div>

</template>

<script lang="ts" setup>
import useModalStore from '@/store/modules/modal'
import { storeToRefs } from 'pinia'
import { defineComponent, reactive, ref, watch } from 'vue'
import '@/assets/userModal.scss'
import { FieldRule, Message } from '@arco-design/web-vue'
import { UserControllerService } from '../../../generated'
import useUserStore from '@/store/modules/user'

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
    },
    {
      validator (value, callback) {
        // 密码不能是非法字符
        const reg = /^[a-zA-Z0-9_]+$/
        if (!reg.test(value)) {
          const errorMsg = '密码只能包含字母、数字、下划线'
          return callback(errorMsg)
        }
      }
    }
  ],
  userAccount: [
    {
      required: true,
      message: '请输入用户名'
    },
    {
      validator (value, callback) {
        if (!value) {
          const errorMsg = '请输入用户名'
          return callback(errorMsg)
        }
        // 只允许输入字母、数字、下划线
        const reg = /^[a-zA-Z0-9_]+$/
        if (!reg.test(value)) {
          const errorMsg = '用户名只能包含字母、数字、下划线'
          return callback(errorMsg)
        }
        if (value.length < 4 || value.length > 20) {
          const errorMsg = '用户名长度在 4 到 20 个字符'
          return callback(errorMsg)
        }
        return callback()
      }
    }
  ]
})

// 打开注册模态框
const doRegister = () => {
  modalStore.userModal.value = false
  modalStore.registerModal.value = true
}
// 登录
const loading = ref(true)
const { userInfo } = storeToRefs(useUserStore())
const doLogin = async ({ errors }: any) => {
  // 验证表单
  if (errors === undefined) {
    loading.value = false
    const res = await UserControllerService.userLoginUsingPost({
      userAccount: formLabelAlign.userAccount,
      userPassword: formLabelAlign.userPassword
    })
    if (res.code === 20000) {
      // 登录成功
      modalStore.userModal.value = false
      Message.success('登录成功!')
      userInfo.value = res.data
      loading.value = true
      formLabelAlign.userAccount = ''
      formLabelAlign.userPassword = ''
    } else {
      Message.error(res.message)
      loading.value = true
    }
  }
}

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
