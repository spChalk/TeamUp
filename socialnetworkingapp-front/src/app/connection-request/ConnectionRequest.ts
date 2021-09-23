import {Account} from "../account/account";

export class ConnectionRequest {

  id: number;
  sender: string;
  receiver: string;

  constructor(id: number, sender: string, receiver: string) {
    this.id = id;
    this.sender = sender;
    this.receiver = receiver;
  }
}
