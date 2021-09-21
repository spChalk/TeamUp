import {Tag} from "../tags/Tag";
import {JobType} from "./jobType";
import {ExperienceLevel} from "./experienceLevel";

export class JobRequest {

  title: string;
  location: string;
  jobType: JobType;
  experienceLevel: ExperienceLevel;
  info: string;
  tags: Tag[];

  constructor(title: string, location: string, jobType: JobType, experienceLevel: ExperienceLevel, info: string, tags: Tag[]) {
    this.title = title;
    this.location = location;
    this.jobType = jobType;
    this.experienceLevel = experienceLevel;
    this.info = info;
    this.tags = tags;
  }
}
