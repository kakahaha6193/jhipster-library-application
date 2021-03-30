import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IThuephong } from '../thuephong.model';

@Component({
  selector: 'jhi-thuephong-detail',
  templateUrl: './thuephong-detail.component.html',
})
export class ThuephongDetailComponent implements OnInit {
  thuephong: IThuephong | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ thuephong }) => {
      this.thuephong = thuephong;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
