import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFicheDeContact } from 'app/shared/model/fiche-de-contact.model';

type EntityResponseType = HttpResponse<IFicheDeContact>;
type EntityArrayResponseType = HttpResponse<IFicheDeContact[]>;

@Injectable({ providedIn: 'root' })
export class FicheDeContactService {
  public resourceUrl = SERVER_API_URL + 'api/fiche-de-contacts';

  constructor(protected http: HttpClient) {}

  create(ficheDeContact: IFicheDeContact): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ficheDeContact);
    return this.http
      .post<IFicheDeContact>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ficheDeContact: IFicheDeContact): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ficheDeContact);
    return this.http
      .put<IFicheDeContact>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFicheDeContact>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFicheDeContact[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(ficheDeContact: IFicheDeContact): IFicheDeContact {
    const copy: IFicheDeContact = Object.assign({}, ficheDeContact, {
      dateDeNaissance:
        ficheDeContact.dateDeNaissance != null && ficheDeContact.dateDeNaissance.isValid()
          ? ficheDeContact.dateDeNaissance.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDeNaissance = res.body.dateDeNaissance != null ? moment(res.body.dateDeNaissance) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ficheDeContact: IFicheDeContact) => {
        ficheDeContact.dateDeNaissance = ficheDeContact.dateDeNaissance != null ? moment(ficheDeContact.dateDeNaissance) : null;
      });
    }
    return res;
  }
}
