import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INhapsach, Nhapsach } from '../nhapsach.model';
import { NhapsachService } from '../service/nhapsach.service';

@Injectable({ providedIn: 'root' })
export class NhapsachRoutingResolveService implements Resolve<INhapsach> {
  constructor(protected service: NhapsachService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INhapsach> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nhapsach: HttpResponse<Nhapsach>) => {
          if (nhapsach.body) {
            return of(nhapsach.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Nhapsach());
  }
}
