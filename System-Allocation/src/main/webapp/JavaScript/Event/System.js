import * as Ctrl from "../Controller/C.js";
import { SystemBuilder } from "../Model/Data/D.js";
import * as U from "../Model/Util.js";
import { swap_Options } from "./Util.js";

U.System.init();
var SystemController = new Ctrl.System();

export let getAllSystems = () => {
  U.gEBI("getAllSystems").addEventListener("click", () => {
    SystemController.getAll();
    updateStatus();
  });
};

export let regSystem = U.gEBI("regSystem").addEventListener("click", () => {
  window.open("../HTML/System-Reg.html", "_blank");
});

export let updateStatus = function () {
  let comp = new SystemBuilder();

  let w_status = document.getElementsByClassName("working");
  let a_status = document.getElementsByClassName("available");

  Array.prototype.forEach.call(w_status, (item) => {
    item.addEventListener("change", (e) => {
      comp
        .setId(item.parentElement.parentElement.getAttribute("id"))
        .setWorking(e.target.value)
        .getSystem();

      if (SystemController.updateStatus(comp)) {
        if (e.target.value === "No") {
          let options =
            item.parentElement.previousSibling.firstChild.childNodes;
          if (options[0].value === "No") options[0].selected = true;
          else options[1].selected = true;

          item.parentElement.previousSibling.firstChild.disabled = true;
        } else if (e.target.value === "Yes") {
          item.parentElement.previousSibling.firstChild.disabled = false;
          let options =
            item.parentElement.previousSibling.firstChild.childNodes;

          if (options[0].value === "Yes") options[0].selected = true;
          else options[1].selected = true;
        }
        swap_Options(item);
        swap_Options(item.parentElement.previousSibling.firstChild);
      }
    });
  });

  Array.prototype.forEach.call(a_status, (item) =>
    item.addEventListener("change", (event) => {
      comp
        .setId(item.parentElement.parentElement.getAttribute("id"))
        .setAvailable(event.target.value)
        .getSystem();

      let options = item.parentElement.nextSibling.firstChild.childNodes;
      if (
        (options[0].value === "Yes" && options[0].selected === true) ||
        (options[1].value === "Yes" && options[1].selected === true)
      ) {
        SystemController.updateStatus(comp);
      } else {
        if (item[0].value === "No") item[0].selected = true;
        else item[1].selected = true;
        window.alert("System is not working cannot change availability status");
      }
      swap_Options(item);
    })
  );
};

export let updateWorking = (e) => {
  comp
    .setId(item.parentElement.parentElement.getAttribute("id"))
    .setWorking(e.target.value)
    .getSystem();

  if (SystemController.updateStatus(comp)) {
    if (e.target.value === "No") {
      let options = item.parentElement.previousSibling.firstChild.childNodes;
      if (options[0].value === "No") options[0].selected = true;
      else options[1].selected = true;

      item.parentElement.previousSibling.firstChild.disabled = true;
    } else if (e.target.value === "Yes") {
      item.parentElement.previousSibling.firstChild.disabled = false;
      let options = item.parentElement.previousSibling.firstChild.childNodes;

      if (options[0].value === "Yes") options[0].selected = true;
      else options[1].selected = true;
    }
    swap_Options(item);
    swap_Options(item.parentElement.previousSibling.firstChild);
  }
};

export let updateAvailable = (e) => {
  comp
    .setId(item.parentElement.parentElement.getAttribute("id"))
    .setAvailable(event.target.value)
    .getSystem();

  let options = item.parentElement.nextSibling.firstChild.childNodes;
  if (
    (options[0].value === "Yes" && options[0].selected === true) ||
    (options[1].value === "Yes" && options[1].selected === true)
  ) {
    SystemController.updateStatus(comp);
  } else {
    if (item[0].value === "No") item[0].selected = true;
    else item[1].selected = true;
    window.alert("System is not working cannot change availability status");
  }
  swap_Options(item);
};

document.addEventListener("DOMContentLoaded", function () {
  if (SystemController !== undefined) getAllSystems();
});
