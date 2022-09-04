export class Session {
  constructor(
    emp_Id,
    emp_Name,
    comp_Id,
    logIn_Date,
    logOut_Date,
    logIn_Time,
    logOut_Time,
    total_Time,
    shift
  ) {
    this.emp_Id = emp_Id;
    this.emp_Name = emp_Name;
    this.comp_Id = comp_Id;
    this.logIn_Date = logIn_Date;
    this.logOut_Date = logOut_Date;
    this.logIn_Time = logIn_Time;
    this.logOut_Time = logOut_Time;
    this.total_Time = total_Time;
    this.shift = shift;
  }
}
export class SessionBuilder {
  constructor() {}
  emp_Id;
  emp_Name;
  comp_Id;
  logIn_Date;
  logOut_Date;
  logIn_Time;
  logOut_Time;
  total_Time;
  shift;

  setEmp_Id(emp_Id) {
    this.emp_Id = emp_Id;
    return this;
  }
  setName(Name) {
    this.emp_Name = Name;
    return this;
  }
  setComp_Id(comp_Id) {
    this.comp_Id = comp_Id;
    return this;
  }
  setLogIn_Date(logIn_Date) {
    this.logIn_Date = logIn_Date;
    return this;
  }
  setLogOut_Date(logOut_Date) {
    this.logOut_Date = logOut_Date;
    return this;
  }
  setLogIn_Time(logIn_Time) {
    this.logIn_Time = logIn_Time;
    return this;
  }
  setLogOut_Time(logOut_Time) {
    this.logOut_Time = logOut_Time;
    return this;
  }
  setTotal_Time(total_Time) {
    this.total_Time = total_Time;
    return this;
  }
  setShift(shift) {
    this.shift = shift;
    return this;
  }

  getSession() {
    return new Session(
      this.emp_Id,
      this.emp_Name,
      this.comp_Id,
      this.logIn_Date,
      this.logOut_Date,
      this.logIn_Time,
      this.logOut_Time,
      this.total_Time,
      this.shift
    );
  }
}
