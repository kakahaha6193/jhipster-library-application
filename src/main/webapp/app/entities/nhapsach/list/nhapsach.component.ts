import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INhapsach } from '../nhapsach.model';
import { NhapsachService } from '../service/nhapsach.service';
import { NhapsachDeleteDialogComponent } from '../delete/nhapsach-delete-dialog.component';

@Component({
  selector: 'jhi-nhapsach',
  templateUrl: './nhapsach.component.html',
})
export class NhapsachComponent implements OnInit {
  nhapsaches?: INhapsach[];
  isLoading = false;

  constructor(protected nhapsachService: NhapsachService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.nhapsachService.query().subscribe(
      (res: HttpResponse<INhapsach[]>) => {
        this.isLoading = false;
        this.nhapsaches = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: INhapsach): number {
    return item.id!;
  }

  delete(nhapsach: INhapsach): void {
    const modalRef = this.modalService.open(NhapsachDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nhapsach = nhapsach;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
