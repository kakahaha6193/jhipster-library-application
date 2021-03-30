import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPhongdungsach, Phongdungsach } from '../phongdungsach.model';
import { PhongdungsachService } from '../service/phongdungsach.service';

@Injectable({ providedIn: 'root' })
export class PhongdungsachRoutingResolveService implements Resolve<IPhongdungsach> {
  constructor(protected service: PhongdungsachService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPhongdungsach> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((phongdungsach: HttpResponse<Phongdungsach>) => {
          if (phongdungsach.body) {
            return of(phongdungsach.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Phongdungsach());
  }
}
