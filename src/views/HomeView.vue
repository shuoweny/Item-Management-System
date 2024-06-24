<template>
  <div class="homeView">
    <div class="title">
      <h3>Findme</h3>
      <router-link to="/register" class="loginButton transparent"
        >Log In</router-link
      >
    </div>
  </div>
</template>
<script lang="ts">
// import { RouterLink } from "vue-router";
export default {
  name: "HomeView",
  components: {},
};
</script>
<style scoped>
.homeView {
  width: 100%;
  height: 100%;
  background: url("../assets/image/bg.svg");
  background-size: cover;
  position: absolute;
  z-index: 0;
}
.backgroundImage {
  width: 100%;
  position: absolute;
  z-index: 0;
}

a {
  text-decoration: none;
  color: rgb(73, 52, 17);
}

.title {
  color: rgb(73, 52, 17);
  z-index: 2;
  text-align: center;
  height: 100%;
  font-size: 45px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.loginButton {
  text-transform: uppercase;
  border: 3px solid rgb(53, 39, 15);
  border-radius: 5px;
  font-size: 30px;
  padding-left: 30px;
  padding-right: 30px;
  padding-top: 10px;
  padding-bottom: 10px;
  z-index: 2;
}

.loginButton:hover {
  backdrop-filter: blur(10px);
}
</style>
