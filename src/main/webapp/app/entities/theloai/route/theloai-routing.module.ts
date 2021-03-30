import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TheloaiComponent } from '../list/theloai.component';
import { TheloaiDetailComponent } from '../detail/theloai-detail.component';
import { TheloaiUpdateComponent } from '../update/theloai-update.component';
import { TheloaiRoutingResolveService } from './theloai-routing-resolve.service';

const theloaiRoute: Routes = [
  {
    path: '',
    component: TheloaiComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TheloaiDetailComponent,
    resolve: {
      theloai: TheloaiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TheloaiUpdateComponent,
    resolve: {
      theloai: TheloaiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TheloaiUpdateComponent,
    resolve: {
      theloai: TheloaiRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(theloaiRoute)],
  exports: [RouterModule],
})
export class TheloaiRoutingModule {}
