
export class Post {

  id: number;
  payload: string;
  authorFirstName: string;
  authorLastName: string;
  authorEmail: string;
  authorImage: string;
  date: string;
  filePath: string;

  constructor(id: number, payload: string, authorFirstName: string, authorLastName: string, authorEmail: string, authorImage: string, date: string, filePath: string) {
    this.id = id;
    this.payload = payload;
    this.authorFirstName = authorFirstName;
    this.authorLastName = authorLastName;
    this.authorEmail = authorEmail;
    this.authorImage = authorImage;
    this.date = date;
    this.filePath = filePath;
  }
}
