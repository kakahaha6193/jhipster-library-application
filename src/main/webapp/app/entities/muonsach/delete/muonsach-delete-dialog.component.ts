import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMuonsach } from '../muonsach.model';
import { MuonsachService } from '../service/muonsach.service';

@Component({
  templateUrl: './muonsach-delete-dialog.component.html',
})
export class MuonsachDeleteDialogComponent {
  muonsach?: IMuonsach;

  constructor(protected muonsachService: MuonsachService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.muonsachService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
