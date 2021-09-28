import {ExperienceLevel} from "../job/experienceLevel";
import {JobType} from "../job/jobType";

export class Experience {

  id: number;
  title: string;
  employmentType: JobType;
  experienceLevel: ExperienceLevel;
  company: string;
  location: string;
  startDate: string;
  endDate: string;
  headline: string;
  description: string;
  visible: boolean;

  constructor(id: number, title: string, employmentType: JobType, experienceLevel: ExperienceLevel, company: string, location: string, startDate: string, endDate: string, headline: string, description: string, visible: boolean) {
    this.id = id;
    this.title = title;
    this.employmentType = employmentType;
    this.experienceLevel = experienceLevel;
    this.company = company;
    this.location = location;
    this.startDate = startDate;
    this.endDate = endDate;
    this.headline = headline;
    this.description = description;
    this.visible = visible;
  }
}
