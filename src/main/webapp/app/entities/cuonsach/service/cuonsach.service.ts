import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICuonsach, getCuonsachIdentifier } from '../cuonsach.model';

export type EntityResponseType = HttpResponse<ICuonsach>;
export type EntityArrayResponseType = HttpResponse<ICuonsach[]>;

@Injectable({ providedIn: 'root' })
export class CuonsachService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/cuonsaches');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(cuonsach: ICuonsach): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cuonsach);
    return this.http
      .post<ICuonsach>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cuonsach: ICuonsach): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cuonsach);
    return this.http
      .put<ICuonsach>(`${this.resourceUrl}/${getCuonsachIdentifier(cuonsach) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(cuonsach: ICuonsach): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cuonsach);
    return this.http
      .patch<ICuonsach>(`${this.resourceUrl}/${getCuonsachIdentifier(cuonsach) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICuonsach>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICuonsach[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCuonsachToCollectionIfMissing(cuonsachCollection: ICuonsach[], ...cuonsachesToCheck: (ICuonsach | null | undefined)[]): ICuonsach[] {
    const cuonsaches: ICuonsach[] = cuonsachesToCheck.filter(isPresent);
    if (cuonsaches.length > 0) {
      const cuonsachCollectionIdentifiers = cuonsachCollection.map(cuonsachItem => getCuonsachIdentifier(cuonsachItem)!);
      const cuonsachesToAdd = cuonsaches.filter(cuonsachItem => {
        const cuonsachIdentifier = getCuonsachIdentifier(cuonsachItem);
        if (cuonsachIdentifier == null || cuonsachCollectionIdentifiers.includes(cuonsachIdentifier)) {
          return false;
        }
        cuonsachCollectionIdentifiers.push(cuonsachIdentifier);
        return true;
      });
      return [...cuonsachesToAdd, ...cuonsachCollection];
    }
    return cuonsachCollection;
  }

  protected convertDateFromClient(cuonsach: ICuonsach): ICuonsach {
    return Object.assign({}, cuonsach, {
      ngayHetHan: cuonsach.ngayHetHan?.isValid() ? cuonsach.ngayHetHan.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.ngayHetHan = res.body.ngayHetHan ? dayjs(res.body.ngayHetHan) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cuonsach: ICuonsach) => {
        cuonsach.ngayHetHan = cuonsach.ngayHetHan ? dayjs(cuonsach.ngayHetHan) : undefined;
      });
    }
    return res;
  }
}
