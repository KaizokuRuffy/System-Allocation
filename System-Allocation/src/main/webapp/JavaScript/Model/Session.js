import * as D from "./Data/D.js";
import * as U from "./Util.js";
import { GET, Accept } from "./M.js";

export class Session {
  constructor(data, request, response) {
    this.data = data;
    this.response = response;
    this.request = request;
  }
  getAll() {
    this.request = new U.ReqBuilder()
      .setMethod(GET)
      .setHeader(new U.HeaderBuilder().setAccept(Accept.json).getHeader())
      .setUrl(
        new U.UrlBuilder()
          .setContext(U.Context)
          .setServlet(U.Controller.Session)
          .setPath("getAllSession")
          .getUrl()
      )
      .getReq();
    this.response = new U.Res();
    this.XHR = new U.XHR(this.request, this.response);

    this.response = this.XHR.sendRequest();

    return this.response;
  }
  getEmpSession(emp_Id) {
    let params = "?" + U.User.Id + "=" + emp_Id;

    this.request = new U.ReqBuilder()
      .setMethod(GET)
      .setHeader(new U.HeaderBuilder().setAccept(Accept.json).getHeader())
      .setUrl(
        new U.UrlBuilder()
          .setContext(U.Context)
          .setServlet(U.Controller.Session)
          .setPath("getEmpSession")
          .setParams(params)
          .getUrl()
      )
      .getReq();
    this.response = new U.Res();
    this.XHR = new U.XHR(this.request, this.response);

    this.response = this.XHR.sendRequest();

    return this.response;
  }
}
