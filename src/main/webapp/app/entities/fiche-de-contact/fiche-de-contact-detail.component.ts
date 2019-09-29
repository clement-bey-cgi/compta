import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFicheDeContact } from 'app/shared/model/fiche-de-contact.model';

@Component({
  selector: 'jhi-fiche-de-contact-detail',
  templateUrl: './fiche-de-contact-detail.component.html'
})
export class FicheDeContactDetailComponent implements OnInit {
  ficheDeContact: IFicheDeContact;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ficheDeContact }) => {
      this.ficheDeContact = ficheDeContact;
    });
  }

  previousState() {
    window.history.back();
  }
}
