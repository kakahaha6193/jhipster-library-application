import { IPhongdungsach } from 'app/entities/phongdungsach/phongdungsach.model';

export interface IGiasach {
  id?: number;
  thuTu?: number | null;
  phongdungsach?: IPhongdungsach | null;
}

export class Giasach implements IGiasach {
  constructor(public id?: number, public thuTu?: number | null, public phongdungsach?: IPhongdungsach | null) {}
}

export function getGiasachIdentifier(giasach: IGiasach): number | undefined {
  return giasach.id;
}
