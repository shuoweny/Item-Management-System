export default {
  mounted(el) {
    const isbnSelectorInput = el.querySelector(".isbnSelector-input");
    const isbnSelectorMenu = el.querySelector(".isbnSelector-menu");

    const oInput = isbnSelectorInput.querySelector("input");
    const oPlaceHolder = isbnSelectorInput.querySelector("label");
    const oIcon = isbnSelectorInput.querySelector("span");

    oInput.addEventListener(
      "focus",
      function () {
        oPlaceHolder.style.display = "none";
        oIcon.className = "iconfont icon-search";

        setTimeout(() => {
          isbnSelectorMenu.style.display = "block";
        }, 200);
      },
      false
    );

    oInput.addEventListener(
      "blur",
      function () {
        oIcon.className = "iconfont icon-arrow-down";
        setTimeout(() => {
          isbnSelectorMenu.style.display = "none";
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
