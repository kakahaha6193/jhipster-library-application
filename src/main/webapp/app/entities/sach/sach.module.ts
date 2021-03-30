import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { SachComponent } from './list/sach.component';
import { SachDetailComponent } from './detail/sach-detail.component';
import { SachUpdateComponent } from './update/sach-update.component';
import { SachDeleteDialogComponent } from './delete/sach-delete-dialog.component';
import { SachRoutingModule } from './route/sach-routing.module';

@NgModule({
  imports: [SharedModule, SachRoutingModule],
  declarations: [SachComponent, SachDetailComponent, SachUpdateComponent, SachDeleteDialogComponent],
  entryComponents: [SachDeleteDialogComponent],
})
export class SachModule {}
