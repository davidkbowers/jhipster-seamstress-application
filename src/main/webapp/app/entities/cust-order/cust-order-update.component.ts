import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICustOrder, CustOrder } from 'app/shared/model/cust-order.model';
import { CustOrderService } from './cust-order.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-cust-order-update',
  templateUrl: './cust-order-update.component.html',
})
export class CustOrderUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    custorderid: [null, [Validators.required]],
    custid: [],
    custorddesc: [null, [Validators.required, Validators.maxLength(1000)]],
    custordcost: [],
    custordpaid: [],
    customer: [],
  });

  constructor(
    protected custOrderService: CustOrderService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ custOrder }) => {
      this.updateForm(custOrder);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(custOrder: ICustOrder): void {
    this.editForm.patchValue({
      id: custOrder.id,
      custorderid: custOrder.custorderid,
      custid: custOrder.custid,
      custorddesc: custOrder.custorddesc,
      custordcost: custOrder.custordcost,
      custordpaid: custOrder.custordpaid,
      customer: custOrder.customer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const custOrder = this.createFromForm();
    if (custOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.custOrderService.update(custOrder));
    } else {
      this.subscribeToSaveResponse(this.custOrderService.create(custOrder));
    }
  }

  private createFromForm(): ICustOrder {
    return {
      ...new CustOrder(),
      id: this.editForm.get(['id'])!.value,
      custorderid: this.editForm.get(['custorderid'])!.value,
      custid: this.editForm.get(['custid'])!.value,
      custorddesc: this.editForm.get(['custorddesc'])!.value,
      custordcost: this.editForm.get(['custordcost'])!.value,
      custordpaid: this.editForm.get(['custordpaid'])!.value,
      customer: this.editForm.get(['customer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustOrder>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICustomer): any {
    return item.id;
  }
}
