<script setup lang="ts">
import { defineComponent } from 'vue'
import useUserStore from '@/store/modules/user'
import { storeToRefs } from 'pinia'
import { useRoute, useRouter } from 'vue-router'

defineComponent({
  name: 'ProfileLayout',
  components: {}
})

// topInfo
const route = useRoute()
const router = useRouter()
const { userInfo } = storeToRefs(useUserStore())
const routeName = route.name
const goRouter = (path: string) => {
  router.push(path)
}
</script>

<template>
  <div id="profile-box">
    <a-layout style="height: 1200px;">
      <a-layout class="pd-box">
        <a-layout-sider class='sider'>
          <div class="top-container">
            <div class="Avatar">
              <a-avatar :size="54" shape="square">
                <img :src="userInfo.userAvatar"/>
              </a-avatar>
            </div>
            <div class="NameRow">Jason</div>
            <div class="PremiumNotice">加入 Plus 会员，尊享特权</div>
            <a-button type="primary" shape="round">升级Plus会员</a-button>
          </div>
          <hr/>
          <div class="menu">
            <div class="menu-item" :class="{'menu-item-check':routeName === 'ProfileInfo'}">
              <joy-svg-icon icon="profile" class="icon"/>
              <a class="ml-1" @click="goRouter('ProfileInfo')">个人资料</a>
            </div>

          </div>
        </a-layout-sider>
        <a-layout-content class="content">
          <router-view></router-view>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </div>
</template>

<style scoped lang="scss">
hr {
  background: #e5e5e5;
}

#profile-box {
  background: #f8f6f6;

  .sider {
    background: #ffffff;
    height: auto;
    margin-right: 10px;
    border-radius: 10px;
    padding: 20px;

    .top-container {
      padding: 20px;
      display: flex;
      flex-direction: column;
      align-items: center;

      .Avatar {
        margin-bottom: 20px;
      }

      .NameRow {
        font-size: 20px;
        font-weight: 600;
        color: #b2a4ae;
        margin-bottom: 10px;
      }

      .PremiumNotice {
        font-size: 10px;
        color: #b2a4ae;
        margin-bottom: 10px;
      }

    }

    .menu {
      margin-top: 20px;

      .menu-item {
        padding: 10px 20px;
        display: flex;
        color: #b2a4ae;
        font-size: 16px;
        cursor: pointer;
        justify-content: center;
        align-items: center;
        border-radius: 5px;

        &:hover {
          background: #f8f6f6;
          border-radius: 5px;
        }
      }

      .menu-item-check {
        color: #3491fa;
        background: #edeef0;
      }
    }
  }

}
</style>
