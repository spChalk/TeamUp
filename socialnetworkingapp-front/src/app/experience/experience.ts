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
  visible: boolean;

  constructor(id: number, title: string, employmentType: EmploymentType, company: string, location: string, startDate: string, endDate: string, headline: string, description: string, visible: boolean) {
    this.id = id;
    this.title = title;
    this.employmentType = employmentType;
    this.company = company;
    this.location = location;
    this.startDate = startDate;
    this.endDate = endDate;
    this.headline = headline;
    this.description = description;
    this.visible = visible;
  }
}
