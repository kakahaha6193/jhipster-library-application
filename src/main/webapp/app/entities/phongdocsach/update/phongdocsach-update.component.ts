import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPhongdocsach, Phongdocsach } from '../phongdocsach.model';
import { PhongdocsachService } from '../service/phongdocsach.service';

@Component({
  selector: 'jhi-phongdocsach-update',
  templateUrl: './phongdocsach-update.component.html',
})
export class PhongdocsachUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tenPhong: [],
    viTri: [],
    sucChua: [],
    giaThue: [],
  });

  constructor(protected phongdocsachService: PhongdocsachService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phongdocsach }) => {
      this.updateForm(phongdocsach);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const phongdocsach = this.createFromForm();
    if (phongdocsach.id !== undefined) {
      this.subscribeToSaveResponse(this.phongdocsachService.update(phongdocsach));
    } else {
      this.subscribeToSaveResponse(this.phongdocsachService.create(phongdocsach));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhongdocsach>>): void {
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

  protected updateForm(phongdocsach: IPhongdocsach): void {
    this.editForm.patchValue({
      id: phongdocsach.id,
      tenPhong: phongdocsach.tenPhong,
      viTri: phongdocsach.viTri,
      sucChua: phongdocsach.sucChua,
      giaThue: phongdocsach.giaThue,
    });
  }

  protected createFromForm(): IPhongdocsach {
    return {
      ...new Phongdocsach(),
      id: this.editForm.get(['id'])!.value,
      tenPhong: this.editForm.get(['tenPhong'])!.value,
      viTri: this.editForm.get(['viTri'])!.value,
      sucChua: this.editForm.get(['sucChua'])!.value,
      giaThue: this.editForm.get(['giaThue'])!.value,
    };
  }
}
