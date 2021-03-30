import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDocgia, Docgia } from '../docgia.model';
import { DocgiaService } from '../service/docgia.service';

@Injectable({ providedIn: 'root' })
export class DocgiaRoutingResolveService implements Resolve<IDocgia> {
  constructor(protected service: DocgiaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocgia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((docgia: HttpResponse<Docgia>) => {
          if (docgia.body) {
            return of(docgia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Docgia());
  }
}
