import * as dayjs from 'dayjs';
import { IThuthu } from 'app/entities/thuthu/thuthu.model';
import { ISach } from 'app/entities/sach/sach.model';

export interface INhapsach {
  id?: number;
  ngayGioNhap?: dayjs.Dayjs | null;
  soLuong?: number | null;
  thuthu?: IThuthu | null;
  saches?: ISach[] | null;
}

export class Nhapsach implements INhapsach {
  constructor(
    public id?: number,
    public ngayGioNhap?: dayjs.Dayjs | null,
    public soLuong?: number | null,
    public thuthu?: IThuthu | null,
    public saches?: ISach[] | null
  ) {}
}

export function getNhapsachIdentifier(nhapsach: INhapsach): number | undefined {
  return nhapsach.id;
}
