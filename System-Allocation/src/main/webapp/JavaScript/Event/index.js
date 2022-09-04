import * as Ctrl from "../Controller/C.js";
import * as U from "../Model/Util.js";
import { userLogin } from "./User.js";
import { firstTime } from "./User.js";

U.Admin.init();
var AdminController = new Ctrl.Admin();

let isPresent = () => {
  AdminController.isPresent();
};
isPresent();

let onEnter = () => {
  if (U.gEBI("emp_Password") !== null)
    U.gEBI("emp_Password").addEventListener("keyup", (e) => {
      if (e.key === "Enter") U.gEBI("userLogin").click();
    });
  U.gEBI("admin_Password").addEventListener("keyup", (e) => {
    if (e.key === "Enter") U.gEBI("adminLogin").click();
  });
};

let login = document
  .getElementById("adminLogin")
  .addEventListener("click", () => {
    AdminController.login();
  });

let create = () => {
  let temp = U.gEBI("regAdmin");
  if (temp !== null)
    temp.addEventListener("click", () => {
      sessionStorage.setItem("first admin", "");
      AdminController.create();
    });
};

document.addEventListener("DOMContentLoaded", () => {
  login;
  create();
  userLogin();
  onEnter();
  firstTime();
});
