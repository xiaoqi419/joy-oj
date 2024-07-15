<script setup lang="ts">
import { onMounted, ref, watchEffect } from 'vue'
import { QuestionControllerService, QuestionEditRequest } from '../../../generated'
import { Message } from '@arco-design/web-vue'
import { useRouter } from 'vue-router'
import dayjs from 'dayjs'

const dataList = ref([])
const total = ref(0)
const searchParams = ref(
  {
    pageSize: 10,
    current: 1
  }
)
const loading = ref(true)
const loadData = async () => {
  const res = await QuestionControllerService.listQuestionByPageUsingPost(searchParams.value)
  if (res.code === 20000) {
    // 格式化数据
    res.data.records.forEach((item: any) => {
      if (item.tags) {
        item.tags = JSON.parse(item.tags)
      }
    })
    dataList.value = res.data.records
    total.value = Number(res.data.total)
    loading.value = false
  } else {
    Message.error('获取数据失败:' + res.message)
  }
}

onMounted(() => {
  loadData()
})

const router = useRouter()
const doUpdate = (record: QuestionEditRequest) => {
  router.push({
    name: 'UpdateQuestion',
    query: {
      id: record.id
    }
  })
}

const doDelete = async (record: QuestionEditRequest) => {
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

const onPageChange = (page: number) => {
  searchParams.value = {
    ...searchParams.value,
    current: page
  }
}

watchEffect(() => {
  loadData()
})
const scrollbar = ref(true)
const scroll = {
  x: 2000,
  y: 1000
}
</script>

<template>
  <div id="manageQuestionView">
    <h2>管理题目</h2>
    <a-table :data="dataList" :loading="loading" @page-change="onPageChange" bordered stripe :scroll="scroll"
             :scrollbar="scrollbar" :sticky-header="60"
             :pagination="{showTotal:true,pageSize: searchParams.pageSize,current:searchParams.current,total:total}">
      <template #columns>
        <a-table-column title="id" data-index="id" align="center" :ellipsis="true" :tooltip="true"
                        :width="100" fixed="left"></a-table-column>
        <a-table-column title="标题" data-index="title" align="center" :width="100"></a-table-column>
        <a-table-column title="内容" data-index="content" align="center" :width="100" :ellipsis="true"
                        :tooltip="true"></a-table-column>
        <a-table-column title="标签" data-index="tags" align="center" :width="100">
          <template #cell="{ record }">
            <a-space>
              <a-tag color="#168cff" v-for="tag in record.tags" :key="tag.id">{{ tag }}
              </a-tag>
            </a-space>
          </template>
        </a-table-column>
        <a-table-column title="答案" data-index="answer" align="center" :ellipsis="true" :width="200"
                        :tooltip="true"></a-table-column>
        <a-table-column title="判题配置" data-index="judgeConfig" :width="250">
          <template #cell="{ record }">
            <json-viewer
              :value="JSON.parse(record.judgeConfig)"
              copyable
              :expand-depth="1"
              boxed
              sort
            ></json-viewer>
          </template>
        </a-table-column>
        <a-table-column title="判题用例" data-index="judgeCase" :width="220">
          <template #cell="{ record }">
            <json-viewer
              :value="{ ...JSON.parse(record.judgeCase) }"
              copyable
              :expand-depth="1"
              boxed
              sort
            ></json-viewer>
          </template>
        </a-table-column>
        <a-table-column title="提交数" data-index="submitNum" align="center"></a-table-column>
        <a-table-column title="通过数" data-index="acceptedNum" align="center"></a-table-column>

        <a-table-column title="用户id" data-index="userId" align="center"></a-table-column>
        <a-table-column title="创建时间" data-index="createTime" align="center">
          <template #cell="{ record }">
            {{ dayjs(record.createTime).format('YYYY/MM/DD') }}
          </template>
        </a-table-column>
        <a-table-column title="操作" align="center">
          <template #cell="{ record }">
            <a-space>
              <a-button type="primary" @click="doUpdate(record)">修改</a-button>
              <a-popconfirm content="确定删除吗?" @on-before-ok="doDelete(record)">
                <a-button status="danger">删除</a-button>
              </a-popconfirm>
            </a-space>
          </template>
        </a-table-column>
      </template>
    </a-table>
  </div>
</template>

<style scoped lang="scss">
#manageQuestionView {
  width: 1200px;
  margin: 40px auto;
}
</style>
