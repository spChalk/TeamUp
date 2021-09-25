
export class PostView {

  id: number;
  firstName: string;
  lastName: string;
  email: string;
  imageUrl: string;

  constructor(id: number, firstName: string, lastName: string, email: string, imageUrl: string) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.imageUrl = imageUrl;
  }
}
