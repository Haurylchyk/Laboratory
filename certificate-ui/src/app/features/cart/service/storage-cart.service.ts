import {Injectable} from '@angular/core';
import {Cart} from '../../../core/model/cart';

@Injectable({
  providedIn: 'root'
})
export class StorageCartService {
  private CART_KEY = 'cart';

  constructor() {
  }

  clearCart(): void {
    localStorage.removeItem(this.CART_KEY);
  }

  getCart(): Cart {
    let cart = JSON.parse(localStorage.getItem(this.CART_KEY));
    if (!cart) {
      cart = new Cart();
      this.saveCart(cart);
    }
    return cart;
  }

  saveCart(cart: Cart): void {
    localStorage.removeItem(this.CART_KEY);
    localStorage.setItem(this.CART_KEY, JSON.stringify(cart));
  }
}
