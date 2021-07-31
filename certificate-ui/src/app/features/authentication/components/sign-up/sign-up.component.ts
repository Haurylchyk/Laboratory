import {Component, OnInit} from '@angular/core';
import {SignUpRequest} from '../../model/sign-up-request';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../../../core/service/authentication.service';


@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  signUpForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthenticationService,
  ) {
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/certificates']);
    }
  }

  ngOnInit(): void {
    this.signUpForm = this.formBuilder.group({
      login: ['', [Validators.required, Validators.pattern('^[A-z0-9_]+$'), Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.pattern('^[a-zA-z0-9!@#$%^&*]+$'), Validators.minLength(3)]],
      name: ['', Validators.required, Validators.pattern('^[A-zА-я_]+$'), Validators.minLength(3)],
    });
  }

  onSubmit(): void {
    const signUpRequest: SignUpRequest = {
      login: this.signUpForm.get('login').value,
      password: this.signUpForm.get('password').value,
      name: this.signUpForm.get('name').value
    };
    this.authService.signUp(signUpRequest).subscribe(data => {
      this.redirectToLoginPage();
    });
  }

  redirectToLoginPage(): void {
    this.router.navigateByUrl('/login');
  }
}
