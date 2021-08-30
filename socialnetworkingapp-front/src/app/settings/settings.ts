import {Account} from "../account/account";

export interface Message {

  id: number;
  settingName: string;
  settingValue: string;
  user: Account;
}
