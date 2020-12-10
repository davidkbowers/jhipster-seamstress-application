import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustOrder } from 'app/shared/model/cust-order.model';

@Component({
  selector: 'jhi-cust-order-detail',
  templateUrl: './cust-order-detail.component.html',
})
export class CustOrderDetailComponent implements OnInit {
  custOrder: ICustOrder | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ custOrder }) => (this.custOrder = custOrder));
  }

  previousState(): void {
    window.history.back();
  }
}
