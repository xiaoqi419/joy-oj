<script setup lang="ts">
import { onMounted, reactive, ref, watch, watchEffect } from "vue";
import { UserControllerService } from "../../../generated";
import { FieldRule, Message } from "@arco-design/web-vue";
import dayjs from "dayjs";

const dataList = ref([]);
const total = ref(0);
const searchParams = ref({
  pageSize: 10,
  current: 1,
  userName: "",
  userRole: ""
});
const loading = ref(true);
const loadData = async () => {
  const res = await UserControllerService.listUserByPageUsingPost(
    searchParams.value
  );
  if (res.code === 20000) {
    dataList.value = res.data.records;
    total.value = Number(res.data.total);
    loading.value = false;
  } else {
    Message.error("获取数据失败:" + res.message);
  }
};

onMounted(() => {
  loadData();
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
      searchParams.value.userRole === "" &&
      searchParams.value.userName === ""
    ) {
      loadData();
    }
  },
  {
    deep: true
  }
);

// 用户管理
const form = ref({
  userName: "",
  userRole: "",
  userEmail: "",
  id: 0
});
const visible = ref(false);
const btnLoading = ref(false);
const showModal = (res: any) => {
  visible.value = true;
  form.value.userName = res.userName;
  form.value.userRole = res.userRole;
  form.value.userEmail = res.userEmail;
  form.value.id = res.id;
};

const handleSubmit = async ({ errors }: any) => {
  console.log(form.value);
  // 验证表单
  if (errors === undefined) {
    btnLoading.value = true;
    const res = await UserControllerService.updateUserUsingPost(form.value);
    if (res.code === 20000) {
      Message.success("修改成功");
      visible.value = false;
      btnLoading.value = false;
      await loadData();
    } else {
      Message.error("修改失败:" + res.message);
      btnLoading.value = false;
    }
  }
};

const rules = reactive<Record<string, FieldRule | FieldRule[]>>({
  userName: [
    {
      required: true,
      message: "请输入用户名"
    },
    {
      validator(value, callback) {
        if (!value) {
          const errorMsg = "请输入用户名";
          return callback(errorMsg);
        }
        // 允许中文、数字、字母不允许非法字符
        const reg = /^[\u4e00-\u9fa5a-zA-Z0-9_]+$/;
        if (!reg.test(value)) {
          const errorMsg = "用户名只能包含中文、英文、数字、下划线";
          return callback(errorMsg);
        }
        return callback();
      }
    }
  ],
  userEmail: [
    {
      required: true,
      message: "请输入邮箱"
    },
    {
      validator(value, callback) {
        if (!value) {
          const errorMsg = "请输入邮箱";
          return callback(errorMsg);
        }
        // 邮箱格式验证
        const reg =
          /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
        if (!reg.test(value)) {
          const errorMsg = "邮箱格式不正确";
          return callback(errorMsg);
        }
        return callback();
      }
    }
  ],
  userRole: [
    {
      required: true,
      message: "请选择角色"
    }
  ]
});
</script>

<template>
  <div id="manageQuestionView">
    <h2>用户管理</h2>
    <a-form :model="searchParams" layout="inline" class="mb-4">
      <a-form-item field="userName" label="用户名称" style="min-width: 280px">
        <a-input
          v-model="searchParams.userName"
          placeholder="请输入用户名称 ..."
        />
      </a-form-item>
      <a-form-item field="userRole" label="角色" style="min-width: 280px">
        <a-input v-model="searchParams.userRole" placeholder="请输入角色 ..." />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="doSearch">搜索</a-button>
      </a-form-item>
    </a-form>
    <a-table
      :data="dataList"
      :loading="loading"
      @page-change="onPageChange"
      bordered
      stripe
      :scrollbar="scrollbar"
      :sticky-header="60"
      :pagination="{
        showTotal: true,
        pageSize: searchParams.pageSize,
        current: searchParams.current,
        total: total
      }"
    >
      <template #columns>
        <a-table-column
          title="id"
          data-index="id"
          align="center"
          :ellipsis="true"
          :tooltip="true"
          :width="100"
          fixed="left"
        ></a-table-column>
        <a-table-column
          title="用户名称"
          data-index="userName"
          align="center"
          :ellipsis="true"
          :tooltip="true"
          :width="200"
        ></a-table-column>
        <a-table-column title="角色" data-index="userRole" align="center">
          <template #cell="{ record }">
            <a-space>
              <a-tag color="#168cff">{{ record.userRole }} </a-tag>
            </a-space>
          </template>
        </a-table-column>
        <a-table-column title="头像" data-index="userAvatar" align="center">
          <template #cell="{ record }">
            <a-avatar>
              <img alt="avatar" :src="record.userAvatar" />
            </a-avatar>
          </template>
        </a-table-column>
        <a-table-column title="注册时间" data-index="createTime" align="center">
          <template #cell="{ record }">
            {{ dayjs(record.createTime).format("YYYY/MM/DD") }}
          </template>
        </a-table-column>
        <a-table-column title="" align="center">
          <template #cell="{ record }">
            <a-space>
              <a-button type="primary" @click="showModal(record)"
                >管理</a-button
              >
            </a-space>
          </template>
        </a-table-column>
      </template>
    </a-table>
    <a-modal v-if="visible" v-model:visible="visible" :footer="false">
      <template #title> 用户管理 </template>
      <div>
        <a-form :model="form" @submit="handleSubmit" :rules="rules">
          <a-form-item field="userName" label="用户名">
            <a-input
              v-model="form.userName"
              placeholder="please enter your username..."
            />
          </a-form-item>
          <a-form-item field="userEmail" label="邮箱">
            <a-input
              v-model="form.userEmail"
              placeholder="please enter your email..."
            />
          </a-form-item>
          <a-form-item field="userRole" label="角色">
            <a-select
              v-model="form.userRole"
              :style="{ width: '160px' }"
              placeholder="Select"
              :trigger-props="{ autoFitPopupMinWidth: true }"
            >
              <a-option value="admin">管理员</a-option>
              <a-option value="student">学生</a-option>
              <a-option value="teacher">老师</a-option>
            </a-select>
          </a-form-item>
          <a-form-item content-flex>
            <div style="width: 100%; justify-content: right; display: flex">
              <!-- 更新 -->
              <a-space>
                <a-button size="large" @click="visible = false">取消</a-button>
                <a-button
                  html-type="submit"
                  :loading="btnLoading"
                  type="primary"
                  size="large"
                  >修改</a-button
                >
              </a-space>
            </div>
          </a-form-item>
        </a-form>
      </div>
    </a-modal>
  </div>
</template>

<style scoped lang="scss">
#manageQuestionView {
  width: 1200px;
  max-width: 1280px;
  margin: 40px auto;
}
</style>
