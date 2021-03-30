export interface INhaxuatban {
  id?: number;
  tenNXB?: string | null;
  diaChi?: string | null;
}

export class Nhaxuatban implements INhaxuatban {
  constructor(public id?: number, public tenNXB?: string | null, public diaChi?: string | null) {}
}

export function getNhaxuatbanIdentifier(nhaxuatban: INhaxuatban): number | undefined {
  return nhaxuatban.id;
}
