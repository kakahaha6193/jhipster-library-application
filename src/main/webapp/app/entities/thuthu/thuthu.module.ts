import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ThuthuComponent } from './list/thuthu.component';
import { ThuthuDetailComponent } from './detail/thuthu-detail.component';
import { ThuthuUpdateComponent } from './update/thuthu-update.component';
import { ThuthuDeleteDialogComponent } from './delete/thuthu-delete-dialog.component';
import { ThuthuRoutingModule } from './route/thuthu-routing.module';

@NgModule({
  imports: [SharedModule, ThuthuRoutingModule],
  declarations: [ThuthuComponent, ThuthuDetailComponent, ThuthuUpdateComponent, ThuthuDeleteDialogComponent],
  entryComponents: [ThuthuDeleteDialogComponent],
})
export class ThuthuModule {}
