<template>
  <div class="common-layout">
    <el-container>
      <el-aside>
        <SideMenu />
      </el-aside>
      <el-container>
        <el-header>
          <HeaderComponent></HeaderComponent>
        </el-header>
        <el-main class="main">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import HeaderComponent from "@/components/Layout/Header/HeaderComponent.vue";
import SideMenu from "../components/Layout/Menu/SideMenu.vue";
</script>
<style scoped>
.common-layout {
  height: 100%;
}
.el-aside {
  width: auto;
}
.el-header {
  padding: 0;
}
</style>
