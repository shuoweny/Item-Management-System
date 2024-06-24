export default {
  mounted(el) {
    const tagSelectorInput = el.querySelector(".tagSelector-input");
    const tagSelectorMenu = el.querySelector(".tagSelector-menu");

    const oInput = tagSelectorInput.querySelector("input");
    const oPlaceHolder = tagSelectorInput.querySelector("label");
    const oIcon = tagSelectorInput.querySelector("span");

    oInput.addEventListener(
      "focus",
      function () {
        oPlaceHolder.style.display = "none";
        oIcon.className = "iconfont icon-search";

        setTimeout(() => {
          tagSelectorMenu.style.display = "block";
        }, 200);
      },
      false
    );

    oInput.addEventListener(
      "blur",
      function () {
        oIcon.className = "iconfont icon-arrow-down";
        setTimeout(() => {
          tagSelectorMenu.style.display = "none";
          // eslint-disable-next-line no-invalid-this
          if (this.value.length === 0) {
            oPlaceHolder.style.display = "block";
          }
        }, 200);
      },
      false
    );
  },
};
