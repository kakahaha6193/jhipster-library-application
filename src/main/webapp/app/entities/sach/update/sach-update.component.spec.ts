jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SachService } from '../service/sach.service';
import { ISach, Sach } from '../sach.model';
import { ITheloai } from 'app/entities/theloai/theloai.model';
import { TheloaiService } from 'app/entities/theloai/service/theloai.service';
import { INhaxuatban } from 'app/entities/nhaxuatban/nhaxuatban.model';
import { NhaxuatbanService } from 'app/entities/nhaxuatban/service/nhaxuatban.service';
import { IGiasach } from 'app/entities/giasach/giasach.model';
import { GiasachService } from 'app/entities/giasach/service/giasach.service';
import { INhapsach } from 'app/entities/nhapsach/nhapsach.model';
import { NhapsachService } from 'app/entities/nhapsach/service/nhapsach.service';

import { SachUpdateComponent } from './sach-update.component';

describe('Component Tests', () => {
  describe('Sach Management Update Component', () => {
    let comp: SachUpdateComponent;
    let fixture: ComponentFixture<SachUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let sachService: SachService;
    let theloaiService: TheloaiService;
    let nhaxuatbanService: NhaxuatbanService;
    let giasachService: GiasachService;
    let nhapsachService: NhapsachService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SachUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(SachUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SachUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      sachService = TestBed.inject(SachService);
      theloaiService = TestBed.inject(TheloaiService);
      nhaxuatbanService = TestBed.inject(NhaxuatbanService);
      giasachService = TestBed.inject(GiasachService);
      nhapsachService = TestBed.inject(NhapsachService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Theloai query and add missing value', () => {
        const sach: ISach = { id: 456 };
        const theloai: ITheloai = { id: 4024 };
        sach.theloai = theloai;

        const theloaiCollection: ITheloai[] = [{ id: 43867 }];
        spyOn(theloaiService, 'query').and.returnValue(of(new HttpResponse({ body: theloaiCollection })));
        const additionalTheloais = [theloai];
        const expectedCollection: ITheloai[] = [...additionalTheloais, ...theloaiCollection];
        spyOn(theloaiService, 'addTheloaiToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ sach });
        comp.ngOnInit();

        expect(theloaiService.query).toHaveBeenCalled();
        expect(theloaiService.addTheloaiToCollectionIfMissing).toHaveBeenCalledWith(theloaiCollection, ...additionalTheloais);
        expect(comp.theloaisSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Nhaxuatban query and add missing value', () => {
        const sach: ISach = { id: 456 };
        const nhaxuatban: INhaxuatban = { id: 18654 };
        sach.nhaxuatban = nhaxuatban;

        const nhaxuatbanCollection: INhaxuatban[] = [{ id: 22848 }];
        spyOn(nhaxuatbanService, 'query').and.returnValue(of(new HttpResponse({ body: nhaxuatbanCollection })));
        const additionalNhaxuatbans = [nhaxuatban];
        const expectedCollection: INhaxuatban[] = [...additionalNhaxuatbans, ...nhaxuatbanCollection];
        spyOn(nhaxuatbanService, 'addNhaxuatbanToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ sach });
        comp.ngOnInit();

        expect(nhaxuatbanService.query).toHaveBeenCalled();
        expect(nhaxuatbanService.addNhaxuatbanToCollectionIfMissing).toHaveBeenCalledWith(nhaxuatbanCollection, ...additionalNhaxuatbans);
        expect(comp.nhaxuatbansSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Giasach query and add missing value', () => {
        const sach: ISach = { id: 456 };
        const giasach: IGiasach = { id: 30596 };
        sach.giasach = giasach;

        const giasachCollection: IGiasach[] = [{ id: 56464 }];
        spyOn(giasachService, 'query').and.returnValue(of(new HttpResponse({ body: giasachCollection })));
        const additionalGiasaches = [giasach];
        const expectedCollection: IGiasach[] = [...additionalGiasaches, ...giasachCollection];
        spyOn(giasachService, 'addGiasachToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ sach });
        comp.ngOnInit();

        expect(giasachService.query).toHaveBeenCalled();
        expect(giasachService.addGiasachToCollectionIfMissing).toHaveBeenCalledWith(giasachCollection, ...additionalGiasaches);
        expect(comp.giasachesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Nhapsach query and add missing value', () => {
        const sach: ISach = { id: 456 };
        const nhapsaches: INhapsach[] = [{ id: 47112 }];
        sach.nhapsaches = nhapsaches;

        const nhapsachCollection: INhapsach[] = [{ id: 93931 }];
        spyOn(nhapsachService, 'query').and.returnValue(of(new HttpResponse({ body: nhapsachCollection })));
        const additionalNhapsaches = [...nhapsaches];
        const expectedCollection: INhapsach[] = [...additionalNhapsaches, ...nhapsachCollection];
        spyOn(nhapsachService, 'addNhapsachToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ sach });
        comp.ngOnInit();

        expect(nhapsachService.query).toHaveBeenCalled();
        expect(nhapsachService.addNhapsachToCollectionIfMissing).toHaveBeenCalledWith(nhapsachCollection, ...additionalNhapsaches);
        expect(comp.nhapsachesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const sach: ISach = { id: 456 };
        const theloai: ITheloai = { id: 91455 };
        sach.theloai = theloai;
        const nhaxuatban: INhaxuatban = { id: 62029 };
        sach.nhaxuatban = nhaxuatban;
        const giasach: IGiasach = { id: 73238 };
        sach.giasach = giasach;
        const nhapsaches: INhapsach = { id: 25696 };
        sach.nhapsaches = [nhapsaches];

        activatedRoute.data = of({ sach });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(sach));
        expect(comp.theloaisSharedCollection).toContain(theloai);
        expect(comp.nhaxuatbansSharedCollection).toContain(nhaxuatban);
        expect(comp.giasachesSharedCollection).toContain(giasach);
        expect(comp.nhapsachesSharedCollection).toContain(nhapsaches);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const sach = { id: 123 };
        spyOn(sachService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ sach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: sach }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(sachService.update).toHaveBeenCalledWith(sach);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const sach = new Sach();
        spyOn(sachService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ sach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: sach }));
        saveSubject.complete();

        // THEN
        expect(sachService.create).toHaveBeenCalledWith(sach);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const sach = { id: 123 };
        spyOn(sachService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ sach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(sachService.update).toHaveBeenCalledWith(sach);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackTheloaiById', () => {
        it('Should return tracked Theloai primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTheloaiById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackNhaxuatbanById', () => {
        it('Should return tracked Nhaxuatban primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackNhaxuatbanById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackGiasachById', () => {
        it('Should return tracked Giasach primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackGiasachById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackNhapsachById', () => {
        it('Should return tracked Nhapsach primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackNhapsachById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedNhapsach', () => {
        it('Should return option if no Nhapsach is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedNhapsach(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Nhapsach for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedNhapsach(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Nhapsach is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedNhapsach(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
