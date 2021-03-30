import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhongdungsach } from '../phongdungsach.model';
import { PhongdungsachService } from '../service/phongdungsach.service';

@Component({
  templateUrl: './phongdungsach-delete-dialog.component.html',
})
export class PhongdungsachDeleteDialogComponent {
  phongdungsach?: IPhongdungsach;

  constructor(protected phongdungsachService: PhongdungsachService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.phongdungsachService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
