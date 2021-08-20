import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ErrorComponent} from './shared/component/error/error.component';

const routes: Routes = [
  {
    path: 'error/:code/:message',
    component: ErrorComponent,
  },
  {
    path: '**',
    redirectTo: 'error/404/The requested page was not found!'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
