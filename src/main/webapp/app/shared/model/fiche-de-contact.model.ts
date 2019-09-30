import { Moment } from 'moment';

export interface IFicheDeContact {
  id?: number;
  genre?: boolean;
  nom?: string;
  prenom?: string;
  titre?: string;
  mail?: string;
  numeroDeTelephone?: string;
  dateDeNaissance?: Moment;
}

export class FicheDeContact implements IFicheDeContact {
  constructor(
    public id?: number,
    public genre?: boolean,
    public nom?: string,
    public prenom?: string,
    public titre?: string,
    public mail?: string,
    public numeroDeTelephone?: string,
    public dateDeNaissance?: Moment
  ) {
    this.genre = this.genre || false;
  }
}
