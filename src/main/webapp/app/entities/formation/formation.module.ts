import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ComptaSharedModule } from 'app/shared/shared.module';
import { FormationComponent } from './formation.component';
import { FormationDetailComponent } from './formation-detail.component';
import { FormationUpdateComponent } from './formation-update.component';
import { FormationDeletePopupComponent, FormationDeleteDialogComponent } from './formation-delete-dialog.component';
import { formationRoute, formationPopupRoute } from './formation.route';

const ENTITY_STATES = [...formationRoute, ...formationPopupRoute];

@NgModule({
  imports: [ComptaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FormationComponent,
    FormationDetailComponent,
    FormationUpdateComponent,
    FormationDeleteDialogComponent,
    FormationDeletePopupComponent
  ],
  entryComponents: [FormationComponent, FormationUpdateComponent, FormationDeleteDialogComponent, FormationDeletePopupComponent]
})
export class ComptaFormationModule {}
