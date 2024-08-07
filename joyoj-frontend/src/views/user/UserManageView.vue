<script setup lang="ts">
import { onMounted, ref, watch, watchEffect } from 'vue'
import { QuestionEditRequest, UserControllerService } from '../../../generated'
import { Message } from '@arco-design/web-vue'
import { useRouter } from 'vue-router'
import dayjs from 'dayjs'

const dataList = ref([])
const total = ref(0)
const searchParams = ref(
  {
    pageSize: 10,
    current: 1,
    userName: '',
    userRole: ''
  }
)
const loading = ref(true)
const loadData = async () => {
  const res = await UserControllerService.listUserByPageUsingPost(searchParams.value)
  if (res.code === 20000) {
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
watch(() => searchParams.value, () => {
  if (searchParams.value.userRole === '' && searchParams.value.userName === '') {
    loadData()
  }
}, {
  deep: true
})

</script>

<template>
  <div id="manageQuestionView">
    <h2>用户管理</h2>
    <a-form :model="searchParams" layout="inline" class="mb-4">
      <a-form-item field="userName" label="用户名称" style="min-width: 280px">
        <a-input v-model="searchParams.userName" placeholder="请输入用户名称 ..."/>
      </a-form-item>
      <a-form-item field="userRole" label="角色" style="min-width: 280px">
        <a-input v-model="searchParams.userRole" placeholder="请输入角色 ..."/>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="doSearch">搜索</a-button>
      </a-form-item>
    </a-form>
    <a-table :data="dataList" :loading="loading" @page-change="onPageChange" bordered stripe
             :scrollbar="scrollbar" :sticky-header="60"
             :pagination="{showTotal:true,pageSize: searchParams.pageSize,current:searchParams.current,total:total}">
      <template #columns>
        <a-table-column title="id" data-index="id" align="center" :ellipsis="true" :tooltip="true"
                        :width="100" fixed="left"></a-table-column>
        <a-table-column title="用户名称" data-index="userName" align="center" :ellipsis="true"
                        :tooltip="true" :width="200"></a-table-column>
        <a-table-column title="角色" data-index="userRole" align="center">
          <template #cell="{ record }">
            <a-space>
              <a-tag color="#168cff">{{ record.userRole }}
              </a-tag>
            </a-space>
          </template>
        </a-table-column>
        <a-table-column title="头像" data-index="userAvatar" align="center">
          <template #cell="{ record }">
            <a-avatar>
              <img
                alt="avatar"
                :src="record.userAvatar"
              />
            </a-avatar>
          </template>
        </a-table-column>
        <a-table-column title="注册时间" data-index="createTime" align="center">
          <template #cell="{ record }">
            {{ dayjs(record.createTime).format('YYYY/MM/DD') }}
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
