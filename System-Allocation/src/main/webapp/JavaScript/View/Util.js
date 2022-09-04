import * as Util from "../Model/Util.js";
import { User } from "../Controller/C.js";
import * as U from "../Event/Util.js";
import * as D from "../Model/Data/D.js";

Util.initAll();
const updateColumn = {
  Employee: [Util.User["Computer Id"], Util.User.Shift],
  System: [Util.System.Available, Util.System.Backup, Util.System.Working],
};

export let displayAsList = function (fields, data, element, name) {
  removeAllChildNodes(element);
  element.setAttribute("align", "center");
  element.style.fontSize = "20px";
  element.style.paddingTop = "50px";

  let field_key = Object.keys(fields);
  let input = {};

  for (let i = 0; i < field_key.length - 1; i++) {
    let temp = document.createElement("input");
    temp.setAttribute("id", fields[field_key[i]]);
    temp.style.width = (data[fields[field_key[i]]] + "").length * 12 + "px";
    temp.disabled = true;
    temp.style.fontSize = "17px";
    temp.style.fontFamily = "Consolas";
    temp.value = data[fields[field_key[i]]];
    input[field_key[i]] = temp;
  }
  let table = document.createElement("table");
  table.setAttribute("id", name + "Profile");
  let tbody = document.createElement("tbody");
  table.appendChild(tbody);

  for (let i = 0; i < field_key.length - 1; i++) {
    let tr = document.createElement("tr");
    tr.setAttribute("id", field_key[i]);

    tbody.appendChild(tr);

    for (let j = 0; j < 2; j++) {
      let td = document.createElement("td");
      if (j === 0) {
        td.setAttribute("class", "name");
        td.appendChild(document.createTextNode(field_key[i] + "  :-"));
        tr.appendChild(td);
      }
      if (j === 1) {
        td.setAttribute("class", "value");
        td.style.paddingLeft = "100px";
        td.style.paddingBottom = "10px";
        td.appendChild(input[field_key[i]]);
        tr.appendChild(td);
      }
    }
    if (tr.getAttribute("id") === "Id") tr.hidden = true;
  }
  element.appendChild(table);
};

export let displayAsTable = function (fields, data, element, name) {
  if (sessionStorage.getItem("who") === "user") delete fields["emp_Id"];

  removeAllChildNodes(element);
  element.setAttribute("align", "center");
  element.style.fontFamily = "consolas";
  element.style.fontSize = "15px";

  if (element.id !== "display session") element.style.paddingTop = "50px";

  let field_key = Object.keys(fields);

  let table = document.createElement("table");
  table.setAttribute("id", name + " data");

  let thead = document.createElement("thead");
  table.appendChild(thead);

  let fltr = document.createElement("tr");
  fltr.id = "Filter";

  let tbody = document.createElement("tbody");
  table.appendChild(tbody);

  let len = Object.create(data[0]);

  for (let i = 0; i < field_key.length - 1; i++) {
    let th = document.createElement("th");
    th.setAttribute("id", field_key[i]);
    th.setAttribute("class", "header");
    th.style.fontFamily = "Times new Roman";
    if (field_key[i] === "MAC") th.appendChild(document.createTextNode("OS"));
    else th.appendChild(document.createTextNode(field_key[i]));

    thead.appendChild(th);

    len[fields[field_key[i]]] = field_key[i].length;

    if (
      field_key[i] === "Available" ||
      field_key[i] === "Working" ||
      field_key[i] === "Shift"
    ) {
      let cell = document.createElement("td");
      let select = document.createElement("select");
      select.className = "Filter";
      select.id = fields[field_key[i]];

      let def = document.createElement("option");
      def.value = "";
      def.innerText = "-";
      def.selected = true;
      select.appendChild(def);

      let optionsList = options[fields[field_key[i]]];

      for (let value in optionsList) {
        select.appendChild(createOption(optionsList[value]));
      }

      select.addEventListener("change", (e) => filter(e));

      cell.appendChild(select);
      fltr.appendChild(cell);
    } else {
      let cell = document.createElement("td");
      let ip = document.createElement("input");
      ip.className = "Filter";
      ip.style.fontFamily = "Times New Roman";
      ip.id = fields[field_key[i]];
      if (field_key[i] === "MAC") ip.placeholder = "OS";
      else ip.placeholder = field_key[i];

      ip.addEventListener("keyup", (e) => filter(e));

      cell.appendChild(ip);
      fltr.appendChild(cell);
    }
  }
  thead.appendChild(fltr);

  for (let i = 0; i < data.length; i++) {
    if (name == "Session" && sessionStorage.getItem("who") === "user") {
      let temp = JSON.parse(sessionStorage.getItem("session"));

      if (data[i][fields["emp_Id"]] !== temp[fields["emp_Id"]]) continue;
    }

    let tr = document.createElement("tr");

    if (name !== "Session") tr.setAttribute("id", data[i][fields["Id"]]);
    else
      tr.setAttribute(
        "id",
        data[i][fields["emp_Id"]] +
          "_" +
          data[i][fields["comp_Id"]] +
          +"_" +
          data[i][fields["Login Date"]]
      );
    tbody.appendChild(tr);

    for (let j = 0; j < field_key.length - 1; j++) {
      let obj = data[i];
      let td = document.createElement("td");

      if (
        obj[fields[field_key[j]]] !== undefined &&
        len[fields[field_key[j]]] < obj[fields[field_key[j]]].toString().length
      )
        len[fields[field_key[j]]] = obj[fields[field_key[j]]].toString().length;

      if (
        fields[field_key[j]] === "working" ||
        fields[field_key[j]] === "available" ||
        fields[field_key[j]] === "emp_Shift" ||
        (fields[field_key[j]] === Util.User["Computer Id"] &&
          name === "Employee")
      ) {
        let select = document.createElement("select");
        select.className = fields[field_key[j]];

        let optionsList = [];
        if (fields[field_key[j]] !== Util.User["Computer Id"])
          optionsList = options[fields[field_key[j]]];
        else optionsList[0] = obj[fields[field_key[j]]];

        for (let value in optionsList) {
          let opt = createOption(optionsList[value]);
          select.appendChild(opt);

          if (optionsList[value] === obj[fields[field_key[j]]])
            opt.selected = true;
          if (fields[field_key[j]] === Util.User["Computer Id"] && value === 0)
            opt.selected = true;
        }

        td.appendChild(select);
        tr.appendChild(td);
        if (
          fields[field_key[j]] === "working" &&
          obj[fields[field_key[j]]] === "No"
        )
          td.previousSibling.firstChild.disabled = true;
      } else {
        let ip = document.createElement("input");
        ip.className = fields[field_key[j]];
        ip.type = "text";
        ip.disabled = true;
        ip.style.height = "22px";
        ip.style.width = (obj[fields[field_key[j]]] + "").length * 9 + "px";
        ip.style.fontSize = "15px";
        ip.style.fontFamily = "Consolas";
        ip.value =
          obj[fields[field_key[j]]] == undefined ||
          obj[fields[field_key[j]]] == null
            ? ""
            : obj[fields[field_key[j]]];

        td.appendChild(ip);
        tr.appendChild(td);

        // EventListener(name, ip.className, ip);
      }
    }
  }
  element.appendChild(table);

  for (let i = 0; i < field_key.length; i++) {
    let els = document.getElementsByClassName(fields[field_key[i]]);

    Array.prototype.forEach.call(els, (el) => {
      let factor = Number(el.style.fontSize.slice(0, -2));
      el.style.width = len[fields[field_key[i]]] * 9 + "px";
    });
  }

  let els = document.getElementsByClassName("Filter");
  Array.prototype.forEach.call(els, (el) => {
    el.style.width = len[el.id] * 9 + "px";
  });

  if (sessionStorage.getItem("who") === "user") fields["emp_Id"] = "emp_Id";

  element.style.paddingBottom = "50px";

  storeAsList();
};

function filter(event) {
  let filter_value = event.target.value;
  let column = event.target.id;
  let rows = document.getElementsByClassName(column);

  if (event.key === "Enter") {
    Array.prototype.forEach.call(rows, (row) => {
      if (filter_value === "") row.parentElement.parentElement.hidden = false;

      if (filter_value !== "" && row.value !== filter_value)
        row.parentElement.parentElement.hidden = true;
    });
  } else {
    Array.prototype.forEach.call(rows, (row) => {
      if (filter_value === "") row.parentElement.parentElement.hidden = false;
      else if (filter_value !== "" && !row.value.includes(filter_value))
        row.parentElement.parentElement.hidden = true;
      else if (filter_value !== "" && row.value.includes(filter_value))
        row.parentElement.parentElement.hidden = false;
    });
  }
}

export function removeAllChildNodes(parent) {
  while (parent.firstChild) {
    parent.removeChild(parent.firstChild);
  }
}

var options = {
  available: ["Yes", "No"],
  working: ["Yes", "No"],
  emp_Shift: ["Morning", "Evening", "Night"],
};

export function createOption(value) {
  let opt = document.createElement("option");
  opt.value = value;
  opt.innerText = value;
  return opt;
}

export var storeAsList = (columns, ID) => {
  let display = document.getElementById("display");
  let table = display.firstChild;
  let id = table.id.substr(0, table.id.indexOf(" "));

  let List = [];
  let tr =
    document.getElementById("display").firstChild.childNodes[1].childNodes;

  let Class =
    id === "System"
      ? new D.System()
      : id === "Session"
      ? new D.Session()
      : new D.User();

  for (let i = 0; i < tr.length; i++) {
    let td = tr[i].childNodes;
    if ((ID !== undefined && tr[i].id === ID) || ID === undefined)
      for (let j = 0; j < td.length; j++) {
        if (columns === undefined) {
          if (typeof Class === typeof new D.System()) {
            Class[[td[j].firstChild.className]] = td[j].firstChild.value;
          } else if (typeof Class === typeof new D.Session()) {
            Class[[td[j].firstChild.className]] = td[j].firstChild.value;
          } else if (typeof Class === typeof new D.User()) {
            Class[[td[j].firstChild.className]] = td[j].firstChild.value;
          }
          List[i] = Class;
        } else {
          for (let temp in columns) {
            if (td[j].firstChild.className === columns[temp]) {
              if (typeof Class === typeof new D.System()) {
                Class[[td[j].firstChild.className]] = td[j].firstChild.value;
              } else if (typeof Class === typeof new D.Session()) {
                Class[[td[j].firstChild.className]] = td[j].firstChild.value;
              } else if (typeof Class === typeof new D.User()) {
                Class[[td[j].firstChild.className]] = td[j].firstChild.value;
              }
              break;
            } else Class[[td[j].firstChild.className]] = undefined;
          }
          List[i] = Class;
        }
      }
  }
  return List;
};

export var EventListener = (table_name, column_name, ele) => {
  let cols = updateColumn[table_name];
  let event;
  let action;

  if (table_name === "Employee") {
    if (column_name === Util.User.Shift) {
      event = "change";
      action = UserEvent.updateShift;
    } else if (column_name === Util.User["Computer Id"]) {
      event = "change";
      action = UserEvent.updateSystemID;
    }
  }
  if (table_name === "System") {
    if (column_name === Util.System.Available) {
      event = "change";
      action = SystemEvent.updateAvailable;
    } else if (column_name === Util.System.Working) {
      event = "change";
      action = SystemEvent.updateWorking;
    }
  }

  if (event !== undefined && action !== undefined)
    for (let col in cols) {
      if (col === column_name) {
        ele.addEventListener(event, action);
        break;
      }
    }
};

/* const updateColumn = {
  Admin: [Util.Admin.Email, Util.Admin["Mobile No"], Util.Admin.Password],
  Employee: [
    Util.User["Computer Id"],
    Util.User.Dept,
    Util.User.Email,
    Util.User["Mobile No"],
    Util.User.Password,
    Util.User.Role,
    Util.User.Shift,
    Util.User.WorkLoc,
  ],
  System: [
    Util.System.Available,
    Util.System.Backup,
    Util.System.Location,
    Util.System.MAC,
    Util.System.Model,
    Util.System.Password,
    Util.System.Working,
    Util.System.Year,
  ],
};
 */
