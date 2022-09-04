import * as Ctrl from "../Controller/C.js";
import * as U from "../Model/Util.js";

import { togglePassword } from "./Util.js";

U.User.init();
let UserController = new Ctrl.User();
let SystemController = new Ctrl.System();

function add() {
  if (U.gEBI(U.User.Password).value !== U.gEBI("confirm_Password").value)
    U.gEBI("confirm_Password").setCustomValidity("Password mismatch");
  else U.gEBI("confirm_Password").setCustomValidity("");

  if (U.gEBI(U.User.Shift) === "")
    U.gEBI(U.User.Shift).setCustomValidity("This field is required");
  else U.gEBI(U.User.Shift).setCustomValidity("");

  if (
    U.gEBI(U.User.Name).checkValidity() &&
    U.gEBI(U.User["Computer Id"]).checkValidity() &&
    U.gEBI(U.User.AdhaarId).checkValidity() &&
    U.gEBI(U.User.Email).checkValidity() &&
    U.gEBI(U.User["Mobile No"]).checkValidity() &&
    U.gEBI(U.User.Password).checkValidity() &&
    U.gEBI(U.User.Shift).checkValidity() &&
    U.gEBI(U.User.Role).checkValidity() &&
    U.gEBI(U.User.Dept).checkValidity() &&
    U.gEBI(U.User.WorkLoc).checkValidity() &&
    U.gEBI("confirm_Password").checkValidity()
  )
    UserController.add();
  else console.log("Invalid data");
}

let getUnallocatedSystems = U.gEBI(U.User.Shift).addEventListener(
  "change",
  () => {
    SystemController.getUnallocatedSystems(U.gEBI(U.User.Shift).value, "No");
  }
);

let addUser = U.gEBI("addUser").addEventListener("click", add);
document.addEventListener("DOMContentLoaded", function () {
  addUser;
  togglePassword();
  getUnallocatedSystems;
  sysPreReq;
});

let sysPreReq = U.gEBI(U.User.Shift).addEventListener("change", (e) => {
  let comp_Id = U.gEBI(U.User["Computer Id"]);

  if (e.target.value === "") comp_Id.disabled = true;
  else if (comp_Id.disabled) comp_Id.disabled = false;
});
