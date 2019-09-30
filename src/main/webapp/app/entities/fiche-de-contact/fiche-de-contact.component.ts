import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFicheDeContact } from 'app/shared/model/fiche-de-contact.model';
import { AccountService } from 'app/core/auth/account.service';
import { FicheDeContactService } from './fiche-de-contact.service';

@Component({
  selector: 'jhi-fiche-de-contact',
  templateUrl: './fiche-de-contact.component.html'
})
export class FicheDeContactComponent implements OnInit, OnDestroy {
  ficheDeContacts: IFicheDeContact[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected ficheDeContactService: FicheDeContactService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.ficheDeContactService
      .query()
      .pipe(
        filter((res: HttpResponse<IFicheDeContact[]>) => res.ok),
        map((res: HttpResponse<IFicheDeContact[]>) => res.body)
      )
      .subscribe(
        (res: IFicheDeContact[]) => {
          this.ficheDeContacts = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFicheDeContacts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFicheDeContact) {
    return item.id;
  }

  registerChangeInFicheDeContacts() {
    this.eventSubscriber = this.eventManager.subscribe('ficheDeContactListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
