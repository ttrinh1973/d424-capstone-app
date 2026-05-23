import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './payment.html',
  styleUrls: ['./payment.css']
})
export class PaymentComponent {

  bookingId: number = 0;
  flightId: number = 0;
  airline: string = '';
  passengerName: string = '';
  email: string = '';
  price: number = 0;


  departure: string = '';
  destination: string = '';
  departureTime: string = '';
  arrivalTime: string = '';

  cardNumber: string = '';
  expiryDate: string = '';
  cvv: string = '';

  constructor(private route: ActivatedRoute, private router: Router) {

    this.route.queryParams.subscribe(params => {
      this.bookingId = Number(params['bookingId']);
      this.flightId = Number(params['flightId']);
      this.airline = params['airline'];
      this.passengerName = params['passengerName'];
      this.email = params['email'];
      this.price = Number(params['price']);


      this.departure = params['departure'] || '';
      this.destination = params['destination'] || '';
      this.departureTime = params['departureTime'] || '';
      this.arrivalTime = params['arrivalTime'] || '';
    });
  }

  payNow() {

    if (!this.cardNumber || !this.expiryDate || !this.cvv) {
      alert('Please fill card details');
      return;
    }


    const receipt = {
      bookingId: this.bookingId,
      flightId: this.flightId,
      airline: this.airline,
      passengerName: this.passengerName,
      email: this.email,
      price: this.price,
      departure: this.departure,
      destination: this.destination,
      departureTime: this.departureTime,
      arrivalTime: this.arrivalTime
    };


    this.router.navigate(['/receipt'], {
      state: {
        receipt: {
          bookingId: this.bookingId,
          flightId: this.flightId,
          airline: this.airline,
          passengerName: this.passengerName,
          email: this.email,
          price: this.price,

          departure: this.departure,
          destination: this.destination,
          departureTime: this.departureTime,
          arrivalTime: this.arrivalTime
        }
      }
    });
  }
}
