import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGiasach, Giasach } from '../giasach.model';
import { GiasachService } from '../service/giasach.service';

@Injectable({ providedIn: 'root' })
export class GiasachRoutingResolveService implements Resolve<IGiasach> {
  constructor(protected service: GiasachService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGiasach> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((giasach: HttpResponse<Giasach>) => {
          if (giasach.body) {
            return of(giasach.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Giasach());
  }
}
