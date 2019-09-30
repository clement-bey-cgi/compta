import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ComptaTestModule } from '../../../test.module';
import { FicheDeContactComponent } from 'app/entities/fiche-de-contact/fiche-de-contact.component';
import { FicheDeContactService } from 'app/entities/fiche-de-contact/fiche-de-contact.service';
import { FicheDeContact } from 'app/shared/model/fiche-de-contact.model';

describe('Component Tests', () => {
  describe('FicheDeContact Management Component', () => {
    let comp: FicheDeContactComponent;
    let fixture: ComponentFixture<FicheDeContactComponent>;
    let service: FicheDeContactService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ComptaTestModule],
        declarations: [FicheDeContactComponent],
        providers: []
      })
        .overrideTemplate(FicheDeContactComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FicheDeContactComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FicheDeContactService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FicheDeContact(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ficheDeContacts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
