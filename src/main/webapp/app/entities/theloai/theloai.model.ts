export interface ITheloai {
  id?: number;
  tenTheLoai?: string | null;
}

export class Theloai implements ITheloai {
  constructor(public id?: number, public tenTheLoai?: string | null) {}
}

export function getTheloaiIdentifier(theloai: ITheloai): number | undefined {
  return theloai.id;
}
