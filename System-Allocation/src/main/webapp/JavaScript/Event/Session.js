import * as Ctrl from "../Controller/C.js";
import * as U from "../Model/Util.js";

U.Session.init();
var SessionController = new Ctrl.Session();

export let getAllSession = () => {
  U.gEBI("getAllSession").addEventListener("click", () => {
    if (sessionStorage.getItem("who") === "user") {
      SessionController.getEmpSession(
        JSON.parse(sessionStorage.getItem("session"))[U.Session.emp_Id]
      );
    } else SessionController.getAll();
  });
};

document.addEventListener("DOMContentLoaded", function () {
  if (U.gEBI("getAllSession") !== null) getAllSession();
});
