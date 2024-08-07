<script setup lang="ts">
import useModalStore from '@/store/modules/modal'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import useUserStore from '@/store/modules/user'
import { QuestionControllerService } from '../../../generated'
import { Message } from '@arco-design/web-vue'

const modalStore = useModalStore()
const handleCancel = () => {
  modalStore.setResultModal(false)
}
const loading = ref(true)
const resultFlag = ref(false)
const route = useRouter()
const userStore = useUserStore()

// 查询判题结果
const queryJudgeResult = async () => {
  // 获取当前题目id
  const questionId = route.currentRoute.value.params.id
  // 获取当前用户id
  const userId = userStore.userInfo?.id
  const res = await QuestionControllerService.getQuestionSubmitByIdUsingPost({
    userId,
    questionId: Number(questionId)
  })
  if (res.code === 20000) {
    if (res.data === 'Accepted') {
      loading.value = false
      resultFlag.value = true
    }
    if (res.data === 'Wrong Answer') {
      loading.value = false
      resultFlag.value = false
    }
    if (res.data === 'waiting') {
      // 判题中隔一段时间再调用本身
      setTimeout(() => {
        queryJudgeResult()
      }, 3000)
    }
  } else {
    Message.error('获取数据失败:' + res.message)
  }
}

queryJudgeResult()

const rouer = useRouter()
const goQuestionSubmitView = () => {
  modalStore.setResultModal(false)
  rouer.push({
    path: '/view/question_submit'
  })
}

</script>

<template>
  <div id="ResultModal">
    <a-modal v-model:visible="modalStore.resultModal" @cancel="handleCancel" :closable="false" :footer="false"
             :mask-closable="false">
      <div v-if="loading" class="loading">
        <a-spin :loading="loading" :size="32" tip="判题中，请稍等 ..."/>
      </div>
      <div v-else>
        <!-- 成功结果 -->
        <a-result v-if="resultFlag" status="success" title="恭喜你，解答成功！">
          <template #subtitle>
            <span>
              请前往浏览题目提交结果页面查看更详细的答题详情
            </span>
          </template>
          <template #extra>
            <a-space>
              <a-button type='primary' @click="goQuestionSubmitView">前往</a-button>
              <a-button type='primary' @click="modalStore.setResultModal(false)">关闭</a-button>
            </a-space>
          </template>
        </a-result>
        <!-- 失败结果 -->
        <a-result v-else status="error" title="很遗憾，解答失败！">
          <template #subtitle>
            请前往浏览题目提交结果页面查看更详细的答题详情
          </template>

          <template #extra>
            <a-space>
              <a-button type='primary' @click="modalStore.setResultModal(false)">继续作答</a-button>
            </a-space>
          </template>
        </a-result>
      </div>
    </a-modal>
  </div>
</template>

<style scoped lang="scss">
.loading {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;

}

span {
  font-size: 16px;
}
</style>
