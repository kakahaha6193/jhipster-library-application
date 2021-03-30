import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { INhapsach, Nhapsach } from '../nhapsach.model';
import { NhapsachService } from '../service/nhapsach.service';
import { IThuthu } from 'app/entities/thuthu/thuthu.model';
import { ThuthuService } from 'app/entities/thuthu/service/thuthu.service';

@Component({
  selector: 'jhi-nhapsach-update',
  templateUrl: './nhapsach-update.component.html',
})
export class NhapsachUpdateComponent implements OnInit {
  isSaving = false;

  thuthusSharedCollection: IThuthu[] = [];

  editForm = this.fb.group({
    id: [],
    ngayGioNhap: [],
    soLuong: [],
    thuthu: [],
    thuthu: [],
  });

  constructor(
    protected nhapsachService: NhapsachService,
    protected thuthuService: ThuthuService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nhapsach }) => {
      if (nhapsach.id === undefined) {
        const today = dayjs().startOf('day');
        nhapsach.ngayGioNhap = today;
      }

      this.updateForm(nhapsach);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nhapsach = this.createFromForm();
    if (nhapsach.id !== undefined) {
      this.subscribeToSaveResponse(this.nhapsachService.update(nhapsach));
    } else {
      this.subscribeToSaveResponse(this.nhapsachService.create(nhapsach));
    }
  }

  trackThuthuById(index: number, item: IThuthu): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INhapsach>>): void {
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

  protected updateForm(nhapsach: INhapsach): void {
    this.editForm.patchValue({
      id: nhapsach.id,
      ngayGioNhap: nhapsach.ngayGioNhap ? nhapsach.ngayGioNhap.format(DATE_TIME_FORMAT) : null,
      soLuong: nhapsach.soLuong,
      thuthu: nhapsach.thuthu,
      thuthu: nhapsach.thuthu,
    });

    this.thuthusSharedCollection = this.thuthuService.addThuthuToCollectionIfMissing(
      this.thuthusSharedCollection,
      nhapsach.thuthu,
      nhapsach.thuthu
    );
  }

  protected loadRelationshipsOptions(): void {
    this.thuthuService
      .query()
      .pipe(map((res: HttpResponse<IThuthu[]>) => res.body ?? []))
      .pipe(
        map((thuthus: IThuthu[]) =>
          this.thuthuService.addThuthuToCollectionIfMissing(thuthus, this.editForm.get('thuthu')!.value, this.editForm.get('thuthu')!.value)
        )
      )
      .subscribe((thuthus: IThuthu[]) => (this.thuthusSharedCollection = thuthus));
  }

  protected createFromForm(): INhapsach {
    return {
      ...new Nhapsach(),
      id: this.editForm.get(['id'])!.value,
      ngayGioNhap: this.editForm.get(['ngayGioNhap'])!.value
        ? dayjs(this.editForm.get(['ngayGioNhap'])!.value, DATE_TIME_FORMAT)
        : undefined,
      soLuong: this.editForm.get(['soLuong'])!.value,
      thuthu: this.editForm.get(['thuthu'])!.value,
      thuthu: this.editForm.get(['thuthu'])!.value,
    };
  }
}
