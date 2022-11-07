import {Component, OnInit} from '@angular/core';
import {GiftCertificate} from '../../../../core/model/gift-certificate';
import {GiftCertificateService} from '../../../../core/service/gift-certificate.service';
import {AuthenticationService} from '../../../../core/service/authentication.service';
import {ActivatedRoute, Router} from '@angular/router';
import {CartManagerService} from '../../../cart/service/cart-manager.service';

@Component({
  selector: 'app-certificate-details',
  templateUrl: './certificate-details.component.html',
  styleUrls: ['./certificate-details.component.css']
})
export class CertificateDetailsComponent implements OnInit {
  certificate: GiftCertificate;
  hasAdminRole: boolean;
  hasSmthRole: string;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private certificateService: GiftCertificateService,
    public authService: AuthenticationService,
    private cartManager: CartManagerService
  ) {
  }

  ngOnInit(): void {
    this.hasAdminRole = this.authService.hasRole('ADMIN');
    this.hasSmthRole = this.authService.getUserRole();
    const certificateId = this.activatedRoute.snapshot.params.id;
    this.certificateService.getCertificateById(certificateId)
      .subscribe(
        (certificate) => {
          this.certificate = certificate;
        }, error => {
          this.router.navigate(['/404']);
        }
      );
  }

  addCertificateToCart(certificate: GiftCertificate): void {
    this.cartManager.addCertificate(certificate);
  }

  redirectToCertificatesPage(): void {
    this.router.navigate(['/certificates']);
  }
}
