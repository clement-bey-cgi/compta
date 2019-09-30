import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ComptaSharedModule } from 'app/shared/shared.module';
import { FicheDeContactComponent } from './fiche-de-contact.component';
import { FicheDeContactDetailComponent } from './fiche-de-contact-detail.component';
import { FicheDeContactUpdateComponent } from './fiche-de-contact-update.component';
import { FicheDeContactDeletePopupComponent, FicheDeContactDeleteDialogComponent } from './fiche-de-contact-delete-dialog.component';
import { ficheDeContactRoute, ficheDeContactPopupRoute } from './fiche-de-contact.route';

const ENTITY_STATES = [...ficheDeContactRoute, ...ficheDeContactPopupRoute];

@NgModule({
  imports: [ComptaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FicheDeContactComponent,
    FicheDeContactDetailComponent,
    FicheDeContactUpdateComponent,
    FicheDeContactDeleteDialogComponent,
    FicheDeContactDeletePopupComponent
  ],
  entryComponents: [
    FicheDeContactComponent,
    FicheDeContactUpdateComponent,
    FicheDeContactDeleteDialogComponent,
    FicheDeContactDeletePopupComponent
  ]
})
export class ComptaFicheDeContactModule {}
