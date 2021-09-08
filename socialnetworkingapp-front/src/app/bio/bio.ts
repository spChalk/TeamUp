import {Account} from "../account/account";
import {Observable} from "rxjs";

export class Bio {

  id: number;
  description: string;
  account: Account;

  constructor(descr: string, account: Account) {

    this.description = descr;
    this.account = account;
  }
}
