import {Account} from "../../account/account";
import {Tags} from "../Tags";

export class AccountInterest {

  id: number;
  account: Account;
  tag: Tags;

  constructor(account: Account, tag: Tags) {
    this.account = account;
    this.tag = tag;
  }
}
