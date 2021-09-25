
export class NetworkEntity {

  firstName: string;
  lastName: string;
  email: string;
  imageUrl: string;
  position: string;
  company: string;

  constructor(firstName: string, lastName: string, email: string, imageUrl: string, position: string, company: string) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.imageUrl = imageUrl;
    this.position = position;
    this.company = company;
  }
}
