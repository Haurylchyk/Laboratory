import {Component, OnInit} from '@angular/core';
import {GiftCertificate} from '../../../../core/model/gift-certificate';
import {GiftCertificateService} from '../../../../core/service/gift-certificate.service';
import {HttpParams} from '@angular/common/http';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../../../core/service/authentication.service';
import {Observable} from 'rxjs';
import {Tag} from '../../../../core/model/tag';
import {TagService} from '../../../../core/service/tag.service';
import {CartManagerService} from '../../../cart/service/cart-manager.service';
import {map} from 'rxjs/operators';
import {Statistic} from '../../../../core/model/statistic';
import {MatDialog} from '@angular/material/dialog';
import {ConfirmationDialogComponent} from '../../../../shared/component/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-certificate-list',
  templateUrl: './certificate-list.component.html',
  styleUrls: ['./certificate-list.component.css']
})
export class CertificateListComponent implements OnInit {
  isAdminRole: boolean;
  isUserRole: boolean;

  searchForm: FormGroup;

  certificateName = '';
  certificates: GiftCertificate[] = [];
  searchedTags: string[] = [];
  searchedPrice: number;
  searchedDuration: number;
  allTags: Observable<Tag[]>;
  maxPrice: number;
  maxDuration: number;

  certificatesStartPage: number;
  certificatesPerPage: number;
  tagsStartPage: number;
  tagsPerPage: number;

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
    this.certificatesStartPage = 1;
    this.certificatesPerPage = 20;
    this.tagsStartPage = 1;
    this.tagsPerPage = 2000;
  }

  ngOnInit(): void {
    this.isAdminRole = this.authService.hasRole('ADMIN');
    this.isUserRole = this.authService.hasRole('USER');
    this.searchForm = this.formBuilder.group({
      categories: ['', Validators.nullValidator],
      name: ['', Validators.nullValidator],
      price: ['', Validators.nullValidator],
      duration: ['', Validators.nullValidator]
    });
    this.extractGiftCertificates();
    this.allTags = this.extractTags();
    this.extractStatistic()
      .subscribe(
        (statistic) => {
          this.maxPrice = statistic.maxPrice;
          this.maxDuration = statistic.maxDuration;
        }
      );
  }

  private extractGiftCertificates(): void {
    const params = this.getCertificateRequestParams(
      this.certificateName,
      this.searchedTags,
      this.searchedPrice,
      this.searchedDuration,
      this.certificatesStartPage,
      this.certificatesPerPage
    );
    this.certificateService.getAllCertificates(params)
      .subscribe(
        (certificates) => {
          if (certificates) {
            this.certificates = certificates;
          }
        });
  }

  private getCertificateRequestParams(
    certificateName: string,
    tags: string[],
    price: number,
    duration: number,
    page: number,
    pageSize: number
  ): HttpParams {
    let params: HttpParams = new HttpParams();
    if (certificateName) {
      params = params.append('name', certificateName);
    }
    if (tags.length > 0) {
      params = params.append(`tagNameList`, tags.join(', '));
    }
    if (price > 0) {
      params = params.append(`priceFilterList`, 'le:' + price.toString());
    }
    if (duration > 0) {
      params = params.append(`durationFilterList`, 'le:' + duration.toString());
    }
    if (page) {
      page = page - 1;
      params = params.append('page', page.toString());
    }
    if (pageSize) {
      params = params.append('size', pageSize.toString());
    }
    console.log('Params - ' + params.toString());
    return params;
  }

  private extractTags(): Observable<Tag[]> {
    const params = this.getTagRequestParams(
      this.tagsStartPage,
      this.tagsPerPage
    );
    return this.tagService.getAllTags(params).pipe(
      map(tags => tags.sort((a, b) => {
        if (a.name > b.name) {
          return 1;
        }
        if (a.name < b.name) {
          return -1;
        }
        return 0;
      }))
    );
  }

  private getTagRequestParams(
    page: number,
    pageSize: number
  ): HttpParams {
    let params: HttpParams = new HttpParams();
    if (page) {
      page = page - 1;
      params = params.append('page', page.toString());
    }
    if (pageSize) {
      params = params.append('size', pageSize.toString());
    }
    return params;
  }

  private extractStatistic(): Observable<Statistic> {
    return this.certificateService.getStatistic();
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
    this.certificatesPerPage = Number(this.certificatesPerPage) + 1;
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
}
