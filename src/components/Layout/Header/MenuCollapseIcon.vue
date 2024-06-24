<template>
  <el-icon class="collapseIcon" @click="menuStore().setCollapse()">
    <component :is="isCollapse ? 'expand' : 'fold'"></component>
  </el-icon>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { menuStore } from "@/stores/menu";
const isCollapse = computed((): boolean => menuStore().isCollapse);
</script>

<style scoped>
.collapseIcon {
  margin-right: 20px;
  font-size: 165%;
  cursor: pointer;
}
</style>
