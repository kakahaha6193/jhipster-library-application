import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PhongdocsachComponent } from '../list/phongdocsach.component';
import { PhongdocsachDetailComponent } from '../detail/phongdocsach-detail.component';
import { PhongdocsachUpdateComponent } from '../update/phongdocsach-update.component';
import { PhongdocsachRoutingResolveService } from './phongdocsach-routing-resolve.service';

const phongdocsachRoute: Routes = [
  {
    path: '',
    component: PhongdocsachComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PhongdocsachDetailComponent,
    resolve: {
      phongdocsach: PhongdocsachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PhongdocsachUpdateComponent,
    resolve: {
      phongdocsach: PhongdocsachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PhongdocsachUpdateComponent,
    resolve: {
      phongdocsach: PhongdocsachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(phongdocsachRoute)],
  exports: [RouterModule],
})
export class PhongdocsachRoutingModule {}
