import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

import { BookingService } from '../../services/booking.service';
import { FlightService } from '../../services/flight.service';

@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './booking.html',
  styleUrls: ['./booking.css']
})
export class BookingComponent implements OnInit {

  booking: any = {
    passengerName: '',
    email: '',
    flightId: 0,
    airline: '',
    price: 0
  };

  flights: any[] = [];
  bookings: any[] = [];

  loading: boolean = false;

  departure = '';
  destination = '';
  departureTime = '';
  arrivalTime = '';

  constructor(
    private bookingService: BookingService,
    private flightService: FlightService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.handleQueryParams();
    this.loadBookings();
  }

  loadFlights(): void {
    console.log('BUTTON CLICKED');

    this.loading = true;

    this.flightService.getAllFlights().subscribe({
      next: (data) => {
        console.log('SUCCESS RESPONSE:', data);

        this.flights = data;
        this.loading = false;
      },

      error: (err) => {
        console.error('ERROR RESPONSE:', err);

        this.loading = false;
      }
    });
  }

  selectFlight(flight: any): void {

    console.log('Selected flight:', flight);

    this.booking.flightId = flight.id;
    this.booking.airline = flight.airline;
    this.booking.price = flight.price;

    this.departure = flight.departure;
    this.destination = flight.destination;
    this.departureTime = flight.departureTime;
    this.arrivalTime = flight.arrivalTime;
  }


  handleQueryParams(): void {
    this.route.queryParams.subscribe(params => {

      this.booking.flightId = Number(params['flightId']) || 0;
      this.booking.airline = params['airline'] || '';
      this.booking.price = Number(params['price']) || 0;

      this.departure = params['departure'] || '';
      this.destination = params['destination'] || '';
      this.departureTime = params['departureTime'] || '';
      this.arrivalTime = params['arrivalTime'] || '';
    });
  }


  loadBookings(): void {
    this.bookingService.getAllBookings().subscribe({
      next: (data: any) => {
        this.bookings = data || [];
      },
      error: (err) => {
        console.error('Error loading bookings:', err);
      }
    });
  }


  bookFlight(): void {

    if (!this.booking.flightId) {
      alert('Please select a flight!');
      return;
    }

    if (!this.booking.passengerName || !this.booking.email) {
      alert('Please fill all fields!');
      return;
    }

    const payload = {
      passengerName: this.booking.passengerName,
      email: this.booking.email,
      flightId: this.booking.flightId
    };

    console.log('Sending booking:', payload);

    this.bookingService.createBooking(payload).subscribe({
      next: (res: any) => {

        const flightPrice = res?.flight?.price || this.booking.price || 0;
        const airline = res?.flight?.airline || this.booking.airline || 'Unknown';

        this.router.navigate(['/payment'], {
          queryParams: {
            bookingId: res.id,
            flightId: payload.flightId,
            passengerName: payload.passengerName,
            email: payload.email,
            price: flightPrice,
            airline: airline,
            departure: this.departure,
            destination: this.destination,
            departureTime: this.departureTime,
            arrivalTime: this.arrivalTime
          }
        });

      },
      error: (err) => {
        console.error('Booking failed:', err);
        alert('Booking failed!');
      }
    });
  }


  deleteBooking(id: number): void {
    this.bookingService.deleteBooking(id).subscribe({
      next: () => this.loadBookings(),
      error: (err) => console.error('Delete error:', err)
    });
  }

}
