import {UserRole} from './enum/user-role';

export class User {
  id: number;
  login: string;
  password: string;
  role: UserRole;
}
