jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ThuthuService } from '../service/thuthu.service';
import { IThuthu, Thuthu } from '../thuthu.model';

import { ThuthuUpdateComponent } from './thuthu-update.component';

describe('Component Tests', () => {
  describe('Thuthu Management Update Component', () => {
    let comp: ThuthuUpdateComponent;
    let fixture: ComponentFixture<ThuthuUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let thuthuService: ThuthuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ThuthuUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ThuthuUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ThuthuUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      thuthuService = TestBed.inject(ThuthuService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const thuthu: IThuthu = { id: 456 };

        activatedRoute.data = of({ thuthu });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(thuthu));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const thuthu = { id: 123 };
        spyOn(thuthuService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ thuthu });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: thuthu }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(thuthuService.update).toHaveBeenCalledWith(thuthu);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const thuthu = new Thuthu();
        spyOn(thuthuService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ thuthu });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: thuthu }));
        saveSubject.complete();

        // THEN
        expect(thuthuService.create).toHaveBeenCalledWith(thuthu);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const thuthu = { id: 123 };
        spyOn(thuthuService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ thuthu });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(thuthuService.update).toHaveBeenCalledWith(thuthu);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
