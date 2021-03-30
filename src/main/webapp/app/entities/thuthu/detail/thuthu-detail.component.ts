import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IThuthu } from '../thuthu.model';

@Component({
  selector: 'jhi-thuthu-detail',
  templateUrl: './thuthu-detail.component.html',
})
export class ThuthuDetailComponent implements OnInit {
  thuthu: IThuthu | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ thuthu }) => {
      this.thuthu = thuthu;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
