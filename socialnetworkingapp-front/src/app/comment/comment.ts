import {Account} from "../account/account";
import {Post} from "../post/post";

export interface Comment {

  id: number;
  payload: string;
  commenter: Account;
  post: Post;
  date: Date;
}
