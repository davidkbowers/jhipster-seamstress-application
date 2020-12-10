import { ICustOrder } from 'app/shared/model/cust-order.model';

export interface ICustomer {
  id?: number;
  custid?: string;
  custname?: string;
  custemail?: string;
  custphone?: string;
  custaddress?: string;
  custOrders?: ICustOrder[];
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public custid?: string,
    public custname?: string,
    public custemail?: string,
    public custphone?: string,
    public custaddress?: string,
    public custOrders?: ICustOrder[]
  ) {}
}
