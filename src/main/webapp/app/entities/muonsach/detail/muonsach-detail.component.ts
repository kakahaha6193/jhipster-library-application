import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMuonsach } from '../muonsach.model';

@Component({
  selector: 'jhi-muonsach-detail',
  templateUrl: './muonsach-detail.component.html',
})
export class MuonsachDetailComponent implements OnInit {
  muonsach: IMuonsach | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ muonsach }) => {
      this.muonsach = muonsach;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
