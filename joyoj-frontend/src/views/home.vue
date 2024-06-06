<template>
  <div id="home-container">
    <a-row class="home-wrapper" style="margin-bottom: 16px;">
      <!--   左侧标语   -->
      <a-col flex="600px">
        <div class="home-wrapper-slogan">
          <div class="home-wrapper-slogan-line">智能在线评测</div>
          <div class="home-wrapper-slogan-line-black">连接轻盈体验</div>
          <div class="home-wrapper-introduce">
            <span>JOY Judge 是一个开源的在线评测系统，支持多种编程语言</span>
            <div style="display: flex;justify-content: center;align-items: center">
              <div class="home-wrapper-bubble">
                <joy-svg-icon icon="bubble"/>
                <span class="bubble-title">#</span>
              </div>
              <span style="margin-left: 10px">支持自定义题目，支持多种评测方式</span>
            </div>
          </div>
          <div class="btn-box">
            <a-row class="grid-demo" :gutter="24">
              <a-col :span="12">
                <a-button type="primary" size="large">开始使用</a-button>
              </a-col>
              <a-col :span="12">
                <a-button size="large">关于我们</a-button>
              </a-col>
            </a-row>
          </div>
        </div>
      </a-col>
      <a-col flex="auto">
        <div>auto</div>
      </a-col>
    </a-row>
  </div>
</template>

<script setup lang="ts">

import { defineComponent } from 'vue'

defineComponent({
  name: 'Home',
  components: {}
})
</script>

<style scoped lang="scss">
#home-container {
  padding: 120px;
  margin: 0 20px;

  .home-wrapper-slogan {
    .home-wrapper-introduce {
      text-align: center;
      margin-top: 20px;
      font-size: 16px;

      .home-wrapper-bubble {
        position: relative;
        margin-bottom: 10px;

        .bubble-title {
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translate(-50%, -50%);
          font-weight: bolder;
        }

        svg {
          width: 30px;
          height: 30px;
          color: #999999;
        }
      }

      span {
        display: block;
        margin-bottom: 10px;

      }
    }

    .home-wrapper-slogan-line {
      text-align: center;
      margin-bottom: 20px;
      background: linear-gradient(92.06deg, #33c2ff -17.9%, #257cff 43.39%, #165dff 99.4%);
      background-clip: text;
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      font-size: 48px;
    }

    .home-wrapper-slogan-line-black {
      text-align: center;
      margin-bottom: 20px;
      background: linear-gradient(92.06deg, #000000 -17.9%, #000000 43.39%, #000000 99.4%);
      background-clip: text;
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      font-size: 48px;
    }

    .btn-box {
      display: flex;
      width: 100%;
      align-items: center;
      justify-content: center;
      margin-top: 5px;
      height: 100px;
    }
  }
}
</style>
