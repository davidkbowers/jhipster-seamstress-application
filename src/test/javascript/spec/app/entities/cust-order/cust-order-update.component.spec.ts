import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSeamstressApplicationTestModule } from '../../../test.module';
import { CustOrderUpdateComponent } from 'app/entities/cust-order/cust-order-update.component';
import { CustOrderService } from 'app/entities/cust-order/cust-order.service';
import { CustOrder } from 'app/shared/model/cust-order.model';

describe('Component Tests', () => {
  describe('CustOrder Management Update Component', () => {
    let comp: CustOrderUpdateComponent;
    let fixture: ComponentFixture<CustOrderUpdateComponent>;
    let service: CustOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSeamstressApplicationTestModule],
        declarations: [CustOrderUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CustOrderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustOrderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustOrderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustOrder(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustOrder();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
