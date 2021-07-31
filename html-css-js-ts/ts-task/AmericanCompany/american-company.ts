interface IEmployee {
    name: string;
    project: string;

    getName(): string;

    getCurrentProject(): string;
}

class Company {
    employees: IEmployee[];

    constructor() {
        this.employees = [];
    }

    public add(employee: IEmployee): void {
        this.employees.push(employee);
    }

    public getProjectList(): string[] {
        return this.employees.map(e => e.getCurrentProject());
    }

    public getNameList(): string[] {
        return this.employees.map(e => e.getName());
    }
}

class Frontend implements IEmployee {
    name: string;
    project: string;

    constructor(name: string, project: string) {
        this.name = name;
        this.project = project;
    }

    getName(): string {
        return this.name;
    }

    getCurrentProject(): string {
        return this.project;
    }
}

class Backend implements IEmployee {
    name: string;
    project: string;

    constructor(name: string, project: string) {
        this.name = name;
        this.project = project;
    }

    getName(): string {
        return this.name;
    }

    getCurrentProject(): string {
        return this.project;
    }
}

const company: Company = new Company();

const employee1: IEmployee = new Frontend('Ivan', 'Project_A');
const employee2: IEmployee = new Frontend('Egor', 'Project_B');
const employee3: IEmployee = new Frontend('Ilona', 'Project_C');

const employee4: IEmployee = new Backend('Olga', 'Project_D');
const employee5: IEmployee = new Backend('Elena', 'Project_E');
const employee6: IEmployee = new Backend('Alex', 'Project_F');

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