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

class Company {
    private employees: Employee[];

    constructor() {
        this.employees = [];
    }

    public add(employee: Employee): void {
        this.employees.push(employee);
    }

    public getProjectList(): string[] {
        return this.employees.map(e => e.getCurrentProject());
    }

    public getNameList(): string[] {
        return this.employees.map(e => e.getName());
    }
}

class Frontend extends Employee {
    constructor(name: string, project: string) {
        super(name, project);
    }
}

class Backend extends Employee {
    constructor(name: string, project: string) {
        super(name, project);
    }
}

const company: Company = new Company();

const employee1: Employee = new Frontend('Ivan', 'Project_A');
const employee2: Employee = new Frontend('Egor', 'Project_B');
const employee3: Employee = new Frontend('Ilona', 'Project_C');

const employee4: Employee = new Backend('Olga', 'Project_D');
const employee5: Employee = new Backend('Elena', 'Project_E');
const employee6: Employee = new Backend('Alex', 'Project_F');


company.add(employee1);
company.add(employee2);
company.add(employee3);

company.add(employee4);
company.add(employee5);
company.add(employee6);

const employeeNames: string[] = company.getNameList();
employeeNames.forEach(n => console.log(n));

const companyProjects: string[] = company.getProjectList();
companyProjects.forEach(p => console.log(p));
