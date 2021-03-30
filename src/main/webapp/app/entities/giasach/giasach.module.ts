import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { GiasachComponent } from './list/giasach.component';
import { GiasachDetailComponent } from './detail/giasach-detail.component';
import { GiasachUpdateComponent } from './update/giasach-update.component';
import { GiasachDeleteDialogComponent } from './delete/giasach-delete-dialog.component';
import { GiasachRoutingModule } from './route/giasach-routing.module';

@NgModule({
  imports: [SharedModule, GiasachRoutingModule],
  declarations: [GiasachComponent, GiasachDetailComponent, GiasachUpdateComponent, GiasachDeleteDialogComponent],
  entryComponents: [GiasachDeleteDialogComponent],
})
export class GiasachModule {}
