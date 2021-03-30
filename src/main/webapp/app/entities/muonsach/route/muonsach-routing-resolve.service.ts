import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMuonsach, Muonsach } from '../muonsach.model';
import { MuonsachService } from '../service/muonsach.service';

@Injectable({ providedIn: 'root' })
export class MuonsachRoutingResolveService implements Resolve<IMuonsach> {
  constructor(protected service: MuonsachService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMuonsach> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((muonsach: HttpResponse<Muonsach>) => {
          if (muonsach.body) {
            return of(muonsach.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Muonsach());
  }
}
