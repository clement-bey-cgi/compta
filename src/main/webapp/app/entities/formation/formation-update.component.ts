import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFormation, Formation } from 'app/shared/model/formation.model';
import { FormationService } from './formation.service';
import { IFicheDeContact } from 'app/shared/model/fiche-de-contact.model';
import { FicheDeContactService } from 'app/entities/fiche-de-contact/fiche-de-contact.service';

@Component({
  selector: 'jhi-formation-update',
  templateUrl: './formation-update.component.html'
})
export class FormationUpdateComponent implements OnInit {
  isSaving: boolean;

  fichedecontacts: IFicheDeContact[];

  editForm = this.fb.group({
    id: [],
    intitule: [],
    domaine: [],
    ficheDeContact: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected formationService: FormationService,
    protected ficheDeContactService: FicheDeContactService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ formation }) => {
      this.updateForm(formation);
    });
    this.ficheDeContactService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFicheDeContact[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFicheDeContact[]>) => response.body)
      )
      .subscribe((res: IFicheDeContact[]) => (this.fichedecontacts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(formation: IFormation) {
    this.editForm.patchValue({
      id: formation.id,
      intitule: formation.intitule,
      domaine: formation.domaine,
      ficheDeContact: formation.ficheDeContact
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const formation = this.createFromForm();
    if (formation.id !== undefined) {
      this.subscribeToSaveResponse(this.formationService.update(formation));
    } else {
      this.subscribeToSaveResponse(this.formationService.create(formation));
    }
  }

  private createFromForm(): IFormation {
    return {
      ...new Formation(),
      id: this.editForm.get(['id']).value,
      intitule: this.editForm.get(['intitule']).value,
      domaine: this.editForm.get(['domaine']).value,
      ficheDeContact: this.editForm.get(['ficheDeContact']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormation>>) {
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

  trackFicheDeContactById(index: number, item: IFicheDeContact) {
    return item.id;
  }
}
