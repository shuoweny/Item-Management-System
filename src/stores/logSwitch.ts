import { defineStore } from "pinia";

export interface ILogState {
  isRegister: boolean;
}
export const logStore = defineStore({
  id: "ILogState",
  state: (): ILogState => ({
    isRegister: false,
  }),
  getters: {},
});
