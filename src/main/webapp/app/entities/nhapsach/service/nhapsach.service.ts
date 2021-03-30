import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INhapsach, getNhapsachIdentifier } from '../nhapsach.model';

export type EntityResponseType = HttpResponse<INhapsach>;
export type EntityArrayResponseType = HttpResponse<INhapsach[]>;

@Injectable({ providedIn: 'root' })
export class NhapsachService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/nhapsaches');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(nhapsach: INhapsach): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nhapsach);
    return this.http
      .post<INhapsach>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(nhapsach: INhapsach): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nhapsach);
    return this.http
      .put<INhapsach>(`${this.resourceUrl}/${getNhapsachIdentifier(nhapsach) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(nhapsach: INhapsach): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nhapsach);
    return this.http
      .patch<INhapsach>(`${this.resourceUrl}/${getNhapsachIdentifier(nhapsach) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INhapsach>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INhapsach[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNhapsachToCollectionIfMissing(nhapsachCollection: INhapsach[], ...nhapsachesToCheck: (INhapsach | null | undefined)[]): INhapsach[] {
    const nhapsaches: INhapsach[] = nhapsachesToCheck.filter(isPresent);
    if (nhapsaches.length > 0) {
      const nhapsachCollectionIdentifiers = nhapsachCollection.map(nhapsachItem => getNhapsachIdentifier(nhapsachItem)!);
      const nhapsachesToAdd = nhapsaches.filter(nhapsachItem => {
        const nhapsachIdentifier = getNhapsachIdentifier(nhapsachItem);
        if (nhapsachIdentifier == null || nhapsachCollectionIdentifiers.includes(nhapsachIdentifier)) {
          return false;
        }
        nhapsachCollectionIdentifiers.push(nhapsachIdentifier);
        return true;
      });
      return [...nhapsachesToAdd, ...nhapsachCollection];
    }
    return nhapsachCollection;
  }

  protected convertDateFromClient(nhapsach: INhapsach): INhapsach {
    return Object.assign({}, nhapsach, {
      ngayGioNhap: nhapsach.ngayGioNhap?.isValid() ? nhapsach.ngayGioNhap.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.ngayGioNhap = res.body.ngayGioNhap ? dayjs(res.body.ngayGioNhap) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((nhapsach: INhapsach) => {
        nhapsach.ngayGioNhap = nhapsach.ngayGioNhap ? dayjs(nhapsach.ngayGioNhap) : undefined;
      });
    }
    return res;
  }
}
