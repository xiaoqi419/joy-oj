<script setup lang="ts">
import { onMounted, ref, watch, watchEffect } from 'vue'
import { QuestionControllerService, QuestionEditRequest } from '../../../generated'
import { Message } from '@arco-design/web-vue'
import { useRouter } from 'vue-router'

const dataList = ref([])
const total = ref(0)
const searchParams = ref(
  {
    pageSize: 10,
    current: 1,
    title: '',
    tags: []
  }
)
const loading = ref(true)
const percent = ref(0)
const loadData = async () => {
  const res = await QuestionControllerService.listQuestionVoByPageUsingPost(searchParams.value)
  if (res.code === 20000) {
    // 将submitNum,acceptedNum转为Number
    res.data.records.forEach((item: any) => {
      item.submitNum = Number(item.submitNum)
      item.acceptedNum = Number(item.acceptedNum)
    })
    // 处理通过率
    res.data.records.forEach((item: any) => {
      if (item.submitNum === 0) {
        item.percent = 0
      } else {
        item.percent = (item.acceptedNum / item.submitNum) * 100
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
// 跳转到题目详情页
const toQuestionPage = (record: QuestionEditRequest) => {
  router.push({
    path: `/view/question/${record.id}`
  })
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

const doSearch = () => {
  searchParams.value = {
    ...searchParams.value,
    current: 1
  }
}

// 如果查询参数中的tag和title为空则执行一次查询
watch(() => searchParams, () => {
  if (searchParams.value.tags.length === 0 && searchParams.value.title === '') {
    loadData()
  }
}, {
  deep: true
})

</script>

<template>
  <div id="manageQuestionView">
    <h2>浏览题目</h2>
    <a-form :model="searchParams" layout="inline" class="mb-4">
      <a-form-item field="title" label="题目名称" style="min-width: 280px">
        <a-input v-model="searchParams.title" placeholder="请输入题目名称 ..."/>
      </a-form-item>
      <a-form-item field="tags" label="标签" style="min-width: 280px">
        <a-input-tag v-model="searchParams.tags" placeholder="请输入标签（回车确认）..."/>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="doSearch">搜索</a-button>
      </a-form-item>
    </a-form>
    <a-table :data="dataList" :loading="loading" @page-change="onPageChange" bordered stripe
             :scrollbar="scrollbar" :sticky-header="60"
             :pagination="{showTotal:true,pageSize: searchParams.pageSize,current:searchParams.current,total:total}">
      <template #columns>
        <a-table-column title="题号" data-index="id" align="center" :ellipsis="true" :tooltip="true"
                        :width="100" fixed="left"></a-table-column>
        <a-table-column title="题目名称" data-index="title" align="center" :ellipsis="true"
                        :tooltip="true" :width="200"></a-table-column>
        <a-table-column title="标签" data-index="tags" align="center">
          <template #cell="{ record }">
            <a-space>
              <a-tag color="#168cff" v-for="tag in record.tags" :key="tag.id">{{ tag }}
              </a-tag>
            </a-space>
          </template>
        </a-table-column>
        <a-table-column title="通过率（通过/提交）" data-index="submitNum" align="center">
          <template #cell="{ record }">
            <a-space>
              {{
                `${record.submitNum !== 0 ? Math.ceil((record.acceptedNum / record.submitNum) * 100) : 0}% (${record.acceptedNum}/${record.submitNum})`
              }}
              <a-progress type="circle" :percent="percent"/>
            </a-space>
          </template>
        </a-table-column>
        <a-table-column title="创建时间" data-index="createTime" align="center">
          <template #cell="{ record }">
            {{ new Date(record.createTime).toLocaleString() }}
          </template>
        </a-table-column>
        <a-table-column title="" align="center">
          <template #cell="{ record }">
            <a-space>
              <a-button type="primary" @click="toQuestionPage(record)">做题</a-button>
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
  max-width: 1280px;
  margin: 40px auto;
}
</style>
