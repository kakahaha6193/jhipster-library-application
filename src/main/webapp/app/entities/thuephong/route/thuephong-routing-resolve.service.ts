import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IThuephong, Thuephong } from '../thuephong.model';
import { ThuephongService } from '../service/thuephong.service';

@Injectable({ providedIn: 'root' })
export class ThuephongRoutingResolveService implements Resolve<IThuephong> {
  constructor(protected service: ThuephongService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IThuephong> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((thuephong: HttpResponse<Thuephong>) => {
          if (thuephong.body) {
            return of(thuephong.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Thuephong());
  }
}
