import {Component, OnInit} from '@angular/core';
import {GiftCertificate} from '../../../../core/model/gift-certificate';
import {GiftCertificateService} from '../../../../core/service/gift-certificate.service';
import {AuthenticationService} from '../../../../core/service/authentication.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Statistic} from '../../../../core/model/statistic';
import {CartManagerService} from '../../../cart/service/cart-manager.service';

@Component({
  selector: 'app-certificate-details',
  templateUrl: './certificate-details.component.html',
  styleUrls: ['./certificate-details.component.css']
})
export class CertificateDetailsComponent implements OnInit {
  certificate: GiftCertificate;
  certificateId: number;
  statistic: Statistic;
  isAdminRole: boolean;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private certificateService: GiftCertificateService,
    public authService: AuthenticationService,
    private cartManager: CartManagerService
  ) {
  }

  ngOnInit(): void {
    this.isAdminRole = this.authService.hasRole('ADMIN');
    this.certificateId = this.activatedRoute.snapshot.params.id;
    this.certificateService.getCertificateById(this.certificateId)
      .subscribe(
        (certificate) => {
          this.certificate = certificate;
        }, error => {
          this.router.navigate(['/404']);
        }
      );
    this.certificateService.getStatistic()
      .subscribe(
        (statistic) => {
          this.statistic = statistic;
        });
  }

  addCertificateToCart(certificate: GiftCertificate): void {
    this.cartManager.addCertificate(certificate);
  }

  redirectToCertificatesPage(): void {
    this.router.navigate(['/certificates']);
  }
}
