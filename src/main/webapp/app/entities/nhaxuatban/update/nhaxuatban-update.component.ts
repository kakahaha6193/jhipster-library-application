import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { INhaxuatban, Nhaxuatban } from '../nhaxuatban.model';
import { NhaxuatbanService } from '../service/nhaxuatban.service';

@Component({
  selector: 'jhi-nhaxuatban-update',
  templateUrl: './nhaxuatban-update.component.html',
})
export class NhaxuatbanUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tenNXB: [],
    diaChi: [],
  });

  constructor(protected nhaxuatbanService: NhaxuatbanService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nhaxuatban }) => {
      this.updateForm(nhaxuatban);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nhaxuatban = this.createFromForm();
    if (nhaxuatban.id !== undefined) {
      this.subscribeToSaveResponse(this.nhaxuatbanService.update(nhaxuatban));
    } else {
      this.subscribeToSaveResponse(this.nhaxuatbanService.create(nhaxuatban));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INhaxuatban>>): void {
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

  protected updateForm(nhaxuatban: INhaxuatban): void {
    this.editForm.patchValue({
      id: nhaxuatban.id,
      tenNXB: nhaxuatban.tenNXB,
      diaChi: nhaxuatban.diaChi,
    });
  }

  protected createFromForm(): INhaxuatban {
    return {
      ...new Nhaxuatban(),
      id: this.editForm.get(['id'])!.value,
      tenNXB: this.editForm.get(['tenNXB'])!.value,
      diaChi: this.editForm.get(['diaChi'])!.value,
    };
  }
}
