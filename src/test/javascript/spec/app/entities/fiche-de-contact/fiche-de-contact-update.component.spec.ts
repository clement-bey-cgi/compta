import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ComptaTestModule } from '../../../test.module';
import { FicheDeContactUpdateComponent } from 'app/entities/fiche-de-contact/fiche-de-contact-update.component';
import { FicheDeContactService } from 'app/entities/fiche-de-contact/fiche-de-contact.service';
import { FicheDeContact } from 'app/shared/model/fiche-de-contact.model';

describe('Component Tests', () => {
  describe('FicheDeContact Management Update Component', () => {
    let comp: FicheDeContactUpdateComponent;
    let fixture: ComponentFixture<FicheDeContactUpdateComponent>;
    let service: FicheDeContactService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ComptaTestModule],
        declarations: [FicheDeContactUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FicheDeContactUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FicheDeContactUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FicheDeContactService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FicheDeContact(123);
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
        const entity = new FicheDeContact();
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
