<template>
  <div class="authorSelector" v-focus>
    <authorSelectorInput
      :placeholder="placeholder"
      :value="inputValue"
      @searchOptions="searchOptions"
    />
    <!-- <authorSelectorMenu
         :data="data"
         @setItemValue="setItemValue"
         :searchValue="searchValue"
       />  -->
  </div>
</template>

<script>
import focus from "../authorSelector/directives/focus.js";

import authorSelectorInput from "../authorSelector/authorinput.vue";
// import authorSelectorMenu from "./menu.vue";

import { reactive, toRefs } from "vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "authorSelector",
  props: {
    data: Array,
    placeholder: String,
  },
  directives: {
    focus,
  },
  components: {
    authorSelectorInput,
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
.authorSelector {
  width: 300px;
  position: relative;
}
</style>
