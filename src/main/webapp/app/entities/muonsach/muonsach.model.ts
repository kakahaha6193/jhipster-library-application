import * as dayjs from 'dayjs';
import { ICuonsach } from 'app/entities/cuonsach/cuonsach.model';
import { IDocgia } from 'app/entities/docgia/docgia.model';
import { IThuthu } from 'app/entities/thuthu/thuthu.model';

export interface IMuonsach {
  id?: number;
  ngayMuon?: dayjs.Dayjs | null;
  hanTra?: dayjs.Dayjs | null;
  ngayTra?: dayjs.Dayjs | null;
  trangThai?: number | null;
  cuonsaches?: ICuonsach[] | null;
  docgia?: IDocgia | null;
  thuthu?: IThuthu | null;
}

export class Muonsach implements IMuonsach {
  constructor(
    public id?: number,
    public ngayMuon?: dayjs.Dayjs | null,
    public hanTra?: dayjs.Dayjs | null,
    public ngayTra?: dayjs.Dayjs | null,
    public trangThai?: number | null,
    public cuonsaches?: ICuonsach[] | null,
    public docgia?: IDocgia | null,
    public thuthu?: IThuthu | null
  ) {}
}

export function getMuonsachIdentifier(muonsach: IMuonsach): number | undefined {
  return muonsach.id;
}
