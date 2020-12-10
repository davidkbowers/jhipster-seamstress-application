import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSeamstressApplicationTestModule } from '../../../test.module';
import { CustOrderDetailComponent } from 'app/entities/cust-order/cust-order-detail.component';
import { CustOrder } from 'app/shared/model/cust-order.model';

describe('Component Tests', () => {
  describe('CustOrder Management Detail Component', () => {
    let comp: CustOrderDetailComponent;
    let fixture: ComponentFixture<CustOrderDetailComponent>;
    const route = ({ data: of({ custOrder: new CustOrder(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSeamstressApplicationTestModule],
        declarations: [CustOrderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CustOrderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustOrderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load custOrder on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.custOrder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
