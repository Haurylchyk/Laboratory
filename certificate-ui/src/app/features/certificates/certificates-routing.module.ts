import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CertificateListComponent} from './components/certificate-list/certificate-list.component';
import {CertificateDetailsComponent} from './components/certificate-details/certificate-details.component';
import {AddCertificateComponent} from './components/add-certificate/add-certificate.component';
import {EditCertificateComponent} from './components/edit-certificate/edit-certificate.component';

const routes: Routes = [
  {path: 'certificates', component: CertificateListComponent},
  {path: 'certificates/add', component: AddCertificateComponent},
  {path: 'certificates/:id', component: CertificateDetailsComponent},
  {path: 'certificates/:id/edit', component: EditCertificateComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CertificatesRoutingModule {
}
