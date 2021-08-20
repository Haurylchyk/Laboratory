import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {Cart} from '../../../core/model/cart';
import {AuthenticationService} from '../../../core/service/authentication.service';
import {StorageCartService} from './storage-cart.service';
import {GiftCertificate} from '../../../core/model/gift-certificate';

@Injectable({
  providedIn: 'root'
})
export class CartManagerService {
  private userCart = new BehaviorSubject<Cart>(new Cart());

  constructor(
    private storageCartService: StorageCartService,
    private authService: AuthenticationService
  ) {
    if (authService.hasRole('USER')) {
      this.getCartOrCreate();
    }
  }

  get cart(): Observable<Cart> {
    this.getCartOrCreate();
    return this.userCart.asObservable();
  }

  private getCartOrCreate(): void {
    let cart$: Observable<Cart>;
    if (this.authService.isLoggedIn()) {
      const cart: Cart = this.storageCartService.getCart();
      cart.userId = +this.authService.getUserId();
      this.storageCartService.clearCart();
      this.storageCartService.saveCart(cart);
    }
    cart$ = of(this.storageCartService.getCart());
    cart$.subscribe((updatedCart) => {
      console.log(updatedCart);
      this.userCart.next(updatedCart);
    });
  }

  addCertificate(certificate: GiftCertificate): void {
    if (this.authService.isLoggedIn()) {
      this.saveCertificateInStorage(certificate);
    }
  }

  removeCertificate(certificateId: number): void {
    if (certificateId == null) {
      return;
    }
    const cart: Cart = this.storageCartService.getCart();
    const cartCertificateList = cart.certificateList;
    const index = cartCertificateList.findIndex(giftCertificate => giftCertificate.id === certificateId);
    cartCertificateList.splice(index, 1);
    cart.certificateList = cartCertificateList;
    cart.cost = this.calculateCartCost(cart.certificateList);
    cart.count = this.getCertificatesCount(cart);
    this.storageCartService.saveCart(cart);
    const cart$ = of(cart);
    cart$.subscribe(updatedCart => this.userCart.next(updatedCart));
  }

  calculateCartCost(certificates: GiftCertificate[]): number {
    let total = 0;
    certificates.forEach(certificate => {
      total += certificate.price;
    });
    return total;
  }

  private getCertificatesCount(cart: Cart): number {
    return cart.certificateList.length;
  }

  private updateCart(cart: Cart, certificate: GiftCertificate): void {
    if (certificate == null) {
      return;
    }
    cart.certificateList.push(certificate);
    console.log(`The certificate(id = ${certificate.id}) has been added to the cart`);
    cart.cost = this.calculateCartCost(cart.certificateList);
    cart.count = this.getCertificatesCount(cart);
  }

  private saveCertificateInStorage(giftCertificate: GiftCertificate): void {
    const cart: Cart = this.storageCartService.getCart();
    this.updateCart(cart, giftCertificate);
    this.storageCartService.clearCart();
    this.storageCartService.saveCart(cart);
    console.log(cart);
    const cart$ = of(cart);
    cart$.subscribe(updatedCart => this.userCart.next(updatedCart));
  }

  cleanCart(): void {
    const cart: Cart = new Cart();
    cart.certificateList = [];
    this.storageCartService.saveCart(cart);
    const cart$ = of(cart);
    cart$.subscribe(updatedCart => this.userCart.next(updatedCart));
  }
}
