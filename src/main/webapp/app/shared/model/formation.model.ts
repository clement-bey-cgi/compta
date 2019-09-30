import { IFicheDeContact } from 'app/shared/model/fiche-de-contact.model';

export interface IFormation {
  id?: number;
  intitule?: string;
  domaine?: string;
  ficheDeContact?: IFicheDeContact;
}

export class Formation implements IFormation {
  constructor(public id?: number, public intitule?: string, public domaine?: string, public ficheDeContact?: IFicheDeContact) {}
}
