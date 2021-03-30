import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NhaxuatbanComponent } from '../list/nhaxuatban.component';
import { NhaxuatbanDetailComponent } from '../detail/nhaxuatban-detail.component';
import { NhaxuatbanUpdateComponent } from '../update/nhaxuatban-update.component';
import { NhaxuatbanRoutingResolveService } from './nhaxuatban-routing-resolve.service';

const nhaxuatbanRoute: Routes = [
  {
    path: '',
    component: NhaxuatbanComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NhaxuatbanDetailComponent,
    resolve: {
      nhaxuatban: NhaxuatbanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NhaxuatbanUpdateComponent,
    resolve: {
      nhaxuatban: NhaxuatbanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NhaxuatbanUpdateComponent,
    resolve: {
      nhaxuatban: NhaxuatbanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nhaxuatbanRoute)],
  exports: [RouterModule],
})
export class NhaxuatbanRoutingModule {}
