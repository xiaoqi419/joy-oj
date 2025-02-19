<script setup lang="ts">
import { defineComponent, onMounted, ref, watch, withDefaults } from "vue";
import {
  PostControllerService,
  QuestionControllerService,
  QuestionEditRequest,
  QuestionSubmitAddRequest
} from "../../../generated";
import { Message } from "@arco-design/web-vue";
import CodeEditor from "@/components/CodeEditor/index.vue";
import MdViewer from "@/components/MdViewer/index.vue";
import Artalk from "@/components/Artalk/index.vue";
import ResultModal from "@/components/Modal/ResultModal.vue";
import useModalStore from "@/store/modules/modal";
import useUserStore from "@/store/modules/user";
import { useRouter } from "vue-router";
import { debounce } from "@pureadmin/utils";
import Solution from "@/components/Solution/index.vue";

defineComponent({
  name: "QuestionInfoView",
  components: {
    CodeEditor,
    MdViewer,
    Artalk,
    Solution
  }
});

type Props = {
  id: String;
};
const props = withDefaults(defineProps<Props>(), {
  id: () => ""
});
const question = ref<QuestionEditRequest>();
const loading = ref(true);
const loadData = async () => {
  const res = await QuestionControllerService.getQuestionVoByIdUsingGet(
    props.id as any
  );
  if (res.code === 20000) {
    question.value = res.data;
    loading.value = false;
  } else {
    Message.error("获取数据失败:" + res.message);
  }
};

const options = ref();
const getLanguageOptions = async () => {
  const res = await QuestionControllerService.getLanguagesUsingGet();
  if (res.code === 20000) {
    options.value = res.data!.map((item: any) => {
      // 首字母大写
      return item.name.charAt(0).toUpperCase() + item.name.slice(1);
    });
    form.value.language = "Java";
  } else {
    Message.error("获取数据失败:" + res.message);
  }
};
const form = ref<QuestionSubmitAddRequest>({
  language: "",
  code: ""
});
/**
 * 提交代码
 */
const submitLoading = ref(false);
const runLoading = ref(false);
const modalStore = useModalStore();
const ResultFlag = ref(false);
const doSubmit = async () => {
  ResultFlag.value = false;
  if (!question.value?.id) {
    return;
  }
  submitLoading.value = !submitLoading.value;
  const res = await QuestionControllerService.doQuestionSubmitUsingPost({
    ...form.value,
    language: form.value.language?.toLowerCase(),
    questionId: question.value.id
  });
  if (res.code === 20000) {
    Message.success("提交成功");
    submitLoading.value = !submitLoading.value;
    ResultFlag.value = true;
    modalStore.setResultModal(true);
  } else {
    Message.error("提交失败:" + res.message);
    submitLoading.value = !submitLoading.value;
  }
};

const changeCode = (value: string) => {
  form.value.code = value;
};

// 本地提交代码
const doLocalJudge = async () => {
  ResultFlag.value = false;
  if (!question.value?.id) {
    return;
  }
  runLoading.value = !runLoading.value;
  const res = await QuestionControllerService.doLocalQuestionSubmitUsingPost({
    ...form.value,
    language: form.value.language?.toLowerCase(),
    questionId: question.value.id
  });
  if (res.code === 20000) {
    Message.success("提交成功");
    runLoading.value = !runLoading.value;
    ResultFlag.value = true;
    modalStore.setResultModal(true);
  } else {
    Message.error("提交失败:" + res.message);
    runLoading.value = !runLoading.value;
  }
};

const userStore = useUserStore();
const route = useRouter();
const isSave = ref(false);

// 获取保存的代码
const questionId = ref(route.currentRoute.value.params.id.toString());
const getCode = async () => {
  const res = await QuestionControllerService.getQuestionSaveUsingPost({
    userId: userStore.userInfo.id,
    questionId: Number(route.currentRoute.value.params.id)
  });
  if (res.code === 20000) {
    if (res.data.code !== null) {
      form.value.code = res.data.code;
    }
  }
};
// 防抖保存代码
const saveCode: any = debounce(async () => {
  isSave.value = true;
  const res = await QuestionControllerService.saveQuestionSubmitUsingPost({
    ...form.value,
    userId: userStore.userInfo.id,
    language: form.value.language?.toLowerCase(),
    questionId: Number(route.currentRoute.value.params.id)
  });
  if (res.code === 20000) {
    Message.success("保存成功");
    isSave.value = false;
    // 重新渲染页面
    await getCode();
  } else {
    Message.error("保存失败:" + res.message);
    isSave.value = false;
    // 重新渲染页面
    await getCode();
  }
}, 1500);
onMounted(async () => {
  await loadData();
  await getLanguageOptions();
  await getCode();
  await saveCode();
  // 确保 form.code 初始化为一个空字符串或之前保存的值
  form.value.code = form.value.code || "";
  form.value.language = form.value.language || "Java"; // 默认语言
});

watch(
  () => form.value.code,
  () => {
    // 如果路由值存在
    if (route.currentRoute.value.params.id) {
      saveCode();
    }
  },
  { deep: true }
);

// 当切换到题解是获取题解内容
const solutionData = ref();
const solutionIds = ref();
const solutions = ref();
const getSolutionData = async () => {
  const res1 = await PostControllerService.listSolutionIdsByPostIdUsingGet(
    props.id as any
  );
  if (res1.code === 20000) {
    solutionIds.value = res1.data;
    const res2 = await PostControllerService.listPostVoByPageUsingPost({
      current: 1,
      pageSize: 5,
      ids: solutionIds.value
    });
    if (res2.code === 20000) {
      solutionData.value = res2.data;
      solutions.value = solutionData.value.records;
    } else {
      Message.error("获取数据失败:" + res2.message);
    }
  } else {
    Message.error("获取数据失败:" + res1.message);
  }
};

const getSearchTitleObj = (res: any) => {
  // 如果不为空
  if (res) {
    // 获得搜索之后的数据返回父组件
    solutions.value = res.records;
    solutionData.value = res;
  } else {
    getSolutionData();
  }
};

// 当页码改变时
const pageChange = async (cur: number) => {
  const res = await PostControllerService.listPostVoByPageUsingPost({
    current: cur,
    pageSize: 5,
    ids: solutionIds.value
  });
  if (res.code === 20000) {
    solutionData.value = res.data;
    solutions.value = res.data.records;
  } else {
    Message.error("获取数据失败:" + res.message);
  }
};

// 如果路由是/view/question/1/solution/的形式的话使tab默认选中题解
const isDefaultTab = ref("questionInfo");
onMounted(async () => {
  if (route.currentRoute.value.name === "solutionInfo") {
    isDefaultTab.value = "answer";
  }
  await getSolutionData();
});
</script>

<template>
  <div id="QuestionInfoView">
    <a-row :gutter="[24, 24]">
      <a-col :md="12" :xs="24">
        <a-card
          class="a_card"
          hoverable
          :style="{ marginBottom: '20px' }"
          :loading="loading"
          style="padding: 5px"
        >
          <a-tabs :default-active-key="isDefaultTab">
            <a-tab-pane key="questionInfo">
              <template #title>
                <a-space>
                  <joy-svg-icon icon="question" />
                  题目详情
                </a-space>
              </template>
              <h1>{{ question?.title }}</h1>
              <!-- 标签 -->
              <a-tag
                style="border-radius: 12px"
                v-for="tag in question?.tags"
                :key="tag"
                color="arcoblue"
                >{{ tag }}
              </a-tag>
              <a-divider />
              <a-descriptions
                title="判题条件"
                :column="{ xs: 1, md: 2, lg: 3 }"
                v-if="question"
              >
                <a-descriptions-item label="时间限制">
                  {{ question.judgeConfig?.timeLimit }}
                </a-descriptions-item>
                <a-descriptions-item label="内存限制">
                  {{ question.judgeConfig?.memoryLimit }}
                </a-descriptions-item>
                <a-descriptions-item label="堆栈限制">
                  {{ question.judgeConfig?.stackLimit }}
                </a-descriptions-item>
              </a-descriptions>
              <div style="margin-bottom: 10px; height: 10px" />
              <MdViewer :value="question?.content" ref="mdViewerRef" />
            </a-tab-pane>
            <a-tab-pane key="comment" title="评论">
              <template #title>
                <a-space>
                  <joy-svg-icon icon="comment" />
                  评论
                </a-space>
              </template>
              <Artalk />
            </a-tab-pane>
            <a-tab-pane key="answer" title="题解">
              <template #title>
                <a-space>
                  <joy-svg-icon icon="flask" />
                  题解
                </a-space>
              </template>
              <div :style="{ paddingRight: '2px' }">
                <!-- 题解组件 -->
                <Solution
                  v-if="solutionData"
                  :solutions="solutions"
                  @searchSolutionByTitle="getSearchTitleObj"
                  :pageSize="Number(solutionData.size)"
                  :current="Number(solutionData.current)"
                  :total="Number(solutionData.total)"
                  @changePage="pageChange"
                />
                <a-skeleton :animation="true" v-else>
                  <a-space
                    direction="vertical"
                    :style="{ width: '100%' }"
                    size="large"
                  >
                    <a-skeleton-line :rows="5" />
                  </a-space>
                </a-skeleton>
              </div>
            </a-tab-pane>
          </a-tabs>
        </a-card>
      </a-col>
      <a-col :md="12" :xs="24">
        <a-card class="a_card" hoverable :style="{ marginBottom: '20px' }">
          <a-form :model="form" layout="inline" class="mb-4">
            <a-form-item
              field="language"
              label="编程语言"
              style="min-width: 280px"
            >
              <a-select
                v-model="form.language"
                :options="options"
                :style="{ width: '120px' }"
                placeholder="请选择语言 ..."
                value-key="id"
                default-value="Java"
                @search="getLanguageOptions"
              />
            </a-form-item>
          </a-form>
          <!-- 代码编辑器 -->
          <CodeEditor
            :question-id="questionId"
            :value="form.code"
            :language="form.language"
            :handle-change="changeCode"
            :user-id="userStore.userInfo.id"
          />
          <a-space
            style="
              padding: 4px 0 4px 4px;
              display: flex;
              justify-content: space-between;
            "
          >
            <div class="status">
              <a-space v-if="isSave">
                <a-spin>
                  <template #icon>
                    <icon-sync />
                  </template>
                </a-spin>
                存储中
              </a-space>
              <span v-else>已存储</span>
            </div>
            <div>
              <a-button
                class="ml-2"
                status="success"
                :loading="runLoading"
                @click="doLocalJudge"
                >运行
              </a-button>
              <a-button
                class="ml-2"
                type="primary"
                @click="doSubmit"
                :loading="submitLoading"
                >提交
              </a-button>
            </div>
          </a-space>
        </a-card>
        <!-- 控制台显示测试用例和输出用例 -->
        <a-card class="a_card">
          <a-row :gutter="[24, 24]">
            <a-col :md="7" :xs="24">
              <a-menu :style="{ width: '200px', borderRadius: '4px' }">
                <a-sub-menu key="0">
                  <template #title>测试用例</template>
                  <a-menu-item key="0_Navigation 10">Case 1</a-menu-item>
                  <a-menu-item key="0_1">Case 2</a-menu-item>
                </a-sub-menu>
              </a-menu>
            </a-col>
            <a-col :md="17" :xs="24">
              <highlightjs code="暂时无法查看" />
            </a-col>
          </a-row>
        </a-card>
      </a-col>
    </a-row>
    <ResultModal v-if="ResultFlag" />
  </div>
</template>

<style scoped lang="scss">
#QuestionInfoView {
  width: 95vw;
  margin: 10px auto;

  .a_card {
    border-radius: 12px;
  }
}
</style>
