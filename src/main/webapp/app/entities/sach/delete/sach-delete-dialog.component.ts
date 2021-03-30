import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISach } from '../sach.model';
import { SachService } from '../service/sach.service';

@Component({
  templateUrl: './sach-delete-dialog.component.html',
})
export class SachDeleteDialogComponent {
  sach?: ISach;

  constructor(protected sachService: SachService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sachService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
