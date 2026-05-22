import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class PaymentService {

  private api = 'http://localhost:8080/api/payments';

  constructor(private http: HttpClient) {}

  processPayment(data: any) {
    return this.http.post(
      `${this.api}/checkout`,
      data,
      { responseType: 'text' }
    );
  }
}
