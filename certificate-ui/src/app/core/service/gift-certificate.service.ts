import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GiftCertificate} from '../model/gift-certificate';
import {map} from 'rxjs/operators';
import {Statistic} from '../model/statistic';

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
      .pipe(map((result: any) => result._embedded ? result._embedded.giftCertificateDTOList : []));
  }

  getCertificateById(certificateId: number): Observable<GiftCertificate> {
    return this.httpClient.get<GiftCertificate>(this.certificateBaseUrl + `/${certificateId}`);
  }

  createCertificate(certificate: GiftCertificate): Observable<GiftCertificate> {
    return this.httpClient.post<GiftCertificate>(this.certificateBaseUrl, certificate);
  }

  updateCertificate(certificate: GiftCertificate, certificateId: number): Observable<GiftCertificate> {
    return this.httpClient.put<GiftCertificate>(this.certificateBaseUrl + `/${certificateId}`, certificate);
  }

  deleteCertificate(certificateId: number): Observable<GiftCertificate> {
    return this.httpClient.delete<GiftCertificate>(this.certificateBaseUrl + `/${certificateId}`);
  }

  getStatistic(): Observable<Statistic> {
    return this.httpClient.get<Statistic>(this.certificateBaseUrl + '/stat');
  }

  getCertificateRequestParams(
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
    return params;
  }

  getTagRequestParams(
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
}
