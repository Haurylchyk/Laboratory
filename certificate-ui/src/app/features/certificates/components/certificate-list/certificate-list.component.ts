import {Component, OnInit} from '@angular/core';
import {GiftCertificate} from '../../../../core/model/gift-certificate';
import {GiftCertificateService} from '../../../../core/service/gift-certificate.service';
import {HttpParams} from '@angular/common/http';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../../../core/service/authentication.service';

@Component({
  selector: 'app-certificate-list',
  templateUrl: './certificate-list.component.html',
  styleUrls: ['./certificate-list.component.css']
})
export class CertificateListComponent implements OnInit {
  searchForm: FormGroup;

  certificateName = '';
  certificates: GiftCertificate[] = [];

  certificatesStartPage: number;
  certificatesPerPage: number;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private certificateService: GiftCertificateService,
    public authService: AuthenticationService,
  ) {
    this.certificatesStartPage = 1;
    this.certificatesPerPage = 20;
  }

  ngOnInit(): void {
    this.searchForm = this.formBuilder.group({
      categories: ['', Validators.nullValidator],
      name: ['', Validators.nullValidator],
    });
    this.searchGiftCertificates();
  }

  private searchGiftCertificates(): void {
    const params = this.getCertificateRequestParams(
      this.certificateName,
      this.certificatesStartPage,
      this.certificatesPerPage
    );
    this.certificateService.getAllCertificates(params)
      .subscribe(
        (certificates) => {
          this.certificates = certificates;
        });
  }

  onSubmit(): void {
    this.certificateName = this.searchForm.get('name').value;
    this.searchGiftCertificates();
  }

  private getCertificateRequestParams(
    certificateName: string,
    page: number,
    pageSize: number
  ): HttpParams {
    let params: HttpParams = new HttpParams();
    if (certificateName) {
      params = params.append('name', certificateName);
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
}
