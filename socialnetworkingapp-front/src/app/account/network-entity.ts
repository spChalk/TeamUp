
export class NetworkEntity {

  firstName: string;
  lastName: string;
  email: string;
  imageUrl: string;

  constructor(firstName: string, lastName: string, email: string, imageUrl: string) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.imageUrl = imageUrl;
  }
}
