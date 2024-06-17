<script setup lang="ts">
import { defineComponent, reactive, ref } from 'vue'
import useUserStore from '@/store/modules/user'
import { storeToRefs } from 'pinia'
import { UserControllerService } from '../../../generated'
import { Message } from '@arco-design/web-vue'

defineComponent({
  name: 'ProfileInfo'
})

const { userInfo } = storeToRefs(useUserStore())

const form = reactive({
  userName: userInfo.value.userName || '',
  userSex: userInfo.value.userSex,
  userProfile: userInfo.value.userProfile,
  userEmail: userInfo.value.userEmail,
  userRole: userInfo.value.userRole
})

// 改更性别数据
const changeSex = (sex: number) => {
  form.userSex = sex
}

// 表单Rule
const rule = {
  userName: [
    {
      required: true,
      message: '请输入昵称',
      trigger: 'blur'
    }
  ],
  userSex: [
    {
      required: true,
      message: '请选择性别',
      trigger: 'blur'
    }
  ],
  userEmail: [
    {
      required: true,
      message: '请输入邮箱',
      trigger: 'blur'
    },
    {
      type: 'email',
      message: '请输入正确的邮箱',
      trigger: 'blur'
    }
  ]
}
// 更新用户信息
const loading = ref(false)
const doUpdateInfo = async ({ errors }: any) => {
  if (errors === undefined) {
    loading.value = true
    const res = await UserControllerService.updateMyUserUsingPost({
      userName: form.userName,
      userSex: form.userSex,
      userProfile: form.userProfile,
      userEmail: form.userEmail
    })
    if (res.code === 20000) {
      Message.success('更新成功')
      await useUserStore().getLoginUser()
      loading.value = false
    } else {
      Message.error('更新失败')
      loading.value = false
    }
  }
}

// 上传头像相关
const file = ref()

const onChange = (_: any, currentFile: any) => {
  file.value = {
    ...currentFile
    // url: URL.createObjectURL(currentFile.file),
  }
}
const onProgress = async (currentFile: any) => {
  file.value = currentFile
}
const doSuccess = async (item: any) => {
  // 更新用户信息
  const res = await UserControllerService.updateMyUserUsingPost({
    userAvatar: item.response.data
  })
  if (res.code === 20000) {
    Message.success('更新成功')
    await useUserStore().getLoginUser()
  } else {
    Message.error('更新失败')
  }
}
</script>
<template>
  <div id="profile-content">
    <a-row class="grid-avatar" :gutter="24">
      <a-col :span="24">
        <div class="avatar">
          <a-upload
            action="http://localhost:8101/api/file/uploadAvatar"
            :fileList="file ? [file] : []"
            :show-file-list="false"
            @change="onChange"
            @progress="onProgress"
            @success="doSuccess"
          >
            <template #upload-button>
              <div
                :class="`arco-upload-list-item${
            file && file.status === 'error' ? ' arco-upload-list-item-error' : ''
          }`"
              >
                <div
                  class=""
                  v-if="file && file.url"
                >
                  <a-avatar :size="89">
                    <img :src="file.url"/>
                    <template #trigger-icon>
                      <IconEdit size="20" :style="{ color: '#3491FA' }"/>
                    </template>
                  </a-avatar>
                  <a-progress
                    v-if="file.status === 'uploading' && file.percent < 100"
                    :percent="file.percent"
                    type="circle"
                    size="mini"
                    :style="{
                position: 'absolute',
                left: '50%',
                top: '50%',
                transform: 'translateX(-50%) translateY(-50%)',
              }"
                  />
                </div>
                <div v-else>
                  <a-avatar :size="89">
                    <img :src="userInfo.userAvatar"/>
                    <template #trigger-icon>
                      <IconEdit size="20" :style="{ color: '#3491FA' }"/>
                    </template>
                  </a-avatar>
                </div>
              </div>
            </template>
          </a-upload>

        </div>
      </a-col>
    </a-row>
    <a-form @submit="doUpdateInfo" :rule="rule" :model="form" layout="vertical" class="ml-24 mt-4" style="width: 100%">
      <a-row justify="space-between" class="row-padding">
        <a-col :span="9">
          <a-form-item field="userName" label="昵称">
            <a-input v-model="form.userName" placeholder="昵称"/>
          </a-form-item>
        </a-col>
        <a-col :span="13">
          <a-form-item field="userSex" label="性别">
            <a-row :gutter="[24, 12]">
              <a-col :span="12" @click="changeSex(1)">
                <div :class="{'sex-button-check':form.userSex === 1}" class="sex-button">
                  <icon-man class="mr-1"/>
                  男
                </div>
              </a-col>
              <a-col :span="12" @click="changeSex(0)">
                <div :class="{'sex-button-women-check':form.userSex === 0}"
                     class="sex-button sex-women">
                  <icon-woman class="mr-1"/>
                  女
                </div>
              </a-col>
            </a-row>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row justify="space-between" class="row-padding">
        <a-col :span="9">
          <a-form-item field="userEmail" label="邮箱" tooltip="邮箱是后续修改密码的凭证">
            <a-input v-model="form.userEmail" placeholder="邮箱"/>
          </a-form-item>
        </a-col>
        <a-col :span="13">
          <a-form-item field="userRole" label="角色">
            <div>
              <a-tag size="large" color="blue">{{ form.userRole }}</a-tag>
              <a-button v-if="form.userRole === 'student'" type="primary" class="ml-2">申请成为老师</a-button>
            </div>
          </a-form-item>
        </a-col>
      </a-row>
      <a-form-item field="userProfile" label="个人介绍">
        <a-textarea style="width: 740px;height: 100px" v-model="form.userProfile" auto-size
                    :max-length="512"></a-textarea>
      </a-form-item>
      <a-form-item>
        <div class="sub-button">
          <a-button :loading="loading" size="large" type="primary" html-type="submit">保存</a-button>
        </div>
      </a-form-item>
    </a-form>
  </div>
</template>

<style scoped lang="scss">
#profile-content {
  height: auto;
  width: 100%;
  background: #fff;
  border-radius: 10px;

  .grid-avatar {
    .avatar {
      text-align: center;
      margin-top: 20px;

      :deep(.arco-avatar-trigger-icon-button) {
        height: 30px;
        width: 30px;
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }
  }

  .sex-button {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 10px;
    border-radius: 4px;
    cursor: pointer;
    background: #f2f3f5;
    color: #6c757d;
    transition: all 0.3s;
    width: 120px;
    font-size: 14px;

    &:hover {
      background: #3491FA;
      color: #fff;
    }
  }

  .sex-women {
    &:hover {
      background: #FF69B4;
      color: #fff;
    }
  }

  .sex-button-check {
    background: #3491FA;
    color: #fff;
  }

  .sex-button-women-check {
    background: #FF69B4;
    color: #fff;
  }

  .sub-button {
    text-align: right;
    width: 740px;
    margin-top: 10px;
  }

}
</style>
