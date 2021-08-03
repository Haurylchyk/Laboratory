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

@Component({
  selector: 'app-certificate-list',
  templateUrl: './certificate-list.component.html',
  styleUrls: ['./certificate-list.component.css']
})
export class CertificateListComponent implements OnInit {
  searchForm: FormGroup;

  certificateName = '';
  certificates: GiftCertificate[] = [];
  searchedTags: string[] = [];
  searchedPrice: number;
  allTags: Observable<Tag[]>;

  certificatesStartPage: number;
  certificatesPerPage: number;
  tagsStartPage: number;
  tagsPerPage: number;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private certificateService: GiftCertificateService,
    public authService: AuthenticationService,
    private tagService: TagService
  ) {
    this.certificatesStartPage = 1;
    this.certificatesPerPage = 20;
    this.tagsStartPage = 1;
    this.tagsPerPage = 2000;
  }

  ngOnInit(): void {
    this.searchForm = this.formBuilder.group({
      categories: ['', Validators.nullValidator],
      name: ['', Validators.nullValidator],
      price: ['', Validators.nullValidator],
    });
    this.extractGiftCertificates();
    this.allTags = this.extractTags();
  }

  private extractGiftCertificates(): void {
    const params = this.getCertificateRequestParams(
      this.certificateName,
      this.searchedTags,
      this.searchedPrice,
      this.certificatesStartPage,
      this.certificatesPerPage
    );
    this.certificateService.getAllCertificates(params)
      .subscribe(
        (certificates) => {
          this.certificates = certificates;
        });
  }

  private extractTags(): Observable<Tag[]> {
    const params = this.getTagRequestParams(
      this.tagsStartPage,
      this.tagsPerPage
    );
    return this.tagService.getAllTags(params);
  }

  onSubmit(): void {
    this.certificateName = this.searchForm.get('name').value;
    this.searchedTags = this.searchForm.get('categories').value;
    this.searchedPrice = this.searchForm.get('price').value;
    this.extractGiftCertificates();
  }

  private getCertificateRequestParams(
    certificateName: string,
    tags: string[],
    price: number,
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
      params = params.append(`priceFilterList`, 'lt:' + price.toString());
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

  redirectToCertificateDetails(certificateId: number): void {
    this.router.navigateByUrl(`/certificates/${certificateId}`);
  }

  redirectToNewCertificate(): void {
    this.router.navigateByUrl(`/certificates/add`);
  }

  showMoreCertificates(): void {
    this.certificatesPerPage = Number(this.certificatesPerPage) + 20;
    this.extractGiftCertificates();
  }
}
