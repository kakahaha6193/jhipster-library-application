import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITheloai } from '../theloai.model';

@Component({
  selector: 'jhi-theloai-detail',
  templateUrl: './theloai-detail.component.html',
})
export class TheloaiDetailComponent implements OnInit {
  theloai: ITheloai | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ theloai }) => {
      this.theloai = theloai;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
