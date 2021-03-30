import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhongdungsach } from '../phongdungsach.model';
import { PhongdungsachService } from '../service/phongdungsach.service';
import { PhongdungsachDeleteDialogComponent } from '../delete/phongdungsach-delete-dialog.component';

@Component({
  selector: 'jhi-phongdungsach',
  templateUrl: './phongdungsach.component.html',
})
export class PhongdungsachComponent implements OnInit {
  phongdungsaches?: IPhongdungsach[];
  isLoading = false;

  constructor(protected phongdungsachService: PhongdungsachService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.phongdungsachService.query().subscribe(
      (res: HttpResponse<IPhongdungsach[]>) => {
        this.isLoading = false;
        this.phongdungsaches = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPhongdungsach): number {
    return item.id!;
  }

  delete(phongdungsach: IPhongdungsach): void {
    const modalRef = this.modalService.open(PhongdungsachDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.phongdungsach = phongdungsach;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
