import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITheloai, Theloai } from '../theloai.model';
import { TheloaiService } from '../service/theloai.service';

@Component({
  selector: 'jhi-theloai-update',
  templateUrl: './theloai-update.component.html',
})
export class TheloaiUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tenTheLoai: [],
  });

  constructor(protected theloaiService: TheloaiService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ theloai }) => {
      this.updateForm(theloai);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const theloai = this.createFromForm();
    if (theloai.id !== undefined) {
      this.subscribeToSaveResponse(this.theloaiService.update(theloai));
    } else {
      this.subscribeToSaveResponse(this.theloaiService.create(theloai));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITheloai>>): void {
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

  protected updateForm(theloai: ITheloai): void {
    this.editForm.patchValue({
      id: theloai.id,
      tenTheLoai: theloai.tenTheLoai,
    });
  }

  protected createFromForm(): ITheloai {
    return {
      ...new Theloai(),
      id: this.editForm.get(['id'])!.value,
      tenTheLoai: this.editForm.get(['tenTheLoai'])!.value,
    };
  }
}
