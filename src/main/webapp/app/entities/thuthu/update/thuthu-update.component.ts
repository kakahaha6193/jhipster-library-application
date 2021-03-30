import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IThuthu, Thuthu } from '../thuthu.model';
import { ThuthuService } from '../service/thuthu.service';

@Component({
  selector: 'jhi-thuthu-update',
  templateUrl: './thuthu-update.component.html',
})
export class ThuthuUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    hoTen: [],
    username: [],
    password: [],
  });

  constructor(protected thuthuService: ThuthuService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ thuthu }) => {
      this.updateForm(thuthu);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const thuthu = this.createFromForm();
    if (thuthu.id !== undefined) {
      this.subscribeToSaveResponse(this.thuthuService.update(thuthu));
    } else {
      this.subscribeToSaveResponse(this.thuthuService.create(thuthu));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IThuthu>>): void {
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

  protected updateForm(thuthu: IThuthu): void {
    this.editForm.patchValue({
      id: thuthu.id,
      hoTen: thuthu.hoTen,
      username: thuthu.username,
      password: thuthu.password,
    });
  }

  protected createFromForm(): IThuthu {
    return {
      ...new Thuthu(),
      id: this.editForm.get(['id'])!.value,
      hoTen: this.editForm.get(['hoTen'])!.value,
      username: this.editForm.get(['username'])!.value,
      password: this.editForm.get(['password'])!.value,
    };
  }
}
