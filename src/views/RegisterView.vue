<template>
  <div class="container" :class="{ mode: logStore().isRegister }">
    <div class="loginContainer">
      <div class="login">
        <div class="login-content">
          <h1 class="login-content-title ml15">Login</h1>
          <LoginForm />
        </div>
      </div>
      <div class="register">
        <div class="register-content">
          <h1 class="register-content-title ml15">Register</h1>
          <RegisterForm />
        </div>
      </div>
    </div>
    <div class="animeContainer">
      <!--left anime-->
      <div class="panel leftAnime">
        <div class="content">
          <h3>Findme</h3>
          <button @click="logStore().isRegister = true" class="btn transparent">
            SIGN UP
          </button>
        </div>
        <img class="registerImage" src="../assets/image/register.svg" alt="" />
      </div>
      <!--right anime-->
      <div class="panel rightAnime">
        <div class="content">
          <button
            @click="logStore().isRegister = false"
            class="btn transparent"
          >
            LOG IN
          </button>
        </div>
        <img class="loginImage" src="../assets/image/login.svg" alt="" />
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import RegisterForm from "../components/login-register/RegisterForm.vue";
// import logoMini from '/@/assets/logo-mini.svg';
// import loginIconTwo from '/@/assets/login-icon-two.svg';
import LoginForm from "../components/login-register/LoginForm.vue";
import { logStore } from "@/stores/logSwitch";
</script>

<style scoped lang="scss">
// .login-container {

.container {
  position: relative;
  width: 100%;
  height: 100%;
  min-height: 90vh;
  background-color: rgba(143, 96, 35, 0.5);
  overflow-y: scroll;
  overflow-x: hidden;
}
.loginContainer {
  position: absolute;
  width: 100%;
  height: 100%;
}
.logIn_logOut {
  /* border: 5px solid var(--el-color-primary-light-8); */
}
.registerImage {
  width: 90%;
}
.loginImage {
  width: 100%;
  nav-down: auto;
}
/* .register{
  background-color: #139ab1;
  border: 5px solid var(--el-color-primary-light-8);
} */
.login {
  position: absolute;
  padding: 2%;
  top: 60%;
  left: 75%;
  height: auto;
  transform: translate(-50%, -50%);
  width: 30%;
  color: rgb(212, 156, 59);
  transition: 1s 0.5s ease-in-out;
  display: grid;
  grid-template-columns: 1fr;
  z-index: 5;
  text-align: center;
  background-color: rgb(30, 28, 33, 0.7);
  box-shadow: 0 5px 10px #cccc;
  overflow: hidden;
}
.register {
  position: absolute;
  padding: 2%;
  top: 40%;
  right: 30%;
  height: auto;
  transform: translate(-50%, -50%);
  width: 35%;
  color: rgb(212, 156, 59);
  transition: 1s 0.5s ease-in-out;
  display: grid;
  grid-template-columns: 1fr;
  z-index: 5;
  text-align: center;
  box-shadow: 0 5px 10px #cccc;
  background-color: rgb(30, 28, 33, 0.7);
  overflow: hidden;
}
.btn {
  width: 110px;
  background-color: rgb(30, 28, 33);
  border: none;
  outline: none;
  height: 50px;
  border-radius: 30px;
  color: #fff;
  font-size: 100%;
  font-weight: 550;
  cursor: pointer;
}
.btn:hover {
  color: orange;
}

.animeContainer {
  position: absolute;
  height: 100%;
  width: 100%;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
}

.panel {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  justify-content: space-around;
  text-align: center;
}

.leftAnime {
  transition: transform 1.5s ease-in-out;
  transition-delay: 0.5s;
  padding: 2rem 17% 4rem 10%;
}

.rightAnime {
  transition: transform 1.5s ease-in-out;
  transition-delay: 0.5s;
  padding: 4rem 15% 3rem 10%;
}

.panel .content {
  color: rgb(79, 51, 1);
}

.panel h3 {
  font-weight: 500;
  line-height: 1;
  font-size: 2rem;
}

.rightAnime {
  transform: translateX(100%);
}

.register {
  transform: translateY(150%);
  visibility: hidden;
}

.container.mode .leftAnime {
  transform: translateX(-100%);
}
.container.mode .register {
  transform: translate(-60%, -10%);
  visibility: visible;
}

.container.mode .rightAnime {
  transform: translateX(0%);
}
.container.mode .login {
  transform: translateY(100%);
  visibility: hidden;
}
.login {
  visibility: visible;
}
</style>
