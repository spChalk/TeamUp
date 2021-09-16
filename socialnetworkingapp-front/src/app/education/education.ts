
export class Education {

  id: number;
  school: string;
  degree: string;
  field: string;
  startDate: string;
  endDate: string;
  grade: number;
  description: string;

  constructor(id: number, school: string, degree: string, field: string, startDate: string, endDate: string, grade: number, description: string) {
    this.id = id;
    this.school = school;
    this.degree = degree;
    this.field = field;
    this.startDate = startDate;
    this.endDate = endDate;
    this.grade = grade;
    this.description = description;
  }
}
