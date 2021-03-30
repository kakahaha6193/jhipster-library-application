jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TheloaiService } from '../service/theloai.service';
import { ITheloai, Theloai } from '../theloai.model';

import { TheloaiUpdateComponent } from './theloai-update.component';

describe('Component Tests', () => {
  describe('Theloai Management Update Component', () => {
    let comp: TheloaiUpdateComponent;
    let fixture: ComponentFixture<TheloaiUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let theloaiService: TheloaiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TheloaiUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TheloaiUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TheloaiUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      theloaiService = TestBed.inject(TheloaiService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const theloai: ITheloai = { id: 456 };

        activatedRoute.data = of({ theloai });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(theloai));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const theloai = { id: 123 };
        spyOn(theloaiService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ theloai });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: theloai }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(theloaiService.update).toHaveBeenCalledWith(theloai);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const theloai = new Theloai();
        spyOn(theloaiService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ theloai });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: theloai }));
        saveSubject.complete();

        // THEN
        expect(theloaiService.create).toHaveBeenCalledWith(theloai);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const theloai = { id: 123 };
        spyOn(theloaiService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ theloai });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(theloaiService.update).toHaveBeenCalledWith(theloai);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
