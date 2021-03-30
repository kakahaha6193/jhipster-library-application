import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISach, getSachIdentifier } from '../sach.model';

export type EntityResponseType = HttpResponse<ISach>;
export type EntityArrayResponseType = HttpResponse<ISach[]>;

@Injectable({ providedIn: 'root' })
export class SachService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/saches');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(sach: ISach): Observable<EntityResponseType> {
    return this.http.post<ISach>(this.resourceUrl, sach, { observe: 'response' });
  }

  update(sach: ISach): Observable<EntityResponseType> {
    return this.http.put<ISach>(`${this.resourceUrl}/${getSachIdentifier(sach) as number}`, sach, { observe: 'response' });
  }

  partialUpdate(sach: ISach): Observable<EntityResponseType> {
    return this.http.patch<ISach>(`${this.resourceUrl}/${getSachIdentifier(sach) as number}`, sach, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISach>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISach[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSachToCollectionIfMissing(sachCollection: ISach[], ...sachesToCheck: (ISach | null | undefined)[]): ISach[] {
    const saches: ISach[] = sachesToCheck.filter(isPresent);
    if (saches.length > 0) {
      const sachCollectionIdentifiers = sachCollection.map(sachItem => getSachIdentifier(sachItem)!);
      const sachesToAdd = saches.filter(sachItem => {
        const sachIdentifier = getSachIdentifier(sachItem);
        if (sachIdentifier == null || sachCollectionIdentifiers.includes(sachIdentifier)) {
          return false;
        }
        sachCollectionIdentifiers.push(sachIdentifier);
        return true;
      });
      return [...sachesToAdd, ...sachCollection];
    }
    return sachCollection;
  }
}
