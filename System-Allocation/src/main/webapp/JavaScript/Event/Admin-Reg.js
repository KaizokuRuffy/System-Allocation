import * as Ctrl from "../Controller/C.js";
import * as U from "../Model/Util.js";
import { togglePassword } from "./Util.js";

U.Admin.init();
var AdminController = new Ctrl.Admin();

function add() {
  if (U.gEBI("admin_Password").value !== U.gEBI("confirm_Password").value)
    U.gEBI("confirm_Password").setCustomValidity("Password mismatch");
  else U.gEBI("confirm_Password").setCustomValidity("");
  if (
    U.gEBI("admin_Name").checkValidity() &&
    U.gEBI("admin_Email").checkValidity() &&
    U.gEBI("admin_ContactNo").checkValidity() &&
    U.gEBI("admin_Password").checkValidity() &&
    U.gEBI("confirm_Password").checkValidity()
  )
    AdminController.add();
  else console.log("Invalid data");
}

let addAdmin = document
  .getElementById("addAdmin")
  .addEventListener("click", add);

document.addEventListener("DOMContentLoaded", function () {
  addAdmin;
  togglePassword();
});
