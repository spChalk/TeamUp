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

export class Experience {

  id: number;
  title: string;
  employmentType: EmploymentType;
  company: string;
  location: string;
  startDate: string;
  endDate: string;
  headline: string;
  description: string;
}
