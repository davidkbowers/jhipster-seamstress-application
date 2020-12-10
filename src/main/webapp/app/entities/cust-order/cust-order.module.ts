import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSeamstressApplicationSharedModule } from 'app/shared/shared.module';
import { CustOrderComponent } from './cust-order.component';
import { CustOrderDetailComponent } from './cust-order-detail.component';
import { CustOrderUpdateComponent } from './cust-order-update.component';
import { CustOrderDeleteDialogComponent } from './cust-order-delete-dialog.component';
import { custOrderRoute } from './cust-order.route';

@NgModule({
  imports: [JhipsterSeamstressApplicationSharedModule, RouterModule.forChild(custOrderRoute)],
  declarations: [CustOrderComponent, CustOrderDetailComponent, CustOrderUpdateComponent, CustOrderDeleteDialogComponent],
  entryComponents: [CustOrderDeleteDialogComponent],
})
export class JhipsterSeamstressApplicationCustOrderModule {}
