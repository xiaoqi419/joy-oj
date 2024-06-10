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
        <h1>注册账号</h1>
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
              placeholder="密码"
              @focus="isPassword = true"
              @blur="isPassword = false"
              allow-clear
              autocomplete
              show-password>
              <template #prefix>
                <icon-lock/>
              </template>
            </a-input-password>
          </a-form-item>
          <a-form-item field="checkPassword" validate-trigger="blur">
            <a-input-password
              v-model="formLabelAlign.checkPassword"
              placeholder="确认密码"
              @focus="isPassword = true"
              @blur="isPassword = false"
              allow-clear
              autocomplete
              show-password>
              <template #prefix>
                <icon-lock/>
              </template>
            </a-input-password>
          </a-form-item>
        </a-form>
        <!-- 登录 -->
        <a-button :loading="loading" type="primary" size="large" @click="doVerify">注册</a-button>

      </div>
    </a-modal>
    <a-modal v-if="verifyModal" :modal-style="{width:'350px'}" v-model:visible="verifyModal" :closable="false"
             :footer="false">
      <Verify @success="doRegister"/>
    </a-modal>
  </div>
</template>

<script lang="ts" setup>
import useModalStore from '@/store/modules/modal'
import { storeToRefs } from 'pinia'
import { defineComponent, reactive, ref, watch } from 'vue'
import { FieldRule } from '@arco-design/web-vue'
import Verify from '@/components/Verify/verify.vue'
import '@/assets/userModal.scss'

defineComponent({
  name: 'RegisterModal',
  components: {
    Verify
  }
})
const isPassword = ref(false)
const verifyModal = ref(false)
const modalStore = storeToRefs(useModalStore())
const dialogVisible = modalStore.registerModal
const loading = ref(false)
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
  checkPassword: '',
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
  checkPassword: [
    {
      required: true,
      message: '请输入确认密码'
    },
    {
      validator: (value, cb) => {
        if (value !== formLabelAlign.userPassword) {
          const errorMsg = '两次输入密码不一致'
          cb(errorMsg)
        }
        return true
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
const doVerify = () => {
  verifyModal.value = true
}
// 验证通过后注册
const doRegister = (e: object) => {
  console.log(e)
  verifyModal.value = false
  loading.value = true
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
