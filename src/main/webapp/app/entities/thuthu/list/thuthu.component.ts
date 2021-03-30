import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IThuthu } from '../thuthu.model';
import { ThuthuService } from '../service/thuthu.service';
import { ThuthuDeleteDialogComponent } from '../delete/thuthu-delete-dialog.component';

@Component({
  selector: 'jhi-thuthu',
  templateUrl: './thuthu.component.html',
})
export class ThuthuComponent implements OnInit {
  thuthus?: IThuthu[];
  isLoading = false;

  constructor(protected thuthuService: ThuthuService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.thuthuService.query().subscribe(
      (res: HttpResponse<IThuthu[]>) => {
        this.isLoading = false;
        this.thuthus = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IThuthu): number {
    return item.id!;
  }

  delete(thuthu: IThuthu): void {
    const modalRef = this.modalService.open(ThuthuDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.thuthu = thuthu;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
