import {Account} from "../../account/account";
import {Tags} from "../Tags";

export class AccountInterest {

  account: Account;
  tag: Tags;

  constructor(account: Account, tag: Tags) {
    this.account = account;
    this.tag = tag;
  }
}
