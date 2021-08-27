
export interface Account {

  id: number;
  /*appUserRole: AppUserRole;*/
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  phone: string;
  imageUrl: string;
  followers: Array<Account>;
  following: Array<Account>;
}
