<script setup lang="ts">
import { useRouter } from "vue-router";
import { Message } from "@arco-design/web-vue";
import { PostControllerService } from "../../../generated";
import { ref } from "vue";
import dayjs from "dayjs";

const router = useRouter();

// 回到全部题解
const backJump = () => {
  router.back();
};

// 复制连接
const copyLink = () => {
  navigator.clipboard
    .writeText(window.location.href)
    .then(() => {
      Message.success("复制链接成功~");
    })
    .catch(() => {
      Message.error("复制链接失败~");
    });
};

// 获取帖子详情
const solutionInfo = ref();
const createTime = ref();
const getSolutionInfo = async () => {
  const id = BigInt(router.currentRoute.value.params.solutionId.toString());
  const res = await PostControllerService.getPostVoByIdUsingGet(id as any);
  if (res.code === 20000) {
    solutionInfo.value = res.data;
    console.log(solutionInfo.value);
    createTime.value = dayjs(solutionInfo.value.createTime).format(
      "YYYY/MM/DD"
    );
  } else {
    Message.error(res.message!);
  }
};
getSolutionInfo();
</script>

<template>
  <div>
    <a-card>
      <template #extra>
        <a-link @click="copyLink" :hoverable="false">
          <icon-gift />
        </a-link>
      </template>
      <template #title>
        <a-link @click="backJump" :hoverable="false">
          <a-space>
            <icon-arrow-left />
            全部题解
          </a-space>
        </a-link>
      </template>
      <a-layout v-if="solutionInfo">
        <a-layout-header>
          <!-- 标题 -->
          <a-typography :style="{ marginTop: '-40px' }">
            <a-typography-title> {{ solutionInfo.title }}</a-typography-title>
            <a-typography-paragraph>
              <a-comment :author="solutionInfo.user.userName" datetime="1 hour">
                <template #content>
                  <a-space>
                    <span>
                      <span>
                        <icon-eye />
                      </span>
                      {{ solutionInfo.viewNum }}
                    </span>
                    <span>
                      <icon-calendar />
                      {{ createTime }}
                    </span>
                  </a-space>
                </template>
                <template #avatar>
                  <a-avatar>
                    <img alt="avatar" :src="solutionInfo.user.userAvatar" />
                  </a-avatar>
                </template>
              </a-comment>
            </a-typography-paragraph>
          </a-typography>
        </a-layout-header>
        <a-layout-content>
          <v-md-editor
            :model-value="solutionInfo.content"
            mode="preview"
          ></v-md-editor>
        </a-layout-content>
        <a-layout-footer>
          <!-- todo 评论 -->
        </a-layout-footer>
      </a-layout>
    </a-card>
  </div>
</template>

<style scoped lang="scss"></style>
