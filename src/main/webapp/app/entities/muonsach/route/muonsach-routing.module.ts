import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MuonsachComponent } from '../list/muonsach.component';
import { MuonsachDetailComponent } from '../detail/muonsach-detail.component';
import { MuonsachUpdateComponent } from '../update/muonsach-update.component';
import { MuonsachRoutingResolveService } from './muonsach-routing-resolve.service';

const muonsachRoute: Routes = [
  {
    path: '',
    component: MuonsachComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MuonsachDetailComponent,
    resolve: {
      muonsach: MuonsachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MuonsachUpdateComponent,
    resolve: {
      muonsach: MuonsachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MuonsachUpdateComponent,
    resolve: {
      muonsach: MuonsachRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(muonsachRoute)],
  exports: [RouterModule],
})
export class MuonsachRoutingModule {}
