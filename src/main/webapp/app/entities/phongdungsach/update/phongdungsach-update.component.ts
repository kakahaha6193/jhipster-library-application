import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPhongdungsach, Phongdungsach } from '../phongdungsach.model';
import { PhongdungsachService } from '../service/phongdungsach.service';

@Component({
  selector: 'jhi-phongdungsach-update',
  templateUrl: './phongdungsach-update.component.html',
})
export class PhongdungsachUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tenPhong: [],
    viTri: [],
  });

  constructor(protected phongdungsachService: PhongdungsachService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phongdungsach }) => {
      this.updateForm(phongdungsach);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const phongdungsach = this.createFromForm();
    if (phongdungsach.id !== undefined) {
      this.subscribeToSaveResponse(this.phongdungsachService.update(phongdungsach));
    } else {
      this.subscribeToSaveResponse(this.phongdungsachService.create(phongdungsach));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhongdungsach>>): void {
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

  protected updateForm(phongdungsach: IPhongdungsach): void {
    this.editForm.patchValue({
      id: phongdungsach.id,
      tenPhong: phongdungsach.tenPhong,
      viTri: phongdungsach.viTri,
    });
  }

  protected createFromForm(): IPhongdungsach {
    return {
      ...new Phongdungsach(),
      id: this.editForm.get(['id'])!.value,
      tenPhong: this.editForm.get(['tenPhong'])!.value,
      viTri: this.editForm.get(['viTri'])!.value,
    };
  }
}
