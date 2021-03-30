import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocgia } from '../docgia.model';
import { DocgiaService } from '../service/docgia.service';

@Component({
  templateUrl: './docgia-delete-dialog.component.html',
})
export class DocgiaDeleteDialogComponent {
  docgia?: IDocgia;

  constructor(protected docgiaService: DocgiaService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.docgiaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
