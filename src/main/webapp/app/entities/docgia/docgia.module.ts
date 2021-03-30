import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DocgiaComponent } from './list/docgia.component';
import { DocgiaDetailComponent } from './detail/docgia-detail.component';
import { DocgiaUpdateComponent } from './update/docgia-update.component';
import { DocgiaDeleteDialogComponent } from './delete/docgia-delete-dialog.component';
import { DocgiaRoutingModule } from './route/docgia-routing.module';

@NgModule({
  imports: [SharedModule, DocgiaRoutingModule],
  declarations: [DocgiaComponent, DocgiaDetailComponent, DocgiaUpdateComponent, DocgiaDeleteDialogComponent],
  entryComponents: [DocgiaDeleteDialogComponent],
})
export class DocgiaModule {}
