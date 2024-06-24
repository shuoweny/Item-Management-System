<template>
  <div class="itemSelector" v-focus>
    <itemSelectorInput
      :placeholder="placeholder"
      :value="inputValue"
      @searchOptions="searchOptions"
    />
    <!-- <itemSelectorMenu
        :data="data"
        @setItemValue="setItemValue"
        :searchValue="searchValue"
      /> -->
  </div>
</template>

<script>
import focus from "../itemSelector/directives/focus.js";

import itemSelectorInput from "../itemSelector/itemnameinput.vue";
// import itemSelectorMenu from "./menu.vue";

import { reactive, toRefs } from "vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "itemSelector",
  props: {
    data: Array,
    placeholder: String,
  },
  directives: {
    focus,
  },
  components: {
    itemSelectorInput,
    // itemSelectorMenu,
  },
  setup(props, ctx) {
    const state = reactive({
      inputValue: "",
      searchValue: "",
    });

    const setItemValue = (item) => {
      state.inputValue = item.text;
      ctx.emit("setItemValue", item.value);
    };

    const searchOptions = (value) => {
      state.searchValue = value;
    };

    return {
      setItemValue,
      searchOptions,

      ...toRefs(state),
    };
  },
};
</script>

<style lang="scss" scoped>
.itemSelector {
  width: 300px;
  position: relative;
}
</style>
