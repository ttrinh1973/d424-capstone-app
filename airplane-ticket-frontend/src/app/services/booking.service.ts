import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment';

export interface Booking {
  id?: number;
  passengerName: string;
  email: string;
  flightId: number;
  flight?: {
    airline?: string;
    price?: number;
  };
}

@Injectable({
  providedIn: 'root'
})
export class BookingService {


  private apiUrl = `${environment.apiUrl}/booking`;

  private headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  constructor(private http: HttpClient) {}


  createBooking(data: Booking): Observable<Booking> {
    return this.http.post<Booking>(
      this.apiUrl,
      data,
      { headers: this.headers }
    ).pipe(
      catchError(this.handleError)
    );
  }


  getAllBookings(): Observable<Booking[]> {
    return this.http.get<Booking[]>(
      this.apiUrl
    ).pipe(
      catchError(this.handleError)
    );
  }


  deleteBooking(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    ).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    console.error('API Error:', error);

    let message = 'Something went wrong';

    if (error.status === 0) {
      message = 'Cannot connect to backend (CORS or server down)';
    } else {
      message = `Server error (${error.status}): ${error.message}`;
    }

    return throwError(() => message);
  }
}
