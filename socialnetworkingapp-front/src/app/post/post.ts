import {Account} from "../account/account";

export interface Post {

  id: number;
  title: string;
  payload: string;
  author: Account;
  date: Date;
  filePath: string;
}
