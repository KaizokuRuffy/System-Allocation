import * as U from "../Model/Util.js";
import * as Util from "./Util.js";
import { systemOptions } from "./System.js";

export class User {
  constructor() {
    this.fields = U.User;
  }

  firstLogin(response) {
    if (response.status === 200) {
      if (!response.body.includes("-1")) {
        if (!response.body.includes("Available - No")) {
          let str = response.body.split(",");
          sessionStorage.setItem(U.System.Id, str[0].replace("comp_Id :", ""));
          sessionStorage.setItem(
            U.User.Id,
            Number(str[1].replace("emp_Id:", ""))
          );
          sessionStorage.setItem("CID", sessionStorage.getItem(U.System.Id));
        } else sessionStorage.setItem("Available", "No");
      } else if (response.body.includes("-1")) {
        let str = response.body.split(",");
        sessionStorage.setItem(U.System.Id, "-1");
        sessionStorage.setItem(
          U.User.Id,
          Number(str[1].replace("emp_Id:", ""))
        );
        sessionStorage.setItem("CID", str[2].replace("CID:", ""));
      }
    } else if (response.status === 403) {
      sessionStorage.setItem("Username", JSON.stringify(response.body));
    }
  }

  login(response) {
    if (response.status === 200) {
      window.location.replace("HTML/User.html");
    } else if (response.status === 403 || response.status === 404) {
      let bool = true;
      try {
        U.gEBI("Umsg").remove();
        U.gEBI("Uerror").remove();
        bool = false;
      } catch (error) {}

      var msg = document.createElement("td");
      msg.setAttribute("id", "Umsg");
      msg.style.color = "Red";
      msg.innerText = "Invlaid username / password";
      msg.style.paddingLeft = "70px";
      msg.style.paddingBottom = "20px";
      var ele = U.gEBI("btn");
      var tr = document.createElement("tr");
      tr.id = "Uerror";
      tr.appendChild(msg);
      ele.appendChild(tr);
      setTimeout(() => {
        if (bool) {
          U.gEBI("Umsg").remove();
          U.gEBI("Uerror").remove();
        }
      }, 3000);
    } else if (
      response.status === 400 ||
      response.status === 409 ||
      response.status === 500
    ) {
      window.alert(JSON.stringify(response.body).replaceAll('"', ""));
    } else {
      window.alert(response.replaceAll('"', ""));
    }
  }
  logout(response) {
    if (response.status === 200) {
      window.location.replace("../index.html");
    } else if (response.status === 403) {
      window.alert("Session Timeout");
      window.location.replace("../index.html");
    } else if (response.status === 500) {
      window.alert("Something went wrong");
    }
  }
  get(response) {
    if (response.status === 200) {
      let display = U.gEBI("display");
      Util.displayAsList(
        this.fields,
        JSON.parse(response.body),
        display,
        "Employee"
      );
    } else if (response.status === 403) {
      window.alert("Session Timeout");
      window.location.replace("../index.html");
    } else {
      window.alert(JSON.parse(response.body));
    }
  }
  getPlus(response) {
    if (response.status === 200) {
      let display = U.gEBI("display");
      let data = JSON.parse(response.body);
      let emp = [];
      emp[0] = data[0];
      let session = data[1];

      Util.displayAsTable(this.fields, emp, display, "Employee");
      U.gEBI("Filter").remove();

      if (session.length != 0) {
        let heading = document.createElement("h2");

        heading.innerText = "'" + emp[0][U.User.Name] + "'" + " - Session data";
        heading.style.paddingTop = "50px";
        heading.style.fontWeight = "500";
        display.appendChild(heading);

        let dsp = document.createElement("div");
        dsp.id = "display session";

        display.appendChild(dsp);
        Util.displayAsTable(U.Session, session, dsp, "Session");
      }
    } else if (response.status === 403) {
      window.alert("Session Timeout");
      window.location.replace("../index.html");
    } else if (response.status === 500) {
      window.alert(JSON.stringify(response.body).replaceAll('"', ""));
    }
  }
  getAll(response) {
    let display = U.gEBI("display");

    if (response.status === 200) {
      display.style.fontSize = "25px";
      response = JSON.parse(response.body);
      Util.displayAsTable(this.fields, response, display, "Employee");

      systemOptions(response);
    } else if (response.status === 404 || response.status === 500) {
      Util.removeAllChildNodes(display);
      window.alert(JSON.stringify(response.body).replaceAll('"', ""));
    } else if (response.status === 403) {
      window.alert("Session Timeout");
      window.location.replace("../index.html");
    }
  }

  add(response) {
    if (response.status === 201) window.alert("Account created successfully");
    else if (response.status === 403) {
      window.alert("Session Timeout");
      window.close();
    } else if (response.status === 500) {
      window.alert(JSON.stringify(response.body).replaceAll('"', ""));
      window.close();
    }
  }
}
