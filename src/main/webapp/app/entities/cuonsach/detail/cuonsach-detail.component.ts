import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICuonsach } from '../cuonsach.model';

@Component({
  selector: 'jhi-cuonsach-detail',
  templateUrl: './cuonsach-detail.component.html',
})
export class CuonsachDetailComponent implements OnInit {
  cuonsach: ICuonsach | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cuonsach }) => {
      this.cuonsach = cuonsach;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
