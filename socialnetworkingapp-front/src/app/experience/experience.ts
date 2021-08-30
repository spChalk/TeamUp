import {Account} from "../account/account";

enum EmploymentType {
  FULL_TIME,
  PART_TIME,
  SELF_EMPLOYED,
  FREELANCE,
  CONTRACT,
  INTERNSHIP,
  APPRENTICESHIP,
  SEASONAL
}

export interface Experience {

  id: number;
  title: string;
  employmentType: EmploymentType;
  company: string;
  location: string;
  startDate: Date;
  endDate: Date;
  headline: string;
  description: string;
  user: Account;
}
