import { INhapsach } from 'app/entities/nhapsach/nhapsach.model';
import { IMuonsach } from 'app/entities/muonsach/muonsach.model';
import { IThuephong } from 'app/entities/thuephong/thuephong.model';

export interface IThuthu {
  id?: number;
  hoTen?: string | null;
  username?: string | null;
  password?: string | null;
  nhapsaches?: INhapsach[] | null;
  muonsaches?: IMuonsach[] | null;
  thuephongs?: IThuephong[] | null;
}

export class Thuthu implements IThuthu {
  constructor(
    public id?: number,
    public hoTen?: string | null,
    public username?: string | null,
    public password?: string | null,
    public nhapsaches?: INhapsach[] | null,
    public muonsaches?: IMuonsach[] | null,
    public thuephongs?: IThuephong[] | null
  ) {}
}

export function getThuthuIdentifier(thuthu: IThuthu): number | undefined {
  return thuthu.id;
}
