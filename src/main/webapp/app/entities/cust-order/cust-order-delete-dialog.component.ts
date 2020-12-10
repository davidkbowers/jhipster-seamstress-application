import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustOrder } from 'app/shared/model/cust-order.model';
import { CustOrderService } from './cust-order.service';

@Component({
  templateUrl: './cust-order-delete-dialog.component.html',
})
export class CustOrderDeleteDialogComponent {
  custOrder?: ICustOrder;

  constructor(protected custOrderService: CustOrderService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.custOrderService.delete(id).subscribe(() => {
      this.eventManager.broadcast('custOrderListModification');
      this.activeModal.close();
    });
  }
}
