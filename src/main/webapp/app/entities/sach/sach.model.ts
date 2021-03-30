import { ITheloai } from 'app/entities/theloai/theloai.model';
import { INhaxuatban } from 'app/entities/nhaxuatban/nhaxuatban.model';
import { IGiasach } from 'app/entities/giasach/giasach.model';
import { INhapsach } from 'app/entities/nhapsach/nhapsach.model';

export interface ISach {
  id?: number;
  tenSach?: string | null;
  giaNiemYet?: number | null;
  tacgia?: string | null;
  giaThue?: number | null;
  nganXep?: string | null;
  theloai?: ITheloai | null;
  nhaxuatban?: INhaxuatban | null;
  giasach?: IGiasach | null;
  nhapsaches?: INhapsach[] | null;
}

export class Sach implements ISach {
  constructor(
    public id?: number,
    public tenSach?: string | null,
    public giaNiemYet?: number | null,
    public tacgia?: string | null,
    public giaThue?: number | null,
    public nganXep?: string | null,
    public theloai?: ITheloai | null,
    public nhaxuatban?: INhaxuatban | null,
    public giasach?: IGiasach | null,
    public nhapsaches?: INhapsach[] | null
  ) {}
}

export function getSachIdentifier(sach: ISach): number | undefined {
  return sach.id;
}
