
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

export interface Job {

  id: number;
  title: string;
  company: string;
  location: string;
  datePosted: Date;
  jobType: JobType;
  experienceLevel: ExperienceLevel;
  info: string;
}
