import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhongdocsach } from '../phongdocsach.model';
import { PhongdocsachService } from '../service/phongdocsach.service';
import { PhongdocsachDeleteDialogComponent } from '../delete/phongdocsach-delete-dialog.component';

@Component({
  selector: 'jhi-phongdocsach',
  templateUrl: './phongdocsach.component.html',
})
export class PhongdocsachComponent implements OnInit {
  phongdocsaches?: IPhongdocsach[];
  isLoading = false;

  constructor(protected phongdocsachService: PhongdocsachService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.phongdocsachService.query().subscribe(
      (res: HttpResponse<IPhongdocsach[]>) => {
        this.isLoading = false;
        this.phongdocsaches = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPhongdocsach): number {
    return item.id!;
  }

  delete(phongdocsach: IPhongdocsach): void {
    const modalRef = this.modalService.open(PhongdocsachDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.phongdocsach = phongdocsach;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
