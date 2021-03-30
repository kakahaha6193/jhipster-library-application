import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { NhapsachComponent } from './list/nhapsach.component';
import { NhapsachDetailComponent } from './detail/nhapsach-detail.component';
import { NhapsachUpdateComponent } from './update/nhapsach-update.component';
import { NhapsachDeleteDialogComponent } from './delete/nhapsach-delete-dialog.component';
import { NhapsachRoutingModule } from './route/nhapsach-routing.module';

@NgModule({
  imports: [SharedModule, NhapsachRoutingModule],
  declarations: [NhapsachComponent, NhapsachDetailComponent, NhapsachUpdateComponent, NhapsachDeleteDialogComponent],
  entryComponents: [NhapsachDeleteDialogComponent],
})
export class NhapsachModule {}
