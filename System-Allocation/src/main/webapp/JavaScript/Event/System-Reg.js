import * as Ctrl from "../Controller/C.js";
import * as U from "../Model/Util.js";

import { togglePassword } from "./Util.js";

U.System.init();
let SystemController = new Ctrl.System();

function add() {
  if (U.gEBI(U.System.Password).value !== U.gEBI("confirm_Password").value)
    U.gEBI("confirm_Password").setCustomValidity("Password mismatch");
  else U.gEBI("confirm_Password").setCustomValidity("");

  let year = Number(U.gEBI(U.System.Year).value);
  let max_year = new Date().getFullYear();
  let min_year = 2010;
  if (year < min_year || year > max_year) {
    if (Number(U.gEBI(U.System.Year).value) < min_year)
      U.gEBI(U.System.Year).setCustomValidity(
        "Year should be greater than " + min_year
      );
    if (Number(U.gEBI(U.System.Year).value) > max_year)
      U.gEBI(U.System.Year).setCustomValidity(
        "Year should be less than " + max_year
      );
  } else U.gEBI(U.System.Year).setCustomValidity("");

  if (
    U.gEBI(U.System.Id).checkValidity() &&
    U.gEBI(U.System.MAC).checkValidity() &&
    U.gEBI(U.System.Available).checkValidity() &&
    U.gEBI(U.System.Working).checkValidity() &&
    U.gEBI(U.System.Password).checkValidity() &&
    U.gEBI(U.System.Location).checkValidity() &&
    U.gEBI(U.System.Backup).checkValidity() &&
    U.gEBI(U.System.Model).checkValidity() &&
    U.gEBI(U.System.Year).checkValidity() &&
    U.gEBI("confirm_Password").checkValidity()
  )
    SystemController.add();
  else console.log("Invalid data");
}

let addSystem = U.gEBI("addSystem").addEventListener("click", add);

document.addEventListener("DOMContentLoaded", function () {
  addSystem;
  togglePassword();
});
