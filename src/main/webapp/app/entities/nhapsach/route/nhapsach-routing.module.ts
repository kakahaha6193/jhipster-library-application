import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NhapsachComponent } from '../list/nhapsach.component';
import { NhapsachDetailComponent } from '../detail/nhapsach-detail.component';
import { NhapsachUpdateComponent } from '../update/nhapsach-update.component';
import { NhapsachRoutingResolveService } from './nhapsach-routing-resolve.service';

const nhapsachRoute: Routes = [
  {
    path: '',
    component: NhapsachComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NhapsachDetailComponent,
    resolve: {
      nhapsach: NhapsachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NhapsachUpdateComponent,
    resolve: {
      nhapsach: NhapsachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NhapsachUpdateComponent,
    resolve: {
      nhapsach: NhapsachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nhapsachRoute)],
  exports: [RouterModule],
})
export class NhapsachRoutingModule {}
