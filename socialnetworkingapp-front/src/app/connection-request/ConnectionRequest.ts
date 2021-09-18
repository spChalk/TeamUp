import {Account} from "../account/account";

export class ConnectionRequest {

  id: number;
  sender: Account;
  receiver: Account;

  constructor(sender: Account, receiver: Account) {
    this.sender = sender;
    this.receiver = receiver;
  }
}
