import * as D from "./Data/D.js";

import { Admin as A } from "./Admin.js";
import { User as U } from "./User.js";
import { System as Sy } from "./System.js";
import { Session as Se } from "./Session.js";

export var Admin = A;
export var User = U;
export var System = Sy;
export var Session = Se;

export const GET = "GET";
export const POST = "POST";
export const PUT = "PUT";
const DELETE = "DELETE";
export const Content_type = {
  urlencoded: "application/x-www-form-urlencoded",
  json: "application/json",
};
export const Accept = {
  text: "text/plain",
  json: "application/json",
};
