jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PhongdungsachService } from '../service/phongdungsach.service';
import { IPhongdungsach, Phongdungsach } from '../phongdungsach.model';

import { PhongdungsachUpdateComponent } from './phongdungsach-update.component';

describe('Component Tests', () => {
  describe('Phongdungsach Management Update Component', () => {
    let comp: PhongdungsachUpdateComponent;
    let fixture: ComponentFixture<PhongdungsachUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let phongdungsachService: PhongdungsachService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PhongdungsachUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PhongdungsachUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhongdungsachUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      phongdungsachService = TestBed.inject(PhongdungsachService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const phongdungsach: IPhongdungsach = { id: 456 };

        activatedRoute.data = of({ phongdungsach });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(phongdungsach));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const phongdungsach = { id: 123 };
        spyOn(phongdungsachService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ phongdungsach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: phongdungsach }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(phongdungsachService.update).toHaveBeenCalledWith(phongdungsach);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const phongdungsach = new Phongdungsach();
        spyOn(phongdungsachService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ phongdungsach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: phongdungsach }));
        saveSubject.complete();

        // THEN
        expect(phongdungsachService.create).toHaveBeenCalledWith(phongdungsach);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const phongdungsach = { id: 123 };
        spyOn(phongdungsachService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ phongdungsach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(phongdungsachService.update).toHaveBeenCalledWith(phongdungsach);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
