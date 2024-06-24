import Search from "./components/Selector";

const JsppUI = {};

JsppUI.install = function (Vue) {
  Vue.component(Search.name, Search);
};

export default JsppUI;
