import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPhongdungsach } from '../phongdungsach.model';

@Component({
  selector: 'jhi-phongdungsach-detail',
  templateUrl: './phongdungsach-detail.component.html',
})
export class PhongdungsachDetailComponent implements OnInit {
  phongdungsach: IPhongdungsach | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phongdungsach }) => {
      this.phongdungsach = phongdungsach;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
