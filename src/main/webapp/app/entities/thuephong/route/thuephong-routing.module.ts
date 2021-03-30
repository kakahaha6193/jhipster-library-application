import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ThuephongComponent } from '../list/thuephong.component';
import { ThuephongDetailComponent } from '../detail/thuephong-detail.component';
import { ThuephongUpdateComponent } from '../update/thuephong-update.component';
import { ThuephongRoutingResolveService } from './thuephong-routing-resolve.service';

const thuephongRoute: Routes = [
  {
    path: '',
    component: ThuephongComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ThuephongDetailComponent,
    resolve: {
      thuephong: ThuephongRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ThuephongUpdateComponent,
    resolve: {
      thuephong: ThuephongRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ThuephongUpdateComponent,
    resolve: {
      thuephong: ThuephongRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(thuephongRoute)],
  exports: [RouterModule],
})
export class ThuephongRoutingModule {}
