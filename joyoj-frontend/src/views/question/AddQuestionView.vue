<script setup lang="ts">
import { onMounted, reactive, watch } from "vue";
import MdEditor from "@/components/MdEditor/index.vue";
import {
  QuestionAddRequest,
  QuestionControllerService
} from "../../../generated";
import { FieldRule, Message } from "@arco-design/web-vue";
import { useRoute } from "vue-router";

const form = reactive<QuestionAddRequest>({
  answer: "",
  content: "",
  judgeCase: [
    {
      input: "",
      output: ""
    }
  ],
  judgeConfig: {
    memoryLimit: 1000,
    stackLimit: 1000,
    timeLimit: 1000
  },
  tags: [],
  title: ""
});

const handleAdd = () => {
  if (form.judgeCase) {
    form.judgeCase.push({
      input: "",
      output: ""
    });
  }
};

const handleDelete = (index: number) => {
  // 如果是最后一个测试用例则不允许删除
  if (form.judgeCase && form.judgeCase.length === 1) {
    Message.error("至少需要一个测试用例");
    return;
  }
  if (form.judgeCase) {
    form.judgeCase.splice(index, 1);
  }
};

const onContentChange = (v: string) => {
  form.content = v;
};

// const onAnswerChange = (v: string) => {
//   form.answer = v
// }

// 页面复用
// 如果路由包含update则视为更新页面
const route = useRoute();
const updatePage = route.path.includes("update");
const loadData = async () => {
  if (updatePage) {
    if (!route.query.id) {
      Message.error("缺少参数id");
      return;
    }
    const id = route.query.id;
    const res = await QuestionControllerService.getQuestionByIdUsingGet(
      id as any
    );
    if (res.code === 20000) {
      const data = res.data! as any;
      form.answer = res.data!.answer;
      form.content = res.data!.content;
      form.judgeCase = JSON.parse(data.judgeCase);
      form.judgeConfig = JSON.parse(data.judgeConfig);
      form.tags = JSON.parse(data.tags);
      form.title = res.data!.title;
    } else {
      Message.error("获取数据失败:" + res.message);
    }
  }
};

const doSubmit = async ({ errors }: any) => {
  // 验证表单
  if (errors === undefined) {
    // 标签只允许三个
    if (form.tags!.length > 3) {
      Message.error("标签最多只能有三个");
      return;
    }
    if (updatePage) {
      const res = await QuestionControllerService.updateQuestionUsingPost({
        ...form,
        id: route.query.id
      } as QuestionAddRequest);
      if (res.code === 20000) {
        Message.success("更新成功");
      } else {
        Message.error("更新失败:" + res.message);
      }
    } else {
      const res = await QuestionControllerService.addQuestionUsingPost(form);
      if (res.code === 20000) {
        Message.success("添加成功");
        // 清空表单
        form.answer = "";
        form.content = "";
        form.judgeCase = [
          {
            input: "",
            output: ""
          }
        ];
        form.judgeConfig = {
          memoryLimit: 1000,
          stackLimit: 1000,
          timeLimit: 1000
        };
        form.tags = [];
        form.title = "";
      } else {
        Message.error("创建失败:" + res.message);
      }
    }
  }
};

// 监听路由变化，如果从包含update变为add 则清空form
watch(
  () => route.path,
  path => {
    if (path.includes("add")) {
      form.answer = "";
      form.content = "";
      form.judgeCase = [
        {
          input: "",
          output: ""
        }
      ];
      form.judgeConfig = {
        memoryLimit: 1000,
        stackLimit: 1000,
        timeLimit: 1000
      };
      form.tags = [];
      form.title = "";
    }
  }
);

onMounted(() => {
  loadData();
});

const rules = reactive<Record<string, FieldRule | FieldRule[]>>({
  title: [
    {
      required: true,
      message: "请输入标题"
    },
    {
      maxLength: 512,
      message: "标题长度不能超过512个字符"
    },
    {
      validator(value, callback) {
        // 不能是非法字符
        const reg = /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/;
        if (!reg.test(value)) {
          const errorMsg = "标题不能包含非法字符";
          return callback(errorMsg);
        }
        return callback();
      }
    }
  ],
  tags: [
    {
      required: true,
      message: "请输入标签"
    }
  ],
  content: [
    {
      required: true,
      message: "请输入题目内容"
    }
  ],
  answer: [
    {
      required: true,
      message: "请输入答案"
    }
  ],
  "judgeConfig.timeLimit": [
    {
      required: true,
      message: "请输入时间限制"
    }
  ],
  "judgeConfig.memoryLimit": [
    {
      required: true,
      message: "请输入堆栈限制"
    }
  ],
  "judgeConfig.stackLimit": [
    {
      required: true,
      message: "请输入内存限制"
    }
  ]
});
</script>

<template>
  <div id="addQuestionView">
    <!--如果是更新就显示更新题目，否则显示创建题目-->
    <h2 v-if="updatePage">更新题目</h2>
    <h2 v-else>创建题目</h2>
    <a-form
      :model="form"
      :rules="rules"
      ref="addQuestionFormRef"
      @submit="doSubmit"
    >
      <a-form-item field="title" label="标题">
        <a-input
          v-model="form.title"
          placeholder="请输入标题"
          style="max-width: 640px"
        />
      </a-form-item>
      <a-form-item field="tags" label="标签">
        <a-input-tag
          v-model="form.tags"
          placeholder="请输入标签（回车确认）"
          allow-clear
          style="max-width: 640px"
        />
      </a-form-item>
      <a-form-item field="content" label="题目内容" style="min-height: 600px">
        <MdEditor :value="form.content" :handle-change="onContentChange" />
      </a-form-item>
      <!--      <a-form-item field="answer" label="答案">-->
      <!--        <MdEditor :value="form.answer" :handle-change="onAnswerChange"/>-->
      <!--      </a-form-item>-->
      <a-form-item label="判题配置" :content-flex="false" :merge-props="false">
        <a-space
          direction="vertical"
          style="min-width: 480px; max-width: 640px"
        >
          <a-form-item
            field="judgeConfig.timeLimit"
            label="时间限制"
            style="min-width: 640px"
          >
            <a-input-number
              v-model="form.judgeConfig!.timeLimit"
              placeholder="请输入时间限制"
              mode="button"
              size="large"
              :min="0"
            />
          </a-form-item>
          <a-form-item field="judgeConfig.memoryLimit" label="堆栈限制">
            <a-input-number
              v-model="form.judgeConfig!.memoryLimit"
              placeholder="请输入堆栈限制"
              mode="button"
              size="large"
              :min="0"
            />
          </a-form-item>
          <a-form-item field="judgeConfig.stackLimit" label="内存限制">
            <a-input-number
              v-model="form.judgeConfig!.stackLimit"
              placeholder="请输入内存限制"
              mode="button"
              size="large"
              :min="0"
            />
          </a-form-item>
        </a-space>
      </a-form-item>
      <a-form-item
        label="测试用例配置"
        :content-flex="false"
        :merge-props="false"
      >
        <a-form-item
          v-for="(judgeCaseItem, index) of form.judgeCase"
          :key="index"
          no-style
        >
          <a-space
            direction="vertical"
            style="min-width: 480px; max-width: 640px"
          >
            <a-form-item
              :field="`form.judgeCase[${index}].input`"
              :label="`输入用例-${index}`"
              style="min-width: 640px"
            >
              <a-input
                v-model="judgeCaseItem.input"
                placeholder="请输入输入用例"
              />
            </a-form-item>
            <a-form-item
              :field="`form.judgeCase[${index}].output`"
              :label="`输出用例-${index}`"
            >
              <a-input
                v-model="judgeCaseItem.output"
                placeholder="请输入输出用例"
              />
            </a-form-item>
            <a-button
              @click="handleDelete(index)"
              status="danger"
              class="ml-12"
              style="margin-bottom: 18px"
            >
              删除
            </a-button>
          </a-space>
        </a-form-item>
        <div>
          <a-button
            @click="handleAdd"
            type="outline"
            status="success"
            class="ml-12"
            >新增测试用例</a-button
          >
        </div>
      </a-form-item>

      <a-form-item>
        <a-button
          type="primary"
          size="large"
          style="min-width: 240px; margin: 0 auto"
          html-type="submit"
          >提交</a-button
        >
      </a-form-item>
    </a-form>
  </div>
</template>

<style scoped lang="scss">
#addQuestionView {
  margin-top: 40px;
  width: 1000px;
  margin-left: auto;
  margin-right: auto;
}
</style>
