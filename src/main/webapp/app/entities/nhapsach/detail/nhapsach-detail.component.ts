import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INhapsach } from '../nhapsach.model';

@Component({
  selector: 'jhi-nhapsach-detail',
  templateUrl: './nhapsach-detail.component.html',
})
export class NhapsachDetailComponent implements OnInit {
  nhapsach: INhapsach | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nhapsach }) => {
      this.nhapsach = nhapsach;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
