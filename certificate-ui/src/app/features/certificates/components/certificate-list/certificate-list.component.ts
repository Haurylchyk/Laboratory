import {Component, OnInit} from '@angular/core';
import {GiftCertificate} from '../../../../core/model/gift-certificate';
import {GiftCertificateService} from '../../../../core/service/gift-certificate.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../../../core/service/authentication.service';
import {Observable} from 'rxjs';
import {Tag} from '../../../../core/model/tag';
import {TagService} from '../../../../core/service/tag.service';
import {CartManagerService} from '../../../cart/service/cart-manager.service';
import {map} from 'rxjs/operators';
import {MatDialog} from '@angular/material/dialog';
import {ConfirmationDialogComponent} from '../../../../shared/component/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-certificate-list',
  templateUrl: './certificate-list.component.html',
  styleUrls: ['./certificate-list.component.css']
})
export class CertificateListComponent implements OnInit {
  hasAdminRole: boolean;
  hasSmthRole: boolean;

  searchForm: FormGroup;

  certificateName = '';
  certificates: GiftCertificate[] = [];
  certificates$: Observable<GiftCertificate[]>;

  searchedTags: string[] = [];
  searchedPrice: number;
  searchedDuration: number;
  allTags: Observable<Tag[]>;
  maxPrice: number;
  maxDuration: number;

  certificatesStartPage = 1;
  certificatesPerPage = 1000;
  tagsStartPage = 1;
  tagsPerPage = 2000;

  dialogTitle = 'Do you really want to delete this certificate?';

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private certificateService: GiftCertificateService,
    public authService: AuthenticationService,
    private tagService: TagService,
    private cartManager: CartManagerService,
    private dialog: MatDialog
  ) {
  }

  ngOnInit(): void {
    this.hasAdminRole = this.authService.hasRole('ADMIN');
    this.hasSmthRole = this.authService.hasRole('USER');
    this.searchForm = this.formBuilder.group({
      categories: ['', Validators.nullValidator],
      name: ['', Validators.nullValidator],
      price: ['', Validators.nullValidator],
      duration: ['', Validators.nullValidator]
    });
    this.extractGiftCertificates();
    this.allTags = this.extractTags();
    this.certificateService.getStatistic()
      .subscribe(
        (statistic) => {
          this.maxPrice = statistic.maxPrice;
          this.maxDuration = statistic.maxDuration;
        }, error => {
          this.redirectToErrorPage();
        });
  }

  private extractGiftCertificates(): void {
    const params = this.certificateService.getCertificateRequestParams(
      this.certificateName,
      this.searchedTags,
      this.searchedPrice,
      this.searchedDuration,
      this.certificatesStartPage,
      this.certificatesPerPage
    );
    this.certificates$ = this.certificateService.getAllCertificates(params);
    this.certificates$
      .subscribe(
        (certificates) => {
          if (certificates) {
            this.certificates = certificates;
          }
        }, error => {
          this.redirectToErrorPage();
        });
  }

  private extractTags(): Observable<Tag[]> {
    const params = this.certificateService.getTagRequestParams(
      this.tagsStartPage,
      this.tagsPerPage
    );
    return this.tagService.getAllTags(params).pipe(
      map(tags => tags.sort((a, b) => {
        return a.name === b.name ? 0 : a.name > b.name ? 1 : -1;
      }))
    );
  }

  onSubmit(): void {
    this.certificateName = this.searchForm.get('name').value;
    this.searchedTags = this.searchForm.get('categories').value;
    this.searchedPrice = this.searchForm.get('price').value;
    this.searchedDuration = this.searchForm.get('duration').value;
    this.extractGiftCertificates();
  }

  addCertificateToCart(certificate: GiftCertificate): void {
    this.cartManager.addCertificate(certificate);
  }

  showMoreCertificates(): void {
    this.certificatesPerPage = this.certificatesPerPage + 1;
    this.extractGiftCertificates();
  }

  openDeleteConfirmationDialog(certificate: GiftCertificate): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: this.dialogTitle
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.certificateService.deleteCertificate(certificate.id).subscribe(
          (data) => {
            console.log('The certificate was deleted successfully');
          });
        this.certificates = this.certificates.filter(cert => cert.id !== certificate.id);
      }
      this.router.navigate(['/certificates']);
    });
  }

  redirectToCertificateDetails(certificateId: number): void {
    this.router.navigateByUrl(`/certificates/${certificateId}`);
  }

  redirectToNewCertificate(): void {
    this.router.navigateByUrl(`/certificates/add`);
  }

  redirectToEditCertificatePage(certificateId: number): void {
    this.router.navigateByUrl(`certificates/${certificateId}/edit`);
  }

  redirectToErrorPage(): void {
    this.router.navigate(['error'],
      {
        queryParams: {errorCode: 500, errorMessage: 'There was an error. Please try again later.'},
        skipLocationChange: true
      });
  }
}
