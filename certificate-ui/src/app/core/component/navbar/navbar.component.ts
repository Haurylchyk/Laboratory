import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../service/authentication.service';
import {CartManagerService} from '../../../features/cart/service/cart-manager.service';
import {Observable} from 'rxjs';
import {Cart} from '../../model/cart';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  countTotal = 0;
  private cart: Observable<Cart>;

  constructor(public authService: AuthenticationService,
              private cartManager: CartManagerService
  ) {
    this.cart = this.cartManager.cart;
  }

  ngOnInit(): void {
    this.cart.subscribe(
      (cart) => {
        this.countTotal = cart.count;
      });
  }
}


