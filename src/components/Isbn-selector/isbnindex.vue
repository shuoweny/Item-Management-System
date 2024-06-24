<template>
  <div class="isbnSelector" v-focus>
    <isbnSelectorInput
      :placeholder="placeholder"
      :value="inputValue"
      @searchOptions="searchOptions"
    />
    <!-- <isbnSelectorMenu
      :data="data"
      @setItemValue="setItemValue"
      :searchValue="searchValue"
    /> -->
  </div>
</template>

<script>
import focus from "../Isbn-selector/directives/focus.js";

import isbnSelectorInput from "../Isbn-selector/isbninput";
// import isbnSelectorMenu from "../components/Isbn-selector/menu.vue";

import { reactive, toRefs } from "vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "isbnSelector",
  props: {
    data: Array,
    placeholder: String,
  },
  directives: {
    focus,
  },
  components: {
    isbnSelectorInput,
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
.isbnSelector {
  width: 300px;
  position: relative;
}
</style>
