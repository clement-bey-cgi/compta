import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ComptaTestModule } from '../../../test.module';
import { FicheDeContactDetailComponent } from 'app/entities/fiche-de-contact/fiche-de-contact-detail.component';
import { FicheDeContact } from 'app/shared/model/fiche-de-contact.model';

describe('Component Tests', () => {
  describe('FicheDeContact Management Detail Component', () => {
    let comp: FicheDeContactDetailComponent;
    let fixture: ComponentFixture<FicheDeContactDetailComponent>;
    const route = ({ data: of({ ficheDeContact: new FicheDeContact(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ComptaTestModule],
        declarations: [FicheDeContactDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FicheDeContactDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FicheDeContactDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ficheDeContact).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
