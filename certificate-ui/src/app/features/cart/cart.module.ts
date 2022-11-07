import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CheckoutComponent} from './component/checkout/checkout.component';
import {MatIconModule} from '@angular/material/icon';
import {CartRoutingModule} from './cart-routing.module';
import {SharedModule} from '../../shared/shared.module';
import {MatButtonModule} from '@angular/material/button';

@NgModule({
  declarations: [CheckoutComponent],
  imports: [
    CommonModule,
    MatIconModule,
    CartRoutingModule,
    SharedModule,
    MatButtonModule
  ]
})
export class CartModule {
}
