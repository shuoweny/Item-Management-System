<template>
  <div class="itemCatSelector" v-focus>
    <itemCatSelectorInput
      :placeholder="placeholder"
      :value="inputValue"
      @searchOptions="searchOptions"
    />
    <itemCatSelectorMenu
      :data="data"
      @setItemValue="setItemValue"
      :searchValue="searchValue"
    />
  </div>
</template>

<script>
import focus from "../itemCatSelector/directives/focus.js";

import itemCatSelectorInput from "../itemCatSelector/itemcatinput.vue";
import itemCatSelectorMenu from "../itemCatSelector/itemcatmenu.vue";

import { reactive, toRefs } from "vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "itemCatSelector",
  props: {
    data: Array,
    placeholder: String,
  },
  directives: {
    focus,
  },
  components: {
    itemCatSelectorInput,
    itemCatSelectorMenu,
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
.itemCatSelector {
  width: 300px;
  position: relative;
}
</style>
