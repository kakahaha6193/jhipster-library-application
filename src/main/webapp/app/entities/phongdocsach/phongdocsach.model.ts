export interface IPhongdocsach {
  id?: number;
  tenPhong?: string | null;
  viTri?: string | null;
  sucChua?: number | null;
  giaThue?: number | null;
}

export class Phongdocsach implements IPhongdocsach {
  constructor(
    public id?: number,
    public tenPhong?: string | null,
    public viTri?: string | null,
    public sucChua?: number | null,
    public giaThue?: number | null
  ) {}
}

export function getPhongdocsachIdentifier(phongdocsach: IPhongdocsach): number | undefined {
  return phongdocsach.id;
}
