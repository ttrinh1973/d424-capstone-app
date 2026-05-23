import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {


  private apiUrl = `${environment.apiUrl}/payments`;

  constructor(private http: HttpClient) {}


  processPayment(data: any): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/checkout`,
      data,
      { responseType: 'text' }
    );
  }
}
