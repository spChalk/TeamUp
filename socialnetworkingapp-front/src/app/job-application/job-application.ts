
export class JobApplication {

  id: number;
  applicantFirstName: string;
  applicantLastName: string;
  applicantEmail: string;
  applicantImage: string;
  jobId: number;

  constructor(id: number, applicantFirstName: string, applicantLastName: string, applicantEmail: string, applicantImage: string, jobId: number) {
    this.id = id;
    this.applicantFirstName = applicantFirstName;
    this.applicantLastName = applicantLastName;
    this.applicantEmail = applicantEmail;
    this.applicantImage = applicantImage;
    this.jobId = jobId;
  }
}
