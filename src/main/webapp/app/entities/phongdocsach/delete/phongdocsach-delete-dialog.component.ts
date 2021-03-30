import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhongdocsach } from '../phongdocsach.model';
import { PhongdocsachService } from '../service/phongdocsach.service';

@Component({
  templateUrl: './phongdocsach-delete-dialog.component.html',
})
export class PhongdocsachDeleteDialogComponent {
  phongdocsach?: IPhongdocsach;

  constructor(protected phongdocsachService: PhongdocsachService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.phongdocsachService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
