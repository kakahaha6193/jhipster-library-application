import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGiasach } from '../giasach.model';
import { GiasachService } from '../service/giasach.service';

@Component({
  templateUrl: './giasach-delete-dialog.component.html',
})
export class GiasachDeleteDialogComponent {
  giasach?: IGiasach;

  constructor(protected giasachService: GiasachService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.giasachService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
