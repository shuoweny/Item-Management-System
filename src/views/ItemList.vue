<template>
  <div class="item-container">
    <el-container>
      <el-header>
        <el-row>
          <el-col :span="18">
            <el-form :model="searchForm" label-width="auto" :inline="true">
              <el-form-item label="name">
                <el-input v-model="searchForm.name" />
              </el-form-item>
              <el-form-item label="Storage">
                <el-select v-model="searchForm.storage">
                  <el-option
                    v-for="storage in data.storages"
                    :key="storage.id"
                    :label="storage.location"
                    :value="storage.location"
                  />
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="search()">search</el-button>
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="4">
            <div class="addBook">
              <el-button type="primary" @click="dialog = true">
                <el-icon style="vertical-align: middle">
                  <Plus />
                </el-icon>
                <span style="vertical-align: middle"> Add Item </span>
              </el-button>
            </div>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-row>
          <el-col
            :xl="6"
            :lg="8"
            :md="12"
            :xs="24"
            v-for="book in data.items"
            :key="book.id"
            offset="1"
          >
            <div class="display-items">
              <itemCard
                :id="book.id"
                :name="book.name"
                :category="book.categoryIds"
                :storage="book.storageId"
                :description="book.description"
                @submit="handleSubmit"
              />
            </div>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
    <el-dialog
      v-model="dialog"
      :before-close="handleClose"
      title="Add your item here"
      style="max-width: 630px"
    >
      <AddItem @submit="handleSubmit"/>
    </el-dialog>
  </div>
</template>
<script lang="ts" setup>
import { Apis } from "@/apis";
import { reactive, onBeforeMount, ref } from "vue";
import itemCard from "../components/valueRenderer/itemCard.vue";

import AddItem from "../components/addItem.vue";
import { ElMessage, ElMessageBox } from "element-plus";
import axios, { type AxiosResponse } from "axios";
const dialog = ref(false);
const data = reactive({ items: [{}], storages: [{}] });
const searchForm = reactive({
  name: "",
  storage: "",
  category: "",
  favourite: false,
  description: "",
});
onBeforeMount(async () => {
  data.items.splice(0);
  data.storages.splice(0);
  (await Apis.getItems()).forEach(async (element) => {
    data.items.push(element);
    console.log(element);
  });
  (await Apis.getStorages()).forEach(async (element) => {
    data.storages.push(element);
    console.log(element);
  });
});
const handleClose = (done: () => void) => {
  ElMessageBox.confirm("Are you sure to close this dialog?")
    .then(() => {
      done();
    })
    .catch(() => {
      // catch error
    });
};
const search = async () => {
  axios
    .get("/api/user/items/search/and", {
      params: {
        token: localStorage["lkToken"],
        name: searchForm.name,
        description: searchForm.description,
        location: searchForm.storage,
        tag: "",
      },
    })
    .then((res: AxiosResponse) => {
      if (res.status == 200 && res.data.length != 0) {
        data.items.splice(0, data.items.length);
        res.data.forEach(async (element) => {
          data.items.push(element);
          console.log(element);
        });
        console.log("Success!");
      } else {
        console.log("Error! Please try again.");
        ElMessage.error("No results found!");
      }
    });
};

const handleSubmit = async () => {
  dialog.value = false;
  data.items.splice(0);
  (await Apis.getItems()).forEach(async (element) => {
    data.items.push(element);
    console.log(element);
  });
}

</script>
<style scoped>
.display-items {
  display: flex;
  flex-direction: row;
  height: 200px;
}

.display-info {
  display: flex;
  flex-direction: row;
}
.el-button {
  position: absolute;

  right: 2%;
}
</style>
