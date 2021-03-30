import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TheloaiComponent } from './list/theloai.component';
import { TheloaiDetailComponent } from './detail/theloai-detail.component';
import { TheloaiUpdateComponent } from './update/theloai-update.component';
import { TheloaiDeleteDialogComponent } from './delete/theloai-delete-dialog.component';
import { TheloaiRoutingModule } from './route/theloai-routing.module';

@NgModule({
  imports: [SharedModule, TheloaiRoutingModule],
  declarations: [TheloaiComponent, TheloaiDetailComponent, TheloaiUpdateComponent, TheloaiDeleteDialogComponent],
  entryComponents: [TheloaiDeleteDialogComponent],
})
export class TheloaiModule {}
