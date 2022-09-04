import * as U from "../Model/Util.js";
import * as Util from "./Util.js";
import { System as M_System } from "../Model/M.js";

U.System.init();

export class System {
  constructor() {
    this.fields = U.System;
  }
  getAll(response) {
    let display = U.gEBI("display");
    if (response.status === 200) {
      display.style.fontSize = "25px";
      Util.displayAsTable(
        this.fields,
        JSON.parse(response.body),
        display,
        "System"
      );
    } else if (response.status === 404 || response.status === 500) {
      Util.removeAllChildNodes(display);
      window.alert(JSON.stringify(response.body).replaceAll('"', ""));
    } else if (response.status === 403) {
      window.alert("Session Timeout");
      window.location.replace("../index.html");
    }
  }
  getUnallocatedSystems(response) {
    if (response.status === 200) {
      let body = JSON.parse(response.body);

      let select = U.gEBI(U.System.Id);
      Util.removeAllChildNodes(select);

      let option = document.createElement("option");

      select.appendChild(option);
      for (let comp in body) {
        option = Util.createOption(body[comp][U.System.Id]);
        select.appendChild(option);
      }
    }
  }
  add(response) {
    if (response.status === 201) window.alert("System added successfully");
    else if (response.status === 403) {
      window.alert("Session Timeout");
      window.close();
    } else if (response.status === 500) {
      window.alert(JSON.stringify(response.body).replaceAll('"', ""));
      window.close();
    }
  }
  updateStatus(response) {
    if (response.status === 200) {
      window.alert("Updated successfully");
      return true;
    } else if (response.status === 403) {
      window.alert("Session Timeout");
      window.location.replace("../index.html");
    } else if (response.status === 500) {
      window.alert(JSON.stringify(response.body).replaceAll('"', ""));
    }
  }
}

export let systemOptions = (response, remove) => {
  let tr = U.gEBI("Filter");
  let columnIndex = Array.prototype.indexOf.call(
    tr.childNodes,
    U.gEBI(U.User["Computer Id"]).parentElement
  );

  for (let i = 0; i < response.length; i++) {
    if (response[i] !== undefined) {
      let unallocatedSystems = new M_System().getAll(
        response[i][U.User.Shift],
        null
      );
      unallocatedSystems = JSON.parse(unallocatedSystems.body);

      tr = U.gEBI(response[i][U.User.Id]);
      let select = tr.childNodes[columnIndex].firstChild;
      if (remove) Util.removeAllChildNodes(select);

      for (let j = 0; j < unallocatedSystems.length; j++) {
        let opt = Util.createOption(
          unallocatedSystems[j][U.User["Computer Id"]]
        );
        if (unallocatedSystems[j][U.System.Backup] === "Yes")
          opt.innerText = opt.innerText + " - bak";
        select.appendChild(opt);
      }
    }
  }
};
