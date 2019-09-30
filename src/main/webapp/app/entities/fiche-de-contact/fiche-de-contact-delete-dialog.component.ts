import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFicheDeContact } from 'app/shared/model/fiche-de-contact.model';
import { FicheDeContactService } from './fiche-de-contact.service';

@Component({
  selector: 'jhi-fiche-de-contact-delete-dialog',
  templateUrl: './fiche-de-contact-delete-dialog.component.html'
})
export class FicheDeContactDeleteDialogComponent {
  ficheDeContact: IFicheDeContact;

  constructor(
    protected ficheDeContactService: FicheDeContactService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.ficheDeContactService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'ficheDeContactListModification',
        content: 'Deleted an ficheDeContact'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-fiche-de-contact-delete-popup',
  template: ''
})
export class FicheDeContactDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ficheDeContact }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FicheDeContactDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.ficheDeContact = ficheDeContact;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/fiche-de-contact', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/fiche-de-contact', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
