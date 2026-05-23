import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-receipt',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './receipt.html',
  styleUrls: ['./receipt.css']
})
export class ReceiptComponent {

  receipt: any = null;

  constructor() {
    const nav = history.state;


    this.receipt = nav?.receipt ?? null;

    console.log('Receipt data:', this.receipt);
  }
}
