import * as U from "../Model/Util.js";
import * as Util from "./Util.js";

export class Session {
  constructor() {
    this.fields = U.Session;
  }
  getAll(response) {
    let display = U.gEBI("display");
    if (response.status === 200) {
      display.style.fontSize = "25px";
      Util.displayAsTable(
        this.fields,
        JSON.parse(response.body),
        display,
        "Session"
      );
    } else if (response.status === 404) {
      Util.removeAllChildNodes(display);
      window.alert(JSON.stringify(response.body).replaceAll('"', ""));
    } else if (response.status === 403) {
      window.alert("Session Timeout");
      window.location.replace("../index.html");
    }
  }
}
