import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICuonsach } from '../cuonsach.model';
import { CuonsachService } from '../service/cuonsach.service';

@Component({
  templateUrl: './cuonsach-delete-dialog.component.html',
})
export class CuonsachDeleteDialogComponent {
  cuonsach?: ICuonsach;

  constructor(protected cuonsachService: CuonsachService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cuonsachService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
