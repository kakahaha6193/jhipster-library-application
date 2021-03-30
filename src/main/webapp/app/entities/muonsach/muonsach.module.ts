import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { MuonsachComponent } from './list/muonsach.component';
import { MuonsachDetailComponent } from './detail/muonsach-detail.component';
import { MuonsachUpdateComponent } from './update/muonsach-update.component';
import { MuonsachDeleteDialogComponent } from './delete/muonsach-delete-dialog.component';
import { MuonsachRoutingModule } from './route/muonsach-routing.module';

@NgModule({
  imports: [SharedModule, MuonsachRoutingModule],
  declarations: [MuonsachComponent, MuonsachDetailComponent, MuonsachUpdateComponent, MuonsachDeleteDialogComponent],
  entryComponents: [MuonsachDeleteDialogComponent],
})
export class MuonsachModule {}
