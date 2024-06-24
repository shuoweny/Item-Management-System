<template>
  <el-form :model="form" label-width="auto" class="info">
    <el-form-item label="Name" class="label">
      <el-input v-model="form.name" class="bookName" />
    </el-form-item>
    <el-row>
      <el-col :span="20">
        <el-form-item label="ISBN">
          <el-input v-model="form.isbn" style="width: 100%" />
        </el-form-item>
      </el-col>
      <el-col :span="3" :offset="1">
        <el-button
          type="primary"
          @click="findByIsbn(form.isbn)"
          style="width: 100%"
          >search</el-button
        >
      </el-col>
    </el-row>
    <el-form-item label="Author">
      <el-input v-model="form.author" />
    </el-form-item>
    <el-form-item label="Publisher">
      <el-input v-model="form.publisher" />
    </el-form-item>
    <el-row>
      <el-col :span="21">
        <el-form-item label="Location">
          <el-select v-model="form.storage" style="width: 100%">
            <el-option
              v-for="storage in data.storages"
              :key="storage.id"
              :label="storage.location"
              :value="storage.id"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-popover placement="bottom" :width="300" trigger="click">
        <template #reference>
          <el-col :span="2" :offset="1">
            <el-button type="primary" :icon="Plus"></el-button>
          </el-col>
        </template>
        <el-form>
          <el-form-item label="name">
            <el-input v-model="LocationForm.name" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="addNewLocation()">Add</el-button>
          </el-form-item>
        </el-form>
      </el-popover>
    </el-row>
    <el-row>
      <el-col :span="21">
        <el-form-item label="Category">
          <el-select v-model="form.categories" style="width: 100%" multiple>
            <el-option
              v-for="category in data.categories"
              :key="category.id"
              :label="category.name"
              :value="category.id"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-popover placement="bottom" :width="300" trigger="click">
        <template #reference>
          <el-col :span="2" :offset="1">
            <el-button type="primary" :icon="Plus"></el-button>
          </el-col>
        </template>
        <el-form>
          <el-form-item label="name">
            <el-input v-model="categoryForm.name" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="addNewCategory()">Add</el-button>
          </el-form-item>
        </el-form>
      </el-popover>
    </el-row>
    <el-form-item label="Description">
      <el-input type="textarea" v-model="form.description" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit()">Submit</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts" setup>
import axios from "axios";
import type { AxiosResponse } from "axios";
import { Plus } from "@element-plus/icons-vue";
import { ElMessage as elMessage } from "element-plus";
import { reactive, onBeforeMount } from "vue";
import { Apis } from "@/apis";
import { fromPairs } from "lodash";

const data = reactive({
  categories: [{}],
  storages: [{}],
});
// do not use same name with ref
const form = reactive({
  name: "",
  isbn: "",
  author: "",
  storage: "",
  publisher: "",
  categories: [""],
  favourite: false,
  description: "",
});

const emits = defineEmits(['submit']);

onBeforeMount(async () => {
  data.storages.pop();
  data.categories.pop();
  (await Apis.getStorages()).forEach(async (element) => {
    data.storages.push(element);
    console.log(element);
  });
  (await Apis.getCategories()).forEach(async (element) => {
    data.categories.push(element);
    console.log(element);
  });
});

const categoryForm = reactive({
  name: "",
});

const addNewCategory = async () => {
  axios
    .get("/api/user/categories/add", {
      params: {
        token: localStorage["lkToken"],
        name: categoryForm.name,
      },
    })
    .then((res: AxiosResponse) => {
      if (res.data.success) {
        console.log("Success!");
      } else {
        console.log("Error! Please try again.");
      }
    });
  data.categories.splice(0, data.categories.length);
  (await Apis.getCategories()).forEach((element) => {
    data.categories.push(element);
  });
};

const LocationForm = reactive({
  name: "",
});

const addNewLocation = async () => {
  axios
    .get("/api/user/storages/add", {
      params: {
        token: localStorage["lkToken"],
        location: LocationForm.name,
      },
    })
    .then((res: AxiosResponse) => {
      if (res.data.success) {
        console.log("Success!");
      } else {
        console.log("Error! Please try again.");
      }
    });
  data.storages.splice(0, data.storages.length);
  (await Apis.getStorages()).forEach((element) => {
    data.storages.push(element);
  });
};

/**
 * Find a book from its isbn
 * @param {string} isbn its isbn
 */
async function findByIsbn(isbn: string) {
  const url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
  const result = await (fetch(url).then((response) => response.json()));
  const book = result.items[0];
  form.name = book.volumeInfo.title;
  form.author = book.volumeInfo.authors;
  form.publisher = book.volumeInfo.publisher;
  form.description = book.volumeInfo.description;
}

const onSubmit = async () => {
  axios
    .get("/api/user/books/add", {
      params: {
        token: localStorage["lkToken"],
        name: form.name,
        description: form.description,
        author: form.author,
        publishedDate: "1970/1/1",
        isbn: form.isbn,
        publisher: form.publisher,
        storageId: form.storage,
        categoryIds: form.categories + '',
      },
    })
    .then((res: AxiosResponse) => {
      if (res.data.success) {
        console.log("Success!");
        elMessage({
          message: 'Success!',
          type: 'success',
        })
        emits('submit');
      } else {
        console.log("Error! Please try again.");
        elMessage.error("Error! check if there are any invalid input and try again.");
      }
    });
};
</script>
<style>
.addBookCard {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 500px;
  height: auto;
}
</style>
