import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ReceiptService {

  private receiptData: any;

  setReceiptData(data: any) {
    this.receiptData = data;
  }

  getReceiptData() {
    return this.receiptData;
  }
}
