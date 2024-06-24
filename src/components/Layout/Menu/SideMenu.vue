<template>
  <div class="sideMenu" :style="{ width: isCollapse ? '60px' : '220px' }">
    <!-- <el-col> -->
    <div class="logo" v-if="isCollapse === false">
      <img class="logoImage" src="@/assets/image/logo.svg" />
      <br />
      Findme
    </div>
    <!-- </el-col> -->

    <!-- <el-col> -->
    <el-scrollbar>
      <el-menu
        :default-active="route.path"
        :style="{ width: isCollapse ? '60px' : '220px' }"
        :router="true"
        :collapse="isCollapse"
        :collapse-transition="false"
        :unique-opened="true"
        text-color="#fcfbf9"
        text-size="500px"
        active-text-color="#e75c05"
      >
        <el-menu-item index="/home">
          <el-icon><home-filled /></el-icon>
          <span>Home</span>
        </el-menu-item>
        <el-menu-item index="/home/myBooks">
          <el-icon><Management /></el-icon>
          <span>My Books</span>
        </el-menu-item>
        <el-menu-item index="/home/myItems">
          <el-icon><Grid /></el-icon>
          <span>My Items</span>
        </el-menu-item>
      </el-menu>
    </el-scrollbar>
    <!-- </el-col> -->
  </div>
</template>

<style scoped></style>
<script lang="ts" setup>
import { HomeFilled, Management } from "@element-plus/icons-vue";
import { computed, ref } from "vue";
import { useRoute } from "vue-router";
import { menuStore } from "@/stores/menu";

const route = useRoute();
const isCollapse = computed((): boolean => menuStore().isCollapse);
// const handleOpen = (key: string, keyPath: string[]) => {
//   console.log(key, keyPath);
// };
// const handleClose = (key: string, keyPath: string[]) => {
//   console.log(key, keyPath);
// };

const screenWidth = ref<number>(0);
const screenHeight = ref<number>(0);

const listeningWindow = () => {
  window.onresize = () => {
    return (() => {
      screenHeight.value = document.body.clientHeight;
      screenWidth.value = document.body.clientWidth;
      if (screenWidth.value < 1200 && isCollapse.value === false) {
        menuStore().setCollapse();
      }
      if (screenWidth.value > 1200 && isCollapse.value === true) {
        menuStore().setCollapse();
      }
    })();
  };
};
listeningWindow();
</script>

<style>
.sideMenu {
  display: flex;
  flex-direction: column;
  background-color: #7e6943;
}

.el-menu {
  display: flex;
  flex-direction: column;
  position: fixed;
  height: 100%;
  background-color: #7e6943;
  border: none !important;
}
.logo {
  font-size: 200%;
  font-weight: bold;
  color: #fcfbf9;
  white-space: nowrap;
  text-align: center;
  height: 25%;
  background-color: #7e6943;
  margin-left: -2px;
}
.logoImage {
  width: 80%;
}
</style>
