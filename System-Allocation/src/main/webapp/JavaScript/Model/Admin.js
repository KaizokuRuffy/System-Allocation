import * as D from "./Data/D.js";
import * as U from "./Util.js";
import { GET, Accept, POST, Content_type, PUT } from "./M.js";

export class Admin {
  constructor(data, request, response, XHR) {
    this.data = data;
    this.response = response;
    this.request = request;
    this.XHR = XHR;
  }

  isPresent() {
    this.request = new U.ReqBuilder()
      .setMethod(GET)
      .setHeader(new U.HeaderBuilder().setAccept(Accept.text).getHeader())
      .setUrl(
        new U.UrlBuilder()
          .setContext(U.Context)
          .setServlet(U.Controller.Admin)
          .setPath("adminPresent")
          .getUrl()
      )
      .getReq();
    this.response = new U.Res();
    this.XHR = new U.XHR(this.request, this.response);

    this.response = this.XHR.sendRequest();

    return this.response;
  }
  create() {
    this.request = new U.ReqBuilder()
      .setMethod(GET)
      .setUrl(
        new U.UrlBuilder()
          .setContext(U.Context)
          .setServlet(U.Controller.Admin)
          .setPath("create")
          .getUrl()
      )
      .getReq();

    this.response = new U.Res();

    this.XHR = new U.XHR(this.request, this.response);
    this.response = this.XHR.sendRequest();

    return this.response;
  }
  login() {
    this.data = new D.AdminBuilder()
      .setEmail(U.gEBI(U.Admin.Email).value)
      .setPassword(U.gEBI(U.Admin.Password).value)
      .getAdmin();

    let params =
      "?" +
      U.Admin.Email +
      "=" +
      this.data[U.Admin.Email] +
      "&" +
      U.Admin.Password +
      "=" +
      this.data[U.Admin.Password];
    this.request = new U.ReqBuilder()
      .setMethod(POST)
      .setHeader(
        new U.HeaderBuilder()
          .setContent(Content_type.urlencoded)
          .setAccept(Accept.text)
          .getHeader()
      )
      .setUrl(
        new U.UrlBuilder()
          .setContext(U.Context)
          .setServlet(U.Controller.Admin)
          .setPath("adminLogin")
          .setParams(params)
          .getUrl()
      )
      .getReq();

    this.response = new U.Res();
    this.XHR = new U.XHR(this.request, this.response);
    this.response = this.XHR.sendRequest();
    if (this.response.status === 200) {
      sessionStorage.setItem(
        U.Admin.Id,
        Number(this.response.body.replace("admin_Id :", ""))
      );
      sessionStorage.setItem("who", "admin");
    }

    return this.response;
  }

  get() {
    let params = "?" + U.Admin.Id + "=" + sessionStorage.getItem(U.Admin.Id);
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
          .setServlet(U.Controller.Admin)
          .setPath("getAdmin")
          .setParams(params)
          .getUrl()
      )
      .getReq();

    this.response = new U.Res();
    this.XHR = new U.XHR(this.request, this.response);
    this.response = this.XHR.sendRequest();
    return this.response;
  }

  logout() {
    this.request = new U.ReqBuilder()
      .setMethod(GET)
      .setUrl(
        new U.UrlBuilder()
          .setContext(U.Context)
          .setServlet(U.Controller.Admin)
          .setPath("adminLogout")
          .getUrl()
      )
      .getReq();
    this.response = new U.Res();
    this.XHR = new U.XHR(this.request, this.response);

    this.response = this.XHR.sendRequest();

    if (this.response.status === 200) sessionStorage.clear();

    return this.response;
  }
  add() {
    this.data = new D.AdminBuilder()
      .setId(-1)
      .setName(U.gEBI(U.Admin.Name).value)
      .setEmail(U.gEBI(U.Admin.Email).value)
      .setContactNo(U.gEBI(U.Admin["Mobile No"]).value)
      .setPassword(U.gEBI(U.Admin.Password).value)
      .getAdmin();

    this.request = new U.ReqBuilder()
      .setMethod(POST)
      .setHeader(new U.HeaderBuilder().setContent(Content_type.json))
      .setUrl(
        new U.UrlBuilder()
          .setContext(U.Context)
          .setServlet(U.Controller.Admin)
          .setPath("addAdmin")
          .getUrl()
      )
      .setBody(JSON.stringify(this.data))
      .getReq();

    this.response = new U.Res();

    this.XHR = new U.XHR(this.request, this.response);
    this.response = this.XHR.sendRequest();
    return this.response;
  }
}
