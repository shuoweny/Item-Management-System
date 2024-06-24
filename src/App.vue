<template>
  <div id="app">
    <link rel="icon" href="@/assets/logo.svg" />
    <!-- <div class="background">
      <img src="./assets/image/background.svg" alt="">
    </div> -->

    <RouterView />
  </div>
</template>

<script setup>
import { onMounted } from "vue";

onMounted(() => {
  document.body.style.setProperty("--el-color-danger", "#ffaaaa");
});
</script>

<style scoped>
html,
body,
#app {
  /* width: 100%;
  height: 100%; */
  /* background: url("./assets/image/background.svg"); */
  /* background-color: aqua; */
  /* background-size: cover; */
  height: 100%;
  position: fixed;
  width: 100%;
}
</style>
