import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../../../core/service/authentication.service';
import {UserRole} from '../../../../core/model/enum/user-role';
import {LoginRequest} from '../../model/login-request';
import {LoginResponse} from '../../model/login-response';
import {map} from 'rxjs/operators';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthenticationService
  ) {
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/certificates']);
    }
  }

  private static redirectUser(role: UserRole): string {
    if (role === UserRole.ADMIN) {
      return 'admin/home';
    }
    return 'certificates';
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      login: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });
  }

  onSubmit(): void {
    const loginRequest: LoginRequest = {
      login: this.loginForm.get('login').value,
      password: this.loginForm.get('password').value
    };
    this.authService.login(loginRequest).pipe(
      map((loginResponse) => {
        this.saveUserInformation(loginResponse);
        return loginResponse;
      })
    ).subscribe(
      (user) => {
        const returnUrl = LoginComponent.redirectUser(user.role);
        this.router.navigateByUrl(returnUrl);
      }
    );
  }

  private saveUserInformation(user: LoginResponse): void {
    this.authService.saveLogin(user.login);
    this.authService.saveUserRole(user.role);
    this.authService.saveToken(user.token);
  }
}
