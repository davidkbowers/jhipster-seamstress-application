import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICustOrder, CustOrder } from 'app/shared/model/cust-order.model';
import { CustOrderService } from './cust-order.service';
import { CustOrderComponent } from './cust-order.component';
import { CustOrderDetailComponent } from './cust-order-detail.component';
import { CustOrderUpdateComponent } from './cust-order-update.component';

@Injectable({ providedIn: 'root' })
export class CustOrderResolve implements Resolve<ICustOrder> {
  constructor(private service: CustOrderService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((custOrder: HttpResponse<CustOrder>) => {
          if (custOrder.body) {
            return of(custOrder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CustOrder());
  }
}

export const custOrderRoute: Routes = [
  {
    path: '',
    component: CustOrderComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'CustOrders',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustOrderDetailComponent,
    resolve: {
      custOrder: CustOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CustOrders',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustOrderUpdateComponent,
    resolve: {
      custOrder: CustOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CustOrders',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustOrderUpdateComponent,
    resolve: {
      custOrder: CustOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CustOrders',
    },
    canActivate: [UserRouteAccessService],
  },
];
