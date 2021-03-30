import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPhongdocsach, Phongdocsach } from '../phongdocsach.model';
import { PhongdocsachService } from '../service/phongdocsach.service';

@Injectable({ providedIn: 'root' })
export class PhongdocsachRoutingResolveService implements Resolve<IPhongdocsach> {
  constructor(protected service: PhongdocsachService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPhongdocsach> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((phongdocsach: HttpResponse<Phongdocsach>) => {
          if (phongdocsach.body) {
            return of(phongdocsach.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Phongdocsach());
  }
}
