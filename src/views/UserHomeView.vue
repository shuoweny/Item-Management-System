<template>
  <el-container>
    <el-header>
      <h1>Hello, {{ data.name }}</h1>
    </el-header>
    <el-main>
      <h2>Favorite</h2>
      <el-row>
        <el-col
          :xl="6"
          :lg="8"
          :md="12"
          :xs="24"
          v-for="book in data.fav"
          :key="book.id"
          offset="1"
        >
          <div class="display-books">
            <itemCard
              :id="book.id"
              :name="book.name"
              :category="book.categoryIds"
              :storage="book.storageId"
              :favorite="book.favorite"
              :description="book.description"
            />
          </div>
        </el-col>
      </el-row>
      <h2>Recent</h2>
      <el-row>
        <el-col
          :xl="6"
          :lg="8"
          :md="12"
          :xs="24"
          v-for="item in data.recents"
          :key="item.id"
          offset="1"
        >
          <div class="display-books">
            <itemCard
              :id="item.id"
              :name="item.name"
              :category="item.categoryIds"
              :storage="item.storageId"
              :favorite="item.favorite"
              :description="item.description"
            />
          </div>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script lang="ts" setup>
import { Apis } from "@/apis";
import { reactive, onBeforeMount } from "vue";
const data = reactive({
  name: "",
  fav: [{}],
  recents: [{}],
  items: [{}],
});
onBeforeMount(async () => {
  data.name = (await Apis.getUserInfo()).name;
  data.fav.splice(0, data.fav.length);
  data.recents.splice(0, data.recents.length);
  data.items.splice(0, data.items.length);
  (await Apis.getItems()).forEach(async (item) => {
    data.items.push(item);
  });
  Apis.getRecentItems().forEach(async (item) => {
    if (item != null) {
      const result = data.items.find(({ id }) => id === item);
      data.recents.push(result);
    }
  });
  (await Apis.getFavItems()).forEach(async (book) => {
    data.fav.push(book);
  });
});
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
