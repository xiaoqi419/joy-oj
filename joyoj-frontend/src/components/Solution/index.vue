<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import {
  PostControllerService,
  SolutionControllerService
} from "../../../generated";
import { Message } from "@arco-design/web-vue"; // 定义颜色数组

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
const tags = ref();
// 切换展开状态
const toggleOverflow = async () => {
  isOverflow.value = !isOverflow.value;
  const res = await SolutionControllerService.getTagsUsingPost({});
  if (res.code === 20000) {
    tags.value = res.data;
  } else {
    Message.error(res.message);
  }
};

const router = useRouter();
const pushToAddSolution = () => {
  const path = window.location.pathname; // 获取路径部分，例如 "/view/question/1"
  const segments = path.split("/"); // 将路径分割为数组 ["", "view", "question", "1"]
  router.push({
    path: `/post-editor/solution/${segments[segments.length - 1]}`
  });
};

// 父组件传递的数据
const props = defineProps({
  solutions: {
    type: Object || null,
    default: () => {}
  },
  current: {
    type: Number,
    default: 1
  },
  pageSize: {
    type: Number,
    default: 10
  },
  total: {
    type: Number,
    default: 0
  }
});

// 搜索框搜索题解
const searchTitle = ref();
const emit = defineEmits<{
  (e: "searchSolutionByTitle", solutions: any): void;
  (e: "changePage", current: number): void;
}>();
const searchSolution = async () => {
  const res = await PostControllerService.listPostVoByPageUsingPost({
    current: props.current,
    pageSize: props.pageSize,
    title: searchTitle.value
  });
  if (res.code === 20000) {
    // 获得搜索之后的数据返回父组件
    emit("searchSolutionByTitle", res.data.records);
  }
};

watch(
  () => searchTitle.value,
  () => {
    if (!searchTitle.value) {
      // 返回空
      emit("searchSolutionByTitle", null);
    }
  },
  {
    deep: true
  }
);

// 改变页码
const changePage = (current: number) => {
  emit("changePage", current);
};

const like = ref(false);
const star = ref(false);

// 排序改变时
const sortChange = async (val: any) => {
  console.log(val);
  const res = await PostControllerService.listPostVoByPageUsingPost({
    current: 1,
    pageSize: 5,
    sortField: val,
    sortOrder: val === "createTime" ? "descend" : "ascend"
  });
  if (res.code === 20000) {
    // 获得搜索之后的数据返回父组件
    emit("searchSolutionByTitle", res.data);
  } else {
    Message.error("获取数据失败:" + res.message);
  }
};

// 获取随机标签
const randomTags = ref([]);
const getRandomTag = async () => {
  const res = await SolutionControllerService.getRandomTagsUsingPost();
  if (res.code === 20000) {
    randomTags.value = res.data;
  } else {
    Message.error(res.message);
  }
};

// 选中标签后搜索
const tagIds = ref<string[]>([]);

async function searchPosts(value: string[]) {
  const res = await PostControllerService.listPostVoByPageUsingPost({
    current: 1,
    pageSize: 5,
    tags: value
  });
  if (res.code === 20000) {
    emit("searchSolutionByTitle", res.data);
  } else {
    Message.error("获取数据失败:" + res.message);
  }
}

// 选中标签后，再次点击该标签则去掉该标签重新搜索
const checkTag = async (value: string) => {
  if (tagIds.value.includes(value)) {
    tagIds.value = tagIds.value.filter((tag: string) => tag !== value);
  } else {
    tagIds.value.push(value.toString());
  }

  await searchPosts(tagIds.value);
};

const isEmpty = ref(true);
// 如果没有数据则显示空
watch(
  () => props.solutions,
  () => {
    if (!props.solutions || props.solutions.length === 0) {
      isEmpty.value = false;
    } else {
      isEmpty.value = true;
    }
  },
  {
    deep: true
  }
);

const searchAllSolution = async () => {
  const res = await PostControllerService.listPostVoByPageUsingPost({
    current: 1,
    pageSize: 5
  });
  if (res.code === 20000) {
    emit("searchSolutionByTitle", res.data);
    // 取消所有选中
    tagIds.value = [];
  } else {
    Message.error("获取数据失败:" + res.message);
  }
};

// 跳转到题解详情
const isGoto = ref(false);
const route = useRoute();
const gotoDetail = (id: number) => {
  const lastPart = route.path.split("/").pop();
  router.push({
    name: "solutionInfo",
    params: {
      id: lastPart,
      solutionId: id
    }
  });
  isGoto.value = true;
  // 使视角恢复到最顶端 丝滑一点
  window.scrollTo({
    top: 0,
    behavior: "smooth"
  });
};

watch(
  () => route.path,
  newPath => {
    // 使用正则匹配动态路由
    isGoto.value = !/^\/view\/question\/\d+$/.test(newPath);
  },
  { immediate: true }
);

onMounted(() => {
  getRandomTag();
});
</script>

<template>
  <div id="solution" v-if="!isGoto">
    <a-row class="grid-demo" style="margin-bottom: 16px">
      <a-col :flex="11">
        <div>
          <a-input
            v-model="searchTitle"
            placeholder="按下回车搜索"
            allow-clear
            @pressEnter="searchSolution"
          >
            <template #prepend>
              <icon-search />
            </template>
          </a-input>
        </div>
      </a-col>
      <a-col :flex="1">
        <div class="ml-1">
          <a-dropdown @select="sortChange">
            <a-button type="text">
              <template #icon>
                <icon-sort />
              </template>
              排序
            </a-button>
            <template #content>
              <a-doption value="thumbNum">点赞最多</a-doption>
              <a-doption value="viewNum">热度最高</a-doption>
              <a-doption value="createTime">最新发布</a-doption>
              <a-doption value="updateTime">最早发布</a-doption>
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
              <a-tag
                checkable
                bordered
                :style="tagStyle"
                @check="searchAllSolution"
                >不限
              </a-tag>
              <a-tag
                v-for="(item, index) in randomTags"
                :key="item"
                :color="colors[index]"
                bordered
                checkable
                :style="tagStyle"
                @check="checkTag(item.tagName)"
              >
                {{ item.tagName }}
              </a-tag>
            </div>
            <a-divider :size="1" v-if="!isOverflow" />
            <div class="tag-classification" v-if="!isOverflow">
              <a-tag
                v-for="(item, index) in tags"
                :key="index"
                checkable
                :color="colors[index]"
                bordered
                :style="tagStyle"
                class="tag-classification-tags"
                @check="checkTag(item.tagName)"
              >
                {{ item.tagName }}
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
            您最近提交的运行速度超过了&nbsp;<span :style="{ color: 'blue' }"
              >1%</span
            >&nbsp;的用户</a-typography-text
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
    <div class="post-container" v-if="isEmpty">
      <a-comment
        v-for="item in props.solutions"
        :key="item"
        @click="gotoDetail(item.id)"
        :style="{ cursor: 'pointer' }"
      >
        <template #author>
          <a-link href="link" :hoverable="false"
            >{{ item.user.userName }}
          </a-link>
        </template>
        <!-- 徽章位 -->
        <template #datetime>🏕️</template>
        <template #content>
          <div>
            {{ item.title }}
          </div>
          <a-typography-paragraph
            :ellipsis="{
              rows: 2
            }"
          >
            {{ item.content }}
          </a-typography-paragraph>
          <a-space>
            <a-tag v-for="(iitem, index) in item.tagList" :key="index"
              >{{ iitem }}
            </a-tag>
          </a-space>
        </template>
        <template #actions>
          <span class="action" key="heart">
            <span v-if="like">
              <IconHeartFill :style="{ color: '#f53f3f' }" />
            </span>
            <span v-else>
              <IconHeart />
            </span>
            {{ item.thumbNum }}
          </span>
          <span class="action" key="star">
            <span v-if="star">
              <IconStarFill :style="{ color: '#ffb400' }" />
            </span>
            <span v-else>
              <icon-eye />
            </span>
            {{ item.viewNum }}
          </span>
          <span class="action" key="reply">
            <IconMessage /> {{ item.commentNum }}</span
          >
        </template>
        <template #avatar>
          <a-avatar>
            <img alt="avatar" :src="item.user.userAvatar" />
          </a-avatar>
        </template>
      </a-comment>
      <!-- 分页 -->
      <div :style="{ marginTop: '15px' }">
        <a-row class="grid-demo" style="margin-bottom: 16px">
          <a-col :flex="11"></a-col>
          <a-col :flex="1">
            <div>
              <a-pagination
                :pageSize="props.pageSize"
                :current="props.current"
                :total="props.total"
                @change="changePage"
              />
            </div>
          </a-col>
        </a-row>
      </div>
    </div>
    <a-empty v-else />
  </div>
  <!-- 题解详情页 -->
  <router-view v-else />
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
