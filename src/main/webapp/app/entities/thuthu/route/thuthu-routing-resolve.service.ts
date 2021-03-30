import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IThuthu, Thuthu } from '../thuthu.model';
import { ThuthuService } from '../service/thuthu.service';

@Injectable({ providedIn: 'root' })
export class ThuthuRoutingResolveService implements Resolve<IThuthu> {
  constructor(protected service: ThuthuService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IThuthu> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((thuthu: HttpResponse<Thuthu>) => {
          if (thuthu.body) {
            return of(thuthu.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Thuthu());
  }
}
