import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISach, Sach } from '../sach.model';
import { SachService } from '../service/sach.service';
import { ITheloai } from 'app/entities/theloai/theloai.model';
import { TheloaiService } from 'app/entities/theloai/service/theloai.service';
import { INhaxuatban } from 'app/entities/nhaxuatban/nhaxuatban.model';
import { NhaxuatbanService } from 'app/entities/nhaxuatban/service/nhaxuatban.service';
import { IGiasach } from 'app/entities/giasach/giasach.model';
import { GiasachService } from 'app/entities/giasach/service/giasach.service';
import { INhapsach } from 'app/entities/nhapsach/nhapsach.model';
import { NhapsachService } from 'app/entities/nhapsach/service/nhapsach.service';

@Component({
  selector: 'jhi-sach-update',
  templateUrl: './sach-update.component.html',
})
export class SachUpdateComponent implements OnInit {
  isSaving = false;

  theloaisSharedCollection: ITheloai[] = [];
  nhaxuatbansSharedCollection: INhaxuatban[] = [];
  giasachesSharedCollection: IGiasach[] = [];
  nhapsachesSharedCollection: INhapsach[] = [];

  editForm = this.fb.group({
    id: [],
    tenSach: [],
    giaNiemYet: [],
    tacgia: [],
    giaThue: [],
    nganXep: [],
    theloai: [],
    nhaxuatban: [],
    giasach: [],
    nhapsaches: [],
  });

  constructor(
    protected sachService: SachService,
    protected theloaiService: TheloaiService,
    protected nhaxuatbanService: NhaxuatbanService,
    protected giasachService: GiasachService,
    protected nhapsachService: NhapsachService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sach }) => {
      this.updateForm(sach);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sach = this.createFromForm();
    if (sach.id !== undefined) {
      this.subscribeToSaveResponse(this.sachService.update(sach));
    } else {
      this.subscribeToSaveResponse(this.sachService.create(sach));
    }
  }

  trackTheloaiById(index: number, item: ITheloai): number {
    return item.id!;
  }

  trackNhaxuatbanById(index: number, item: INhaxuatban): number {
    return item.id!;
  }

  trackGiasachById(index: number, item: IGiasach): number {
    return item.id!;
  }

  trackNhapsachById(index: number, item: INhapsach): number {
    return item.id!;
  }

  getSelectedNhapsach(option: INhapsach, selectedVals?: INhapsach[]): INhapsach {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISach>>): void {
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

  protected updateForm(sach: ISach): void {
    this.editForm.patchValue({
      id: sach.id,
      tenSach: sach.tenSach,
      giaNiemYet: sach.giaNiemYet,
      tacgia: sach.tacgia,
      giaThue: sach.giaThue,
      nganXep: sach.nganXep,
      theloai: sach.theloai,
      nhaxuatban: sach.nhaxuatban,
      giasach: sach.giasach,
      nhapsaches: sach.nhapsaches,
    });

    this.theloaisSharedCollection = this.theloaiService.addTheloaiToCollectionIfMissing(this.theloaisSharedCollection, sach.theloai);
    this.nhaxuatbansSharedCollection = this.nhaxuatbanService.addNhaxuatbanToCollectionIfMissing(
      this.nhaxuatbansSharedCollection,
      sach.nhaxuatban
    );
    this.giasachesSharedCollection = this.giasachService.addGiasachToCollectionIfMissing(this.giasachesSharedCollection, sach.giasach);
    this.nhapsachesSharedCollection = this.nhapsachService.addNhapsachToCollectionIfMissing(
      this.nhapsachesSharedCollection,
      ...(sach.nhapsaches ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.theloaiService
      .query()
      .pipe(map((res: HttpResponse<ITheloai[]>) => res.body ?? []))
      .pipe(
        map((theloais: ITheloai[]) => this.theloaiService.addTheloaiToCollectionIfMissing(theloais, this.editForm.get('theloai')!.value))
      )
      .subscribe((theloais: ITheloai[]) => (this.theloaisSharedCollection = theloais));

    this.nhaxuatbanService
      .query()
      .pipe(map((res: HttpResponse<INhaxuatban[]>) => res.body ?? []))
      .pipe(
        map((nhaxuatbans: INhaxuatban[]) =>
          this.nhaxuatbanService.addNhaxuatbanToCollectionIfMissing(nhaxuatbans, this.editForm.get('nhaxuatban')!.value)
        )
      )
      .subscribe((nhaxuatbans: INhaxuatban[]) => (this.nhaxuatbansSharedCollection = nhaxuatbans));

    this.giasachService
      .query()
      .pipe(map((res: HttpResponse<IGiasach[]>) => res.body ?? []))
      .pipe(
        map((giasaches: IGiasach[]) => this.giasachService.addGiasachToCollectionIfMissing(giasaches, this.editForm.get('giasach')!.value))
      )
      .subscribe((giasaches: IGiasach[]) => (this.giasachesSharedCollection = giasaches));

    this.nhapsachService
      .query()
      .pipe(map((res: HttpResponse<INhapsach[]>) => res.body ?? []))
      .pipe(
        map((nhapsaches: INhapsach[]) =>
          this.nhapsachService.addNhapsachToCollectionIfMissing(nhapsaches, ...(this.editForm.get('nhapsaches')!.value ?? []))
        )
      )
      .subscribe((nhapsaches: INhapsach[]) => (this.nhapsachesSharedCollection = nhapsaches));
  }

  protected createFromForm(): ISach {
    return {
      ...new Sach(),
      id: this.editForm.get(['id'])!.value,
      tenSach: this.editForm.get(['tenSach'])!.value,
      giaNiemYet: this.editForm.get(['giaNiemYet'])!.value,
      tacgia: this.editForm.get(['tacgia'])!.value,
      giaThue: this.editForm.get(['giaThue'])!.value,
      nganXep: this.editForm.get(['nganXep'])!.value,
      theloai: this.editForm.get(['theloai'])!.value,
      nhaxuatban: this.editForm.get(['nhaxuatban'])!.value,
      giasach: this.editForm.get(['giasach'])!.value,
      nhapsaches: this.editForm.get(['nhapsaches'])!.value,
    };
  }
}
