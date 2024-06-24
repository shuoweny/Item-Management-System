<template>
  <div class="avarta">
    <el-avatar
      :fit="'cover'"
      :size="100"
      :src="'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'"
    ></el-avatar>
  </div>
  <el-form :model="form" label-width="auto">
    <el-form-item label="UserName" class="label">
      <el-input v-model="form.name"> form.name</el-input>
    </el-form-item>
    <el-row>
      <el-col>
        <el-form-item label="Password">
          <el-input v-model="form.name" />
        </el-form-item>
      </el-col>
    </el-row>
    <el-col>
      <el-form-item>
        <el-button type="primary" @click="onSubmit()">update</el-button>
      </el-form-item>
    </el-col>
  </el-form>
</template>

<script lang="ts" setup>
import axios from "axios";
import type { AxiosResponse } from "axios";
import { reactive } from "vue";

// do not use same name with ref
const form = reactive({
  name: "",
  avarta: "",
  profile: "",
});

const onSubmit = async () => {
  axios
    .get("/api/user/info/get", {
      params: {
        token: localStorage["lkToken"],
        info: form.name,
      },
    })

    .then((res: AxiosResponse) => {
      if (res.data.success) {
        console.log("Success!");
      } else {
        console.log("Error! Please try again.");
      }
    });
  console.log(form.name);
};
</script>
<style>
.avarta {
  text-align: center;
}
</style>
