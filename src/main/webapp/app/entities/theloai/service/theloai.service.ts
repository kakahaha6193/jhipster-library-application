import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITheloai, getTheloaiIdentifier } from '../theloai.model';

export type EntityResponseType = HttpResponse<ITheloai>;
export type EntityArrayResponseType = HttpResponse<ITheloai[]>;

@Injectable({ providedIn: 'root' })
export class TheloaiService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/theloais');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(theloai: ITheloai): Observable<EntityResponseType> {
    return this.http.post<ITheloai>(this.resourceUrl, theloai, { observe: 'response' });
  }

  update(theloai: ITheloai): Observable<EntityResponseType> {
    return this.http.put<ITheloai>(`${this.resourceUrl}/${getTheloaiIdentifier(theloai) as number}`, theloai, { observe: 'response' });
  }

  partialUpdate(theloai: ITheloai): Observable<EntityResponseType> {
    return this.http.patch<ITheloai>(`${this.resourceUrl}/${getTheloaiIdentifier(theloai) as number}`, theloai, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITheloai>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITheloai[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTheloaiToCollectionIfMissing(theloaiCollection: ITheloai[], ...theloaisToCheck: (ITheloai | null | undefined)[]): ITheloai[] {
    const theloais: ITheloai[] = theloaisToCheck.filter(isPresent);
    if (theloais.length > 0) {
      const theloaiCollectionIdentifiers = theloaiCollection.map(theloaiItem => getTheloaiIdentifier(theloaiItem)!);
      const theloaisToAdd = theloais.filter(theloaiItem => {
        const theloaiIdentifier = getTheloaiIdentifier(theloaiItem);
        if (theloaiIdentifier == null || theloaiCollectionIdentifiers.includes(theloaiIdentifier)) {
          return false;
        }
        theloaiCollectionIdentifiers.push(theloaiIdentifier);
        return true;
      });
      return [...theloaisToAdd, ...theloaiCollection];
    }
    return theloaiCollection;
  }
}
