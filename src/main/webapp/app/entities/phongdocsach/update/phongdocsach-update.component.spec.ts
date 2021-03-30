jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PhongdocsachService } from '../service/phongdocsach.service';
import { IPhongdocsach, Phongdocsach } from '../phongdocsach.model';

import { PhongdocsachUpdateComponent } from './phongdocsach-update.component';

describe('Component Tests', () => {
  describe('Phongdocsach Management Update Component', () => {
    let comp: PhongdocsachUpdateComponent;
    let fixture: ComponentFixture<PhongdocsachUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let phongdocsachService: PhongdocsachService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PhongdocsachUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PhongdocsachUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhongdocsachUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      phongdocsachService = TestBed.inject(PhongdocsachService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const phongdocsach: IPhongdocsach = { id: 456 };

        activatedRoute.data = of({ phongdocsach });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(phongdocsach));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const phongdocsach = { id: 123 };
        spyOn(phongdocsachService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ phongdocsach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: phongdocsach }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(phongdocsachService.update).toHaveBeenCalledWith(phongdocsach);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const phongdocsach = new Phongdocsach();
        spyOn(phongdocsachService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ phongdocsach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: phongdocsach }));
        saveSubject.complete();

        // THEN
        expect(phongdocsachService.create).toHaveBeenCalledWith(phongdocsach);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const phongdocsach = { id: 123 };
        spyOn(phongdocsachService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ phongdocsach });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(phongdocsachService.update).toHaveBeenCalledWith(phongdocsach);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
