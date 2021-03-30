import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITheloai } from '../theloai.model';
import { TheloaiService } from '../service/theloai.service';

@Component({
  templateUrl: './theloai-delete-dialog.component.html',
})
export class TheloaiDeleteDialogComponent {
  theloai?: ITheloai;

  constructor(protected theloaiService: TheloaiService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.theloaiService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
