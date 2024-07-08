<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { Question, QuestionControllerService } from '../../../generated'
import { Message } from '@arco-design/web-vue'
import { useRouter } from 'vue-router'

const dataList = ref([])
const total = ref(0)
const searchParams = ref(
  {
    pageSize: 10,
    pageNum: 1
  }
)
const loadData = async () => {
  const res = await QuestionControllerService.listQuestionByPageUsingPost(searchParams.value)
  if (res.code === 20000) {
    dataList.value = res.data.records
    total.value = Number(res.data.total)
  } else {
    Message.error('获取数据失败:' + res.message)
  }
}

onMounted(() => {
  loadData()
})

const columns = [
  {
    title: 'id',
    dataIndex: 'id'
  }, {
    title: '标题',
    dataIndex: 'title'
  }, {
    title: '内容',
    dataIndex: 'content'
  }, {
    title: '标签',
    dataIndex: 'tags'
  },
  {
    title: '答案',
    dataIndex: 'answer'
  },
  {
    title: '提交数',
    dataIndex: 'submitNum'
  },
  {
    title: '通过数',
    dataIndex: 'acceptedNum'
  },
  {
    title: '判题配置',
    dataIndex: 'judgeConfig'
  },
  {
    title: '判题用例',
    dataIndex: 'judgeCase'
  },
  {
    title: '用户id',
    dataIndex: 'userId'
  },
  {
    title: '创建时间',
    dataIndex: 'createTime'
  },
  {
    title: '操作',
    slotName: 'optional'
  }]
const router = useRouter()
const doUpdate = (record: Question) => {
  router.push({
    name: 'UpdateQuestion',
    query: {
      id: record.id
    }
  })
}

const doDelete = async (record: Question) => {
  const res = await QuestionControllerService.deleteQuestionUsingPost({
    id: record.id
  })
  if (res.code === 20000) {
    Message.success('删除成功')
    await loadData()
  } else {
    Message.error('删除失败:' + res.message)
  }
}
</script>

<template>
  <div id="manageQuestionView">
    <h2>管理题目</h2>
    <a-table :columns="columns" :data="dataList"
             :pagination="{showTotal:true,pageSize: searchParams.pageSize,current:searchParams.pageNum,total:total}">
      <template #optional="{ record }">
        <a-space>
          <a-button type="primary" @click="doUpdate(record)">修改</a-button>
          <a-popconfirm content="确定删除吗?" @on-before-ok="doDelete(record)">
            <a-button status="danger">删除</a-button>
          </a-popconfirm>

        </a-space>

      </template>
    </a-table>
  </div>
</template>

<style scoped lang="scss">
#manageQuestionView {
  margin-top: 40px;
  width: 1200px;
  margin-left: auto;
  margin-right: auto;
}
</style>
