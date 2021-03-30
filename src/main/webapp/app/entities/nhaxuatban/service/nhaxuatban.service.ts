import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INhaxuatban, getNhaxuatbanIdentifier } from '../nhaxuatban.model';

export type EntityResponseType = HttpResponse<INhaxuatban>;
export type EntityArrayResponseType = HttpResponse<INhaxuatban[]>;

@Injectable({ providedIn: 'root' })
export class NhaxuatbanService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/nhaxuatbans');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(nhaxuatban: INhaxuatban): Observable<EntityResponseType> {
    return this.http.post<INhaxuatban>(this.resourceUrl, nhaxuatban, { observe: 'response' });
  }

  update(nhaxuatban: INhaxuatban): Observable<EntityResponseType> {
    return this.http.put<INhaxuatban>(`${this.resourceUrl}/${getNhaxuatbanIdentifier(nhaxuatban) as number}`, nhaxuatban, {
      observe: 'response',
    });
  }

  partialUpdate(nhaxuatban: INhaxuatban): Observable<EntityResponseType> {
    return this.http.patch<INhaxuatban>(`${this.resourceUrl}/${getNhaxuatbanIdentifier(nhaxuatban) as number}`, nhaxuatban, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INhaxuatban>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INhaxuatban[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNhaxuatbanToCollectionIfMissing(
    nhaxuatbanCollection: INhaxuatban[],
    ...nhaxuatbansToCheck: (INhaxuatban | null | undefined)[]
  ): INhaxuatban[] {
    const nhaxuatbans: INhaxuatban[] = nhaxuatbansToCheck.filter(isPresent);
    if (nhaxuatbans.length > 0) {
      const nhaxuatbanCollectionIdentifiers = nhaxuatbanCollection.map(nhaxuatbanItem => getNhaxuatbanIdentifier(nhaxuatbanItem)!);
      const nhaxuatbansToAdd = nhaxuatbans.filter(nhaxuatbanItem => {
        const nhaxuatbanIdentifier = getNhaxuatbanIdentifier(nhaxuatbanItem);
        if (nhaxuatbanIdentifier == null || nhaxuatbanCollectionIdentifiers.includes(nhaxuatbanIdentifier)) {
          return false;
        }
        nhaxuatbanCollectionIdentifiers.push(nhaxuatbanIdentifier);
        return true;
      });
      return [...nhaxuatbansToAdd, ...nhaxuatbanCollection];
    }
    return nhaxuatbanCollection;
  }
}
