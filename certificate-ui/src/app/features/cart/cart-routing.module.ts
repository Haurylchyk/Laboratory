import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {CheckoutComponent} from './component/checkout/checkout.component';
import {AuthGuard} from '../../core/guard/auth.guard';
import {UserRole} from '../../core/model/enum/user-role';

const routes: Routes = [
  {
    path: 'cart',
    component: CheckoutComponent,
    canActivate: [AuthGuard],
    data: {role: [UserRole.USER]}
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CartRoutingModule {
}
