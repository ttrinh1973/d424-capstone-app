import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class PaymentService {

  private apiUrl = `${environment.apiUrl}/payments';

  constructor(private http: HttpClient) {}

  processPayment(data: any) {
    return this.http.post(
      `${this.api}/checkout`,
      data,
      { responseType: 'text' }
    );
  }
}
