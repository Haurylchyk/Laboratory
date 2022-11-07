import {UserRole} from '../../../core/model/enum/user-role';

export class LoginResponse {
  id: number;
  login: string;
  role: UserRole;
  token: string;
}
