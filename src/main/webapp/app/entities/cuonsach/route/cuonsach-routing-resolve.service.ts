import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICuonsach, Cuonsach } from '../cuonsach.model';
import { CuonsachService } from '../service/cuonsach.service';

@Injectable({ providedIn: 'root' })
export class CuonsachRoutingResolveService implements Resolve<ICuonsach> {
  constructor(protected service: CuonsachService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICuonsach> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cuonsach: HttpResponse<Cuonsach>) => {
          if (cuonsach.body) {
            return of(cuonsach.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Cuonsach());
  }
}
