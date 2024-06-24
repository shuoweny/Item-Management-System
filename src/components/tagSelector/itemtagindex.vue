<template>
  <div class="tagSelector" v-focus>
    <tagSelectorInput
      :placeholder="placeholder"
      :value="inputValue"
      @searchOptions="searchOptions"
    />
    <tagSelectorMenu
      :data="data"
      @setItemValue="setItemValue"
      :searchValue="searchValue"
    />
  </div>
</template>

<script>
import focus from "../tagSelector/directives/focus.js";

import tagSelectorInput from "../tagSelector/itemtaginput.vue";
import tagSelectorMenu from "../tagSelector/itemtagmenu.vue";

import { reactive, toRefs } from "vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "tagSelector",
  props: {
    data: Array,
    placeholder: String,
  },
  directives: {
    focus,
  },
  components: {
    tagSelectorInput,
    tagSelectorMenu,
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
.tagSelector {
  width: 300px;
  position: relative;
}
</style>
