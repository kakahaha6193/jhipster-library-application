import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IThuephong } from '../thuephong.model';
import { ThuephongService } from '../service/thuephong.service';

@Component({
  templateUrl: './thuephong-delete-dialog.component.html',
})
export class ThuephongDeleteDialogComponent {
  thuephong?: IThuephong;

  constructor(protected thuephongService: ThuephongService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.thuephongService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
