import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { FlightService } from '../../services/flight.service';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './search.html',
  styleUrls: ['./search.css']
})
export class SearchComponent {

  departure = '';
  destination = '';
  flights: any[] = [];
  loading = false;

  constructor(
    private router: Router,
    private flightService: FlightService
  ) {}


  search() {

    const dep = this.departure.trim();
    const dest = this.destination.trim();

    if (!dep || !dest) {
      alert('Please enter departure and destination');
      return;
    }

    this.loading = true;

    this.flightService.searchFlights(dep, dest)
      .subscribe({
        next: (data: any) => {

          console.log('Search result:', data);

          this.flights = Array.isArray(data) ? data : [];

          this.loading = false;

          if (this.flights.length === 0) {
            alert(`❌ No flights found from ${dep} to ${dest}`);
          }

        },
        error: (err: any) => {

          console.error('Search error:', err);

          this.flights = [];
          this.loading = false;

          alert('❌ Error searching flights');
        }
      });
  }


  bookFlight(f: any) {

    if (!f?.id) {
      alert('Invalid flight selection');
      return;
    }

    this.router.navigate(['/booking'], {
      queryParams: {
        flightId: f.id,
        airline: f.airline,
        price: f.price,
        departure: f.departure,
        destination: f.destination,
        departureTime: f.departureTime,
        arrivalTime: f.arrivalTime
      }
    });
  }
}
