import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICustOrder } from 'app/shared/model/cust-order.model';

type EntityResponseType = HttpResponse<ICustOrder>;
type EntityArrayResponseType = HttpResponse<ICustOrder[]>;

@Injectable({ providedIn: 'root' })
export class CustOrderService {
  public resourceUrl = SERVER_API_URL + 'api/cust-orders';

  constructor(protected http: HttpClient) {}

  create(custOrder: ICustOrder): Observable<EntityResponseType> {
    return this.http.post<ICustOrder>(this.resourceUrl, custOrder, { observe: 'response' });
  }

  update(custOrder: ICustOrder): Observable<EntityResponseType> {
    return this.http.put<ICustOrder>(this.resourceUrl, custOrder, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustOrder[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
