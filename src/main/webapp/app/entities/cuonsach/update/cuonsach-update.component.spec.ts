jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CuonsachService } from '../service/cuonsach.service';
import { ICuonsach, Cuonsach } from '../cuonsach.model';
import { ISach } from 'app/entities/sach/sach.model';
import { SachService } from 'app/entities/sach/service/sach.service';
import { IMuonsach } from 'app/entities/muonsach/muonsach.model';
import { MuonsachService } from 'app/entities/muonsach/service/muonsach.service';

import { CuonsachUpdateComponent } from './cuonsach-update.component';

describe('Component Tests', () => {
  describe('Cuonsach Management Update Component', () => {
    let comp: CuonsachUpdateComponent;
    let fixture: ComponentFixture<CuonsachUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let cuonsachService: CuonsachService;
    let sachService: SachService;
    let muonsachService: MuonsachService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CuonsachUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CuonsachUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CuonsachUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      cuonsachService = TestBed.inject(CuonsachService);
      sachService = TestBed.inject(SachService);
      muonsachService = TestBed.inject(MuonsachService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Sach query and add missing value', () => {
        const cuonsach: ICuonsach = { id: 456 };
        const sach: ISach = { id: 89518 };
        cuonsach.sach = sach;

        const sachCollection: ISach[] = [{ id: 94660 }];
        spyOn(sachService, 'query').and.returnValue(of(new HttpResponse({ body: sachCollection })));
        const additionalSaches = [sach];
        const expectedCollection: ISach[] = [...additionalSaches, ...sachCollection];
        spyOn(sachService, 'addSachToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ cuonsach });
        comp.ngOnInit();

        expect(sachService.query).toHaveBeenCalled();
        expect(sachService.addSachToCollectionIfMissing).toHaveBeenCalledWith(sachCollection, ...additionalSaches);
        expect(comp.sachesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Muonsach query and add missing value', () => {
        const cuonsach: ICuonsach = { id: 456 };
        const muonsach: IMuonsach = { id: 34233 };
        cuonsach.muonsach = muonsach;

        const muonsachCollection: IMuonsach[] = [{ id: 62244 }];
        spyOn(muonsachService, 'query').and.returnValue(of(new HttpResponse({ body: muonsachCollection })));
        const additionalMuonsaches = [muonsach];
        const expectedCollection: IMuonsach[] = [...additionalMuonsaches, ...muonsachCollection];
        spyOn(muonsachService, 'addMuonsachToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ cuonsach });
        comp.ngOnInit();

        expect(muonsachService.query).toHaveBeenCalled();
        expect(muonsachService.addMuonsachToCollectionIfMissing).toHaveBeenCalledWith(muonsachCollection, ...additionalMuonsaches);
        expect(comp.muonsachesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const cuonsach: ICuonsach = { id: 456 };
        const sach: ISach = { id: 33342 };
        cuonsach.sach = sach;
        const muonsach: IMuonsach = { id: 46690 };
        cuonsach.muonsach = muonsach;

        activatedRoute.data = of({ cuonsach });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(cuonsach));
        expect(comp.sachesSharedCollection).toContain(sach);
        expect(comp.muonsachesSharedCollection).toContain(muonsach);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cuonsach = { id: 123 };
        spyOn(cuonsachService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cuonsach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cuonsach }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(cuonsachService.update).toHaveBeenCalledWith(cuonsach);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cuonsach = new Cuonsach();
        spyOn(cuonsachService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cuonsach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cuonsach }));
        saveSubject.complete();

        // THEN
        expect(cuonsachService.create).toHaveBeenCalledWith(cuonsach);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cuonsach = { id: 123 };
        spyOn(cuonsachService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cuonsach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(cuonsachService.update).toHaveBeenCalledWith(cuonsach);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackSachById', () => {
        it('Should return tracked Sach primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackSachById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackMuonsachById', () => {
        it('Should return tracked Muonsach primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackMuonsachById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
