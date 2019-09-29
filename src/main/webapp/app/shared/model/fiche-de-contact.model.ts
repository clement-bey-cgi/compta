import { Moment } from 'moment';
import { ICv } from 'app/shared/model/cv.model';
import { IFormation } from 'app/shared/model/formation.model';

export interface IFicheDeContact {
  id?: number;
  genre?: boolean;
  nom?: string;
  prenom?: string;
  titre?: string;
  mail?: string;
  numeroDeTelephone?: string;
  dateDeNaissance?: Moment;
  cv?: ICv;
  formations?: IFormation[];
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
    public dateDeNaissance?: Moment,
    public cv?: ICv,
    public formations?: IFormation[]
  ) {
    this.genre = this.genre || false;
  }
}
