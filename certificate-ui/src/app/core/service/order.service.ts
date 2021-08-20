import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Order} from '../model/order';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private orderBaseUrl = 'http://localhost:8080/users';

  constructor(private httpClient: HttpClient) {
  }

  createOrder(userId: number, certificateIdList: number[]): Observable<Order> {
    return this.httpClient.post<Order>(`${this.orderBaseUrl}/${userId}/orders`, certificateIdList);
  }
}
