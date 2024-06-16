<script setup lang="ts">
import { defineComponent, reactive } from 'vue'
import useUserStore from '@/store/modules/user'
import { storeToRefs } from 'pinia'

defineComponent({
  name: 'ProfileInfo'
})

const form = reactive({
  name: '',
  post: '',
  isRead: false
})

// UserModal
const { userInfo } = storeToRefs(useUserStore())
</script>

<template>
  <div id="profile-content">
    <a-row class="grid-avatar" :gutter="24">
      <a-col :span="24">
        <div class="avatar">
          <a-avatar :size="89">
            <img :src="userInfo.userAvatar"/>
            <template #trigger-icon>
              <IconEdit size="20" :style="{ color: '#3491FA' }"/>
            </template>
          </a-avatar>
        </div>
      </a-col>
    </a-row>
    <a-form :model="form" layout="vertical">
      <a-row justify="space-between" class="row-padding">
        <a-col :span="12">
          <a-form-item field="name" label="Username">
            <a-input v-model="form.name" placeholder="please enter your username..."/>
          </a-form-item>
        </a-col>
        <a-col :span="10">
          <a-form-item field="post" label="Post">
            <a-input v-model="form.post" placeholder="please enter your post..."/>
          </a-form-item>
        </a-col>
      </a-row>

      <a-form-item field="isRead">
        <a-checkbox v-model="form.isRead">
          I have read the manual
        </a-checkbox>
      </a-form-item>
      <a-form-item>
        <a-button>Submit</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<style scoped lang="scss">
#profile-content {

  height: auto;
  background: #fff;

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

  .row-padding {
    padding: 24px;
  }
}
</style>
