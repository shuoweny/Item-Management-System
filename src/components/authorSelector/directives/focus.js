export default {
  mounted(el) {
    const authorSelectorInput = el.querySelector(".authorSelector-input");
    const authorSelectorMenu = el.querySelector(".authorSelector-menu");

    const oInput = authorSelectorInput.querySelector("input");
    const oPlaceHolder = authorSelectorInput.querySelector("label");
    const oIcon = authorSelectorInput.querySelector("span");

    oInput.addEventListener(
      "focus",
      function () {
        oPlaceHolder.style.display = "none";
        oIcon.className = "iconfont icon-search";

        setTimeout(() => {
          authorSelectorMenu.style.display = "block";
        }, 200);
      },
      false
    );

    oInput.addEventListener(
      "blur",
      function () {
        oIcon.className = "iconfont icon-arrow-down";
        setTimeout(() => {
          authorSelectorMenu.style.display = "none";
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
