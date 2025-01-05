<script setup lang="ts">
import { useRouter } from 'vue-router'

const images = [
  'https://upload.ojason.top/hexo/2550f202882711ebb6edd017c2d2eca2.jpg',
  'https://upload.ojason.top/hexo/01e32cc90dd34191a09a4f866dd6ab31.jpg',
  'https://upload.ojason.top/hexo/file_1723625958964_315.png'
]

const router = useRouter()
</script>

<template>
  <div id="about">
    <a-carousel
      :autoPlay="true"
      animation-name="card"
      show-arrow="never"
      indicator-position="outer"
      indicator-type="never"
      :style="{
      width: '100%',
      height: '340px',
    }"
    >
      <a-carousel-item v-for="image in images" :style="{ width: '60%' }" :key="image">
        <img
          :src="image"
          :style="{
          width: '100%',
        }"
         alt=""/>
      </a-carousel-item>
    </a-carousel>
    <div class="card-box">
      <a-card hoverable :style="{ width: '1040px', marginBottom: '20px', borderRadius:'12px' }">
        <div class="card-content">
          <!-- 关于我们 -->
          <div class="info-box">
            <h2>关于我们</h2>
            <a-row>
              <a-col :span="10">
                <div class="left-logo">
                  <span @click="router.push('/')">Joy Judge</span>
                </div>
              </a-col>
              <a-col :span="14">
                <div class="right-info">
                  Joy Judge 是一个专为编程竞赛和技术评估提供判题服务的平台，同时也是为全球程序员提供职业技能提升的平台。
                  我们的使命是为开发者提供进阶所需的工具和资源，帮助他们在技术行业中脱颖而出。
                </div>
              </a-col>
            </a-row>
          </div>
          <!-- 发展历程 -->
          <div class="develop-box">
            <h2>发展历程</h2>
            <a-row>
              <a-col :span="12">
                <div class="develop-box-left">
                  <a-timeline mode="alternate" labelPosition="relative">
                    <a-timeline-item label="2024-08-07" lineType="dashed">
                      <template #dot>
                        <div class="dot" style="background-color: #EE3A8C"></div>
                      </template>
                      <a-row :style="{ display: 'inline-flex', alignItems: 'center' }">
                        <span :style="{ marginRight: '16px', marginBottom: '12px',fontSize:'30px' }">
                       🌟
                        </span>
                        <div :style="{ marginBottom: '12px' }">
                          前端重大更新
                          <div :style="{ fontSize: '12px', color: '#4E5969' }">
                            完成Joy Judge 1.2前端
                          </div>
                        </div>
                      </a-row>
                    </a-timeline-item>
                    <a-timeline-item label="2024-08-02" lineType="dashed">
                      <template #dot>
                        <div class="dot"></div>
                      </template>
                      <a-row :style="{ display: 'inline-flex', alignItems: 'center' }">
                        <span :style="{ marginRight: '16px', marginBottom: '12px',fontSize:'30px' }">
                        ⏰
                        </span>
                        <div :style="{ marginBottom: '12px' }">
                          判题服务重大更新
                          <div :style="{ fontSize: '12px', color: '#4E5969' }">
                            完成远程代码沙箱判题1.0
                          </div>
                        </div>
                      </a-row>
                    </a-timeline-item>
                    <a-timeline-item label="2024-08-02" lineType="dashed">
                      <template #dot>
                        <div class="dot" style="background-color: #F4A460"></div>
                      </template>
                      <a-row :style="{ display: 'inline-flex', alignItems: 'center' }">
                        <span :style="{ marginRight: '16px', marginBottom: '12px',fontSize:'30px' }">
                        ☃️
                        </span>
                        <div :style="{ marginBottom: '12px' }">
                          后端重大更新
                          <div :style="{ fontSize: '12px', color: '#4E5969' }">
                            完成Joy Judge 1.0后端
                          </div>
                        </div>
                      </a-row>
                    </a-timeline-item>
                    <a-timeline-item label="2024-06-04" lineType="dashed">
                      <template #dot>
                        <div class="dot" style="background-color: #3491fa"></div>
                      </template>
                      <a-row :style="{ display: 'inline-flex', alignItems: 'center' }">
                        <span :style="{ marginRight: '16px', marginBottom: '12px',fontSize:'30px' }">
                        🌏
                        </span>
                        <div :style="{ marginBottom: '12px' }">
                          项目开始
                          <div :style="{ fontSize: '12px', color: '#4E5969' }">
                            向GitHub中提交第一行代码
                          </div>
                        </div>
                      </a-row>
                    </a-timeline-item>
                  </a-timeline>

                </div>
              </a-col>
              <a-col :span="12">
                <div class="develop-box-right">
                  <joy-svg-icon icon="time-bg" className="time-bg"/>
                </div>
              </a-col>
            </a-row>
          </div>
          <!-- 品牌理念 -->
          <div class="brand-concept">
            <h2>品牌理念</h2>
            <div class="values">
              <div class="value-item">
                <h3>
                  <a-space>
                    成就用户
                    <joy-svg-icon icon="flash"/>
                  </a-space>
                </h3>
                <p>我们始终以用户的需求为导向，致力于提供最优质的判题服务，帮助用户提升技术能力，实现自我价值。</p>
              </div>
              <div class="value-item">
                <h3>
                  <a-space>
                    极致匠心
                    <joy-svg-icon icon="code"/>
                  </a-space>
                </h3>
                <p>我们追求技术的极致，注重每一个细节，以匠心精神打造最优秀的判题系统。</p>
              </div>
              <div class="value-item">
                <h3>
                  <a-space>
                    信任敢为
                    <joy-svg-icon icon="yang"/>
                  </a-space>
                </h3>
                <p>
                  我们相信每一个用户，鼓励他们敢于挑战，敢于创新，我们提供的不仅仅是一个判题系统，更是一个信任和支持的平台。</p>
              </div>
              <div class="value-item">
                <h3>
                  <a-space>
                    冠军意识
                    <joy-svg-icon icon="sketch"/>
                  </a-space>
                </h3>
                <p>我们有冠军的意识，始终追求第一，我们希望通过我们的平台，培养出更多的编程冠军。</p>
              </div>
            </div>
          </div>
        </div>
      </a-card>
    </div>
  </div>
</template>

<style scoped lang="scss">
#about {
  max-width: 1480px;
  margin: 20px auto;

  .card-box {
    display: flex;
    justify-content: center;
    align-items: center;
    padding-bottom: 50px;

    ::v-deep(.arco-card-body) {
      padding: 0;
    }

    .card-content {

      .left-logo {
        font-size: 28px;
        font-weight: bold;
        color: #1890ff;
        display: flex;
        justify-content: center;
        align-items: center;
        line-height: 1.8;
        height: 180px;

        span {
          cursor: pointer;
        }
      }

      .right-info {
        font-size: 18px;
        color: #666;
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 20px;
        line-height: 1.8;
      }

      .info-box {
        padding: 70px 140px;

        h2 {
          margin-bottom: 20px;
          font-size: 24px;
          font-weight: bold;
          color: #333;
          text-align: center;
        }
      }

      .develop-box {
        background-color: #f8f9fa;
        padding: 30px 0 40px 0;

        h2 {
          font-size: 24px;
          font-weight: bold;
          color: #333;
          text-align: center;
          margin-bottom: 40px;
        }

        .develop-box-left {
          margin-top: 20px;

          .dot {
            width: 6px;
            height: 6px;
            background-color: #000;
            border-radius: 50%;
            animation: blink 3s ease-in-out infinite;
          }

          @keyframes blink {
            0% {
              opacity: 0;
            }
            50% {
              opacity: 1;
            }
            100% {
              opacity: 0;
            }
          }
        }

        .develop-box-right {
          margin-top: 20px;
          height: 230px;
          display: flex;
          justify-content: center;
          align-items: center;

          .time-bg {
            width: 100%;
            height: 100%;

          }
        }

      }

      .brand-concept {

        padding: 30px 60px 40px 60px;

        h2 {
          font-size: 24px;
          font-weight: bold;
          color: #333;
          text-align: center;
          margin-bottom: 40px;
        }

        .values {
          display: flex;
          flex-direction: row;
          justify-content: space-between;

          .value-item {
            margin-top: 20px;
            width: 20%;
            padding: 10px;
            box-sizing: border-box;
          }

          h3 {
            font-size: 20px;
            font-weight: bold;
            margin-bottom: 10px;
          }

          p {
            font-size: 16px;
            line-height: 1.5;
          }
        }

      }
    }

  }
}
</style>
