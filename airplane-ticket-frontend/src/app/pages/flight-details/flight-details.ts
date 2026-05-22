import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';

import { FlightService, Flight } from '../../services/flight.service';

@Component({
  selector: 'app-flight-details',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './flight-details.html',
  styleUrls: ['./flight-details.css']
})
export class FlightDetailsComponent implements OnInit {

  flight: Flight | null = null;
  flights: Flight[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private flightService: FlightService
  ) {}

  ngOnInit(): void {

    const id = this.route.snapshot.paramMap.get('id');


    if (id) {
      this.flightService.getFlightById(Number(id)).subscribe({
        next: (data: Flight) => {
          this.flight = data;
        },
        error: (err) => {
          console.error('Error loading flight:', err);
          this.flight = null;
        }
      });


    } else {
      this.flightService.getAllFlights().subscribe({
        next: (data: Flight[]) => {
          this.flights = data;
        },
        error: (err) => {
          console.error('Error loading flights:', err);
          this.flights = [];
        }
      });
    }
  }


  goBack(): void {
    this.router.navigate(['/search']);
  }

  viewAllFlights(): void {
    this.router.navigate(['/flight-details']);
  }
}
