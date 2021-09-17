
export class Education {

  id: number;
  school: string;
  degree: string;
  field: string;
  startDate: string;
  endDate: string;
  grade: number;
  description: string;
  visible: boolean;

  constructor(id: number, school: string, degree: string, field: string, startDate: string, endDate: string, grade: number, description: string, visible: boolean) {
    this.id = id;
    this.school = school;
    this.degree = degree;
    this.field = field;
    this.startDate = startDate;
    this.endDate = endDate;
    this.grade = grade;
    this.description = description;
    this.visible = visible;
  }
}
