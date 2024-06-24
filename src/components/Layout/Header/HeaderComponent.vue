<template>
  <div class="header">
    <!-- <div class="header-lf flx-center"> -->
    <div class="left">
      <MenuCollapseIcon></MenuCollapseIcon>
      <BreadCrumb></BreadCrumb>
    </div>
    <div class="right">
      <AvatarIconVue></AvatarIconVue>
    </div>

    <!-- </div> -->
  </div>
</template>

<script setup lang="ts">
import MenuCollapseIcon from "./MenuCollapseIcon.vue";
import BreadCrumb from "./BreadCrumb.vue";
import AvatarIconVue from "./avatarRelated/AvatarIcon.vue";
</script>

<style scoped lang="scss">
.header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  height: 100% !important;
  bottom: 0px;
  width: 100%;
  background-color: rgb(231, 217, 207);
  border-bottom: solid #f6f6f6;
  box-sizing: border-box;
}
.left {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.right {
  width: auto;
  height: auto;
  // overflow: hidden;
  cursor: pointer;
  // border-radius: 50%;
  margin: 0 4%;
}
</style>
