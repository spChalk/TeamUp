import {Account} from "../account/account";

enum JobType {
  FULL_TIME,
  PART_TIME,
  INTERNSHIP,
  CONTRACT
}

enum ExperienceLevel {
  INTERNSHIP,
  ENTRY_LEVEL,
  ASSOCIATE,
  MIN_SENIOR,
  DIRECTOR,
  EXECUTIVE
}

export class Job {

  id: number;
  title: string;
  publisher: Account;
  location: string;
  datePosted: Date;
  jobType: JobType;
  experienceLevel: ExperienceLevel;
  info: string;

  constructor(title: string, publisher: Account, location: string, datePosted: Date, jobType: JobType, experienceLevel: ExperienceLevel, info: string) {
    this.title = title;
    this.publisher = publisher;
    this.location = location;
    this.datePosted = datePosted;
    this.jobType = jobType;
    this.experienceLevel = experienceLevel;
    this.info = info;
  }
}
