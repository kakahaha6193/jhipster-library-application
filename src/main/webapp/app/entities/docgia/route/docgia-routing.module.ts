import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DocgiaComponent } from '../list/docgia.component';
import { DocgiaDetailComponent } from '../detail/docgia-detail.component';
import { DocgiaUpdateComponent } from '../update/docgia-update.component';
import { DocgiaRoutingResolveService } from './docgia-routing-resolve.service';

const docgiaRoute: Routes = [
  {
    path: '',
    component: DocgiaComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DocgiaDetailComponent,
    resolve: {
      docgia: DocgiaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DocgiaUpdateComponent,
    resolve: {
      docgia: DocgiaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DocgiaUpdateComponent,
    resolve: {
      docgia: DocgiaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(docgiaRoute)],
  exports: [RouterModule],
})
export class DocgiaRoutingModule {}
