<script setup lang="ts">
import { defineComponent, defineProps, onMounted, ref, withDefaults } from 'vue'
import {
  QuestionControllerService,
  QuestionEditRequest,
  QuestionSubmitAddRequest,
  QuestionSubmitControllerService
} from '../../../generated'
import { Message } from '@arco-design/web-vue'
import CodeEditor from '@/components/CodeEditor/index.vue'
import MdViewer from '@/components/MdViewer/index.vue'
import Artalk from '@/components/Artalk/index.vue'

defineComponent({
  name: 'QuestionInfoView',
  components: {
    CodeEditor,
    MdViewer,
    Artalk
  }
})

type Props = {
  id: String
}
const props = withDefaults(defineProps<Props>(), {
  id: () => ''
})
const question = ref<QuestionEditRequest>()
const loading = ref(true)
const loadData = async () => {
  const res = await QuestionControllerService.getQuestionVoByIdUsingGet(props.id as any)
  if (res.code === 20000) {
    question.value = res.data
    loading.value = false
  } else {
    Message.error('获取数据失败:' + res.message)
  }
}

const languageLoading = ref(false)
const options = ref([])
const getLanguageOptions = async () => {
  const res = await QuestionControllerService.getLanguagesUsingGet()
  languageLoading.value = true
  if (res.code === 20000) {
    options.value = res.data.map((item: any) => {
      // 首字母大写
      return item.name.charAt(0).toUpperCase() + item.name.slice(1)
    })
    languageLoading.value = false
  } else {
    Message.error('获取数据失败:' + res.message)
    languageLoading.value = false
  }
}
const form = ref<QuestionSubmitAddRequest>({
  language: '',
  code: ''
})
/**
 * 提交代码
 */
const submitLoading = ref(false)
const runLoading = ref(false)
const doSubmit = async () => {
  if (!question.value?.id) {
    return
  }
  const res = await QuestionSubmitControllerService.doQuestionSubmitUsingPost({
    ...form.value,
    questionId: question.value.id
  })
  submitLoading.value = true
  if (res.code === 20000) {
    Message.success('提交成功')
    submitLoading.value = false
  } else {
    Message.error('提交失败:' + res.message)
    submitLoading.value = false
  }
}

const changeCode = (value: string) => {
  form.value.code = value
}

onMounted(() => {
  loadData()
  getLanguageOptions()
})
</script>

<template>
  <div id="QuestionInfoView">
    <a-row :gutter="[24,24]">
      <a-col :md="11" :xs="24">
        <a-card class="a_card" hoverable :style="{  marginBottom: '20px' }" :loading="loading" style="padding: 10px">
          <a-tabs default-active-key="questionInfo">
            <a-tab-pane key="questionInfo">
              <template #title>
                <a-space>
                  <joy-svg-icon icon="question"/>
                  题目详情
                </a-space>
              </template>
              <h1>{{ question?.title }}</h1>
              <!-- 标签 -->
              <a-tag style="border-radius: 12px" v-for="tag in question?.tags" :key="tag" color="arcoblue">{{
                  tag
                }}
              </a-tag>
              <a-divider/>
              <a-descriptions title="判题条件" :column="{xs:1,md:2,lg:3}" v-if="question">
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
              <div style="margin-bottom: 10px;height: 10px"/>
              <MdViewer :value="question?.content" ref="mdViewerRef"/>
            </a-tab-pane>
            <a-tab-pane key="comment" title="评论">
              <template #title>
                <a-space>
                  <joy-svg-icon icon="comment"/>
                  评论
                </a-space>
              </template>
              <Artalk/>
            </a-tab-pane>
            <a-tab-pane key="answer" title="题解">
              <template #title>
                <a-space>
                  <joy-svg-icon icon="flask"/>
                  题解
                </a-space>
              </template>
              <a-empty>
                <template #image>
                  <icon-exclamation-circle-fill/>
                </template>
                暂时无法查看答案！
              </a-empty>

            </a-tab-pane>
          </a-tabs>
        </a-card>
      </a-col>
      <a-col :md="13" :xs="24">
        <a-card class="a_card" hoverable :style="{ marginBottom: '20px' }">
          <a-form :model="form" layout="inline" class="mb-4">
            <a-form-item field="language" label="编程语言" style="min-width: 280px">
              <a-select v-model="form.language" :options="options" :style="{width:'120px'}" :loading="languageLoading"
                        placeholder="请选择语言 ..." value-key="id" default-value="java"
                        @search="getLanguageOptions"/>
            </a-form-item>
          </a-form>
          <!-- 代码编辑器 -->
          <CodeEditor :value="form.code" :language="form.language" :handle-change="changeCode"/>
          <a-space style="padding: 4px 0 4px 4px;display: flex;justify-content: space-between">
            <div class="status">
              已存储
            </div>
            <div>
              <a-button class="ml-2" status="success" :loading="runLoading">运行</a-button>
              <a-button class="ml-2" type="primary" @click="doSubmit" :loading="submitLoading">提交</a-button>
            </div>
          </a-space>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<style scoped lang="scss">
#QuestionInfoView {
  width: 1400px;
  max-width: 1480px;
  margin: 20px auto;

  .a_card {
    border-radius: 12px;
  }
}
</style>
