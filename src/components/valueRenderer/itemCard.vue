<template>
  <el-card
    class="box-card"
    @click="
      () => {
        InfoDialog = true;
        Apis.addRecent(id);
      }
    "
  >
    <el-row>
      <el-col :span="10">
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
      </el-col>
      <el-col :span="12">
        <div class="information" v-if="windowWidth >= 480">
          Name: {{ name }}
          <p />
          Category: {{ category }}
          <p />
          Location: {{ data.location }}
          <p />
        </div>
      </el-col>
      <el-col :span="2">
        <el-icon
          v-if="data.favorite"
          :size="25"
          class="fav"
          @click.stop="removeFavorites"
          ><StarFilled
        /></el-icon>
        <el-icon v-else :size="25" class="fav" @click.stop="setFavorites"
          ><Star
        /></el-icon>
      </el-col>
    </el-row>
  </el-card>
  <el-dialog
    v-model="InfoDialog"
    :before-close="handleClose"
    title="Item Info"
    style="max-width: 630px"
  >
    <itemInfo
      :id="id"
      :name="name"
      :storage="storage"
      :category="category"
      :description="description"
      :favorite="data.favorite"
      @submit="handleSubmit"
    />
  </el-dialog>
</template>
<script lang="ts" setup>
import axios from "axios";
import type { AxiosResponse } from "axios";
import itemInfo from "@/components/ItemInfo.vue";
import { ref, reactive, onBeforeMount, nextTick } from "vue";
import { Apis } from "@/apis";
import {
  Star,
  StarFilled,
  Picture as IconPicture,
} from "@element-plus/icons-vue";
import { ElMessageBox } from "element-plus";
const windowWidth = document.documentElement.clientWidth;
const InfoDialog = ref(false);
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
    type: String,
    required: true,
  },
  description: {
    type: String,
    required: true,
  },
});

const emits = defineEmits(['submit']);

const data = reactive({
  location: "",
  favorite: false,
  hWidth: 0,
});
onBeforeMount(async () => {
  data.location = await Apis.getStorage(props.storage);
  data.favorite = await Apis.isFavItem(props.id);
});
nextTick(() => {
  data.hWidth = document.documentElement.clientWidth;
  // console.log(document.documentElement.clientWidth);
});
const setFavorites = () => {
  data.favorite = true;
  axios
    .get("/api/user/favorites/add", {
      params: {
        token: localStorage["lkToken"],
        uuid: props.id,
      },
    })
    .then((res: AxiosResponse) => {
      if (res.data.success) {
        console.log("Success!");
      } else {
        console.log("Error! Please try again.");
      }
    });
};

const removeFavorites = () => {
  data.favorite = false;
  axios
    .get("/api/user/favorites/remove", {
      params: {
        token: localStorage["lkToken"],
        uuid: props.id,
      },
    })
    .then((res: AxiosResponse) => {
      if (res.data.success) {
        console.log("Success!");
      } else {
        console.log("Error! Please try again.");
      }
    });
};
const handleClose = (done: () => void) => {
  ElMessageBox.confirm("Are you sure to close this dialog?")
    .then(() => {
      done();
    })
    .catch(() => {
      // catch error
    });
};

const handleSubmit = () => {
  InfoDialog.value = false;
  emits('submit');
};
</script>
<style scoped>
.box-card {
  height: 180px;
  width: 400px;
}
.fav {
  position: relative;
  top: 40%;
  margin-right: 0px;
  color: gold;
}
.box-card .demo-image__placeholder .image-slot {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0px;
  width: 140px;
  height: 140px;
  background: var(--el-fill-color-light);
  color: var(--el-text-color-secondary);
  font-size: 14px;
}
.box-card .demo-image__placeholder .dot {
  animation: dot 2s infinite steps(3, start);
  overflow: hidden;
}
</style>
