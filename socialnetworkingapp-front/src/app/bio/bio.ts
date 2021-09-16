import {Account} from "../account/account";
import {Observable} from "rxjs";

export class Bio {

  id: number;
  description: string;

  constructor(descr: string) {
    this.description = descr;
  }
}
