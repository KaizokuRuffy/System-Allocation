export class Admin {
  constructor(
    admin_Id,
    admin_Name,
    admin_Email,
    admin_ContactNo,
    admin_Password
  ) {
    this.admin_Id = admin_Id;
    this.admin_Name = admin_Name;
    this.admin_Email = admin_Email;
    this.admin_ContactNo = admin_ContactNo;
    this.admin_Password = admin_Password;
  }
}

export class AdminBuilder {
  constructor() {}
  admin_Id;
  admin_Name;
  admin_Email;
  admin_ContactNo;
  admin_Password;

  setId(Id) {
    this.admin_Id = Id;
    return this;
  }
  setName(Name) {
    this.admin_Name = Name;
    return this;
  }
  setEmail(Email) {
    this.admin_Email = Email;
    return this;
  }
  setContactNo(ContactNo) {
    this.admin_ContactNo = ContactNo;
    return this;
  }
  setPassword(Password) {
    this.admin_Password = Password;
    return this;
  }
  getAdmin() {
    return new Admin(
      this.admin_Id,
      this.admin_Name,
      this.admin_Email,
      this.admin_ContactNo,
      this.admin_Password
    );
  }
}
