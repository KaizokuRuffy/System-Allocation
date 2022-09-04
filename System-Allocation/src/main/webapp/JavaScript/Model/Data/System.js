export class System {
  constructor(
    comp_Id,
    MAC,
    comp_Password,
    available,
    working,
    backup,
    comp_Loc,
    model,
    year
  ) {
    this.comp_Id = comp_Id;
    this.MAC = MAC;
    this.comp_Password = comp_Password;
    if (available === undefined) this.available = "Yes";
    else this.available = available;
    if (working === undefined) this.working = "Yes";
    else this.working = working;
    this.backup = backup;
    this.comp_Loc = comp_Loc;
    this.model = model;
    this.year = year;
  }
}
export class SystemBuilder {
  constructor() {}
  comp_Id;
  MAC;
  comp_Password;
  available;
  working;
  backup;
  comp_Loc;
  model;
  year;

  setId(Id) {
    this.comp_Id = Id;
    return this;
  }
  setMAC(MAC) {
    this.MAC = MAC;
    return this;
  }
  setPassword(Password) {
    this.comp_Password = Password;
    return this;
  }
  setAvailable(available) {
    this.available = available;
    return this;
  }
  setWorking(working) {
    this.working = working;
    return this;
  }
  setBackup(backup) {
    this.backup = backup;
    return this;
  }
  setLoc(loc) {
    this.comp_Loc = loc;
    return this;
  }
  setModel(model) {
    this.model = model;
    return this;
  }
  setYear(year) {
    this.year = year;
    return this;
  }
  getSystem() {
    return new System(
      this.comp_Id,
      this.MAC,
      this.comp_Password,
      this.available,
      this.working,
      this.backup,
      this.comp_Loc,
      this.model,
      this.year
    );
  }
}
