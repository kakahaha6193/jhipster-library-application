import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ThuthuComponent } from '../list/thuthu.component';
import { ThuthuDetailComponent } from '../detail/thuthu-detail.component';
import { ThuthuUpdateComponent } from '../update/thuthu-update.component';
import { ThuthuRoutingResolveService } from './thuthu-routing-resolve.service';

const thuthuRoute: Routes = [
  {
    path: '',
    component: ThuthuComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ThuthuDetailComponent,
    resolve: {
      thuthu: ThuthuRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ThuthuUpdateComponent,
    resolve: {
      thuthu: ThuthuRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ThuthuUpdateComponent,
    resolve: {
      thuthu: ThuthuRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(thuthuRoute)],
  exports: [RouterModule],
})
export class ThuthuRoutingModule {}
