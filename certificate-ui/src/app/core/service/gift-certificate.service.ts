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
      .pipe(map((result: any) => result._embedded !== undefined ? result._embedded.giftCertificateDTOList : []));
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
}
