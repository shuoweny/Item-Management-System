<template>
  <el-form :model="form" label-width="auto">
    <el-form-item label="Name" class="label">
      <el-input v-model="form.name" />
    </el-form-item>
    <el-form-item label="Favourite">
      <el-switch v-model="form.favourite" />
    </el-form-item>
    <el-row>
      <el-col :span="21">
        <el-form-item label="Category">
          <el-select v-model="form.Category" style="width: 100%" multiple>
            <el-option
              v-for="category in data.categories"
              :key="category.id"
              :label="category.name"
              :value="category.id"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="2" :offset="1">
        <el-button type="primary" :icon="Plus" @click="addNewLocation()"></el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="21">
        <el-form-item label="Location">
          <el-select v-model="form.Location" style="width: 100%">
            <el-option
              v-for="storage in data.storages"
              :key="storage.id"
              :label="storage.location"
              :value="storage.id"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="2" :offset="1">
        <el-button type="primary" :icon="Plus" @click="addNewCategory()"></el-button>
      </el-col>
    </el-row>
    <el-form-item>
      <el-button type="primary" @click="onSubmit()">Add Item</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts" setup>
import axios from "axios";
import type { AxiosResponse } from "axios";
import { Plus } from "@element-plus/icons-vue";
import { reactive, onBeforeMount } from "vue";
import { Apis } from "@/apis";
import { ElMessage as elMessage } from "element-plus";

// do not use same name with ref
const form = reactive({
  name: "",
  Location: "",
  Category: [""],
  favourite: false,
  description: "",
});

const data = reactive({
  storages: [{}],
  categories: [{}],
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

const onSubmit = async () => {
  axios
    .get("/api/user/items/add", {
      params: {
        token: localStorage["lkToken"],
        name: form.name,
        description: form.description,
        storageId: form.Location,
        categoryIds: form.Category + '',
      },
    })
    .then((res: AxiosResponse) => {
      if (res.data.success) {
        console.log("Success!");
        elMessage({
          message: 'Success!',
          type: 'success',
        });
        emits('submit');
      } else {
        console.log("Error! Please try again.");
        elMessage.error('Error! Check if there is a invalid input and try again.');
      }
    });
};
</script>
<style></style>
