<template>
  <div class="">
    <el-dropdown trigger="click">
      <div class="avatar">
        <el-avatar
          :fit="'cover'"
          :src="'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'"
        ></el-avatar>
      </div>
      <template #dropdown>
        <el-dropdown-menu>
          <!-- <el-dropdown-item @click="SettingDialog = true">
            <el-icon><Setting /></el-icon>
            Settings
          </el-dropdown-item> -->

          <el-dropdown-item @click="LogOutDialog = true">
            <el-icon><SwitchButton /></el-icon>
            Logout
          </el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
    <!-- <el-dialog v-model="SettingDialog" :before-close="handleClose">
      <SettingForm></SettingForm>
    </el-dialog> -->
    <el-dialog v-model="LogOutDialog" :before-close="handleClose">
      <!-- <span>This is a log out message</span> -->
      <span>Are you sure you want to log out?</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="LogOutDialog = false">Cancel</el-button>
          <el-button type="primary" @click="logout">Confirm</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<script setup lang="ts">
import { ref } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import router from "@/router";
const LogOutDialog = ref<boolean>(false);
const logout = async () => {
  await localStorage.clear();
  ElMessage.success("You have been successfully logged out");
  router.push("/");
};
const handleClose = (done: () => void) => {
  ElMessageBox.confirm("Are you sure to close this dialog?")
    .then(() => {
      done();
    })
    .catch(() => {
      // catch error
    });
};
</script>
<style></style>
