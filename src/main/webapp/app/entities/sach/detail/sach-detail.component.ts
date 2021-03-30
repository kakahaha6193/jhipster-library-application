import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISach } from '../sach.model';

@Component({
  selector: 'jhi-sach-detail',
  templateUrl: './sach-detail.component.html',
})
export class SachDetailComponent implements OnInit {
  sach: ISach | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sach }) => {
      this.sach = sach;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
