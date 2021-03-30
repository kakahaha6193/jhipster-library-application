import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDocgia, Docgia } from '../docgia.model';
import { DocgiaService } from '../service/docgia.service';

@Component({
  selector: 'jhi-docgia-update',
  templateUrl: './docgia-update.component.html',
})
export class DocgiaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    hoTen: [],
    ngaySinh: [],
    diaChi: [],
    cmt: [],
    trangThai: [],
    tienCoc: [],
  });

  constructor(protected docgiaService: DocgiaService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ docgia }) => {
      if (docgia.id === undefined) {
        const today = dayjs().startOf('day');
        docgia.ngaySinh = today;
      }

      this.updateForm(docgia);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const docgia = this.createFromForm();
    if (docgia.id !== undefined) {
      this.subscribeToSaveResponse(this.docgiaService.update(docgia));
    } else {
      this.subscribeToSaveResponse(this.docgiaService.create(docgia));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocgia>>): void {
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

  protected updateForm(docgia: IDocgia): void {
    this.editForm.patchValue({
      id: docgia.id,
      hoTen: docgia.hoTen,
      ngaySinh: docgia.ngaySinh ? docgia.ngaySinh.format(DATE_TIME_FORMAT) : null,
      diaChi: docgia.diaChi,
      cmt: docgia.cmt,
      trangThai: docgia.trangThai,
      tienCoc: docgia.tienCoc,
    });
  }

  protected createFromForm(): IDocgia {
    return {
      ...new Docgia(),
      id: this.editForm.get(['id'])!.value,
      hoTen: this.editForm.get(['hoTen'])!.value,
      ngaySinh: this.editForm.get(['ngaySinh'])!.value ? dayjs(this.editForm.get(['ngaySinh'])!.value, DATE_TIME_FORMAT) : undefined,
      diaChi: this.editForm.get(['diaChi'])!.value,
      cmt: this.editForm.get(['cmt'])!.value,
      trangThai: this.editForm.get(['trangThai'])!.value,
      tienCoc: this.editForm.get(['tienCoc'])!.value,
    };
  }
}
