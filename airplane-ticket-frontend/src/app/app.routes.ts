import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [

  // PUBLIC
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },

  {
    path: 'login',
    loadComponent: () =>
      import('./pages/login/login').then(m => m.LoginComponent)
  },

  {
    path: 'admin',
    loadComponent: () =>
      import('./pages/admin/admin').then(m => m.AdminComponent)
  },

  // PROTECTED
  {
    path: '',
    canActivate: [authGuard],
    children: [

      {
        path: 'home',
        loadComponent: () =>
          import('./pages/home/home').then(m => m.HomeComponent)
      },

      {
        path: 'flights',
        loadComponent: () =>
          import('./pages/search/search').then(m => m.SearchComponent)
      },

      {
        path: 'booking',
        loadComponent: () =>
          import('./pages/booking/booking').then(m => m.BookingComponent)
      },

      {
        path: 'payment',
        loadComponent: () =>
          import('./pages/payment/payment').then(m => m.PaymentComponent)
      },

      {
        path: 'receipt',
        loadComponent: () =>
          import('./pages/receipt/receipt').then(m => m.ReceiptComponent)
      },

      {
        path: 'about',
            loadComponent: () =>
               import('./pages/about/about').then(m => m.AboutComponent)
            }

    ]
  },

  {
    path: '**',
    redirectTo: 'login'
  }
];
