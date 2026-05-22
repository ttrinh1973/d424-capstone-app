import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {

  loginRequest = {
    email: '',
    password: ''
  };

  submitted = false;
  errorMessage = '';

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  // ✅ LOGIN METHOD
  login() {
    this.submitted = true;
    this.errorMessage = '';

    if (!this.loginRequest.email || !this.loginRequest.password) {
      this.errorMessage = 'Please enter both email and password';
      return;
    }

    this.http.post<any>('http://localhost:8080/api/auth/login', this.loginRequest)
      .subscribe({
        next: (res) => {

          localStorage.setItem('token', res.token);
          localStorage.setItem('role', res.role);
          localStorage.setItem('name', res.name);
          localStorage.setItem('email', this.loginRequest.email);

          if (res.role === 'ADMIN') {
            this.router.navigate(['/admin']);
          } else {
            this.router.navigate(['/home']);
          }
        },

        error: () => {
          this.errorMessage = 'Invalid email or password';
        }
      });
  }

  // ✅ LOGOUT (OUTSIDE login)
  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  goToRegister() {
    this.router.navigate(['/register']);
  }
}
