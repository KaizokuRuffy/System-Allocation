import * as M from "../Model/M.js";
import * as V from "../View/V.js";

export class User {
  constructor() {
    this.Model = new M.User();
    this.View = new V.User();
  }
  firstLogin() {
    this.View.firstLogin(this.Model.login("-1"));
  }
  login(comp_Id) {
    this.View.login(this.Model.login(comp_Id));
  }
  logout() {
    this.View.logout(this.Model.logout());
  }
  add() {
    this.View.add(this.Model.add());
  }
  get() {
    this.View.get(this.Model.get());
  }
  getPlus(emp_Id) {
    this.View.getPlus(this.Model.getPlus(emp_Id));
  }
  getAll() {
    this.View.getAll(this.Model.getAll());
  }
}
