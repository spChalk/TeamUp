import {Account} from "../account/account";

export interface Message {

  id: number;
  payload: string;
  sender: Account;
  receiver: Account;
}
