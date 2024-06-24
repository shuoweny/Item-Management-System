export default {
  mounted(el) {
    const catSelectorInput = el.querySelector(".catSelector-input");
    const catSelectorMenu = el.querySelector(".catSelector-menu");

    const oInput = catSelectorInput.querySelector("input");
    const oPlaceHolder = catSelectorInput.querySelector("label");
    const oIcon = catSelectorInput.querySelector("span");

    oInput.addEventListener(
      "focus",
      function () {
        oPlaceHolder.style.display = "none";
        oIcon.className = "iconfont icon-search";

        setTimeout(() => {
          catSelectorMenu.style.display = "block";
        }, 200);
      },
      false
    );

    oInput.addEventListener(
      "blur",
      function () {
        oIcon.className = "iconfont icon-arrow-down";
        setTimeout(() => {
          catSelectorMenu.style.display = "none";
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
