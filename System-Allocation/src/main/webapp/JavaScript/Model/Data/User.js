export class User {
  constructor(
    emp_Id,
    comp_Id,
    emp_Name,
    emp_AdhaarId,
    emp_Email,
    emp_MobileNo,
    emp_Password,
    emp_Shift,
    emp_Role,
    emp_Dept,
    emp_WorkLoc
  ) {
    this.emp_Id = emp_Id;
    this.comp_Id = comp_Id;
    this.emp_Name = emp_Name;
    this.emp_AdhaarId = emp_AdhaarId;
    this.emp_Email = emp_Email;
    this.emp_MobileNo = emp_MobileNo;
    this.emp_Password = emp_Password;
    this.emp_Shift = emp_Shift;
    this.emp_Role = emp_Role;
    this.emp_Dept = emp_Dept;
    this.emp_WorkLoc = emp_WorkLoc;
  }
}
export class UserBuilder {
  constructor() {}
  emp_Id;
  comp_Id;
  emp_Name;
  emp_AdhaarId;
  emp_Email;
  emp_MobileNo;
  emp_Password;
  emp_Shift;
  emp_Role;
  emp_Dept;
  emp_WorkLoc;

  setId(Id) {
    this.emp_Id = Id;
    return this;
  }
  setCompId(Id) {
    this.comp_Id = Id;
    return this;
  }
  setName(Name) {
    this.emp_Name = Name;
    return this;
  }
  setAdhaarId(AdhaarId) {
    this.emp_AdhaarId = AdhaarId;
    return this;
  }
  setEmail(Email) {
    this.emp_Email = Email;
    return this;
  }
  setMobileNo(MobileNo) {
    this.emp_MobileNo = MobileNo;
    return this;
  }
  setPassword(Password) {
    this.emp_Password = Password;
    return this;
  }
  setShift(Shift) {
    this.emp_Shift = Shift;
    return this;
  }
  setRole(Role) {
    this.emp_Role = Role;
    return this;
  }
  setDept(Dept) {
    this.emp_Dept = Dept;
    return this;
  }
  setWorkLoc(WorkLoc) {
    this.emp_WorkLoc = WorkLoc;
    return this;
  }
  getUser() {
    return new User(
      this.emp_Id,
      this.comp_Id,
      this.emp_Name,
      this.emp_AdhaarId,
      this.emp_Email,
      this.emp_MobileNo,
      this.emp_Password,
      this.emp_Shift,
      this.emp_Role,
      this.emp_Dept,
      this.emp_WorkLoc
    );
  }
}
