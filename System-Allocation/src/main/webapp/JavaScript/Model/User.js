import * as D from "./Data/D.js";
import * as U from "./Util.js";

import { GET, Accept, POST, Content_type, PUT } from "./M.js";

export class User {
  constructor(data, request, response) {
    this.data = data;
    this.response = response;
    this.request = request;
  }
  login(comp_Id) {
    let params =
      "?" +
      U.User.Email +
      "=" +
      U.gEBI(U.User.Email).value +
      "&" +
      U.User.Password +
      "=" +
      U.gEBI(U.User.Password).value;

    this.data = new D.SessionBuilder()
      .setEmp_Id(-1)
      .setComp_Id(comp_Id)
      .setLogIn_Date(U.getDate())
      .setLogIn_Time(U.getTime())
      .getSession();

    this.request = new U.ReqBuilder()
      .setMethod(POST)
      .setHeader(
        new U.HeaderBuilder().setContent(Content_type.json).getHeader()
      )
      .setUrl(
        new U.UrlBuilder()
          .setContext(U.Context)
          .setServlet(U.Controller.User)
          .setPath("userLogin")
          .setParams(params)
          .getUrl()
      )
      .setBody(JSON.stringify(this.data))
      .getReq();

    this.response = new U.Res();

    this.XHR = new U.XHR(this.request, this.response);
    this.response = this.XHR.sendRequest();
    if (
      this.response.status === 200 &&
      !this.response.body.includes("comp_Id")
    ) {
      this.data[U.Session.emp_Id] = Number(this.response.body);
      afterLogin(comp_Id);
      sessionStorage.setItem("session", JSON.stringify(this.data));
      sessionStorage.setItem("who", "user");
    }

    return this.response;
  }
  logout() {
    this.data = JSON.parse(sessionStorage.getItem("session"));

    this.data[U.Session["Logout Date"]] = U.getDate();
    this.data[U.Session["Logout Time"]] = U.getTime();

    this.request = new U.ReqBuilder()
      .setMethod(POST)
      .setHeader(
        new U.HeaderBuilder().setContent(Content_type.json).getHeader()
      )
      .setUrl(
        new U.UrlBuilder()
          .setContext(U.Context)
          .setServlet(U.Controller.User)
          .setPath("userLogout")
          .getUrl()
      )
      .setBody(JSON.stringify(this.data))
      .getReq();

    this.response = new U.Res();

    this.XHR = new U.XHR(this.request, this.response);
    this.response = this.XHR.sendRequest();

    if (this.response.status === 200) sessionStorage.clear();

    return this.response;
  }
  getAll() {
    this.request = new U.ReqBuilder()
      .setMethod(GET)
      .setHeader(new U.HeaderBuilder().setAccept(Accept.json).getHeader())
      .setUrl(
        new U.UrlBuilder()
          .setContext(U.Context)
          .setServlet(U.Controller.User)
          .setPath("getAllUsers")
          .getUrl()
      )
      .getReq();
    this.response = new U.Res();
    this.XHR = new U.XHR(this.request, this.response);

    this.response = this.XHR.sendRequest();

    return this.response;
  }
  get(email) {
    let params =
      email === undefined
        ? "?" +
          U.User.Id +
          "=" +
          JSON.parse(sessionStorage.getItem("session"))[U.Session.emp_Id]
        : "?" + U.User.Email + "=" + email;

    this.request = new U.ReqBuilder()
      .setMethod(GET)
      .setHeader(
        new U.HeaderBuilder()
          .setContent(Content_type.urlencoded)
          .setAccept(Content_type.json)
          .getHeader()
      )
      .setUrl(
        new U.UrlBuilder()
          .setContext(U.Context)
          .setServlet(U.Controller.User)
          .setPath("getUser")
          .setParams(params)
          .getUrl()
      )
      .getReq();

    this.response = new U.Res();
    this.XHR = new U.XHR(this.request, this.response);
    this.response = this.XHR.sendRequest();
    return this.response;
  }
  getPlus(emp_Id) {
    let params = "?" + U.User.Id + "=" + emp_Id;
    this.request = new U.ReqBuilder()
      .setMethod(GET)
      .setHeader(
        new U.HeaderBuilder()
          .setContent(Content_type.urlencoded)
          .setAccept(Content_type.json)
          .getHeader()
      )
      .setUrl(
        new U.UrlBuilder()
          .setContext(U.Context)
          .setServlet(U.Controller.User)
          .setPath("getUser")
          .setParams(params)
          .getUrl()
      )
      .getReq();

    this.response = new U.Res();
    this.XHR = new U.XHR(this.request, this.response);
    this.response = this.XHR.sendRequest();
    return this.response;
  }
  add() {
    this.data = new D.UserBuilder()
      .setId(-1)
      .setName(U.gEBI(U.User.Name).value)
      .setCompId(U.gEBI(U.User["Computer Id"]).value)
      .setAdhaarId(U.gEBI(U.User.AdhaarId).value)
      .setEmail(U.gEBI(U.User.Email).value)
      .setMobileNo(U.gEBI(U.User["Mobile No"]).value)
      .setPassword(U.gEBI(U.User.Password).value)
      .setShift(U.gEBI(U.User.Shift).value)
      .setRole(U.gEBI(U.User.Role).value)
      .setWorkLoc(U.gEBI(U.User.WorkLoc).value)
      .setDept(U.gEBI(U.User.Dept).value)
      .getUser();

    this.request = new U.ReqBuilder()
      .setMethod(POST)
      .setHeader(new U.HeaderBuilder().setContent(Content_type.json))
      .setBody(JSON.stringify(this.data))
      .setUrl(
        new U.UrlBuilder()
          .setContext(U.Context)
          .setServlet(U.Controller.User)
          .setPath("addUser")
          .getUrl()
      );
    this.response = new U.Res();
    this.XHR = new U.XHR(this.request, this.response);
    this.response = this.XHR.sendRequest();

    return this.response;
  }
}

let afterLogin = (CID) => {
  sessionStorage.removeItem(U.System.Id);
  sessionStorage.removeItem("Username");
  sessionStorage.removeItem("Available");

  localStorage.setItem(U.User.Id, Number(sessionStorage.getItem(U.User.Id)));
  sessionStorage.removeItem(U.User.Id);

  localStorage.setItem(U.Session.comp_Id, CID);
  localStorage.setItem(U.Session["Login Time"], U.getTime());
  localStorage.setItem(U.Session["Login Date"], U.getDate());
};
