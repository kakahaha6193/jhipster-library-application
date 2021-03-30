import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IThuthu } from '../thuthu.model';
import { ThuthuService } from '../service/thuthu.service';

@Component({
  templateUrl: './thuthu-delete-dialog.component.html',
})
export class ThuthuDeleteDialogComponent {
  thuthu?: IThuthu;

  constructor(protected thuthuService: ThuthuService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.thuthuService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
