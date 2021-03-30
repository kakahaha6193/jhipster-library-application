import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocgia } from '../docgia.model';

@Component({
  selector: 'jhi-docgia-detail',
  templateUrl: './docgia-detail.component.html',
})
export class DocgiaDetailComponent implements OnInit {
  docgia: IDocgia | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ docgia }) => {
      this.docgia = docgia;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
