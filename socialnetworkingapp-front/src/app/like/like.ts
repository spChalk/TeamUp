import {Account} from "../account/account";
import {Post} from "../post/post";

export interface Like {

  id: number;
  user: Account;
  post: Post;
}
