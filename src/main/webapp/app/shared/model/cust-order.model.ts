import { ICustomer } from 'app/shared/model/customer.model';

export interface ICustOrder {
  id?: number;
  custorderid?: string;
  custid?: string;
  custorddesc?: string;
  custordcost?: number;
  custordpaid?: number;
  customer?: ICustomer;
}

export class CustOrder implements ICustOrder {
  constructor(
    public id?: number,
    public custorderid?: string,
    public custid?: string,
    public custorddesc?: string,
    public custordcost?: number,
    public custordpaid?: number,
    public customer?: ICustomer
  ) {}
}
