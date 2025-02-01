给我
<template>
  <div class="notices-container">
    <Vue3Marquee>
      <a-space>
        <a-alert v-for="(notice, index) in notices" :key="index">
          <template #icon>
            <joy-svg-icon icon="notice" class="mr-1" />
          </template>
          <p class="notices">
            {{ notice }}
          </p>
        </a-alert>
      </a-space>
    </Vue3Marquee>
  </div>
  <div id="home-container" class="header">
    <a-row class="home-wrapper" style="margin-bottom: 16px">
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
                <TypeIt :options="options" />
              </a-col>
            </a-row>
          </div>
          <div class="home-wrapper-slogan-line-black">连接轻盈体验</div>
          <div class="home-wrapper-introduce">
            <span
              >Joy Judge 是一个<a style="color: #3491fa">开源</a
              >的在线评测系统，支持多种编程语言</span
            >
            <span>本平台采用Vue3 + SpringBoot 前后端分离</span>

            <div
              style="
                display: flex;
                justify-content: center;
                align-items: center;
              "
            >
              <div class="home-wrapper-bubble">
                <joy-svg-icon icon="bubble" />
                <span class="bubble-title" style="color: #999999">#</span>
              </div>
              <span style="margin-left: 10px"
                >支持自定义题目，支持多种<a style="color: #3491fa">评测</a
                >方式</span
              >
            </div>
          </div>
          <div class="btn-box">
            <a-row :gutter="24">
              <a-col :span="12">
                <HomeGoButton />
              </a-col>
              <a-col :span="12">
                <a-button
                  size="large"
                  class="button-about"
                  @click="() => router.push('/about')"
                  >关于我们
                </a-button>
              </a-col>
            </a-row>
          </div>
        </div>
      </a-col>
      <a-col flex="auto">
        <div>
          <CodeTypeIt />
        </div>
      </a-col>
    </a-row>
  </div>
  <!--  <hr/>-->
  <icon-double-down class="foldDown" @click="startStep" />
  <div class="stepBox" id="home-container">
    <div class="step-first" id="start-step">
      <span class="bg-text pink-blue">🥕 开始</span>
      <p class="id-text">
        在首页点击开始按钮跳转到浏览题目页面
        <br />
        尽情浏览来自五湖四海的题目
      </p>
      <img src="../../assets/images/step-question-view.png" />
    </div>
    <div class="step-second step">
      <span class="bg-text pink-blue">🍺 答题</span>
      <p class="id-text">
        选中心仪的题目点击做题按钮跳转到答题页面
        <br />
        在代码编辑器中输入题目答案
      </p>
      <img
        class="mt-4"
        src="../../assets/images/step-question-submit.png"
        height="551px"
        width="1200px"
      />
    </div>
    <div class="step-third step" style="margin-top: 200px">
      <span class="bg-text pink-blue">🐇 评测</span>
      <p class="id-text">
        你已经完成了答题请前往浏览题目提交界面
        <br />
        查看你的“得分”情况吧
      </p>
      <img
        class="mt-5"
        src="../../assets/images/step-question-submit-view.png"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineComponent, onMounted, ref } from "vue";
import { TypeIt, type TypeItOptions } from "@/components/ReTypeit";
import HomeGoButton from "@/components/Button/HomeGoButton.vue";
import CodeTypeIt from "@/components/Home/CodeTypeIt.vue";
import router from "@/router";
import { Vue3Marquee } from "vue3-marquee";

defineComponent({
  name: "home",
  components: {
    TypeIt,
    HomeGoButton,
    CodeTypeIt,
    Vue3Marquee
  }
});

const options: TypeItOptions = {
  strings: ["评测", "教学", "比赛"],
  loop: true,
  breakLines: false,
  cursorChar: "|",
  speed: 600,
  deleteSpeed: 200
};
const observer: any = ref(null);
onMounted(() => {
  observer.value = new IntersectionObserver(
    (entries, instance) => {
      entries.forEach(entry => {
        const target = entry.target as HTMLElement;
        if (entry.isIntersecting) {
          // Add `visible` to the first element and remove from the second when scrolling down
          if (target.classList.contains("step-first")) {
            target.classList.add("visible");
          }
          if (target.classList.contains("step-second")) {
            target.classList.add("visible");
          }
          if (target.classList.contains("step-third")) {
            target.classList.add("visible");
          }
        } else {
          // Remove `visible` from the first element when it goes out of view
          if (target.classList.contains("step-first")) {
            target.classList.remove("visible");
          }
          if (target.classList.contains("step-second")) {
            target.classList.remove("visible");
          }
          if (target.classList.contains("step-third")) {
            target.classList.remove("visible");
          }
        }
      });
    },
    {
      root: null,
      threshold: 0.6 // Adjust the threshold value as needed
    }
  ) as any;

  // Observe both elements
  const firstElement = document.querySelector(".step-first") as HTMLElement;
  const secondElement = document.querySelector(".step-second") as HTMLElement;
  const thirdElement = document.querySelector(".step-third") as HTMLElement;
  if (firstElement && secondElement) {
    observer.value.observe(firstElement);
    observer.value.observe(secondElement);
    observer.value.observe(thirdElement);
  }
});
const scrollToElement = (elementId: string) => {
  const element = document.getElementById(elementId);
  if (element) {
    const topPosition = element.getBoundingClientRect().top + window.scrollY;
    window.scrollTo({
      top: topPosition - 208,
      behavior: "smooth"
    });
  }
};
const startStep = () => {
  scrollToElement("start-step");
};

// 公告配置
const notices = ref(["Joy Judge 是一个开源的在线评测系统，支持多种编程语言"]);
// 获取系统配置
const { basic } = JSON.parse(localStorage.getItem("systems")!);
// 只保留新公告announcement到notices中
if (basic) {
  notices.value = notices.value.slice(0, 0);
  notices.value.push(basic.announcement);
}
</script>

<style scoped lang="scss">
#home-container {
  padding: 180px 120px 170px 120px;

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
      background: linear-gradient(
        92.06deg,
        #33c2ff -17.9%,
        #257cff 43.39%,
        #165dff 99.4%
      );
      background-clip: text;
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      font-size: 48px;
      width: auto;
    }

    .home-wrapper-slogan-line-black {
      text-align: center;
      margin-bottom: 50px;
      background: linear-gradient(
        92.06deg,
        #000000 -17.9%,
        #000000 43.39%,
        #000000 99.4%
      );
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

.foldDown {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 38px;
  margin: 0 auto;
  animation: float 3s ease-in-out infinite;
  cursor: pointer;
}

/* Keyframes for floating animation */
@keyframes float {
  0% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px); /* Adjust floating height */
  }
  100% {
    transform: translateY(0);
  }
}

.stepBox {
  text-align: center;

  .bg-text {
    font-size: 48px;
    font-weight: 600;
    letter-spacing: -1.2px;
    line-height: 48px;
    text-decoration: none solid rgba(0, 0, 0, 0);
    word-spacing: 0px;
    // 渐变 从粉色到蓝色
    background: linear-gradient(92.06deg, #ec4899 -17.9%, #165dff 99.4%);
    background-clip: text;
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
  }

  .id-text {
    font-size: 16px;
    font-weight: 600;
    letter-spacing: 0px;
    line-height: 24px;
    text-decoration: none solid rgba(0, 0, 0, 0);
    word-spacing: 0px;
    margin-top: 40px;
  }

  .step-first,
  .step-second {
    opacity: 0;
    transform: translateY(20px); /* Starts below the screen */
    transition:
      opacity 0.5s ease-in-out,
      transform 0.5s ease-in-out;
  }

  .step-first.visible,
  .step-second.visible {
    opacity: 1;
    transform: translateY(0); /* Moves up into view */
  }

  .step {
    margin-top: 160px;
  }
}

.notices-container {
  .notices {
    font-size: 18px;
    letter-spacing: 0;
    line-height: 13px;
    text-decoration: none solid rgba(0, 0, 0, 0);
    word-spacing: 0;
  }
}
</style>
