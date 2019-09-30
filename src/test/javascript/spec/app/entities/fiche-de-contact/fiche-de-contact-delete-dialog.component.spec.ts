import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ComptaTestModule } from '../../../test.module';
import { FicheDeContactDeleteDialogComponent } from 'app/entities/fiche-de-contact/fiche-de-contact-delete-dialog.component';
import { FicheDeContactService } from 'app/entities/fiche-de-contact/fiche-de-contact.service';

describe('Component Tests', () => {
  describe('FicheDeContact Management Delete Component', () => {
    let comp: FicheDeContactDeleteDialogComponent;
    let fixture: ComponentFixture<FicheDeContactDeleteDialogComponent>;
    let service: FicheDeContactService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ComptaTestModule],
        declarations: [FicheDeContactDeleteDialogComponent]
      })
        .overrideTemplate(FicheDeContactDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FicheDeContactDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FicheDeContactService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
