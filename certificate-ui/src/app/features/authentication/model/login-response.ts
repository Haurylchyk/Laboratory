import {UserRole} from '../../../core/model/enum/user-role';

export class LoginResponse {
  login: string;
  role: UserRole;
  token: string;
}
