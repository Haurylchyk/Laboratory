class Employee {
    private name: string;
    private project: string;

    constructor(name: string, project: string) {
        this.name = name;
        this.project = project;
    }

    public getName(): string {
        return this.name;
    }

    public getCurrentProject(): string {
        return this.project;
    }
}

interface ILocation {
    addPerson(employee: Employee): void;

    getPerson(index: number): Employee;

    getCount(): number;

    getPersons(): Employee[];
}

class CompanyLocationArray implements ILocation {
    private employees: Employee[] = [];

    addPerson(employee: Employee): void {
        this.employees.push(employee);
    }

    getCount(): number {
        return this.employees.length;
    }

    getPerson(index: number): Employee {
        return this.employees[index];
    }

    getPersons(): Employee[] {
        return this.employees;
    }
}

class CompanyLocationLocalStorage implements ILocation {

    addPerson(employee: Employee): void {
        localStorage.setItem('employees', JSON.stringify(employee));
    }

    getCount(): number {
        const employees = this.getPersons();
        return employees.length;
    }

    getPerson(index: number): Employee {
        const employees = this.getPersons();
        return employees[index];
    }

    getPersons(): Employee[] {
        const employeesJson = localStorage.getItem('employees');
        return JSON.parse(employeesJson);
    }
}

class Company {
    private location: ILocation;

    constructor(location: ILocation) {
        this.location = location;
    }

    public addEmployee(employee: Employee): void {
        this.location.addPerson(employee);
    }

    public getProjectList(): string[] {
        return this.location.getPersons().map(e => e.getCurrentProject());
    }

    public getNameList(): string[] {
        return this.location.getPersons().map(e => e.getName());
    }
}

console.log('The first company:');
const locationArray: ILocation = new CompanyLocationArray();
const company1: Company = new Company(locationArray);
const employee1: Employee = new Employee('Ivan', 'Project_A');
const employee2: Employee = new Employee('Egor', 'Project_B');
const employee3: Employee = new Employee('Ilona', 'Project_C');

company1.addEmployee(employee1);
company1.addEmployee(employee2);
company1.addEmployee(employee3);

const employeeNames1: string[] = company1.getNameList();
employeeNames1.forEach(n => console.log(n));
const companyProjects1: string[] = company1.getProjectList();
companyProjects1.forEach(p => console.log(p));

console.log('The second company:');
const locationLocalStorage: ILocation = new CompanyLocationArray();
const company2: Company = new Company(locationLocalStorage);
const employee4: Employee = new Employee('Olga', 'Project_D');
const employee5: Employee = new Employee('Elena', 'Project_E');
const employee6: Employee = new Employee('Alex', 'Project_F');
company2.addEmployee(employee4);
company2.addEmployee(employee5);
company2.addEmployee(employee6);
const employeeNames2: string[] = company2.getNameList();
employeeNames2.forEach(n => console.log(n));
const companyProjects2: string[] = company2.getProjectList();
companyProjects2.forEach(p => console.log(p));