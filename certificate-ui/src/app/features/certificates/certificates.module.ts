import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {CertificatesRoutingModule} from './certificates-routing.module';
import {CertificateListComponent} from './components/certificate-list/certificate-list.component';
import {ReactiveFormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import {MatSelectModule} from '@angular/material/select';
import {MatSliderModule} from '@angular/material/slider';
import {CertificateDetailsComponent} from './components/certificate-details/certificate-details.component';
import {AddCertificateComponent} from './components/add-certificate/add-certificate.component';
import {EditCertificateComponent} from './components/edit-certificate/edit-certificate.component';
import {SharedModule} from '../../shared/shared.module';
import {MatDialogModule} from '@angular/material/dialog';

@NgModule({
  declarations: [
    CertificateListComponent,
    CertificateDetailsComponent,
    AddCertificateComponent,
    EditCertificateComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    CertificatesRoutingModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule,
    MatSliderModule,
    SharedModule,
    MatDialogModule
  ]
})
export class CertificatesModule {
}
