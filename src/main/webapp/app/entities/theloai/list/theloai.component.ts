import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITheloai } from '../theloai.model';
import { TheloaiService } from '../service/theloai.service';
import { TheloaiDeleteDialogComponent } from '../delete/theloai-delete-dialog.component';

@Component({
  selector: 'jhi-theloai',
  templateUrl: './theloai.component.html',
})
export class TheloaiComponent implements OnInit {
  theloais?: ITheloai[];
  isLoading = false;

  constructor(protected theloaiService: TheloaiService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.theloaiService.query().subscribe(
      (res: HttpResponse<ITheloai[]>) => {
        this.isLoading = false;
        this.theloais = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITheloai): number {
    return item.id!;
  }

  delete(theloai: ITheloai): void {
    const modalRef = this.modalService.open(TheloaiDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.theloai = theloai;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
