import * as Ctrl from "../Controller/C.js";
import * as U from "../Model/Util.js";
import * as session from "./Session.js";
import { createOption, storeAsList } from "../View/Util.js";
import { systemOptions } from "../View/System.js";
import { getElementInRow } from "./Util.js";

U.User.init();
U.System.init();

var UserController = new Ctrl.User();

if (
  sessionStorage.getItem("who") === "user" &&
  location.href.split("/").slice(-1)[0] === "User.html"
)
  UserController.get();

export let userLogin = () => {
  if (U.gEBI("userLogin") !== null)
    U.gEBI("userLogin").addEventListener("click", () => {
      if (sessionStorage.getItem("Username") === null) {
        if (sessionStorage.getItem("Available") === "No") {
          window.alert("System not available");
        } else if (comp_Id === "-1" || comp_Id === null) {
          if (beforeLogin(true)) {
            let CID = window.prompt("Please enter Computer Id");
            UserController.login(CID);
          } else {
            window.alert(
              "System already in use by " + localStorage.getItem(U.User.Id)
            );
          }
        } else if (comp_Id !== "-1") {
          if (beforeLogin(false)) UserController.login(comp_Id);
          else window.alert("Cannot login to different system");
        }
      } else {
        UserController.View.login(sessionStorage.getItem("Username"));
      }
    });
};

export let getAllUsers = () => {
  U.gEBI("getAllUsers").addEventListener("click", () => {
    UserController.getAll();
    updateShift();
    updateSystemID();
  });
};

export let userLogout = () => {
  U.gEBI("userLogout").addEventListener("click", () => UserController.logout());
};

export let getUser = () => {
  U.gEBI("getUser").addEventListener("click", () => {
    UserController.get();
  });
};

export let regUser = () => {
  U.gEBI("regUser").addEventListener("click", () => {
    window.open("../HTML/User-Reg.html", "_blank");
  });
};

let keypress = false;
let comp_Id = null;

export let firstTime = () => {
  if (U.gEBI(U.User.Password) !== null) {
    U.gEBI(U.User.Email).addEventListener("keyup", () => {
      keypress = false;
      sessionStorage.removeItem(U.System.Id);
      sessionStorage.removeItem("Username");
      sessionStorage.removeItem("Available");
    });

    U.gEBI(U.User.Password).addEventListener("keyup", () => {
      if (!keypress) {
        keypress = true;
        UserController.firstLogin();

        window.alert(
          "Your allocated system is " + sessionStorage.getItem("CID")
        );
        sessionStorage.setItem(
          U.User["Computer Id"],
          sessionStorage.getItem("CID")
        );
        comp_Id = sessionStorage.getItem(U.System.Id);
      }
    });
  }
};

document.addEventListener("DOMContentLoaded", function () {
  if (U.gEBI("getUser") !== null) getUser();
  if (U.gEBI("userLogout") !== null) userLogout();
  session.getAllSession;
});

let beforeLogin = (bool) => {
  if (bool) {
    if (localStorage.getItem(U.Session["Login Date"]) !== U.getDate()) {
      localStorage.clear();
      return true;
    } else if (
      U.getShift(localStorage.getItem(U.Session["Login Time"])) !==
      U.getShift(U.getTime())
    ) {
      localStorage.clear();
      return true;
    } else if (
      localStorage.getItem(U.User.Id) === sessionStorage.getItem(U.User.Id)
    )
      return true;
    else return false;
  } else {
    if (
      localStorage.getItem(U.User.Id) === sessionStorage.getItem(U.User.Id) &&
      localStorage.getItem(U.System.Id) === comp_Id
    )
      return true;
    return false;
  }
};

export let updateShift = function () {
  let shift = document.getElementsByClassName(U.User.Shift);
  Array.prototype.forEach.call(shift, (item) => {
    item.addEventListener("change", (e) => {
      item.setAttribute("shiftChanged", true);

      let list = storeAsList(
        [U.User.Shift, U.User.Id],
        item.parentElement.parentElement.id
      );

      systemOptions(list, true);

      let select = getElementInRow(
        item.parentElement.parentElement,
        U.User["Computer Id"]
      );
      let opt = createOption("");
      select.insertBefore(opt, select.firstChild);
      opt.selected = true;
    });
  });
};

export let updateSystemID = function () {
  let comp_Id = document.getElementsByClassName(U.User["Computer Id"]);
  Array.prototype.forEach.call(comp_Id, (item) => {
    item.addEventListener("change", (e) => {});
  });
};