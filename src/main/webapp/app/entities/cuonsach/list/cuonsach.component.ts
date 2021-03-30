import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICuonsach } from '../cuonsach.model';
import { CuonsachService } from '../service/cuonsach.service';
import { CuonsachDeleteDialogComponent } from '../delete/cuonsach-delete-dialog.component';

@Component({
  selector: 'jhi-cuonsach',
  templateUrl: './cuonsach.component.html',
})
export class CuonsachComponent implements OnInit {
  cuonsaches?: ICuonsach[];
  isLoading = false;

  constructor(protected cuonsachService: CuonsachService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.cuonsachService.query().subscribe(
      (res: HttpResponse<ICuonsach[]>) => {
        this.isLoading = false;
        this.cuonsaches = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ICuonsach): number {
    return item.id!;
  }

  delete(cuonsach: ICuonsach): void {
    const modalRef = this.modalService.open(CuonsachDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cuonsach = cuonsach;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
