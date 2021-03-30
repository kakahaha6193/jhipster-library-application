import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INhaxuatban, Nhaxuatban } from '../nhaxuatban.model';
import { NhaxuatbanService } from '../service/nhaxuatban.service';

@Injectable({ providedIn: 'root' })
export class NhaxuatbanRoutingResolveService implements Resolve<INhaxuatban> {
  constructor(protected service: NhaxuatbanService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INhaxuatban> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nhaxuatban: HttpResponse<Nhaxuatban>) => {
          if (nhaxuatban.body) {
            return of(nhaxuatban.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Nhaxuatban());
  }
}
