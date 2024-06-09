<template>
  <div id="home-container">
    <a-row class="home-wrapper" style="margin-bottom: 16px;">
      <!--   左侧标语   -->
      <a-col flex="500px">
        <div class="home-wrapper-slogan">
          <div class="home-wrapper-slogan-title">
            <a-row>
              <a-col flex="200px" style="margin-left: 100px">
                <div class="home-wrapper-slogan-line">智能在线</div>
              </a-col>
              <a-col flex="200px">
                <!-- 打字机 -->
                <TypeIt :options="options"/>
              </a-col>
            </a-row>
          </div>
          <div class="home-wrapper-slogan-line-black">连接轻盈体验</div>
          <div class="home-wrapper-introduce">
            <span>Joy Judge 是一个<a style="color:#3491FA">开源</a>的在线评测系统，支持多种编程语言</span>
            <span>本平台采用Vue3 + SpringBoot 前后端分离</span>

            <div style="display: flex;justify-content: center;align-items: center">
              <div class="home-wrapper-bubble">
                <joy-svg-icon icon="bubble"/>
                <span class="bubble-title" style="color:#999999">#</span>
              </div>
              <span style="margin-left: 10px">支持自定义题目，支持多种<a style="color:#3491FA">评测</a>方式</span>
            </div>
          </div>
          <div class="btn-box">
            <a-row :gutter="24">
              <a-col :span="12">
                <HomeGoButton/>
              </a-col>
              <a-col :span="12">
                <a-button size="large" class="button-about">关于我们</a-button>
              </a-col>
            </a-row>
          </div>
        </div>
      </a-col>
      <a-col flex="auto">
        <div>
          <CodeTypeIt/>

        </div>
      </a-col>
    </a-row>
  </div>
</template>

<script setup lang="ts">
import { defineComponent } from 'vue'
import { TypeIt, type TypeItOptions } from '@/components/ReTypeit'
import HomeGoButton from '@/components/Button/HomeGoButton.vue'
import CodeTypeIt from '@/components/Home/CodeTypeIt.vue'

defineComponent({
  name: 'home',
  components: {
    TypeIt,
    HomeGoButton,
    CodeTypeIt
  }
})

const options: TypeItOptions = {
  strings: ['评测', '教学', '比赛'],
  loop: true,
  breakLines: false,
  cursorChar: '|',
  speed: 600,
  deleteSpeed: 200
}
</script>

<style scoped lang="scss">

#home-container {
  padding: 120px;
  margin: 0 20px;

  .home-wrapper-slogan {
    .home-wrapper-slogan-title {

      :deep(.type-it) {
        font-size: 48px;
        color: #165dff;
      }
    }

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
      width: auto;
    }

    .home-wrapper-slogan-line-black {
      text-align: center;
      margin-bottom: 50px;
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
      height: 100px;

      .button-about {
        border-radius: 10px;

        &:hover {
          background-color: #c4c4c4;
        }
      }
    }
  }
}

</style>
