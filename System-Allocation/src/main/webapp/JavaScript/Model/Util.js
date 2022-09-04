import * as D from "./Data/D.js";

export const Context = "http://localhost:8080/System-Allocation/";

export const Controller = {
  Admin: "AdminController/",
  User: "EmployeeController/",
  System: "SystemController/",
  Session: "SessionController/",
};

export class XHR {
  constructor(request, response) {
    this.Request = request;
    this.Response = response;
  }
  sendRequest() {
    let xhr = new XMLHttpRequest();

    xhr.open(this.Request.method, this.Request.url.requestURL, false);

    if (this.Request.header != null && this.Request.header != undefined) {
      if (
        this.Request.header.Content !== null &&
        this.Request.header.Content !== undefined
      )
        xhr.setRequestHeader("Content-type", this.Request.header.Content);
      if (
        this.Request.header.Accept !== null &&
        this.Request.header.Accept !== undefined
      )
        xhr.setRequestHeader("Accept", this.Request.header.Accept);
    }

    if (this.Request.body !== null && this.Request.body != undefined)
      xhr.send(this.Request.body);
    else xhr.send();

    this.Response.body = xhr.response;
    this.Response.status = xhr.status;

    return this.Response;
  }
}

export class Req {
  constructor(method, header, url, body) {
    this.method = method;
    this.header = header;
    this.url = url;
    this.body = body;
  }
}
export class ReqBuilder {
  constructor() {}
  method;
  header;
  url;
  body;

  setMethod(method) {
    this.method = method;
    return this;
  }
  setHeader(header) {
    this.header = header;
    return this;
  }
  setUrl(url) {
    this.url = url;
    return this;
  }
  setBody(body) {
    this.body = body;
    return this;
  }
  getReq() {
    return new Req(this.method, this.header, this.url, this.body);
  }
}
export class Url {
  constructor(context, servlet, path, params) {
    this.context = context;
    this.servlet = servlet;
    this.path = path;
    this.params = params;

    this.requestURL =
      this.context +
      this.servlet +
      this.path +
      (this.params !== undefined ? this.params : "");
  }
}
export class UrlBuilder {
  constructor() {}
  context;
  servlet;
  path;
  params;

  setContext(context) {
    this.context = context;
    return this;
  }
  setServlet(servlet) {
    this.servlet = servlet;
    return this;
  }
  setPath(path) {
    this.path = path;
    return this;
  }
  setParams(params) {
    this.params = params;
    return this;
  }
  getUrl() {
    return new Url(this.context, this.servlet, this.path, this.params);
  }
}
export class Header {
  constructor(Content, Accept) {
    this.Content = Content;
    this.Accept = Accept;
  }
}
export class HeaderBuilder {
  constructor() {}

  Content;
  Accept;

  setContent(content) {
    this.Content = content;
    return this;
  }
  setAccept(accept) {
    this.Accept = accept;
    return this;
  }
  getHeader() {
    return new Header(this.Content, this.Accept);
  }
}

export class Res {
  constructor() {}
  status;
  body;
}

export class Event {
  constructor(element, event, func) {
    this.element = element;
    this.event = event;
    this.func = func;

    this.getEventHandler = function () {
      return element.addEventListener(event, func);
    };
  }
}

export var Admin = {
  Id: null,
  Name: null,
  Email: null,
  "Mobile No": null,
  Password: null,

  init() {
    initialize(this, new D.Admin());
  },
};

export var User = {
  Id: null,
  "Computer Id": null,
  Name: null,
  AdhaarId: null,
  Email: null,
  "Mobile No": null,
  Password: null,
  Shift: null,
  Role: null,
  Dept: null,
  WorkLoc: null,

  init() {
    initialize(this, new D.User());
  },
};

export var System = {
  Id: null,
  MAC: null,
  Password: null,
  Available: null,
  Working: null,
  Backup: null,
  Location: null,
  Model: null,
  Year: null,

  init() {
    initialize(this, new D.System());
  },
};

export var Session = {
  emp_Id: null,
  Name: null,
  comp_Id: null,
  "Login Date": null,
  "Logout Date": null,
  "Login Time": null,
  "Logout Time": null,
  "Total Time": null,
  Shift: null,

  init() {
    initialize(this, new D.Session());
  },
};

export let Shift = {
  Morning: "Morning",
  Evening: "Evening",
  Night: "Night",
};

function initialize(name, data) {
  let obj_keys = Object.keys(data);
  let name_keys = Object.keys(name);

  for (let i = 0; i < obj_keys.length; i++) name[name_keys[i]] = obj_keys[i];
}

export let initAll = function () {
  Admin.init();
  User.init();
  Session.init();
  System.init();
};

export let gEBI = (id) => {
  return document.getElementById(id);
};

export let getDate = () => {
  let temp = new Date();
  let yyyy = temp.getFullYear();
  let mm = temp.getMonth() + 1;
  let dd = temp.getDate();
  let date =
    yyyy + "-" + (mm < 10 ? "0" + mm : mm) + "-" + (dd < 10 ? "0" + dd : dd);
  return date;
};
export let getTime = () => {
  let temp = new Date();
  let hh = temp.getHours();
  let mm = temp.getMinutes();
  let time = (hh < 10 ? "0" + hh : hh) + ":" + (mm < 10 ? "0" + mm : mm);
  return time;
};

export let getShift = (time) => {
  return time >= "08:00" && time <= "16:00"
    ? Shift.Morning
    : time >= "16:00" && time <= "23:59"
    ? Shift.Evening
    : Shift.Night;
};
