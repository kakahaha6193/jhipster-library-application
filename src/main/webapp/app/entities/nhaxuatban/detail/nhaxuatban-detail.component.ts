import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INhaxuatban } from '../nhaxuatban.model';

@Component({
  selector: 'jhi-nhaxuatban-detail',
  templateUrl: './nhaxuatban-detail.component.html',
})
export class NhaxuatbanDetailComponent implements OnInit {
  nhaxuatban: INhaxuatban | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nhaxuatban }) => {
      this.nhaxuatban = nhaxuatban;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
