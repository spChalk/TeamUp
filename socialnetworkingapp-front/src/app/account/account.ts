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

  constructor(id: number, appUserRole: AppUserRole, firstName: string, lastName: string, email: string, password: string, phone: string, imageUrl: string, dateCreated: Date, bio: Bio, followers: Array<Account>, following: Array<Account>, authorities: []) {
    this.id = id;
    this.appUserRole = appUserRole;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.imageUrl = imageUrl;
    this.dateCreated = dateCreated;
    this.bio = bio;
    this.followers = followers;
    this.following = following;
  }
}
