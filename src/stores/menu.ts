import { defineStore } from "pinia";

export interface IMenuState {
  isCollapse: boolean;
}
export const menuStore = defineStore({
  id: "IMenuState",
  state: (): IMenuState => ({
    isCollapse: false,
  }),
  getters: {},
  actions: {
    async setCollapse() {
      this.isCollapse = !this.isCollapse;
    },
  },
});
