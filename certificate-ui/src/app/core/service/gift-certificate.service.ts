import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GiftCertificate} from '../model/gift-certificate';
import {map} from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class GiftCertificateService {
  private certificateBaseUrl = 'http://localhost:8080/certificates';

  constructor(
    private httpClient: HttpClient,
  ) {
  }

  getAllCertificates(params: HttpParams): Observable<GiftCertificate[]> {
    return this.httpClient.get<GiftCertificate[]>(this.certificateBaseUrl, {params})
      .pipe(map((result: any) => result._embedded.giftCertificateDTOList));
  }
}
