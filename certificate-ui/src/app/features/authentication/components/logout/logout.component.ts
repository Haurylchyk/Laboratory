import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../../../core/service/authentication.service';
import {CartManagerService} from '../../../cart/service/cart-manager.service';


@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {
  constructor(
    private authService: AuthenticationService,
    private router: Router,
    private cartManagerService: CartManagerService
  ) {
  }

  ngOnInit(): void {
    this.authService.logout();
    this.cartManagerService.cleanCart();
    this.router.navigateByUrl('login');
  }
}
