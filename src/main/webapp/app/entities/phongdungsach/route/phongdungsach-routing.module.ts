import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PhongdungsachComponent } from '../list/phongdungsach.component';
import { PhongdungsachDetailComponent } from '../detail/phongdungsach-detail.component';
import { PhongdungsachUpdateComponent } from '../update/phongdungsach-update.component';
import { PhongdungsachRoutingResolveService } from './phongdungsach-routing-resolve.service';

const phongdungsachRoute: Routes = [
  {
    path: '',
    component: PhongdungsachComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PhongdungsachDetailComponent,
    resolve: {
      phongdungsach: PhongdungsachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PhongdungsachUpdateComponent,
    resolve: {
      phongdungsach: PhongdungsachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PhongdungsachUpdateComponent,
    resolve: {
      phongdungsach: PhongdungsachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(phongdungsachRoute)],
  exports: [RouterModule],
})
export class PhongdungsachRoutingModule {}
