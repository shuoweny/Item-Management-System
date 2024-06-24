<template>
  <el-form
    ref="registFormRef"
    :model="registUser"
    status-icon
    :rules="registerRules"
    label-width="25%"
    class="registForm"
    @submit.prevent
    style="width: 100%; height: 45%"
  >
    <el-form-item label="Username" prop="username">
      <el-input
        v-model="registUser.username"
        type="username"
        class="inputBar"
        autocomplete="off"
      />
    </el-form-item>
    <el-form-item label="Email" prop="email">
      <el-input
        v-model="registUser.email"
        type="email"
        class="inputBar"
        autocomplete="off"
      />
    </el-form-item>
    <el-form-item label="Password" prop="password">
      <el-input
        v-model="registUser.password"
        type="password"
        class="inputBar"
        autocomplete="off"
        show-password
      />
    </el-form-item>
    <el-form-item
      label="Confirm"
      prop="confirmPassword"
      class="comfirmPassword"
    >
      <el-input
        v-model="registUser.confirmPassword"
        type="password"
        autocomplete="off"
        class="inputBar"
        placeholder="comfirm your password here.."
        show-password
      />
    </el-form-item>
    <el-form-item>
      <div class="register-buttons">
        <el-button
          type="primary"
          @click="submitForm(registFormRef)"
          class="submitButton"
          native-type="submit"
          size="large"
          >Register
        </el-button>
        <el-button
          @click="resetForm(registFormRef)"
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
// import { useRouter } from "vue-router";
import axios from "axios";
import type { AxiosResponse } from "axios";
import { logStore } from "@/stores/logSwitch";
const registFormRef = ref<FormInstance>();

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
// const router = useRouter();
const validateEmail = (
  rule: FormItemRule,
  value: string,
  callback: (arg0: Error | undefined) => void
) => {
  if (value === "") {
    callback(new Error("Please input the email"));
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
    if (registUser.confirmPassword !== "") {
      if (!registFormRef.value) return;
      registFormRef.value.validateField("confirmPassword", () => null);
    }
    callback(undefined);
  }
};
const validateComfirmPassword = (
  rule: FormItemRule,
  value: string,
  callback: (arg0: Error | undefined) => void
) => {
  if (value === "") {
    callback(new Error("Please input the password again"));
  } else if (value !== registUser.password) {
    callback(new Error("Two inputs don't match!"));
  } else {
    callback(undefined);
  }
};

const registUser = reactive({
  username: "",
  email: "",
  password: "",
  confirmPassword: "",
});

const registerRules = reactive({
  username: [
    {
      required: true,
      validator: validateUsername,
      trigger: "blur",
    },
    {
      min: 2,
      max: 16,
      message: "The username length should keep between 2 and 16",
    },
  ],
  email: [
    {
      required: true,
      validator: validateEmail,
      trigger: "blur",
    },
    {
      type: "email",
      message: "Please input right email address",
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
      min: 4,
      max: 12,
      message: "the password length should keep between 4 and 12",
    },
  ],
  confirmPassword: [
    {
      required: true,
      validator: validateComfirmPassword,
      trigger: "blur",
    },
  ],
});
const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate((valid) => {
    if (valid) {
      axios
        .get("/api/user/register", {
          params: {
            name: registUser.username,
            password: registUser.password,
          },
        })
        .then((res: AxiosResponse) => {
          if (res.status == 200) {
            const { success, contents } = res.data;
            if (success) {
              ElMessageBox.alert(
                "Regist success! You are able to log in",
                "Tip",
                {
                  confirmButtonText: "Got it",
                  callback: () => {
                    logStore().isRegister = false;
                  },
                }
              );

              console.log("Regist success! You are able to log in");
              localStorage.setItem("lkToken", contents);
              // router.push("/home");
            } else {
              ElMessageBox.alert(contents, "Tip", {
                confirmButtonText: "Got it",
              });
            }
          } else {
            console.log("regist error!");
          }
        });
    } else {
      console.log("error submit!");
      return false;
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
.register-buttons {
  display: flex;
  /* justify-content:space-between; */
  flex-direction: row;
  /* width: 50%; */
  margin-left: 10%;
  /* margin-top: 40px; */
  /* white-space: nowrap; */
}
</style>
