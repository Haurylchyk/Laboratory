import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {Cart} from '../../../../core/model/cart';
import {Router} from '@angular/router';
import {CartManagerService} from '../../service/cart-manager.service';
import {ConfirmationDialogComponent} from '../../../../shared/component/dialod/confirmation-dialod/confirmation-dialog.component';
import {MatDialog} from '@angular/material/dialog';
import {OrderService} from '../../../../core/service/order.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  currentCart: Cart = new Cart();
  cart$: Observable<Cart>;

  dialogTitle = 'Do you really want to confirm this order?';

  constructor(
    private router: Router,
    private orderService: OrderService,
    private cartManager: CartManagerService,
    private dialog: MatDialog
  ) {
    this.cart$ = this.cartManager.cart;
  }

  ngOnInit(): void {
    this.cart$.subscribe(
      (cart) => {
        this.currentCart.certificateList = cart.certificateList;
        console.log(cart);
        this.currentCart.cost = cart.cost;
        this.currentCart.count = cart.count;
        this.currentCart.userId = cart.userId;
        console.log('userId - ' + this.currentCart.userId);
      });
  }

  removeCertificateFromCart(certificateId: number): void {
    this.cartManager.removeCertificate(certificateId);
  }

  redirectToCertificatesPage(): void {
    this.router.navigate(['/certificates']);
  }

  openOrderConfirmationDialog(): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: this.dialogTitle
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const idList: number[] = this.currentCart.certificateList.map(certificate => certificate.id);
        this.orderService.createOrder(this.currentCart.userId, idList)
          .subscribe(
            (order) => {
              console.log('The order was made successfully');
              this.cartManager.cleanCart();
            });
      }
      this.router.navigate(['/certificates']);
    });
  }
}
