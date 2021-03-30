export interface IPhongdungsach {
  id?: number;
  tenPhong?: string | null;
  viTri?: string | null;
}

export class Phongdungsach implements IPhongdungsach {
  constructor(public id?: number, public tenPhong?: string | null, public viTri?: string | null) {}
}

export function getPhongdungsachIdentifier(phongdungsach: IPhongdungsach): number | undefined {
  return phongdungsach.id;
}
