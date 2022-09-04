import * as M from "../Model/M.js";
import * as V from "../View/V.js";

export class Session {
  constructor() {
    this.Model = new M.Session();
    this.View = new V.Session();
  }
  getAll() {
    this.View.getAll(this.Model.getAll());
  }
  getEmpSession(emp_Id) {
    this.View.getAll(this.Model.getEmpSession(emp_Id));
  }
}
