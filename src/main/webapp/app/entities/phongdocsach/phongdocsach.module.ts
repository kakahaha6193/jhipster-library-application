import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { PhongdocsachComponent } from './list/phongdocsach.component';
import { PhongdocsachDetailComponent } from './detail/phongdocsach-detail.component';
import { PhongdocsachUpdateComponent } from './update/phongdocsach-update.component';
import { PhongdocsachDeleteDialogComponent } from './delete/phongdocsach-delete-dialog.component';
import { PhongdocsachRoutingModule } from './route/phongdocsach-routing.module';

@NgModule({
  imports: [SharedModule, PhongdocsachRoutingModule],
  declarations: [PhongdocsachComponent, PhongdocsachDetailComponent, PhongdocsachUpdateComponent, PhongdocsachDeleteDialogComponent],
  entryComponents: [PhongdocsachDeleteDialogComponent],
})
export class PhongdocsachModule {}
