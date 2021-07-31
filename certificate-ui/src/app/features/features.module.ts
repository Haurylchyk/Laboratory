import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AuthenticationModule} from './authentication/authentication.module';
import {CertificatesModule} from './certificates/certificates.module';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    AuthenticationModule,
    CertificatesModule
  ]
})
export class FeaturesModule {
}
