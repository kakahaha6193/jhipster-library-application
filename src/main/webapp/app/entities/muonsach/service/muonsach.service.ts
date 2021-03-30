import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMuonsach, getMuonsachIdentifier } from '../muonsach.model';

export type EntityResponseType = HttpResponse<IMuonsach>;
export type EntityArrayResponseType = HttpResponse<IMuonsach[]>;

@Injectable({ providedIn: 'root' })
export class MuonsachService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/muonsaches');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(muonsach: IMuonsach): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(muonsach);
    return this.http
      .post<IMuonsach>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(muonsach: IMuonsach): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(muonsach);
    return this.http
      .put<IMuonsach>(`${this.resourceUrl}/${getMuonsachIdentifier(muonsach) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(muonsach: IMuonsach): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(muonsach);
    return this.http
      .patch<IMuonsach>(`${this.resourceUrl}/${getMuonsachIdentifier(muonsach) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMuonsach>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMuonsach[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMuonsachToCollectionIfMissing(muonsachCollection: IMuonsach[], ...muonsachesToCheck: (IMuonsach | null | undefined)[]): IMuonsach[] {
    const muonsaches: IMuonsach[] = muonsachesToCheck.filter(isPresent);
    if (muonsaches.length > 0) {
      const muonsachCollectionIdentifiers = muonsachCollection.map(muonsachItem => getMuonsachIdentifier(muonsachItem)!);
      const muonsachesToAdd = muonsaches.filter(muonsachItem => {
        const muonsachIdentifier = getMuonsachIdentifier(muonsachItem);
        if (muonsachIdentifier == null || muonsachCollectionIdentifiers.includes(muonsachIdentifier)) {
          return false;
        }
        muonsachCollectionIdentifiers.push(muonsachIdentifier);
        return true;
      });
      return [...muonsachesToAdd, ...muonsachCollection];
    }
    return muonsachCollection;
  }

  protected convertDateFromClient(muonsach: IMuonsach): IMuonsach {
    return Object.assign({}, muonsach, {
      ngayMuon: muonsach.ngayMuon?.isValid() ? muonsach.ngayMuon.toJSON() : undefined,
      hanTra: muonsach.hanTra?.isValid() ? muonsach.hanTra.toJSON() : undefined,
      ngayTra: muonsach.ngayTra?.isValid() ? muonsach.ngayTra.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.ngayMuon = res.body.ngayMuon ? dayjs(res.body.ngayMuon) : undefined;
      res.body.hanTra = res.body.hanTra ? dayjs(res.body.hanTra) : undefined;
      res.body.ngayTra = res.body.ngayTra ? dayjs(res.body.ngayTra) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((muonsach: IMuonsach) => {
        muonsach.ngayMuon = muonsach.ngayMuon ? dayjs(muonsach.ngayMuon) : undefined;
        muonsach.hanTra = muonsach.hanTra ? dayjs(muonsach.hanTra) : undefined;
        muonsach.ngayTra = muonsach.ngayTra ? dayjs(muonsach.ngayTra) : undefined;
      });
    }
    return res;
  }
}
