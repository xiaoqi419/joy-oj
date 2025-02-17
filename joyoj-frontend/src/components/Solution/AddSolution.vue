<script setup lang="ts">
import { ref, watch } from "vue";
import { Message } from "@arco-design/web-vue";
import {
  PostControllerService,
  SolutionControllerService
} from "../../../generated";
import { useRoute } from "vue-router";
import router from "@/router";

type Tags = {
  tagName: string;
  id: number;
};

// 题解部分
const text = ref(
  "::: tip\n" +
    "  在下方展示你的独家方法吧~:smiling_imp:\n" +
    ":::\n" +
    "\n" +
    "# 思路\n" +
    "> 你选用何种方法解题？\n" +
    "\n" +
    "# 解题过程\n" +
    "> 这些方法具体怎么运用？\n" +
    "\n" +
    "# Code\n" +
    "``` language\n" +
    "\n" +
    "```"
);

const handleCopyCodeSuccess = () => {
  Message.success("代码复制成功~");
};

// 搜索标签
const tagData = ref();
const randomTags = ref();
const isEmpty = ref(true);
const isFullTags = ref(false);
const TagsColor = ["red", "green", "cyan", "blue"];
const tags = ref<Tags[]>([]);

const searchTagsByClick = async () => {
  const res = await SolutionControllerService.getRandomTagsUsingPost();
  if (res.code === 20000) {
    randomTags.value = res.data;
    isEmpty.value = false;
  } else {
    isEmpty.value = true;
    Message.error(res.message);
  }
};
const addTags = (item: Tags) => {
  // 如果tags中已经存在该对象，则提示用户已经添加相同标签
  if (tags.value.some(tag => tag.id === item.id)) {
    Message.error("标签已存在，请勿重复添加");
    return;
  }
  // 向tags数组末尾添加一个新的对象
  if (tags.value.length < 5) {
    tags.value.push(item);
  }
};
const removeTag = (item: number) => {
  // 从tags中删除该对象
  if (!tags.value || tags.value.length === 0) {
    console.warn("No tags to remove.");
    return;
  }

  const initialLength = tags.value.length;
  tags.value = tags.value.filter(tag => tag.id !== item);

  if (tags.value.length === initialLength) {
    console.warn(`Tag with id ${item} not found.`);
  }
};

// 搜索标签
const searchTagsByEnter = async () => {
  const res = await SolutionControllerService.getTagsUsingPost({
    tagName: tagData.value
  });
  console.log(res);
  if (res.code === 20000) {
    randomTags.value = res.data || []; // 确保 data 是数组
    // 如果返回的数据为空
    if (!randomTags.value || randomTags.value.length === 0) {
      isEmpty.value = true;
      return;
    }
    isEmpty.value = false;
  } else {
    isEmpty.value = true;
    Message.error(res.message || "未知错误，请稍后重试");
  }
};

// 题解表单
const title = ref("");
const route = useRoute();
const addSolution = async () => {
  if (title.value === "") {
    Message.error("标题不能为空");
    return;
  }
  if (text.value === "") {
    Message.error("内容不能为空");
    return;
  }
  if (tags.value.length === 0) {
    Message.error("标签不能为空");
    return;
  }
  const res = await PostControllerService.addPostUsingPost({
    title: title.value,
    content: text.value,
    tags: tags.value.map(tag => tag.tagName),
    questionId: Number(route.params.id)
  });
  console.log(res);
  if (res.code === 20000) {
    Message.success("添加成功");
    await router.push({
      path: `/view/question/${route.params.id}`
    });
  } else {
    Message.error(res.message);
  }
};

const backQuestionView = () => {
  router.push({
    path: `/view/question/${route.params.id}`
  });
};

watch(
  () => tags.value,
  () => {
    isFullTags.value = tags.value.length === 5;
  },
  { deep: true }
);
watch(
  () => tagData.value,
  () => {
    if (tagData.value === "") {
      searchTagsByClick();
    }
  },
  { deep: true }
);
</script>

<template>
  <div id="AddSolution">
    <a-layout style="height: auto">
      <a-layout-header>
        <a-card :style="{ marginBottom: '0.3125rem' }" :hoverable="true">
          <a-row class="form-input-title" align="center">
            <a-col :flex="9">
              <div>
                <a-input
                  default-value=""
                  placeholder="请输入标题"
                  allow-clear
                  size="large"
                  v-model:model-value="title"
                />
              </div>
            </a-col>
            <a-col :flex="1">
              <a-button
                type="primary"
                status="warning"
                @click="backQuestionView"
                :style="{ borderRadius: '.5rem' }"
                >返回题目
              </a-button>
            </a-col>
            <a-col :flex="1">
              <div>
                <a-button
                  type="primary"
                  :style="{ borderRadius: '.5rem' }"
                  @click="addSolution"
                >
                  <template #icon>
                    <icon-thunderbolt />
                  </template>
                  发布题解
                </a-button>
              </div>
            </a-col>
          </a-row>
          <div
            :style="{
              marginTop: '.7rem',
              display: 'flex',
              alignItems: 'center'
            }"
          >
            <div>
              <a-trigger
                trigger="click"
                :unmount-on-close="false"
                show-arrow
                :popup-translate="[50, 0]"
              >
                <a-button
                  type="primary"
                  shape="round"
                  status="success"
                  @click="searchTagsByClick"
                  :disabled="isFullTags"
                >
                  <template #icon>
                    <icon-plus />
                  </template>
                  标签
                </a-button>
                <template #content>
                  <a-scrollbar
                    type="track"
                    style="height: 300px; overflow: auto"
                  >
                    <div class="trigger">
                      <a-input
                        placeholder="搜索标签（回车搜索）"
                        v-model:model-value="tagData"
                        allow-clear
                        :style="{ borderRadius: '0.4375rem' }"
                        @press-enter="searchTagsByEnter"
                      >
                        <template #prefix>
                          <icon-search />
                        </template>
                      </a-input>
                      <!-- 渲染获取的标签 -->
                      <div v-if="!isEmpty">
                        <a-tag
                          v-for="(item, index) in randomTags"
                          :key="index"
                          size="large"
                          :color="TagsColor[index]"
                          :style="{ width: '100%', marginTop: '0.625rem' }"
                          checkable
                          checked
                          @click="addTags(item)"
                        >
                          {{ item.tagName }}
                        </a-tag>
                      </div>
                      <a-empty v-else />
                    </div>
                  </a-scrollbar>
                </template>
              </a-trigger>
            </div>

            <!-- 题解标签 -->
            <div>
              <a-space align="center">
                <a-tag
                  v-for="item of tags"
                  :key="item.id"
                  closable
                  size="large"
                  :style="{
                    borderRadius: 'calc(32px * 0.5)',
                    marginLeft: '0.625rem'
                  }"
                  @close="removeTag(item.id)"
                  >{{ item.tagName }}
                </a-tag>
              </a-space>
            </div>
          </div>
        </a-card>
      </a-layout-header>
      <a-layout-content>
        <v-md-editor
          v-model="text"
          height="43.75rem"
          left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol todo-list table hr | link image code | tip emoji"
          @copy-code-success="handleCopyCodeSuccess"
        ></v-md-editor>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<style scoped lang="scss">
#AddSolution {
  padding: 1.25rem 9.375rem;

  .form-input-title {
    text-align: center;
  }
}

.trigger {
  padding: 0.625rem;
  width: 12.5rem;
  background-color: var(--color-bg-popup);
  border-radius: 0.25rem;
  box-shadow: 0 0.125rem 0.5rem 0 rgba(0, 0, 0, 0.15);
}
</style>
