import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment';

export interface Flight {
  id: number;
  airline: string;
  departure: string;
  destination: string;
  departureTime: string;
  arrivalTime: string;
  price: number;
}

@Injectable({
  providedIn: 'root'
})
export class FlightService {


  private apiUrl = `${environment.apiUrl}/api/flights`;

  constructor(private http: HttpClient) {}


  getAllFlights(): Observable<Flight[]> {
    return this.http.get<Flight[]>(this.apiUrl)
      .pipe(catchError(this.handleError));
  }


  getFlightById(id: number): Observable<Flight> {
    return this.http.get<Flight>(`${this.apiUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }


  searchFlights(departure: string, destination: string): Observable<Flight[]> {
    return this.http.get<Flight[]>(
      `${this.apiUrl}/search?departure=${departure}&destination=${destination}`
    ).pipe(catchError(this.handleError));
  }


  private handleError(error: HttpErrorResponse) {
    console.error('🔥 Flight API Error:', error);

    let message = 'Something went wrong';

    if (error.status === 0) {
      message = 'Cannot connect to backend (CORS or server down)';
    } else {
      message = `Server error ${error.status}`;
    }

    return throwError(() => message);
  }
}
