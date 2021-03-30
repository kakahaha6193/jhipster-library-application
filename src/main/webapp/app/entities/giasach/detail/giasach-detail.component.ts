import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGiasach } from '../giasach.model';

@Component({
  selector: 'jhi-giasach-detail',
  templateUrl: './giasach-detail.component.html',
})
export class GiasachDetailComponent implements OnInit {
  giasach: IGiasach | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ giasach }) => {
      this.giasach = giasach;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
