import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IThuephong, getThuephongIdentifier } from '../thuephong.model';

export type EntityResponseType = HttpResponse<IThuephong>;
export type EntityArrayResponseType = HttpResponse<IThuephong[]>;

@Injectable({ providedIn: 'root' })
export class ThuephongService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/thuephongs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(thuephong: IThuephong): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(thuephong);
    return this.http
      .post<IThuephong>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(thuephong: IThuephong): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(thuephong);
    return this.http
      .put<IThuephong>(`${this.resourceUrl}/${getThuephongIdentifier(thuephong) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(thuephong: IThuephong): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(thuephong);
    return this.http
      .patch<IThuephong>(`${this.resourceUrl}/${getThuephongIdentifier(thuephong) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IThuephong>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IThuephong[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addThuephongToCollectionIfMissing(
    thuephongCollection: IThuephong[],
    ...thuephongsToCheck: (IThuephong | null | undefined)[]
  ): IThuephong[] {
    const thuephongs: IThuephong[] = thuephongsToCheck.filter(isPresent);
    if (thuephongs.length > 0) {
      const thuephongCollectionIdentifiers = thuephongCollection.map(thuephongItem => getThuephongIdentifier(thuephongItem)!);
      const thuephongsToAdd = thuephongs.filter(thuephongItem => {
        const thuephongIdentifier = getThuephongIdentifier(thuephongItem);
        if (thuephongIdentifier == null || thuephongCollectionIdentifiers.includes(thuephongIdentifier)) {
          return false;
        }
        thuephongCollectionIdentifiers.push(thuephongIdentifier);
        return true;
      });
      return [...thuephongsToAdd, ...thuephongCollection];
    }
    return thuephongCollection;
  }

  protected convertDateFromClient(thuephong: IThuephong): IThuephong {
    return Object.assign({}, thuephong, {
      ngayThue: thuephong.ngayThue?.isValid() ? thuephong.ngayThue.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.ngayThue = res.body.ngayThue ? dayjs(res.body.ngayThue) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((thuephong: IThuephong) => {
        thuephong.ngayThue = thuephong.ngayThue ? dayjs(thuephong.ngayThue) : undefined;
      });
    }
    return res;
  }
}
