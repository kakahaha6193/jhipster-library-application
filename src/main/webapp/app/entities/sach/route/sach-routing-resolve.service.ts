import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISach, Sach } from '../sach.model';
import { SachService } from '../service/sach.service';

@Injectable({ providedIn: 'root' })
export class SachRoutingResolveService implements Resolve<ISach> {
  constructor(protected service: SachService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISach> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sach: HttpResponse<Sach>) => {
          if (sach.body) {
            return of(sach.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Sach());
  }
}
