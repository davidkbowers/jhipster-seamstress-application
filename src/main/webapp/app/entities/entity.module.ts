import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.JhipsterSeamstressApplicationCustomerModule),
      },
      {
        path: 'cust-order',
        loadChildren: () => import('./cust-order/cust-order.module').then(m => m.JhipsterSeamstressApplicationCustOrderModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JhipsterSeamstressApplicationEntityModule {}
