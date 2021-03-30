import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INhapsach } from '../nhapsach.model';
import { NhapsachService } from '../service/nhapsach.service';

@Component({
  templateUrl: './nhapsach-delete-dialog.component.html',
})
export class NhapsachDeleteDialogComponent {
  nhapsach?: INhapsach;

  constructor(protected nhapsachService: NhapsachService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nhapsachService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
