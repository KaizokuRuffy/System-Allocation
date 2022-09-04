import * as M from "../Model/M.js";
import * as V from "../View/V.js";

export class System {
  constructor() {
    this.Model = new M.System();
    this.View = new V.System();
  }

  add() {
    this.View.add(this.Model.add());
  }

  updateStatus(comp) {
    return this.View.updateStatus(this.Model.updateStatus(comp));
  }
  getAll() {
    this.View.getAll(this.Model.getAll());
  }
  getUnallocatedSystems(shift, backup) {
    this.View.getUnallocatedSystems(this.Model.getAll(shift, backup));
  }
}
