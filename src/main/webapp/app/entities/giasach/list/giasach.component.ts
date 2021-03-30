import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGiasach } from '../giasach.model';
import { GiasachService } from '../service/giasach.service';
import { GiasachDeleteDialogComponent } from '../delete/giasach-delete-dialog.component';

@Component({
  selector: 'jhi-giasach',
  templateUrl: './giasach.component.html',
})
export class GiasachComponent implements OnInit {
  giasaches?: IGiasach[];
  isLoading = false;

  constructor(protected giasachService: GiasachService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.giasachService.query().subscribe(
      (res: HttpResponse<IGiasach[]>) => {
        this.isLoading = false;
        this.giasaches = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IGiasach): number {
    return item.id!;
  }

  delete(giasach: IGiasach): void {
    const modalRef = this.modalService.open(GiasachDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.giasach = giasach;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
