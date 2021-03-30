jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { GiasachService } from '../service/giasach.service';
import { IGiasach, Giasach } from '../giasach.model';
import { IPhongdungsach } from 'app/entities/phongdungsach/phongdungsach.model';
import { PhongdungsachService } from 'app/entities/phongdungsach/service/phongdungsach.service';

import { GiasachUpdateComponent } from './giasach-update.component';

describe('Component Tests', () => {
  describe('Giasach Management Update Component', () => {
    let comp: GiasachUpdateComponent;
    let fixture: ComponentFixture<GiasachUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let giasachService: GiasachService;
    let phongdungsachService: PhongdungsachService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [GiasachUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(GiasachUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GiasachUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      giasachService = TestBed.inject(GiasachService);
      phongdungsachService = TestBed.inject(PhongdungsachService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Phongdungsach query and add missing value', () => {
        const giasach: IGiasach = { id: 456 };
        const phongdungsach: IPhongdungsach = { id: 72686 };
        giasach.phongdungsach = phongdungsach;

        const phongdungsachCollection: IPhongdungsach[] = [{ id: 30397 }];
        spyOn(phongdungsachService, 'query').and.returnValue(of(new HttpResponse({ body: phongdungsachCollection })));
        const additionalPhongdungsaches = [phongdungsach];
        const expectedCollection: IPhongdungsach[] = [...additionalPhongdungsaches, ...phongdungsachCollection];
        spyOn(phongdungsachService, 'addPhongdungsachToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ giasach });
        comp.ngOnInit();

        expect(phongdungsachService.query).toHaveBeenCalled();
        expect(phongdungsachService.addPhongdungsachToCollectionIfMissing).toHaveBeenCalledWith(
          phongdungsachCollection,
          ...additionalPhongdungsaches
        );
        expect(comp.phongdungsachesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const giasach: IGiasach = { id: 456 };
        const phongdungsach: IPhongdungsach = { id: 72620 };
        giasach.phongdungsach = phongdungsach;

        activatedRoute.data = of({ giasach });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(giasach));
        expect(comp.phongdungsachesSharedCollection).toContain(phongdungsach);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const giasach = { id: 123 };
        spyOn(giasachService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ giasach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: giasach }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(giasachService.update).toHaveBeenCalledWith(giasach);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const giasach = new Giasach();
        spyOn(giasachService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ giasach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: giasach }));
        saveSubject.complete();

        // THEN
        expect(giasachService.create).toHaveBeenCalledWith(giasach);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const giasach = { id: 123 };
        spyOn(giasachService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ giasach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(giasachService.update).toHaveBeenCalledWith(giasach);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackPhongdungsachById', () => {
        it('Should return tracked Phongdungsach primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPhongdungsachById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
