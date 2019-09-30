import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'fiche-de-contact',
        loadChildren: () => import('./fiche-de-contact/fiche-de-contact.module').then(m => m.ComptaFicheDeContactModule)
      },
      {
        path: 'formation',
        loadChildren: () => import('./formation/formation.module').then(m => m.ComptaFormationModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: []
})
export class ComptaEntityModule {}
