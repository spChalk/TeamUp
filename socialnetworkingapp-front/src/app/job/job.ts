
enum JobType {
  FULL_TIME,
  PART_TIME,
  INTERNSHIP,
  CONTRACT,
  OTHER
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
  company: string;
  location: string;
  datePosted: Date;
  jobType: JobType;
  experienceLevel: ExperienceLevel;
  info: string;

  constructor(title: string, company: string, location: string, datePosted: Date, jobType: JobType, experienceLevel: ExperienceLevel, info: string) {
    this.title = title;
    this.company = company;
    this.location = location;
    this.datePosted = datePosted;
    this.jobType = jobType;
    this.experienceLevel = experienceLevel;
    this.info = info;
  }
}
