import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { environment } from '../../../environments/environment'; // ✅ IMPORTANT

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './admin.html',
  styleUrls: ['./admin.css']
})
export class AdminComponent {

  flights: any[] = [];

  editMode = false;
  editId: number | null = null;

  newFlight = {
    airline: '',
    departure: '',
    destination: '',
    departureTime: '',
    arrivalTime: '',
    price: 0
  };

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}


  loadFlights() {
    const email = localStorage.getItem('email');

    this.http.get<any[]>(
      `${environment.apiUrl}/api/admin/flights?email=${email}`
    ).subscribe({
      next: (data) => this.flights = data,
      error: (err) => console.log(err)
    });
  }


  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }


  submit() {
    const email = localStorage.getItem('email');

    if (this.editMode && this.editId) {

      this.http.put(
        `${environment.apiUrl}/api/admin/flights/${this.editId}?email=${email}`,
        this.newFlight
      ).subscribe(() => {
        this.resetForm();
        this.loadFlights();
      });

    } else {

      this.http.post(
        `${environment.apiUrl}/api/admin/flights?email=${email}`,
        this.newFlight
      ).subscribe(() => {
        this.resetForm();
        this.loadFlights();
      });
    }
  }


  editFlight(f: any) {
    this.editMode = true;
    this.editId = f.id;
    this.newFlight = { ...f };
  }


  deleteFlight(id: number) {
    const email = localStorage.getItem('email');

    this.http.delete(
      `${environment.apiUrl}/api/admin/flights/${id}?email=${email}`
    ).subscribe({
      next: () => {
        alert('Flight deleted successfully');
        this.loadFlights();
      },
      error: (err) => {
        console.error(err);
        alert('Delete failed');
      }
    });
  }


  resetForm() {
    this.editMode = false;
    this.editId = null;

    this.newFlight = {
      airline: '',
      departure: '',
      destination: '',
      departureTime: '',
      arrivalTime: '',
      price: 0
    };
  }
}
