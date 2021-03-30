import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { NhaxuatbanComponent } from './list/nhaxuatban.component';
import { NhaxuatbanDetailComponent } from './detail/nhaxuatban-detail.component';
import { NhaxuatbanUpdateComponent } from './update/nhaxuatban-update.component';
import { NhaxuatbanDeleteDialogComponent } from './delete/nhaxuatban-delete-dialog.component';
import { NhaxuatbanRoutingModule } from './route/nhaxuatban-routing.module';

@NgModule({
  imports: [SharedModule, NhaxuatbanRoutingModule],
  declarations: [NhaxuatbanComponent, NhaxuatbanDetailComponent, NhaxuatbanUpdateComponent, NhaxuatbanDeleteDialogComponent],
  entryComponents: [NhaxuatbanDeleteDialogComponent],
})
export class NhaxuatbanModule {}
