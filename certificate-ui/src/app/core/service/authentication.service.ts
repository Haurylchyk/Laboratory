import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SignUpRequest} from '../../features/authentication/model/sign-up-request';
import {User} from '../model/user';
import {Observable} from 'rxjs';
import {LoginRequest} from '../../features/authentication/model/login-request';
import {LoginResponse} from '../../features/authentication/model/login-response';
import {UserRole} from '../model/enum/user-role';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private authBaseUrl = 'http://localhost:8080/users';
  private ID_KEY = 'user-id';
  private LOGIN_KEY = 'user-login';
  private ROLE_KEY = 'user-roles';
  private TOKEN_KEY = 'auth-token';

  constructor(
    private httpClient: HttpClient,
  ) {
  }

  signUp(signUpRequest: SignUpRequest): Observable<User> {
    return this.httpClient.post<User>(this.authBaseUrl, signUpRequest);
  }

  login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.httpClient.post<LoginResponse>(`${this.authBaseUrl}/auth`, loginRequest);
  }

  logout(): void {
    localStorage.clear();
  }

  public hasAnyRole(): boolean {
    return this.getUserRole() != null;
  }

  public hasRole(role: string): boolean {
    const userRoleName = this.getUserRole();
    if (userRoleName != null) {
      return userRoleName === role;
    }
    return false;
  }

  public saveUserId(id: number): void {
    localStorage.removeItem(this.ID_KEY);
    localStorage.setItem(this.ID_KEY, '' + id);
  }

  public getUserId(): string {
    return localStorage.getItem(this.ID_KEY);
  }

  public saveLogin(login: string): void {
    localStorage.removeItem(this.LOGIN_KEY);
    localStorage.setItem(this.LOGIN_KEY, login);
  }

  public getLogin(): string {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  public saveUserRole(role: UserRole): void {
    localStorage.removeItem(this.ROLE_KEY);
    localStorage.setItem(this.ROLE_KEY, role.toString());
  }

  public getUserRole(): string {
    const role = localStorage.getItem(this.ROLE_KEY);
    if (role) {
      return role;
    }
    return null;
  }

  public saveToken(token: string): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  public isLoggedIn(): boolean {
    return this.getToken() != null;
  }
}
