<script lang="ts" setup>
import { onMounted, onUnmounted, ref, watch, watchEffect } from "vue";
import { QuestionControllerService } from "../../../generated";
import { Message } from "@arco-design/web-vue";
import dayjs from "dayjs";
import useUserStore from "@/store/modules/user";
import axios from "axios";
import TypeIt from "typeit";
import { marked } from "marked";

const dataList = ref([]);
const total = ref(0);
const searchParams = ref({
  pageSize: 4,
  current: 1,
  language: "",
  questionId: undefined
});
const loading = ref(true);
const loadData = async () => {
  const res = await QuestionControllerService.listQuestionSubmitByPageUsingPost(
    {
      ...searchParams.value,
      sortField: "createTime",
      sortOrder: "asc"
    }
  );
  if (res.code === 20000) {
    // 将submitNum,acceptedNum转为Number
    res.data.records.forEach((item: any) => {
      item.submitNum = Number(item.submitNum);
      item.acceptedNum = Number(item.acceptedNum);
    });
    // 将judgeInfo转为JSON
    res.data.records.forEach((item: any) => {
      // 将item.judgeInfo中英文字段转为中文显示
      item.judgeInfo = {
        内存: item.judgeInfo.memory + "MB",
        时间: item.judgeInfo.time + "毫秒",
        结果: item.judgeInfo.message === "Accepted" ? "正确" : "错误"
      };
      item.judgeInfo = JSON.stringify(item.judgeInfo);
    });
    dataList.value = res.data.records;
    total.value = Number(res.data.total);
    loading.value = false;
  } else {
    Message.error("获取数据失败:" + res.message);
  }
};
let intervalId: number;
onMounted(() => {
  loadData();
  intervalId = window.setInterval(() => {
    loadData();
  }, 10000);
});

const onPageChange = (page: number) => {
  searchParams.value = {
    ...searchParams.value,
    current: page
  };
};

watchEffect(() => {
  loadData();
});
const scrollbar = ref(true);

const doSearch = () => {
  searchParams.value = {
    ...searchParams.value,
    current: 1
  };
};

// 如果查询参数中的tag和title为空则执行一次查询
watch(
  () => searchParams.value,
  () => {
    if (
      searchParams.value.questionId === undefined &&
      searchParams.value.language === ""
    ) {
      loadData();
    }
  },
  {
    deep: true
  }
);

// 定时刷新，每隔10s刷新一次获取最新数据,当退出页面时消除定时器
onUnmounted(() => {
  window.clearInterval(intervalId); // Clear interval on component unmount
});

// 答题详情
const submitInfoVisible = ref(false);
const userStore = useUserStore();
const submitInfo = ref();
const showModal = (info: any) => {
  if (
    info.userId !== userStore.userInfo.id &&
    userStore.userInfo.userRole !== "admin"
  ) {
    Message.error("无权限查看");
    return;
  }
  submitInfo.value = info;
  submitInfoVisible.value = true;
  console.log(submitInfo.value);
};

// AI分析相关
const analysisVisible = ref(false);
const analysisInfo = ref();
const isShowLoading = ref(true);
const showAnalysisModal = (info: any) => {
  analysisInfo.value = info;
  analysisVisible.value = true;
  console.log(analysisInfo.value);
  isShowLoading.value = true;
  fetchAndDisplayText(info.code);
};
// 响应式变量：存储逐字显示的文本
const displayedText = ref("");
// 响应式变量：存储最终渲染的 HTML
const renderedHTML = ref();
// 创建 AbortController 实例
const abortController = new AbortController();
// 发起 API 请求并逐字显示文本
const fetchAndDisplayText = async (code: any) => {
  // 在code后面添加一段文字：（用中文回答）
  code += "(用中文回答)";
  const options = {
    method: "POST",
    url: "https://api.siliconflow.cn/v1/chat/completions",
    headers: {
      Authorization:
        "Bearer sk-rcxkslbuvfjzbiigovxusjxeqfrmochyifnnrpfpndajwgfq",
      "Content-Type": "application/json"
    },
    data: {
      model: "Qwen/Qwen2.5-Coder-32B-Instruct",
      messages: [
        {
          role: "user",
          content: code
        }
      ]
    },
    signal: abortController.signal
  };

  try {
    // 发起请求
    const response = await axios(options);
    const fullText = response.data.choices[0].message.content;

    // 清空之前的显示内容
    displayedText.value = "";
    renderedHTML.value = marked.parse(fullText);
    if (fullText) {
      isShowLoading.value = false;
    }
    // 初始化 TypeIt
    new TypeIt("#typing-container", {
      strings: renderedHTML.value, // 要显示的文本
      speed: 50, // 打字速度（单位：毫秒）
      waitUntilVisible: true, // 确保元素可见后再开始
      afterComplete: (instance: any) => {
        instance.destroy(); // 可选：销毁实例
      }
    }).go();
  } catch (error) {
    if (axios.isCancel(error)) {
      console.log("请求已中止:", error.message);
    } else {
      console.error("API 请求失败:", error);
    }
  }
};
const deleteContent = () => {
  isShowLoading.value = true;
  renderedHTML.value = null;
  // 在组件卸载时中止请求
  abortController.abort("组件已卸载，请求被中止");
};
</script>

<template>
  <div id="questionSubmitView">
    <h2>浏览题目提交</h2>
    <a-form :model="searchParams" class="mb-4" layout="inline">
      <a-form-item field="language" label="编程语言" style="min-width: 17.5rem">
        <a-input
          v-model="searchParams.language"
          placeholder="请输入编程语言 ..."
        />
      </a-form-item>
      <a-form-item field="questionId" label="题号" style="min-width: 280px">
        <a-input
          v-model="searchParams.questionId"
          placeholder="请输入题号 ..."
        />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="doSearch">搜索</a-button>
      </a-form-item>
    </a-form>
    <a-table
      :data="dataList"
      :loading="loading"
      :pagination="{
        showTotal: true,
        pageSize: searchParams.pageSize,
        current: searchParams.current,
        total: total
      }"
      :scrollbar="scrollbar"
      :sticky-header="60"
      bordered
      stripe
      @page-change="onPageChange"
    >
      <template #columns>
        <a-table-column
          :ellipsis="true"
          :tooltip="true"
          :width="100"
          align="center"
          data-index="id"
          fixed="left"
          title="提交号"
        ></a-table-column>
        <a-table-column
          :width="100"
          align="center"
          data-index="language"
          title="编程语言"
        >
          <template #cell="{ record }">
            <a-space>
              <a-tag color="#168cff">{{ record.language }}</a-tag>
            </a-space>
          </template>
        </a-table-column>
        <a-table-column :width="250" data-index="judgeInfo" title="判题信息">
          <template #cell="{ record }">
            <json-viewer
              :expand-depth="1"
              :value="JSON.parse(record.judgeInfo)"
              boxed
              copyable
              sort
            ></json-viewer>
          </template>
        </a-table-column>
        <a-table-column
          :width="100"
          align="center"
          data-index="status"
          title="判题状态"
        >
          <template #cell="{ record }">
            <a-space>
              <!-- 不同状态显示不同的标签（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败），不同颜色 -->
              <a-tag v-if="record.status === 0" color="gray">待判题</a-tag>
              <a-tag v-if="record.status === 1" color="#168cff" loading
                >判题中
              </a-tag>
              <a-tag v-if="record.status === 2" color="green">成功</a-tag>
              <a-tag v-if="record.status === 3" color="red">失败</a-tag>
            </a-space>
          </template>
        </a-table-column>
        <a-table-column
          :ellipsis="true"
          :tooltip="true"
          :width="100"
          align="center"
          data-index="questionId"
          fixed="left"
          title="题目id"
        ></a-table-column>
        <a-table-column
          :ellipsis="true"
          :tooltip="true"
          :width="100"
          align="center"
          data-index="userVO.userName"
          fixed="left"
          title="提交者"
        ></a-table-column>
        <a-table-column align="center" data-index="createTime" title="答题时间">
          <template #cell="{ record }">
            {{ dayjs(record.createTime).format("YYYY/MM/DD") }}
          </template>
        </a-table-column>
        <a-table-column align="center" title="操作">
          <template #cell="{ record }">
            <a-space>
              <a-button type="primary" @click="showModal(record)"
                >答题详情
              </a-button>
              <a-button
                status="warning"
                type="primary"
                @click="showAnalysisModal(record)"
                >AI分析
              </a-button>
            </a-space>
          </template>
        </a-table-column>
      </template>
    </a-table>
    <!--  答题详情  -->
    <a-modal v-if="submitInfoVisible" v-model:visible="submitInfoVisible">
      <template #title> 答题详情</template>
      <div>
        <highlightjs :code="submitInfo.code" language="java"></highlightjs>
      </div>
    </a-modal>
    <!--  AI分析  -->
    <a-modal
      v-if="analysisVisible"
      v-model:visible="analysisVisible"
      :width="800"
      @beforeClose="deleteContent"
    >
      <template #title> AI分析</template>
      <div>
        <div v-if="isShowLoading">
          <h1>思考中</h1>
          <a-spin :size="32" />
        </div>
        <div id="typing-container"></div>
      </div>
    </a-modal>
  </div>
</template>

<style lang="scss" scoped>
#questionSubmitView {
  width: 1200px;
  max-width: 1280px;
  margin: 40px auto;
}

#typing-container {
  white-space: pre-wrap; /* 保留换行符 */
  font-size: 18px;
  color: #333;
}
</style>
