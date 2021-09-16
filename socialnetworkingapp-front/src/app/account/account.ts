import {Bio} from "../bio/bio";
import {Experience} from "../experience/experience";
import {Tag} from "../tags/Tag";
import {Education} from "../education/education";

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
  experience: Array<Experience>;
  tags: Array<Tag>;
  education: Array<Education>;

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
    this.experience = acc.experience;
    this.tags = acc.tags;
    this.education = acc.education;
  }
}
