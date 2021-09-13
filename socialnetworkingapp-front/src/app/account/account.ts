import {Bio} from "../bio/bio";

export enum AppUserRole {
  USER,
  ADMIN
}

export class Account {

  id: number;
  appUserRole: AppUserRole;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  phone: string;
  imageUrl: string;
  dateCreated: Date;
  bio: Bio;
  followers: Array<Account>;
  following: Array<Account>;

  constructor(acc: Account) {
    this.id = acc.id;
    this.appUserRole = acc.appUserRole;
    this.firstName = acc.firstName;
    this.lastName = acc.lastName;
    this.email = acc.email;
    this.password = acc.password;
    this.phone = acc.phone;
    this.imageUrl = acc.imageUrl;
    this.dateCreated = acc.dateCreated;
    this.bio = acc.bio;
    this.followers = acc.followers;
    this.following = acc.following;
  }
}
