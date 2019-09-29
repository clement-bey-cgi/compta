import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IFicheDeContact, FicheDeContact } from 'app/shared/model/fiche-de-contact.model';
import { FicheDeContactService } from './fiche-de-contact.service';
import { ICv } from 'app/shared/model/cv.model';
import { CvService } from 'app/entities/cv/cv.service';

@Component({
  selector: 'jhi-fiche-de-contact-update',
  templateUrl: './fiche-de-contact-update.component.html'
})
export class FicheDeContactUpdateComponent implements OnInit {
  isSaving: boolean;

  cvs: ICv[];
  dateDeNaissanceDp: any;

  editForm = this.fb.group({
    id: [],
    genre: [],
    nom: [],
    prenom: [],
    titre: [],
    mail: [],
    numeroDeTelephone: [],
    dateDeNaissance: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ficheDeContactService: FicheDeContactService,
    protected cvService: CvService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ficheDeContact }) => {
      this.updateForm(ficheDeContact);
    });
    this.cvService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICv[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICv[]>) => response.body)
      )
      .subscribe((res: ICv[]) => (this.cvs = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(ficheDeContact: IFicheDeContact) {
    this.editForm.patchValue({
      id: ficheDeContact.id,
      genre: ficheDeContact.genre,
      nom: ficheDeContact.nom,
      prenom: ficheDeContact.prenom,
      titre: ficheDeContact.titre,
      mail: ficheDeContact.mail,
      numeroDeTelephone: ficheDeContact.numeroDeTelephone,
      dateDeNaissance: ficheDeContact.dateDeNaissance
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const ficheDeContact = this.createFromForm();
    if (ficheDeContact.id !== undefined) {
      this.subscribeToSaveResponse(this.ficheDeContactService.update(ficheDeContact));
    } else {
      this.subscribeToSaveResponse(this.ficheDeContactService.create(ficheDeContact));
    }
  }

  private createFromForm(): IFicheDeContact {
    return {
      ...new FicheDeContact(),
      id: this.editForm.get(['id']).value,
      genre: this.editForm.get(['genre']).value,
      nom: this.editForm.get(['nom']).value,
      prenom: this.editForm.get(['prenom']).value,
      titre: this.editForm.get(['titre']).value,
      mail: this.editForm.get(['mail']).value,
      numeroDeTelephone: this.editForm.get(['numeroDeTelephone']).value,
      dateDeNaissance: this.editForm.get(['dateDeNaissance']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFicheDeContact>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCvById(index: number, item: ICv) {
    return item.id;
  }
}
