<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router"; // 定义颜色数组

// 定义颜色数组
const colors = ref([
  "orangered",
  "orange",
  "gold",
  "lime",
  "green",
  "cyan",
  "blue",
  "arcoblue",
  "purple",
  "pinkpurple",
  "magenta",
  "red",
  "arcoblue",
  "purple",
  "pinkpurple",
  "magenta",
  "red",
  "arcoblue",
  "purple",
  "pinkpurple",
  "magenta",
  "red",
  "arcoblue",
  "purple",
  "pinkpurple",
  "magenta",
  "red",
  "arcoblue",
  "purple",
  "pinkpurple",
  "magenta"
]);

// 标签统一样式
const tagStyle = {
  borderRadius: "12px"
};

// 状态变量
const isOverflow = ref(true); // 是否处于省略状态
// 切换展开状态
const toggleOverflow = () => {
  isOverflow.value = !isOverflow.value;
  // if (isOverflow.value) {
  //   // 截取数组中的前8个重新赋值
  //   colors.value = colors.value.slice(0, 10);
  // }
};

const router = useRouter();
const pushToAddSolution = () => {
  const path = window.location.pathname; // 获取路径部分，例如 "/view/question/1"
  const segments = path.split("/"); // 将路径分割为数组 ["", "view", "question", "1"]
  router.push({
    path: `/post-editor/solution/${segments[segments.length - 1]}`
  });
};
</script>

<template>
  <div id="solution">
    <a-row class="grid-demo" style="margin-bottom: 16px">
      <a-col :flex="11">
        <div>
          <a-input v-model="searchTitle" placeholder="搜索" allow-clear>
            <template #prepend>
              <icon-search />
            </template>
          </a-input>
        </div>
      </a-col>
      <a-col :flex="1">
        <div class="ml-1">
          <a-dropdown @select="handleSelect">
            <a-button type="text">
              <template #icon>
                <icon-sort />
              </template>
              排序
            </a-button>
            <template #content>
              <a-doption>点赞最多</a-doption>
              <a-doption>热度最高</a-doption>
              <a-doption>最新发布</a-doption>
              <a-doption>最早发布</a-doption>
            </template>
          </a-dropdown>
        </div>
      </a-col>
    </a-row>
    <!--  标签  -->
    <div class="tag-container">
      <div class="tags-wrapper">
        <a-row>
          <a-col :flex="11">
            <div
              class="tags-content"
              :class="{ 'overflow-hidden': isOverflow }"
              ref="tagsContent"
            >
              <a-tag checkable bordered :style="tagStyle">不限</a-tag>
              <a-tag
                v-for="(color, index) in colors"
                :key="index"
                checkable
                :color="color"
                bordered
                :style="tagStyle"
              >
                {{ color }}
              </a-tag>
            </div>
            <a-divider :size="1" v-if="!isOverflow" />
            <div class="tag-classification" v-if="!isOverflow">
              <a-tag
                v-for="(color, index) in colors"
                :key="index"
                checkable
                :color="color"
                bordered
                :style="tagStyle"
                class="tag-classification-tags"
              >
                {{ color }}
              </a-tag>
            </div>
          </a-col>
          <a-col :flex="0">
            <div class="button-overlay">
              <a-button
                class="toggle-btn"
                @click="toggleOverflow"
                :style="{ right: '0' }"
              >
                <template #icon>
                  <icon-down v-if="isOverflow" />
                  <icon-up v-else />
                </template>
              </a-button>
            </div>
          </a-col>
        </a-row>
      </div>
    </div>
    <!--  添加题解  -->
    <a-card
      size="small"
      hoverable
      :style="{
        width: '100%',
        marginTop: '15px',
        borderRadius: '12px'
      }"
    >
      <div
        :style="{
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between'
        }"
      >
        <span
          :style="{ display: 'flex', alignItems: 'center', color: '#1D2129' }"
        >
          <icon-plus-circle :style="{ fontSize: '22px', marginRight: '5px' }" />
          <a-typography-text>
            您最近提交的运行速度超过了&nbsp;1%&nbsp;的用户</a-typography-text
          >
        </span>

        <a-button type="primary" shape="round" @click="pushToAddSolution">
          <template #icon>
            <icon-edit />
          </template>
          <template #default>发布题解</template>
        </a-button>
      </div>
    </a-card>
    <!--  题解渲染  -->
    <div class="post-container">
      <a-comment v-for="item in 5" :key="item">
        <template #author>
          <a-link href="link" :hoverable="false">测试用户名</a-link>
        </template>
        <!-- 徽章位 -->
        <template #datetime>🏕️</template>
        <template #content>
          <a-typography-paragraph
            :ellipsis="{
              rows: 2
            }"
          >
            A design is a plan or specification for the construction of an
            object or system or for the implementation of an activity or
            process, or the result of that plan or specification in the form of
            a prototype, product or process. The verb to design expresses the
            process of developing a design. The verb to design expresses the
            process of developing a design.A design is a plan or specification
            for the construction of an object or system or for the
            implementation of an activity or process, or the result of that plan
            or specification in the form of a prototype, product or process. The
            verb to design expresses the process of developing a design. The
            verb to design expresses the process of developing a design.
          </a-typography-paragraph>
        </template>
        <template #actions>
          <span class="action" key="heart" @click="onLikeChange">
            <span v-if="like">
              <IconHeartFill :style="{ color: '#f53f3f' }" />
            </span>
            <span v-else>
              <IconHeart />
            </span>
            {{ 83 + (like ? 1 : 0) }}
          </span>
          <span class="action" key="star" @click="onStarChange">
            <span v-if="star">
              <IconStarFill :style="{ color: '#ffb400' }" />
            </span>
            <span v-else>
              <icon-eye />
            </span>
            {{ 3 + (star ? 1 : 0) }}
          </span>
          <span class="action" key="reply"> <IconMessage /> 111 </span>
        </template>
        <template #avatar>
          <a-avatar>
            <img
              alt="avatar"
              src="https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/3ee5f13fb09879ecb5185e440cef6eb9.png~tplv-uwbnlip3yd-webp.webp"
            />
          </a-avatar>
        </template>
      </a-comment>
      <!-- 分页 -->
      <div :style="{ marginTop: '15px' }">
        <a-row class="grid-demo" style="margin-bottom: 16px">
          <a-col :flex="11"></a-col>
          <a-col :flex="1">
            <div>
              <a-pagination :total="50" />
            </div>
          </a-col>
        </a-row>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
#solution {
  height: 100%;
  width: 100%;

  .tag-container {
    margin: 8px 0;
  }

  .tags-wrapper {
    width: 100%;
    position: relative;
    padding-right: 2px;

    .tag-classification {
      margin-bottom: 20px;
      gap: 8px;
      display: flex;
      flex-wrap: wrap;
      transition:
        max-width 0.3s ease,
        flex-wrap 0.3s ease;
    }
  }

  .tags-content {
    display: flex;
    gap: 8px;
    transition: all 0.3s ease;

    // 未展开时的样式
    &.overflow-hidden {
      flex-wrap: nowrap;
      overflow: hidden; // 渐变遮罩
      max-width: 100%; // 限制宽度
      flex-shrink: 0;
      transition:
        max-width 0.3s ease,
        flex-shrink 0.3s ease;

      .arco-tag {
        flex-shrink: 0; // 禁止标签收缩
      }
    }

    // 展开时的样式
    &:not(.overflow-hidden) {
      flex-wrap: wrap;
      max-width: none; // 取消宽度限制
      mask-image: none;
    }
  }

  .toggle-btn {
    // 没点击的时候浮空
    position: absolute;
    top: -3px;
    right: 0;
    z-index: 99;
    width: 40px;
    transition: all 0.3s ease;
  }

  // 暗色模式适配
  [data-arco-theme="dark"] {
    .tags-content.overflow-hidden {
      mask-image: linear-gradient(
        90deg,
        #000 0%,
        #000 calc(100% - 40px),
        transparent
      );
    }
  }

  .post-container {
    margin-top: 25px;
  }
}
</style>
