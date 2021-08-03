import { Component, OnInit } from '@angular/core';
import {GiftCertificate} from '../../../../core/model/gift-certificate';
import {GiftCertificateService} from '../../../../core/service/gift-certificate.service';
import {AuthenticationService} from '../../../../core/service/authentication.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-certificate-details',
  templateUrl: './certificate-details.component.html',
  styleUrls: ['./certificate-details.component.css']
})
export class CertificateDetailsComponent implements OnInit {
  certificate: GiftCertificate;
  certificateId: number;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private certificateService: GiftCertificateService,
    public authService: AuthenticationService,
  ) {
  }

  ngOnInit(): void {
    this.certificateId = this.activatedRoute.snapshot.params.id;
    this.certificateService.getCertificateById(this.certificateId)
      .subscribe(
        (certificate) => {
          this.certificate = certificate;
        });
  }

  redirectToEditCertificatePage(certificateId: number): void {
    this.router.navigateByUrl(`certificates/${certificateId}/edit`);
  }
}
