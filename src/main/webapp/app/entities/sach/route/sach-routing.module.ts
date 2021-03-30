import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SachComponent } from '../list/sach.component';
import { SachDetailComponent } from '../detail/sach-detail.component';
import { SachUpdateComponent } from '../update/sach-update.component';
import { SachRoutingResolveService } from './sach-routing-resolve.service';

const sachRoute: Routes = [
  {
    path: '',
    component: SachComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SachDetailComponent,
    resolve: {
      sach: SachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SachUpdateComponent,
    resolve: {
      sach: SachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SachUpdateComponent,
    resolve: {
      sach: SachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(sachRoute)],
  exports: [RouterModule],
})
export class SachRoutingModule {}
