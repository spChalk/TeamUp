
enum AppUserRole {
  USER,
  ADMIN
}

export interface Account {

  id: number;
  appUserRole: AppUserRole;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  phone: string;
  imageUrl: string;
  dateCreated: Date;
  followers: Array<Account>;
  following: Array<Account>;
}
