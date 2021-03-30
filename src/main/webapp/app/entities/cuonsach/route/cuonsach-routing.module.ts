import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CuonsachComponent } from '../list/cuonsach.component';
import { CuonsachDetailComponent } from '../detail/cuonsach-detail.component';
import { CuonsachUpdateComponent } from '../update/cuonsach-update.component';
import { CuonsachRoutingResolveService } from './cuonsach-routing-resolve.service';

const cuonsachRoute: Routes = [
  {
    path: '',
    component: CuonsachComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CuonsachDetailComponent,
    resolve: {
      cuonsach: CuonsachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CuonsachUpdateComponent,
    resolve: {
      cuonsach: CuonsachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CuonsachUpdateComponent,
    resolve: {
      cuonsach: CuonsachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cuonsachRoute)],
  exports: [RouterModule],
})
export class CuonsachRoutingModule {}
