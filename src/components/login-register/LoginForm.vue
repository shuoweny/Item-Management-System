<template>
  <el-form
    ref="loginFormRef"
    :model="loginUser"
    status-icon
    :rules="rules"
    class="loginForm"
    label-width="25%"
    @submit.prevent
  >
    <el-form-item label="Username" prop="username">
      <el-input
        class="inputBar"
        v-model="loginUser.username"
        type="username"
        autocomplete="off"
        placeholder="input username here.."
      />
    </el-form-item>
    <el-form-item label="Password" prop="password">
      <el-input
        class="inputBar"
        v-model="loginUser.password"
        type="password"
        autocomplete="off"
        placeholder="input password here.."
        show-password
      />
    </el-form-item>
    <el-form-item>
      <div class="login-buttons">
        <el-button
          type="primary"
          @click="validateLogin(loginFormRef)"
          class="submitButton"
          native-type="submit"
          size="large"
          >Login
        </el-button>
        <el-button
          @click="resetForm(loginFormRef)"
          class="resetButton"
          size="large"
          >Reset
        </el-button>
      </div>
    </el-form-item>
  </el-form>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import type { FormInstance, FormItemRule } from "element-plus";
import { ElMessageBox } from "element-plus";
import { useRouter } from "vue-router";
import axios from "axios";
import type { AxiosResponse } from "axios";

const validateUsername = (
  rule: FormItemRule,
  value: string,
  callback: (arg0: Error | undefined) => void
) => {
  if (value === "") {
    callback(new Error("Please input the username"));
  } else {
    callback(undefined);
  }
};
const validatePassword = (
  rule: FormItemRule,
  value: string,
  callback: (arg0: Error | undefined) => void
) => {
  if (value === "") {
    callback(new Error("Please input the password"));
  } else {
    callback(undefined);
  }
};
const loginUser = reactive({
  username: "",
  password: "",
});
const rules = reactive({
  username: [
    {
      required: true,
      validator: validateUsername,
      trigger: "blur",
    },
  ],
  password: [
    {
      required: true,
      validator: validatePassword,
      trigger: "blur",
    },
    {
      message: "the password length should keep between 4 and 12",
      trigger: "blur",
      min: 4,
      max: 12,
    },
  ],
});
const loginFormRef = ref<FormInstance>();
const router = useRouter();
const validateLogin = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate((valid) => {
    if (valid) {
      axios
        .get("/api/user/login", {
          params: {
            name: loginUser.username,
            password: loginUser.password,
          },
        })
        .then((res: AxiosResponse) => {
          console.log(res.data.success);
          if (res.data.success) {
            const { success, contents } = res.data;
            if (success) {
              ElMessageBox.alert("Success!", "Tip", {
                confirmButtonText: "Got it",
              });
              console.log("login successful");
              localStorage.setItem("lkToken", contents);
              router.push("/home");
            } else {
              ElMessageBox.alert(contents, "Tip", {
                confirmButtonText: "Got it",
              });
            }
          } else {
            console.log("error submit!");
            ElMessageBox.alert(
              "You are submit incorrect password or account!",
              "Tip",
              {
                confirmButtonText: "Got it",
              }
            );
            return false;
          }
        });
    }
  });
};
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
};
</script>
<style>
@import "@/scss/form.css";
.login-buttons {
  margin-left: 25%;
}
</style>
