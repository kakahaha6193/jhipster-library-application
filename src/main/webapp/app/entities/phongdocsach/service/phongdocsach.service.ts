import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPhongdocsach, getPhongdocsachIdentifier } from '../phongdocsach.model';

export type EntityResponseType = HttpResponse<IPhongdocsach>;
export type EntityArrayResponseType = HttpResponse<IPhongdocsach[]>;

@Injectable({ providedIn: 'root' })
export class PhongdocsachService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/phongdocsaches');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(phongdocsach: IPhongdocsach): Observable<EntityResponseType> {
    return this.http.post<IPhongdocsach>(this.resourceUrl, phongdocsach, { observe: 'response' });
  }

  update(phongdocsach: IPhongdocsach): Observable<EntityResponseType> {
    return this.http.put<IPhongdocsach>(`${this.resourceUrl}/${getPhongdocsachIdentifier(phongdocsach) as number}`, phongdocsach, {
      observe: 'response',
    });
  }

  partialUpdate(phongdocsach: IPhongdocsach): Observable<EntityResponseType> {
    return this.http.patch<IPhongdocsach>(`${this.resourceUrl}/${getPhongdocsachIdentifier(phongdocsach) as number}`, phongdocsach, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPhongdocsach>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPhongdocsach[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPhongdocsachToCollectionIfMissing(
    phongdocsachCollection: IPhongdocsach[],
    ...phongdocsachesToCheck: (IPhongdocsach | null | undefined)[]
  ): IPhongdocsach[] {
    const phongdocsaches: IPhongdocsach[] = phongdocsachesToCheck.filter(isPresent);
    if (phongdocsaches.length > 0) {
      const phongdocsachCollectionIdentifiers = phongdocsachCollection.map(
        phongdocsachItem => getPhongdocsachIdentifier(phongdocsachItem)!
      );
      const phongdocsachesToAdd = phongdocsaches.filter(phongdocsachItem => {
        const phongdocsachIdentifier = getPhongdocsachIdentifier(phongdocsachItem);
        if (phongdocsachIdentifier == null || phongdocsachCollectionIdentifiers.includes(phongdocsachIdentifier)) {
          return false;
        }
        phongdocsachCollectionIdentifiers.push(phongdocsachIdentifier);
        return true;
      });
      return [...phongdocsachesToAdd, ...phongdocsachCollection];
    }
    return phongdocsachCollection;
  }
}
