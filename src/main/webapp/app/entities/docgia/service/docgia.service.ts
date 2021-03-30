import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDocgia, getDocgiaIdentifier } from '../docgia.model';

export type EntityResponseType = HttpResponse<IDocgia>;
export type EntityArrayResponseType = HttpResponse<IDocgia[]>;

@Injectable({ providedIn: 'root' })
export class DocgiaService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/docgias');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(docgia: IDocgia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(docgia);
    return this.http
      .post<IDocgia>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(docgia: IDocgia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(docgia);
    return this.http
      .put<IDocgia>(`${this.resourceUrl}/${getDocgiaIdentifier(docgia) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(docgia: IDocgia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(docgia);
    return this.http
      .patch<IDocgia>(`${this.resourceUrl}/${getDocgiaIdentifier(docgia) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDocgia>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDocgia[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDocgiaToCollectionIfMissing(docgiaCollection: IDocgia[], ...docgiasToCheck: (IDocgia | null | undefined)[]): IDocgia[] {
    const docgias: IDocgia[] = docgiasToCheck.filter(isPresent);
    if (docgias.length > 0) {
      const docgiaCollectionIdentifiers = docgiaCollection.map(docgiaItem => getDocgiaIdentifier(docgiaItem)!);
      const docgiasToAdd = docgias.filter(docgiaItem => {
        const docgiaIdentifier = getDocgiaIdentifier(docgiaItem);
        if (docgiaIdentifier == null || docgiaCollectionIdentifiers.includes(docgiaIdentifier)) {
          return false;
        }
        docgiaCollectionIdentifiers.push(docgiaIdentifier);
        return true;
      });
      return [...docgiasToAdd, ...docgiaCollection];
    }
    return docgiaCollection;
  }

  protected convertDateFromClient(docgia: IDocgia): IDocgia {
    return Object.assign({}, docgia, {
      ngaySinh: docgia.ngaySinh?.isValid() ? docgia.ngaySinh.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.ngaySinh = res.body.ngaySinh ? dayjs(res.body.ngaySinh) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((docgia: IDocgia) => {
        docgia.ngaySinh = docgia.ngaySinh ? dayjs(docgia.ngaySinh) : undefined;
      });
    }
    return res;
  }
}
