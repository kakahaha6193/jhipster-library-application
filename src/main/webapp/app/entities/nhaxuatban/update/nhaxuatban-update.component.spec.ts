jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NhaxuatbanService } from '../service/nhaxuatban.service';
import { INhaxuatban, Nhaxuatban } from '../nhaxuatban.model';

import { NhaxuatbanUpdateComponent } from './nhaxuatban-update.component';

describe('Component Tests', () => {
  describe('Nhaxuatban Management Update Component', () => {
    let comp: NhaxuatbanUpdateComponent;
    let fixture: ComponentFixture<NhaxuatbanUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let nhaxuatbanService: NhaxuatbanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NhaxuatbanUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(NhaxuatbanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NhaxuatbanUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      nhaxuatbanService = TestBed.inject(NhaxuatbanService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const nhaxuatban: INhaxuatban = { id: 456 };

        activatedRoute.data = of({ nhaxuatban });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(nhaxuatban));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const nhaxuatban = { id: 123 };
        spyOn(nhaxuatbanService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ nhaxuatban });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: nhaxuatban }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(nhaxuatbanService.update).toHaveBeenCalledWith(nhaxuatban);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const nhaxuatban = new Nhaxuatban();
        spyOn(nhaxuatbanService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ nhaxuatban });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: nhaxuatban }));
        saveSubject.complete();

        // THEN
        expect(nhaxuatbanService.create).toHaveBeenCalledWith(nhaxuatban);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const nhaxuatban = { id: 123 };
        spyOn(nhaxuatbanService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ nhaxuatban });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(nhaxuatbanService.update).toHaveBeenCalledWith(nhaxuatban);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
