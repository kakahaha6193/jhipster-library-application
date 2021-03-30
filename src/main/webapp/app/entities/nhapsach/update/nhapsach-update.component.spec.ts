jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NhapsachService } from '../service/nhapsach.service';
import { INhapsach, Nhapsach } from '../nhapsach.model';
import { IThuthu } from 'app/entities/thuthu/thuthu.model';
import { ThuthuService } from 'app/entities/thuthu/service/thuthu.service';

import { NhapsachUpdateComponent } from './nhapsach-update.component';

describe('Component Tests', () => {
  describe('Nhapsach Management Update Component', () => {
    let comp: NhapsachUpdateComponent;
    let fixture: ComponentFixture<NhapsachUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let nhapsachService: NhapsachService;
    let thuthuService: ThuthuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NhapsachUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(NhapsachUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NhapsachUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      nhapsachService = TestBed.inject(NhapsachService);
      thuthuService = TestBed.inject(ThuthuService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Thuthu query and add missing value', () => {
        const nhapsach: INhapsach = { id: 456 };
        const thuthu: IThuthu = { id: 16058 };
        nhapsach.thuthu = thuthu;
        const thuthu: IThuthu = { id: 54006 };
        nhapsach.thuthu = thuthu;

        const thuthuCollection: IThuthu[] = [{ id: 12788 }];
        spyOn(thuthuService, 'query').and.returnValue(of(new HttpResponse({ body: thuthuCollection })));
        const additionalThuthus = [thuthu, thuthu];
        const expectedCollection: IThuthu[] = [...additionalThuthus, ...thuthuCollection];
        spyOn(thuthuService, 'addThuthuToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ nhapsach });
        comp.ngOnInit();

        expect(thuthuService.query).toHaveBeenCalled();
        expect(thuthuService.addThuthuToCollectionIfMissing).toHaveBeenCalledWith(thuthuCollection, ...additionalThuthus);
        expect(comp.thuthusSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const nhapsach: INhapsach = { id: 456 };
        const thuthu: IThuthu = { id: 98278 };
        nhapsach.thuthu = thuthu;
        const thuthu: IThuthu = { id: 68606 };
        nhapsach.thuthu = thuthu;

        activatedRoute.data = of({ nhapsach });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(nhapsach));
        expect(comp.thuthusSharedCollection).toContain(thuthu);
        expect(comp.thuthusSharedCollection).toContain(thuthu);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const nhapsach = { id: 123 };
        spyOn(nhapsachService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ nhapsach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: nhapsach }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(nhapsachService.update).toHaveBeenCalledWith(nhapsach);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const nhapsach = new Nhapsach();
        spyOn(nhapsachService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ nhapsach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: nhapsach }));
        saveSubject.complete();

        // THEN
        expect(nhapsachService.create).toHaveBeenCalledWith(nhapsach);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const nhapsach = { id: 123 };
        spyOn(nhapsachService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ nhapsach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(nhapsachService.update).toHaveBeenCalledWith(nhapsach);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackThuthuById', () => {
        it('Should return tracked Thuthu primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackThuthuById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
