import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FicheDeContact } from 'app/shared/model/fiche-de-contact.model';
import { FicheDeContactService } from './fiche-de-contact.service';
import { FicheDeContactComponent } from './fiche-de-contact.component';
import { FicheDeContactDetailComponent } from './fiche-de-contact-detail.component';
import { FicheDeContactUpdateComponent } from './fiche-de-contact-update.component';
import { FicheDeContactDeletePopupComponent } from './fiche-de-contact-delete-dialog.component';
import { IFicheDeContact } from 'app/shared/model/fiche-de-contact.model';

@Injectable({ providedIn: 'root' })
export class FicheDeContactResolve implements Resolve<IFicheDeContact> {
  constructor(private service: FicheDeContactService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFicheDeContact> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FicheDeContact>) => response.ok),
        map((ficheDeContact: HttpResponse<FicheDeContact>) => ficheDeContact.body)
      );
    }
    return of(new FicheDeContact());
  }
}

export const ficheDeContactRoute: Routes = [
  {
    path: '',
    component: FicheDeContactComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FicheDeContacts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FicheDeContactDetailComponent,
    resolve: {
      ficheDeContact: FicheDeContactResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FicheDeContacts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FicheDeContactUpdateComponent,
    resolve: {
      ficheDeContact: FicheDeContactResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FicheDeContacts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FicheDeContactUpdateComponent,
    resolve: {
      ficheDeContact: FicheDeContactResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FicheDeContacts'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const ficheDeContactPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FicheDeContactDeletePopupComponent,
    resolve: {
      ficheDeContact: FicheDeContactResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FicheDeContacts'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
