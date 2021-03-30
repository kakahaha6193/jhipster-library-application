import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CuonsachComponent } from './list/cuonsach.component';
import { CuonsachDetailComponent } from './detail/cuonsach-detail.component';
import { CuonsachUpdateComponent } from './update/cuonsach-update.component';
import { CuonsachDeleteDialogComponent } from './delete/cuonsach-delete-dialog.component';
import { CuonsachRoutingModule } from './route/cuonsach-routing.module';

@NgModule({
  imports: [SharedModule, CuonsachRoutingModule],
  declarations: [CuonsachComponent, CuonsachDetailComponent, CuonsachUpdateComponent, CuonsachDeleteDialogComponent],
  entryComponents: [CuonsachDeleteDialogComponent],
})
export class CuonsachModule {}
