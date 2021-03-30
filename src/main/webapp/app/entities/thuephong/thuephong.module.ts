import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ThuephongComponent } from './list/thuephong.component';
import { ThuephongDetailComponent } from './detail/thuephong-detail.component';
import { ThuephongUpdateComponent } from './update/thuephong-update.component';
import { ThuephongDeleteDialogComponent } from './delete/thuephong-delete-dialog.component';
import { ThuephongRoutingModule } from './route/thuephong-routing.module';

@NgModule({
  imports: [SharedModule, ThuephongRoutingModule],
  declarations: [ThuephongComponent, ThuephongDetailComponent, ThuephongUpdateComponent, ThuephongDeleteDialogComponent],
  entryComponents: [ThuephongDeleteDialogComponent],
})
export class ThuephongModule {}
