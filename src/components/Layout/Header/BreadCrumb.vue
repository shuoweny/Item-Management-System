<template>
  <el-breadcrumb :separator-icon="ArrowRight">
    <el-breadcrumb-item :to="{ path: '/home' }">Home</el-breadcrumb-item>
    <el-breadcrumb-item v-for="url in findPath" :key="url.path">{{
      url.meta.title
    }}</el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script lang="ts" setup>
import { ArrowRight } from "@element-plus/icons-vue";
import { computed } from "vue";
import { useRoute } from "vue-router";

const findPath = computed(() =>
  useRoute().matched.filter(
    (url) => url.meta && url.meta.title && url.meta.title != "Home"
  )
);
</script>
<style>
.el-breadcrumb {
  font-size: 130%;
}
</style>
