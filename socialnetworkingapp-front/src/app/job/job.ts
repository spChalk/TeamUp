import {Tag} from "../tags/Tag";
import {ExperienceLevel} from "./experienceLevel";
import {JobType} from "./jobType";

export class Job {

  id: number;
  title: string;
  publisherFirstName: string;
  publisherLastName: string;
  publisherEmail: string;
  publisherImage: string;
  location: string;
  date: string;
  jobType: JobType;
  experienceLevel: ExperienceLevel;
  info: string;
  tags: Tag[];

  constructor(id: number, title: string, publisherFirstName: string, publisherLastName: string, publisherEmail: string, publisherImage: string, location: string, datePosted: string, jobType: JobType, experienceLevel: ExperienceLevel, info: string, tags: Tag[]) {
    this.id = id;
    this.title = title;
    this.publisherFirstName = publisherFirstName;
    this.publisherLastName = publisherLastName;
    this.publisherEmail = publisherEmail;
    this.publisherImage = publisherImage;
    this.location = location;
    this.date = datePosted;
    this.jobType = jobType;
    this.experienceLevel = experienceLevel;
    this.info = info;
    this.tags = tags;
  }
}
