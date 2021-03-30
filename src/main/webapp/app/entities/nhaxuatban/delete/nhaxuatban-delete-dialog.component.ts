import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INhaxuatban } from '../nhaxuatban.model';
import { NhaxuatbanService } from '../service/nhaxuatban.service';

@Component({
  templateUrl: './nhaxuatban-delete-dialog.component.html',
})
export class NhaxuatbanDeleteDialogComponent {
  nhaxuatban?: INhaxuatban;

  constructor(protected nhaxuatbanService: NhaxuatbanService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nhaxuatbanService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
