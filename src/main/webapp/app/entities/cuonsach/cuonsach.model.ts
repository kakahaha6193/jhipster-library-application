import * as dayjs from 'dayjs';
import { ISach } from 'app/entities/sach/sach.model';
import { IMuonsach } from 'app/entities/muonsach/muonsach.model';

export interface ICuonsach {
  id?: number;
  ngayHetHan?: dayjs.Dayjs | null;
  trangThai?: number | null;
  sach?: ISach | null;
  muonsach?: IMuonsach | null;
}

export class Cuonsach implements ICuonsach {
  constructor(
    public id?: number,
    public ngayHetHan?: dayjs.Dayjs | null,
    public trangThai?: number | null,
    public sach?: ISach | null,
    public muonsach?: IMuonsach | null
  ) {}
}

export function getCuonsachIdentifier(cuonsach: ICuonsach): number | undefined {
  return cuonsach.id;
}
