<template>
  <div class="catSelector" v-focus>
    <catSelectorInput
      :placeholder="placeholder"
      :value="inputValue"
      @searchOptions="searchOptions"
    />
    <catSelectorMenu
      :data="data"
      @setItemValue="setItemValue"
      :searchValue="searchValue"
    />
  </div>
</template>

<script>
import focus from "../catSelector/directives/focus.js";

import catSelectorInput from "../catSelector/catinput.vue";
import catSelectorMenu from "./menu.vue";

import { reactive, toRefs } from "vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "catSelector",
  props: {
    data: Array,
    placeholder: String,
  },
  directives: {
    focus,
  },
  components: {
    catSelectorInput,
    catSelectorMenu,
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
.catSelector {
  width: 300px;
  position: relative;
}
</style>
