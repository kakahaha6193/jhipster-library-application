import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { PhongdungsachComponent } from './list/phongdungsach.component';
import { PhongdungsachDetailComponent } from './detail/phongdungsach-detail.component';
import { PhongdungsachUpdateComponent } from './update/phongdungsach-update.component';
import { PhongdungsachDeleteDialogComponent } from './delete/phongdungsach-delete-dialog.component';
import { PhongdungsachRoutingModule } from './route/phongdungsach-routing.module';

@NgModule({
  imports: [SharedModule, PhongdungsachRoutingModule],
  declarations: [PhongdungsachComponent, PhongdungsachDetailComponent, PhongdungsachUpdateComponent, PhongdungsachDeleteDialogComponent],
  entryComponents: [PhongdungsachDeleteDialogComponent],
})
export class PhongdungsachModule {}
