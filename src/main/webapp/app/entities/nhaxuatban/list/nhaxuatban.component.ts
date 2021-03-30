import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INhaxuatban } from '../nhaxuatban.model';
import { NhaxuatbanService } from '../service/nhaxuatban.service';
import { NhaxuatbanDeleteDialogComponent } from '../delete/nhaxuatban-delete-dialog.component';

@Component({
  selector: 'jhi-nhaxuatban',
  templateUrl: './nhaxuatban.component.html',
})
export class NhaxuatbanComponent implements OnInit {
  nhaxuatbans?: INhaxuatban[];
  isLoading = false;

  constructor(protected nhaxuatbanService: NhaxuatbanService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.nhaxuatbanService.query().subscribe(
      (res: HttpResponse<INhaxuatban[]>) => {
        this.isLoading = false;
        this.nhaxuatbans = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: INhaxuatban): number {
    return item.id!;
  }

  delete(nhaxuatban: INhaxuatban): void {
    const modalRef = this.modalService.open(NhaxuatbanDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nhaxuatban = nhaxuatban;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
