import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IGiasach, Giasach } from '../giasach.model';
import { GiasachService } from '../service/giasach.service';
import { IPhongdungsach } from 'app/entities/phongdungsach/phongdungsach.model';
import { PhongdungsachService } from 'app/entities/phongdungsach/service/phongdungsach.service';

@Component({
  selector: 'jhi-giasach-update',
  templateUrl: './giasach-update.component.html',
})
export class GiasachUpdateComponent implements OnInit {
  isSaving = false;

  phongdungsachesSharedCollection: IPhongdungsach[] = [];

  editForm = this.fb.group({
    id: [],
    thuTu: [],
    phongdungsach: [],
  });

  constructor(
    protected giasachService: GiasachService,
    protected phongdungsachService: PhongdungsachService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ giasach }) => {
      this.updateForm(giasach);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const giasach = this.createFromForm();
    if (giasach.id !== undefined) {
      this.subscribeToSaveResponse(this.giasachService.update(giasach));
    } else {
      this.subscribeToSaveResponse(this.giasachService.create(giasach));
    }
  }

  trackPhongdungsachById(index: number, item: IPhongdungsach): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGiasach>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(giasach: IGiasach): void {
    this.editForm.patchValue({
      id: giasach.id,
      thuTu: giasach.thuTu,
      phongdungsach: giasach.phongdungsach,
    });

    this.phongdungsachesSharedCollection = this.phongdungsachService.addPhongdungsachToCollectionIfMissing(
      this.phongdungsachesSharedCollection,
      giasach.phongdungsach
    );
  }

  protected loadRelationshipsOptions(): void {
    this.phongdungsachService
      .query()
      .pipe(map((res: HttpResponse<IPhongdungsach[]>) => res.body ?? []))
      .pipe(
        map((phongdungsaches: IPhongdungsach[]) =>
          this.phongdungsachService.addPhongdungsachToCollectionIfMissing(phongdungsaches, this.editForm.get('phongdungsach')!.value)
        )
      )
      .subscribe((phongdungsaches: IPhongdungsach[]) => (this.phongdungsachesSharedCollection = phongdungsaches));
  }

  protected createFromForm(): IGiasach {
    return {
      ...new Giasach(),
      id: this.editForm.get(['id'])!.value,
      thuTu: this.editForm.get(['thuTu'])!.value,
      phongdungsach: this.editForm.get(['phongdungsach'])!.value,
    };
  }
}
