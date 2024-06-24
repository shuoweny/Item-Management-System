<template>
  <div class="itemCatSelector-menu">
    <template v-if="searchData.length > 0">
      <div
        class="menu-item"
        v-for="(item, index) of searchData"
        :key="index"
        @click="setItemValue(item)"
      >
        {{ item.text }}
      </div>
    </template>
    <itemCatNoDataTip v-else />
  </div>
</template>

<script>
import itemCatNoDataTip from "../itemCatSelector/itemcatNoDataTip.vue";

import { watch, onMounted, ref } from "vue";

export default {
  name: "itemCatSelectorMenu",
  components: {
    itemCatNoDataTip,
  },
  props: {
    searchValue: String,
    data: {
      type: Array,
      default: () => [
        {
          id: 1,
          value: "1",
          text: "option 1",
        },
        {
          id: 2,
          value: "2",
          text: "option 2",
        },
        {
          id: 3,
          value: "3",
          text: "option 3",
        },
      ],
    },
  },
  setup(props, ctx) {
    const searchData = ref([]);

    const setItemValue = (item) => {
      ctx.emit("setItemValue", item);
    };

    onMounted(() => {
      searchData.value = props.data;
    });

    const filterData = () => {
      searchData.value = props.data.filter((item) => {
        return item.text
          .toLowerCase()
          .includes(props.searchValue.toLowerCase());
      });
    };

    watch(
      () => {
        return props.searchValue;
      },
      (value) => {
        filterData(value);
      }
    );

    return {
      setItemValue,
      searchData,
    };
  },
};
</script>

<style lang="scss" scoped>
.itemCatSelector-menu {
  display: none;
  position: absolute;
  left: 0;
  top: 48px;
  width: 100%;
  background-color: #fff;
  box-shadow: 0 0 10px #ddd;
  border-radius: 5px;
  cursor: pointer;

  .menu-item {
    height: 35px;
    text-align: center;
    line-height: 35px;
    font-size: 14px;
    color: #666;
    margin: 10px 0;
    transition: background-color 0.2s linear;

    &:hover {
      background-color: #ededed;
    }
  }
}
</style>
