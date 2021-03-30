import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPhongdocsach } from '../phongdocsach.model';

@Component({
  selector: 'jhi-phongdocsach-detail',
  templateUrl: './phongdocsach-detail.component.html',
})
export class PhongdocsachDetailComponent implements OnInit {
  phongdocsach: IPhongdocsach | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phongdocsach }) => {
      this.phongdocsach = phongdocsach;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
