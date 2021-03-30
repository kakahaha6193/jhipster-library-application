import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPhongdungsach, getPhongdungsachIdentifier } from '../phongdungsach.model';

export type EntityResponseType = HttpResponse<IPhongdungsach>;
export type EntityArrayResponseType = HttpResponse<IPhongdungsach[]>;

@Injectable({ providedIn: 'root' })
export class PhongdungsachService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/phongdungsaches');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(phongdungsach: IPhongdungsach): Observable<EntityResponseType> {
    return this.http.post<IPhongdungsach>(this.resourceUrl, phongdungsach, { observe: 'response' });
  }

  update(phongdungsach: IPhongdungsach): Observable<EntityResponseType> {
    return this.http.put<IPhongdungsach>(`${this.resourceUrl}/${getPhongdungsachIdentifier(phongdungsach) as number}`, phongdungsach, {
      observe: 'response',
    });
  }

  partialUpdate(phongdungsach: IPhongdungsach): Observable<EntityResponseType> {
    return this.http.patch<IPhongdungsach>(`${this.resourceUrl}/${getPhongdungsachIdentifier(phongdungsach) as number}`, phongdungsach, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPhongdungsach>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPhongdungsach[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPhongdungsachToCollectionIfMissing(
    phongdungsachCollection: IPhongdungsach[],
    ...phongdungsachesToCheck: (IPhongdungsach | null | undefined)[]
  ): IPhongdungsach[] {
    const phongdungsaches: IPhongdungsach[] = phongdungsachesToCheck.filter(isPresent);
    if (phongdungsaches.length > 0) {
      const phongdungsachCollectionIdentifiers = phongdungsachCollection.map(
        phongdungsachItem => getPhongdungsachIdentifier(phongdungsachItem)!
      );
      const phongdungsachesToAdd = phongdungsaches.filter(phongdungsachItem => {
        const phongdungsachIdentifier = getPhongdungsachIdentifier(phongdungsachItem);
        if (phongdungsachIdentifier == null || phongdungsachCollectionIdentifiers.includes(phongdungsachIdentifier)) {
          return false;
        }
        phongdungsachCollectionIdentifiers.push(phongdungsachIdentifier);
        return true;
      });
      return [...phongdungsachesToAdd, ...phongdungsachCollection];
    }
    return phongdungsachCollection;
  }
}
