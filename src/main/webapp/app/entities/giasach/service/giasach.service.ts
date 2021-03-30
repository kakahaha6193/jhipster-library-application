import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGiasach, getGiasachIdentifier } from '../giasach.model';

export type EntityResponseType = HttpResponse<IGiasach>;
export type EntityArrayResponseType = HttpResponse<IGiasach[]>;

@Injectable({ providedIn: 'root' })
export class GiasachService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/giasaches');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(giasach: IGiasach): Observable<EntityResponseType> {
    return this.http.post<IGiasach>(this.resourceUrl, giasach, { observe: 'response' });
  }

  update(giasach: IGiasach): Observable<EntityResponseType> {
    return this.http.put<IGiasach>(`${this.resourceUrl}/${getGiasachIdentifier(giasach) as number}`, giasach, { observe: 'response' });
  }

  partialUpdate(giasach: IGiasach): Observable<EntityResponseType> {
    return this.http.patch<IGiasach>(`${this.resourceUrl}/${getGiasachIdentifier(giasach) as number}`, giasach, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGiasach>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGiasach[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addGiasachToCollectionIfMissing(giasachCollection: IGiasach[], ...giasachesToCheck: (IGiasach | null | undefined)[]): IGiasach[] {
    const giasaches: IGiasach[] = giasachesToCheck.filter(isPresent);
    if (giasaches.length > 0) {
      const giasachCollectionIdentifiers = giasachCollection.map(giasachItem => getGiasachIdentifier(giasachItem)!);
      const giasachesToAdd = giasaches.filter(giasachItem => {
        const giasachIdentifier = getGiasachIdentifier(giasachItem);
        if (giasachIdentifier == null || giasachCollectionIdentifiers.includes(giasachIdentifier)) {
          return false;
        }
        giasachCollectionIdentifiers.push(giasachIdentifier);
        return true;
      });
      return [...giasachesToAdd, ...giasachCollection];
    }
    return giasachCollection;
  }
}
