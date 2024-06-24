<template>
  <div class="demo-image__placeholder">
    <el-image class="image-slot" fit="contain" lazy>
      <template #placeholder>
        <div class="image-slot">Loading<span class="dot">...</span></div>
      </template>
      <template #error>
        <div class="image-slot">
          <el-icon :size="25"><icon-picture /></el-icon>
        </div>
      </template>
    </el-image>
  </div>
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
      <el-popover
        placement="bottom"
        title="Title"
        :width="300"
        :visible="addLocation"
      >
        <template #reference>
          <el-col :span="2" :offset="1">
            <el-button
              type="primary"
              :icon="Plus"
              @click="addLocation = true"
            ></el-button>
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
          <el-select v-model="form.category" style="width: 100%">
            <el-option
              v-for="category in data.categories"
              :key="category.id"
              :label="category.name"
              :value="category.id"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-popover
        placement="bottom"
        title="Title"
        :width="300"
        :visible="addCategory"
      >
        <template #reference>
          <el-col :span="2" :offset="1">
            <el-button
              type="primary"
              :icon="Plus"
              @click="addCategory = true"
            ></el-button>
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
      <el-input
        type="textarea"
        v-model="form.description"
        :value="description"
      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit()">Submit</el-button>
      <el-button type="danger" @click="onDelete()">Delete book</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts" setup>
import axios from "axios";
import type { AxiosResponse } from "axios";
import { ref, reactive, onBeforeMount } from "vue";
import { Apis } from "@/apis";
import { ElMessage as elMessage } from "element-plus";
import { Plus, Picture as IconPicture } from "@element-plus/icons-vue";
const addLocation = ref(false);
const addCategory = ref(false);
const props = defineProps({
  id: {
    type: String,
    required: true,
  },
  name: {
    type: String,
    required: true,
  },
  storage: {
    type: String,
    required: true,
  },
  category: {
    type: [String],
    required: true,
  },
  description: {
    type: String,
    required: true,
  },
});

const emits = defineEmits(['submit']);

const form = reactive({
  name: props.name,
  storage: props.storage,
  category: props.category,
  description: props.description,
});

const data = reactive({
  storages: [{}],
  categories: [{}],
});
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

// do not use same name with ref

const categoryForm = reactive({
  name: "",
});

const addNewCategory = async () => {
  addCategory.value = false;
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
  addLocation.value = false;
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
    .get("/api/user/items/update", {
      params: {
        id: props.id,
        token: localStorage["lkToken"],
        name: form.name,
        description: form.description,
        storageId: form.storage,
        category: form.category,
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
      }
    });
};

const onDelete = async () => {
  axios
    .get("/api/user/items/delete", {
      params: {
        token: localStorage["lkToken"],
        id: props.id,
      },
    })
    .then((res: AxiosResponse) => {
      if (res.data.success) {
        console.log("Success!");
        elMessage({
          message: 'success',
          type: 'success',
        });
        emits('submit');
      } else {
        console.log("Error! Please try again.");
        elMessage.error("Connection error! Please try again");
      }
    });
};
</script>
<style scoped>
.demo-image__placeholder {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 220px;
}
.demo-image__placeholder .image-slot {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0px;
  width: 200px;
  height: 200px;
  background: var(--el-fill-color-light);
  color: var(--el-text-color-secondary);
  font-size: 14px;
}
.demo-image__placeholder .dot {
  animation: dot 2s infinite steps(3, start);
  overflow: hidden;
}

.BookInfoCard {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 500px;
  height: auto;
}
</style>
