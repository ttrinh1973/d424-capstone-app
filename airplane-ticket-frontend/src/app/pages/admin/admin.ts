import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './admin.html',
  styleUrls: ['./admin.css'] // ✅ FIX (was styleUrl)
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
    private router: Router // ✅ ADD THIS
  ) {}

  // =====================
  // LOAD FLIGHTS
  // =====================
  loadFlights() {
    const email = localStorage.getItem('email');

    this.http.get<any[]>(
      `http://localhost:8080/api/admin/flights?email=${email}`
    ).subscribe({
      next: (data) => this.flights = data,
      error: (err) => console.log(err)
    });
  }

  // =====================
  // LOGOUT
  // =====================
  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  // =====================
  // SUBMIT (ADD / UPDATE)
  // =====================
  submit() {
    const email = localStorage.getItem('email');

    if (this.editMode && this.editId) {

      this.http.put(
        `http://localhost:8080/api/admin/flights/${this.editId}?email=${email}`,
        this.newFlight
      ).subscribe(() => {
        this.resetForm();
        this.loadFlights();
      });

    } else {

      this.http.post(
        `http://localhost:8080/api/admin/flights?email=${email}`,
        this.newFlight
      ).subscribe(() => {
        this.resetForm();
        this.loadFlights();
      });
    }
  }

  // =====================
  // EDIT FLIGHT
  // =====================
  editFlight(f: any) {
    this.editMode = true;
    this.editId = f.id;
    this.newFlight = { ...f };
  }

  // =====================
  // DELETE FLIGHT
  // =====================
  deleteFlight(id: number) {
    const email = localStorage.getItem('email');

    this.http.delete(
      `http://localhost:8080/api/admin/flights/${id}?email=${email}`
    ).subscribe({
      next: () => {
        alert('Flight deleted successfully');
        this.loadFlights();
      },
      error: (err) => {
        console.error('DELETE ERROR:', err);
        alert('Delete failed (500). Check backend logs.');
      }
    });
  }

  // =====================
  // RESET FORM
  // =====================
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
