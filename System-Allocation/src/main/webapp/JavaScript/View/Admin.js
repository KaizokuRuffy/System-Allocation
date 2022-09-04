import * as U from "../Model/Util.js";
import * as Util from "./Util.js";
import * as C from "../Controller/C.js";

export class Admin {
  constructor() {
    this.fields = U.Admin;
  }

  isPresent(response) {
    if (response.status === 200 && response.body.includes("Yes")) {
      if (!response.body.includes("Yes Yes")) {
        let empTitle = document.getElementsByClassName("employee");
        let empLogin = U.gEBI("employee");
        empTitle[0].remove();
        empLogin.remove();
      }
    } else if (response.status === 200 && !response.body.includes("Yes")) {
      let div = U.gEBI("Register button");
      let button = document.createElement("button");
      button.setAttribute("type", "button");
      button.setAttribute("id", "regAdmin");
      button.style.width = "85px";
      button.innerText = "Register";
      div.appendChild(button);

      let admin_Email = U.gEBI("admin_Email");
      let adminPassword = U.gEBI("admin_Password");
      let adminLogin = U.gEBI("adminLogin");
      adminLogin.disabled = true;
      admin_Email.disabled = true;
      adminPassword.disabled = true;

      let empTitle = document.getElementsByClassName("employee");
      let empLogin = U.gEBI("employee");
      empTitle[0].remove();
      empLogin.remove();
    } else if (response.status !== 200) {
      window.alert("Something went wrong");
    }
  }
  create(response) {
    if (response.status == 201 || response.status == 200)
      window.open("HTML/Admin-Reg.html", "_blank");
    else {
      window.alert(JSON.stringify(response.body));
      window.location.replace("");
    }
  }
  login(response) {
    if (response.status === 200) {
      window.location.replace("HTML/Admin.html");
    } else {
      let bool = true;

      try {
        U.gEBI("Amsg").remove();
        U.gEBI("Aerror").remove();

        bool = false;
      } catch (error) {}

      var msg = document.createElement("td");
      msg.setAttribute("id", "Amsg");
      msg.style.color = "Red";
      msg.innerText = "Invlaid username / password";
      msg.style.paddingLeft = "70px";
      var ele = U.gEBI("button");
      var tr = document.createElement("tr");
      tr.id = "Aerror";
      tr.appendChild(msg);
      ele.appendChild(tr);
      setTimeout(() => {
        if (bool) {
          U.gEBI("Amsg").remove();
          U.gEBI("Aerror").remove();
        }
      }, 3000);
    }
  }
  get(response) {
    if (response.status === 200) {
      let display = U.gEBI("display");
      Util.displayAsList(
        this.fields,
        JSON.parse(response.body),
        display,
        "Admin"
      );
    } else if (response.status === 403) {
      window.alert("Session Timeout");
      window.location.replace("../index.html");
    } else {
      window.alert(JSON.stringify(response.body).replaceAll('"', ""));
    }
  }
  logout(response) {
    if (response.status === 200) {
      window.location.replace("../index.html");
    } else if (response.status === 403) {
      window.alert("Session Timeout");
      window.location.replace("../index.html");
    } else {
      window.alert("Something went wrong");
    }
  }
  add(response) {
    if (response.status === 201) {
      window.alert("Account created successfully");
      if (sessionStorage.getItem("first admin") !== null) {
        sessionStorage.removeItem("first admin");
        window.close();
      }
    } else if (response.status === 403) {
      window.alert("Session Timeout");
      window.close();
    } else {
      window.alert(JSON.stringify(response.body).replaceAll('"', ""));
      window.close();
    }
  }
}

export function refresh() {
  console.log("refresh called");
  console.log(document);
  let display = U.gEBI("display");
  console.log(display);
  if (display !== undefined && display !== null) {
    let refresh = display.firstChild;
    console.log(refresh);
    if (refresh !== undefined && refresh !== null) {
      let already = false;

      if (!already) {
        already = true;

        refresh.id.includes("Employee")
          ? new C.User().getAll()
          : refresh.id.includes("System")
          ? new C.System().getAll()
          : new C.Session().getAll();

        setTimeout(() => (refresh = false), 5000);
      }
    }
  }
}
