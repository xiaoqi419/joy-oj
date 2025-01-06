<template>
  <div class="login-box" v-if="dialogVisible">
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
        <h1>忘记密码</h1>
      </template>
      <div class="box-main">
        <a-form
          :model="formLabelAlign"
          :rules="rules"
          ref="longinFormRef"
          style="max-width: 400px;"
          @submit="doForgetPassword"
        >
          <a-form-item field="userAccount" validate-trigger="blur">
            <a-input v-model="formLabelAlign.userAccount" placeholder="用户名" allow-clear>
              <template #prefix>
                <icon-user/>
              </template>
            </a-input>
          </a-form-item>
          <a-form-item field="newPassword" validate-trigger="blur">
            <a-input-password
              v-model="formLabelAlign.newPassword"
              placeholder="新密码"
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
          <a-form-item field="userEmail" validate-trigger="blur">
            <a-input v-model="formLabelAlign.userEmail" placeholder="邮箱">
              <template #prefix>
                <icon-email/>
              </template>
            </a-input>
          </a-form-item>
          <a-form-item field="emailCode" validate-trigger="blur">
            <a-input :max-length="6" v-model="formLabelAlign.emailCode" placeholder="邮箱验证码">
              <template #prefix>
                <icon-question-circle/>
              </template>
            </a-input>
            <!-- 验证码 -->
            <a-button type="primary" @click="getEmailCode" v-if="!isEmailCode" :loading="emailLoading">获取验证码
            </a-button>
            <a-button type="primary" disabled v-else>{{ counter }}秒后获取</a-button>
          </a-form-item>
          <!--          <a-form-item field="code" validate-trigger="blur">-->
          <!--            <a-input placeholder="验证码" v-model="formLabelAlign.code" :max-length="4">-->
          <!--              <template #prefix>-->
          <!--                <icon-exclamation-circle/>-->
          <!--              </template>-->
          <!--            </a-input>-->
          <!--            &lt;!&ndash; 从后端获取图片验证码并展示 &ndash;&gt;-->
          <!--            <img :src="imgSrc" @click="getVerifyCode" alt="验证码" style="width: 100px;height:50px"/>-->
          <!--          </a-form-item>-->
          <a-form-item content-flex>
            <div style="width: 100%;justify-content: center">
              <!-- 忘记密码 -->
              <a-button html-type="submit" :loading="loading" type="primary" size="large">
                修改密码
              </a-button>
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
import { defineComponent, onMounted, reactive, ref, watch } from 'vue'
import { FieldRule, Message } from '@arco-design/web-vue'
import '@/assets/userModal.scss'
import { UserControllerService, VerifyControllerService } from '../../../generated'
import useUtilsStore from '@/store/modules/utils'

defineComponent({
  name: 'RegisterModal'
})
const isPassword = ref(false)
const modalStore = storeToRefs(useModalStore())
const dialogVisible = modalStore.forgetModal
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
  newPassword: '',
  code: '',
  userEmail: '',
  emailCode: ''
})
const rules = reactive<Record<string, FieldRule | FieldRule[]>>({
  newPassword: [
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
  ],
  userEmail: [
    {
      required: true,
      message: '请输入邮箱'
    },
    {
      validator (value, callback) {
        if (!value) {
          const errorMsg = '请输入邮箱'
          return callback(errorMsg)
        }
        // 邮箱格式校验
        const reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+$/
        if (!reg.test(value)) {
          const errorMsg = '邮箱格式不正确'
          return callback(errorMsg)
        }
        return callback()
      }
    }
  ],
  emailCode: [
    {
      required: true,
      message: '请输入邮箱验证码'
    },
    {
      validator (value, callback) {
        if (value.length !== 6) {
          const errorMsg = '邮箱验证码长度为6位'
          return callback(errorMsg)
        }
        return callback()
      }
    }
  ],
  code: [
    {
      required: true,
      message: '请输入验证码'
    },
    {
      validator (value, callback) {
        if (value.length !== 4) {
          const errorMsg = '验证码长度为4位'
          return callback(errorMsg)
        }
        // 如果不是数字或字母报错
        const reg = /^[a-zA-Z0-9]+$/
        if (!reg.test(value)) {
          const errorMsg = '验证码只能包含字母、数字'
          return callback(errorMsg)
        }
        return callback()
      }
    }
  ]
})
// 校验通过后修改密码
const longinFormRef = ref()
const doForgetPassword = async (value: any) => {
  if (value.errors === undefined) {
    loading.value = true
    const res = await UserControllerService.forgetPasswordUsingPost({
      userAccount: formLabelAlign.userAccount,
      newPassword: formLabelAlign.newPassword,
      userEmail: formLabelAlign.userEmail,
      emailCode: formLabelAlign.emailCode
    })
    if (res.code === 20000) {
      Message.success('修改密码成功')
      modalStore.forgetModal.value = false
      formLabelAlign.value = {
        userAccount: '',
        newPassword: '',
        code: '',
        userEmail: '',
        emailCode: ''
      }
      loading.value = false
    } else {
      Message.error(res.message!)
      loading.value = false
    }
  }
}
// 获取邮箱验证码
const isEmailCode = ref(false)
const { counter } = storeToRefs(useUtilsStore())
const emailLoading = ref(false)
const getEmailCode = async () => {
  // 如果邮箱格式校验通过且邮箱不为空
  if (formLabelAlign.userEmail) {
    // 校验格式
    const reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+$/
    if (!reg.test(formLabelAlign.userEmail)) {
      return
    }
    emailLoading.value = true
    const res = await VerifyControllerService.getEmailCodeUsingGet(formLabelAlign.userEmail)
    if (res.code === 20000) {
      counter.value = 60
      // 发送验证码成功
      Message.success('验证码发送成功')
      emailLoading.value = false
      isEmailCode.value = true
      // 倒计时
      const timer = setInterval(() => {
        counter.value--
        if (counter.value === 0) {
          clearInterval(timer)
          isEmailCode.value = false
        }
      }, 1000)
    } else {
      emailLoading.value = false
      Message.error(res.message!)
    }
  } else {
    Message.error('请输入邮箱')
  }
}

// 获取验证码
const imgSrc = ref()
const getVerifyCode = async () => {
  // 当方法被执行时重新获取验证码
  const res = await VerifyControllerService.getArithmeticUsingGet()

  imgSrc.value = res.data
}
// 每次打开重新获取验证码
onMounted(() => {
  getVerifyCode()
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
