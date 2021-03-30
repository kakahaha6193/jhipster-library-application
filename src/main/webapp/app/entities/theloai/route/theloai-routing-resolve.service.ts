import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITheloai, Theloai } from '../theloai.model';
import { TheloaiService } from '../service/theloai.service';

@Injectable({ providedIn: 'root' })
export class TheloaiRoutingResolveService implements Resolve<ITheloai> {
  constructor(protected service: TheloaiService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITheloai> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((theloai: HttpResponse<Theloai>) => {
          if (theloai.body) {
            return of(theloai.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Theloai());
  }
}
