import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IThuthu, getThuthuIdentifier } from '../thuthu.model';

export type EntityResponseType = HttpResponse<IThuthu>;
export type EntityArrayResponseType = HttpResponse<IThuthu[]>;

@Injectable({ providedIn: 'root' })
export class ThuthuService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/thuthus');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(thuthu: IThuthu): Observable<EntityResponseType> {
    return this.http.post<IThuthu>(this.resourceUrl, thuthu, { observe: 'response' });
  }

  update(thuthu: IThuthu): Observable<EntityResponseType> {
    return this.http.put<IThuthu>(`${this.resourceUrl}/${getThuthuIdentifier(thuthu) as number}`, thuthu, { observe: 'response' });
  }

  partialUpdate(thuthu: IThuthu): Observable<EntityResponseType> {
    return this.http.patch<IThuthu>(`${this.resourceUrl}/${getThuthuIdentifier(thuthu) as number}`, thuthu, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IThuthu>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IThuthu[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addThuthuToCollectionIfMissing(thuthuCollection: IThuthu[], ...thuthusToCheck: (IThuthu | null | undefined)[]): IThuthu[] {
    const thuthus: IThuthu[] = thuthusToCheck.filter(isPresent);
    if (thuthus.length > 0) {
      const thuthuCollectionIdentifiers = thuthuCollection.map(thuthuItem => getThuthuIdentifier(thuthuItem)!);
      const thuthusToAdd = thuthus.filter(thuthuItem => {
        const thuthuIdentifier = getThuthuIdentifier(thuthuItem);
        if (thuthuIdentifier == null || thuthuCollectionIdentifiers.includes(thuthuIdentifier)) {
          return false;
        }
        thuthuCollectionIdentifiers.push(thuthuIdentifier);
        return true;
      });
      return [...thuthusToAdd, ...thuthuCollection];
    }
    return thuthuCollection;
  }
}
