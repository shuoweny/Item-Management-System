<template>
  <div class="selector" v-focus>
    <SelectorInput
      :placeholder="placeholder"
      :value="inputValue"
      @searchOptions="searchOptions"
    />
    <!-- <SelectorMenu
      :data="data"
      @setItemValue="setItemValue"
      :searchValue="searchValue"
    /> -->
  </div>
</template>

<script>
import focus from "../Selector/directives/focus.js";
import SelectorInput from "../Selector/booknameinput.vue";
// import SelectorMenu from "../components/Selector/menu.vue";

import { reactive, toRefs } from "vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Selector",
  props: {
    data: Array,
    placeholder: String,
  },
  directives: {
    focus,
  },
  components: {
    SelectorInput,
    // SelectorMenu,
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
.selector {
  width: 300px;
  position: relative;
}
</style>
