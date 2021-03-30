import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GiasachComponent } from '../list/giasach.component';
import { GiasachDetailComponent } from '../detail/giasach-detail.component';
import { GiasachUpdateComponent } from '../update/giasach-update.component';
import { GiasachRoutingResolveService } from './giasach-routing-resolve.service';

const giasachRoute: Routes = [
  {
    path: '',
    component: GiasachComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GiasachDetailComponent,
    resolve: {
      giasach: GiasachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GiasachUpdateComponent,
    resolve: {
      giasach: GiasachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GiasachUpdateComponent,
    resolve: {
      giasach: GiasachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(giasachRoute)],
  exports: [RouterModule],
})
export class GiasachRoutingModule {}
