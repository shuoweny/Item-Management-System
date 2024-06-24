<template>
  <el-container>
    <el-header>
      <el-row>
        <el-col :span="18">
          <el-form :model="searchForm" label-width="auto" :inline="true">
            <el-form-item label="name">
              <el-input v-model="searchForm.name" />
            </el-form-item>
            <el-form-item label="Storage">
              <el-select v-model="searchForm.storage" clearable>
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
        <div class="addBook">
          <el-button type="primary" @click="addBookDialog = true">
            <el-icon style="vertical-align: middle">
              <Plus />
            </el-icon>
            <span style="vertical-align: middle"> Add Book </span>
          </el-button>
        </div>
      </el-row>
    </el-header>
    <el-main>
      <el-row>
        <el-col
          :xl="6"
          :lg="8"
          :md="12"
          :xs="24"
          v-for="book in data.books"
          :key="book.id"
          offset="1"
        >
          <div class="display-books">
            <bookCard
              :id="book.id"
              :name="book.name"
              :isbn="book.isbn"
              :author="book.author"
              :publisher="book.publisher"
              :category="book.categoryIds"
              :storage="book.storageId"
              :favorite="book.favorite"
              :description="book.description"
              :categoryList="data.categories"
              @submit="handleSubmit"
            />
          </div>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
  <el-dialog
    v-model="addBookDialog"
    :before-close="handleClose"
    title="Add your book here"
    style="max-width: 630px"
  >
    <AddBook @submit="handleSubmit"/>
  </el-dialog>
</template>
<script lang="ts" setup>
import { Apis } from "@/apis";
import { reactive, onBeforeMount, ref } from "vue";
import bookCard from "../components/valueRenderer/bookCard.vue";

import AddBook from "../components/addBook.vue";
import { ElMessage, ElMessageBox } from "element-plus";
import axios, { type AxiosResponse } from "axios";
const addBookDialog = ref(false);
const detailDialog = ref(false);
const data = reactive({ books: [{}], storages: [{}], categories: [{}]});
const searchForm = reactive({
  name: "",
  isbn: "",
  author: "",
  storage: "",
  publisher: "",
  category: "",
  favourite: false,
  description: "",
});
onBeforeMount(async () => {
  data.books.splice(0);
  data.storages.splice(0);
  (await Apis.getBooks()).forEach(async (element) => {
    data.books.push(element);
    console.log(element);
  });
  (await Apis.getStorages()).forEach(async (element) => {
    data.storages.push(element);
    console.log(element);
  });
  data.categories.splice(0);
  (await Apis.getCategories()).forEach(async (element) => {
    data.categories.push(element);
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
    .get("/api/user/books/search/and", {
      params: {
        token: localStorage["lkToken"],
        name: searchForm.name,
        description: searchForm.description,
        author: searchForm.author,
        isbn: searchForm.isbn,
        publisher: searchForm.publisher,
        location: searchForm.storage,
        tag: "",
      },
    })
    .then((res: AxiosResponse) => {
      if (res.status == 200 && res.data.length != 0) {
        data.books.splice(0, data.books.length);
        res.data.forEach(async (element) => {
          data.books.push(element);
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
  addBookDialog.value = false;
  data.books.splice(0);
  (await Apis.getBooks()).forEach(async (element) => {
    data.books.push(element);
    console.log(element);
  });
}
</script>
<style scoped>
.display-books {
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
