
export class JobView {

  id: number;
  firstName: string;
  lastName: string;
  email: string;
  imageUrl: string;
  times: number;

  constructor(id: number, firstName: string, lastName: string, email: string, imageUrl: string, times: number) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.imageUrl = imageUrl;
    this.times = times;
  }
}
