import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ICuonsach, Cuonsach } from '../cuonsach.model';
import { CuonsachService } from '../service/cuonsach.service';
import { ISach } from 'app/entities/sach/sach.model';
import { SachService } from 'app/entities/sach/service/sach.service';
import { IMuonsach } from 'app/entities/muonsach/muonsach.model';
import { MuonsachService } from 'app/entities/muonsach/service/muonsach.service';

@Component({
  selector: 'jhi-cuonsach-update',
  templateUrl: './cuonsach-update.component.html',
})
export class CuonsachUpdateComponent implements OnInit {
  isSaving = false;

  sachesSharedCollection: ISach[] = [];
  muonsachesSharedCollection: IMuonsach[] = [];

  editForm = this.fb.group({
    id: [],
    ngayHetHan: [],
    trangThai: [],
    sach: [],
    muonsach: [],
  });

  constructor(
    protected cuonsachService: CuonsachService,
    protected sachService: SachService,
    protected muonsachService: MuonsachService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cuonsach }) => {
      if (cuonsach.id === undefined) {
        const today = dayjs().startOf('day');
        cuonsach.ngayHetHan = today;
      }

      this.updateForm(cuonsach);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cuonsach = this.createFromForm();
    if (cuonsach.id !== undefined) {
      this.subscribeToSaveResponse(this.cuonsachService.update(cuonsach));
    } else {
      this.subscribeToSaveResponse(this.cuonsachService.create(cuonsach));
    }
  }

  trackSachById(index: number, item: ISach): number {
    return item.id!;
  }

  trackMuonsachById(index: number, item: IMuonsach): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICuonsach>>): void {
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

  protected updateForm(cuonsach: ICuonsach): void {
    this.editForm.patchValue({
      id: cuonsach.id,
      ngayHetHan: cuonsach.ngayHetHan ? cuonsach.ngayHetHan.format(DATE_TIME_FORMAT) : null,
      trangThai: cuonsach.trangThai,
      sach: cuonsach.sach,
      muonsach: cuonsach.muonsach,
    });

    this.sachesSharedCollection = this.sachService.addSachToCollectionIfMissing(this.sachesSharedCollection, cuonsach.sach);
    this.muonsachesSharedCollection = this.muonsachService.addMuonsachToCollectionIfMissing(
      this.muonsachesSharedCollection,
      cuonsach.muonsach
    );
  }

  protected loadRelationshipsOptions(): void {
    this.sachService
      .query()
      .pipe(map((res: HttpResponse<ISach[]>) => res.body ?? []))
      .pipe(map((saches: ISach[]) => this.sachService.addSachToCollectionIfMissing(saches, this.editForm.get('sach')!.value)))
      .subscribe((saches: ISach[]) => (this.sachesSharedCollection = saches));

    this.muonsachService
      .query()
      .pipe(map((res: HttpResponse<IMuonsach[]>) => res.body ?? []))
      .pipe(
        map((muonsaches: IMuonsach[]) =>
          this.muonsachService.addMuonsachToCollectionIfMissing(muonsaches, this.editForm.get('muonsach')!.value)
        )
      )
      .subscribe((muonsaches: IMuonsach[]) => (this.muonsachesSharedCollection = muonsaches));
  }

  protected createFromForm(): ICuonsach {
    return {
      ...new Cuonsach(),
      id: this.editForm.get(['id'])!.value,
      ngayHetHan: this.editForm.get(['ngayHetHan'])!.value ? dayjs(this.editForm.get(['ngayHetHan'])!.value, DATE_TIME_FORMAT) : undefined,
      trangThai: this.editForm.get(['trangThai'])!.value,
      sach: this.editForm.get(['sach'])!.value,
      muonsach: this.editForm.get(['muonsach'])!.value,
    };
  }
}
